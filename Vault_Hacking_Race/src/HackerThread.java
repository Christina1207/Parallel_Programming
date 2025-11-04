public abstract class HackerThread extends Thread{
    protected Vault vault;
    public HackerThread(Vault vault)
    {
        this.vault=vault;
    }

    protected void foundPassword(int guess) {
        System.out.println("-------------------------------------------------");
        System.out.println(this.getName() + " found the password! It was " + guess);
        System.out.println("HACKERS WIN!");
        System.out.println("-------------------------------------------------");

        System.exit(0);
    }
    @Override
    public abstract void run();

}
