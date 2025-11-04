public class DescendingHackerThread extends HackerThread{
    public DescendingHackerThread(Vault vault)
    {
        super(vault);
        this.setName("DescendingHacker");
    }

    @Override
    public void run() {
        for (int guess=vault.MAX_PASS; guess>=0;guess--)
        {
            if(vault.isCorrectPassword(guess))
            {
                foundPassword(guess);
            }
        }
    }
}
