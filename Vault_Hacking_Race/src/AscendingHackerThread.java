public class AscendingHackerThread extends Thread {

    private Vault vault;

    public AscendingHackerThread(Vault vault){
        this.vault=vault;
        this.setName("AscendingHacker");
    }

    @Override
    public void run() {
        for(int guess=0 ; guess<=vault.MAX_PASS; guess++)
        {
            if(vault.isCorrectPassword(guess)){
                System.out.println("-------------------------------------------------");
                System.out.println(this.getName() + " found the password! It was " + guess);
                System.out.println("HACKERS WIN!");
                System.out.println("-------------------------------------------------");

                System.exit(0);
            }
        }
    }
}
