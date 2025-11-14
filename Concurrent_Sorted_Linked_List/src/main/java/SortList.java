public abstract class SortList {

    public Entry head;

    public SortList() {
        this.head = new Entry(Integer.MIN_VALUE);
       this.head.next =new Entry(Integer.MAX_VALUE);
    }

    public  abstract  boolean add(Integer obj);
    public  abstract  boolean remove(Integer obj);
    public  abstract  boolean contain(Integer obj);

    public void printList(){
        Entry curr = this.head;
        while (curr != null){
            System.out.println(curr.object);
            curr = curr.next;

        }
    }

    // Task 1
    public boolean isSorted() {
        Entry curr = this.head;
        while (curr.next != null) {
            if (curr.object.compareTo(curr.next.object) > 0) {
                return false;
            }
            curr = curr.next;
        }
        return true;
    }
    public int getListSize() {
        int count = 0;
        Entry curr = this.head;
        curr = curr.next; // Start after MIN_VALUE sentinel
        while (curr != null && curr.next != null) { // Stop before MAX_VALUE sentinel
            count++;
            curr = curr.next;
        }
        return count;
    }

}
