public class AscendingHackerThread extends HackerThread {

    public AscendingHackerThread(Vault vault){
        super(vault);
        this.setName("AscendingHacker");
    }

    @Override
    public void run() {
        for(int guess=0 ; guess<=vault.MAX_PASS; guess++)
        {
            if(vault.isCorrectPassword(guess)){
                foundPassword(guess);
            }
        }
    }
}
