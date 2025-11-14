public class ContainThread extends TestThread implements Runnable{

    private int totalFailureCount = 0;
    private int totalSuccessCount = 0;
    public ContainThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            // Call the list's contain method and check the result
            if(list.contain(nums[i]))
            {
                totalSuccessCount++;
            }
            else{
                totalFailureCount++;
            }
        }
    }

    public int getSuccessCount() {
        return totalSuccessCount;
    }

    public int getFailureCount() {
        return totalFailureCount;
    }
}
