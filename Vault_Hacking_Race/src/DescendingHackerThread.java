public class DescendingHackerThread extends Thread{
    private Vault vault;
    public DescendingHackerThread(Vault vault)
    {
        this.vault=vault;
        this.setName("DescendingHacker");
    }

    @Override
    public void run() {
        for (int guess=vault.MAX_PASS; guess>=0;guess--)
        {
            if(vault.isCorrectPassword(guess))
            {
                System.out.println("-------------------------------------------------");
                System.out.println(this.getName() + " found the password! It was " + guess);
                System.out.println("HACKERS WIN!");
                System.out.println("-------------------------------------------------");

                System.exit(0);
            }
        }
    }
}
