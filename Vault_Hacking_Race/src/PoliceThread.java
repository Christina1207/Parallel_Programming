public class PoliceThread extends Thread {
    public PoliceThread()
    {
        this.setName("Police");
    }

    @Override
    public void run()
    {
        for(int i=10;i>0;i--)
        {
            try{
                Thread.sleep(1000);
                System.out.println(i + " seconds remaining until police arrive...");
            }
            catch   (InterruptedException e)
            {

            }

        }
        System.out.println("-------------------------------------------------");
        System.out.println("Police have arrived!");
        System.out.println("Game over for you hackers. POLICE WIN!");
        System.out.println("-------------------------------------------------");

        System.exit(0);
    }
}
