public class MainProgram {
    public static void main(String[] args){

        //Creating Vault with random pass
        Vault vault = new Vault();

        //Creating the 3 threads
        Thread ascendingHacker = new AscendingHackerThread(vault);
        Thread descendingHacker = new DescendingHackerThread(vault);
        Thread police = new PoliceThread();

        // setting hacker's priorities
        ascendingHacker.setPriority(Thread.MAX_PRIORITY);
        descendingHacker.setPriority(Thread.MAX_PRIORITY);

        // launching all three threads
        ascendingHacker.start();
        descendingHacker.start();
        police.start();
    }
}
