import java.util.Scanner;
public class RingAlgorithm {
     public static void main(String [] args){
          
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the total number of all processes :");
        int numberOfProcesses = sc.nextInt();

        Process[] processes =new Process[numberOfProcesses];

        for(int i=0;i<numberOfProcesses;i++){
            processes[i]=new Process(i);
        }

        for(int i=0;i<numberOfProcesses;i++){
            System.out.println("Enter the ID for the process "+(i+1)+": ");
            processes[i].id=sc.nextInt();
        }

        for (int i=0;i<numberOfProcesses-1;i++){
            for(int j=0;j<numberOfProcesses-i-1;j++){
                if(processes[j].id > processes[j+1].id){
                    Process temp=processes[j];
                    processes[j]=processes[j+1];
                    processes[j+1]=temp;
                }
            }
        }

        System.out.println("Processes in the sorted order ");
        for (Process p:processes){
            System.out.println(p.id);
        }


        while(true)
        {
            System.out.println("1. Initate the election.");
            System.out.println("2.Quit");
            int choice=sc.nextInt();

            switch(choice){
                case 1 : {
                    System.out.println("Enter the index of a process which is initiating the election.");
                    int initiatorIndex=sc.nextInt();
                    if(initiatorIndex>=0 && initiatorIndex<numberOfProcesses){
                        initiateElection(processes,initiatorIndex,numberOfProcesses);
                    } else {
                        System.out.println("Invalid Process Index");
                    }
                break; }

                case 2 : {
                    System.out.println("The program is terminated");
                    break;
                }
            }
        }
     }


     public static void initiateElection(Process[] processes, int initiatorIndex,int numberOfProcesses){
        resetElectionFlags(processes);

        int highesID=processes[initiatorIndex].id;
        int currentIndex=initiatorIndex;
        int nextIndex=(currentIndex+1)%numberOfProcesses;

        processes[initiatorIndex].hasSentMessage= true;

        while(nextIndex!=initiatorIndex){
            System.out.println("Process"+processes[currentIndex].id+"has sent message to "+processes[nextIndex].id);
            if(processes[nextIndex].id >highesID){
                highesID=processes[nextIndex].id;
            }

            currentIndex=nextIndex;
            nextIndex=(currentIndex+1)%numberOfProcesses;
        }

        System.out.println("Process"+processes[currentIndex].id+"has sent message to "+processes[nextIndex].id);


        for(Process p:processes){
           if( p.id==highesID){
            p.state="coordinator";
            System.out.println("Process"+(p.index+1)+"(ID"+p.id+") becomes the new coordinator");
           } else {
            p.state="inactive";
           }
        }


    }


    public static void resetElectionFlags(Process[] processes){
        for(Process p:processes){
            p.hasSentMessage=false;
            p.state="active";
        }
    }
}

class Process {
    public int id;
    public boolean hasSentMessage;
    public String state;
    public int index;

    public Process(int index){
        this.index=index;
    }
}
