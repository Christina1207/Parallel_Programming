public class RemoveThread extends TestThread implements Runnable{
    public RemoveThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    private   int totalRemoveSuccessCount = 0;
    private   int totalRemoveFailureCount = 0;


    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            if (list.remove(nums[i])) {
                totalRemoveSuccessCount++;
            } else {
                totalRemoveFailureCount++;
            }
        }
    }

    public  int getTotalRemoveSuccessCount() {
        return totalRemoveSuccessCount;
    }

    public  int getTotalRemoveFailureCount() {
        return totalRemoveFailureCount;
    }
}