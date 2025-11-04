import java.util.Random;
public class Vault {
    private int password;
    public static final int MAX_PASS=9999;
    public Vault()
    {
        this.password=new Random().nextInt(MAX_PASS+1);
        System.out.println("Vault created, The password is : "+this.password);
    }

    // method to check a guess against the vault's password
    public boolean isCorrectPassword(int guess){
        try{
            Thread.sleep(5);
        }
        catch(InterruptedException e){

        }
        return guess==this.password;
    }
}
