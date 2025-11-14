import junit.framework.TestCase;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;

public class SyncListTest extends TestCase {

    public void testAddList(){

        SyncList syncList = new SyncList();
//        syncList.remove(Integer.MAX_VALUE);
        syncList.add(1);
        syncList.add(2);
        syncList.add(3);
        syncList.add(Integer.MIN_VALUE);
        syncList.add(3);
        System.out.println(syncList.contain(5));
        System.out.println(syncList.contain(2));
        syncList.remove(3);
    }

    public void testRandSeq() {
        RandomSeq randomSeq = new RandomSeq(0, 80_000);
        for (int i = 0; i < 10; i++) {
            System.out.print(randomSeq.next() + " ");
        }
    }

    int randLen = 80_000;
    int numOfThreads = 16;
   public void testHelp(SortList list, String label) {
        RandomSeq seq = new RandomSeq(0, 80_000);

       List<AddThread> addThreads = new ArrayList<>();
       List<ContainThread> containThreads = new ArrayList<>();
       List<RemoveThread> removeThreads = new ArrayList<>();
        // Aggregated metrics across all threads
        final int[] totalContainSucessesCount = {0};
        final int[] totalContainFailureCount = {0};
        final int[] totalRemoveSucessesCount = {0};
        final int[] totalRemoveFailureCount = {0};

       int partSize = randLen / numOfThreads;

        for (int i = 0; i < numOfThreads; i++) {
            AddThread addThread = new AddThread(seq, randLen / numOfThreads, list);
            ContainThread containThread = new ContainThread(seq, randLen / numOfThreads, list);
            RemoveThread removeThread = new RemoveThread(seq, randLen / numOfThreads, list);
            addThreads.add(addThread);
            containThreads.add(containThread);
            removeThreads.add(removeThread);
        }

        //Phase 1 : Add operations
       long startA = System.currentTimeMillis();

       addThreads.stream().forEach(e -> e.start() );
       addThreads.stream().forEach(e -> {
           try {
               e.join();
           } catch (InterruptedException ex) {
               throw new RuntimeException(ex);
           }
       });
       long endA = System.currentTimeMillis() - startA;

        System.out.println("ADD "+label+" execution task: "+endA+" ms");
       int listLengthAfterAdds = list.getListSize();
       System.out.println("   List Sorted (after Add) = " + list.isSorted());
       System.out.println("   listLengthAfterAdds = " + listLengthAfterAdds);


       long startC = System.currentTimeMillis();

       containThreads.stream().forEach(e -> e.start() );
       containThreads.stream().forEach(e -> {
           try {
               e.join();
               totalContainSucessesCount[0] += e.getSuccessCount();
               totalContainFailureCount[0] += e.getFailureCount();
           } catch (InterruptedException ex) {
               throw new RuntimeException(ex);
           }
       });
       long endC = System.currentTimeMillis() - startC;
       System.out.println("Contain "+label+" execution task: "+endC+" ms");
       System.out.println("   Contain Successes: " + totalContainSucessesCount[0] +
               ", Failures: " + totalContainFailureCount[0]);



       long startR = System.currentTimeMillis();

       removeThreads.stream().forEach(e -> e.start() );
       removeThreads.stream().forEach(e -> {
           try {
               e.join();
               totalRemoveSucessesCount[0] += e.getTotalRemoveSuccessCount();
               totalRemoveFailureCount[0] += e.getTotalRemoveFailureCount();
           } catch (InterruptedException ex) {
               throw new RuntimeException(ex);
           }
       });
       long endR = System.currentTimeMillis() - startR;


       System.out.println("Remove "+label+" execution task: "+endR+" ms");
       System.out.println("   Remove Successes: " + totalRemoveSucessesCount[0] +
               ", Failures: " + totalRemoveFailureCount[0]);
       int listLengthAfterRemove = list.getListSize();
       System.out.println("   List Sorted (after Remove) = " + list.isSorted());
       System.out.println("   listLengthAfterRemove = " + listLengthAfterRemove);
    }

    public void testRun(){
        System.out.println("Results when Random Length = " + randLen + " with " + numOfThreads + " threads : ");
        SyncList syncList = new SyncList();
        testHelp(syncList,"Synchronization");

        System.out.println("==============");

        RWLockList rwLockList = new RWLockList();
        testHelp(rwLockList, "RWLock");

        System.out.println("==============");

        LockList list = new LockList();
        testHelp(list,"Lock");
    }
}
