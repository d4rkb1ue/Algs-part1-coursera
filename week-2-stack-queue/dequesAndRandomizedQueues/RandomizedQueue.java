/*
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure. 

Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time.
"constant amortized time" != "constant worst-case time"
*/
import edu.princeton.cs.algs4.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] arr;
	// pointer to first valid element + 1, arr[first-1] is the first element
	// private int first;
	// pointer to last valid element + 1, arr[last-1] is the last element
	// private int last;
	// number of elements
	private int n;
	// construct an empty randomized queue 
	public RandomizedQueue(){
		arr = (Item[]) new Object[2];
		n = 0;
	}
	// is the queue empty?
	public boolean isEmpty(){
		return n == 0;
	}
	// return the number of items on the queue 
	public int size(){
		return n;
	}
	// resize array to para
	private void reSize(int capacity){
		if (capacity <= n){
			throw new UnsupportedOperationException("can not shrink array");
		}
		Item[] newArr = (Item[]) new Object[capacity];
		// int i = 0;
		// for(Item e : arr){
			// newArr[i++] = e;
		// }
		for (int i = 0;i < n;i++){
			newArr[i] = arr[i];
		}
		arr = newArr;
	}
	// add the item 
	public void enqueue(Item item){
		if (item == null){
			throw new NullPointerException("adding item is null");
		}
		if (arr.length == n){
			reSize(arr.length*2);
		}
		arr[n++] = item;
	}
	// remove and return a random item 
	public Item dequeue(){
		if (isEmpty()) throw new NoSuchElementException("queue is empty");
		// return 0 - (n-1)
		int no = StdRandom.uniform(n);
		Item ret = arr[no];
		arr[no] = arr[--n];
		if (n > 0 && n <= arr.length/4){
			reSize(arr.length/2);
		}
		return ret;
	}
	// return (but do not remove) a random item 
	public Item sample(){
		if (isEmpty()) throw new NoSuchElementException("queue is empty");
		return arr[StdRandom.uniform(n)];
	}
	// return an independent iterator over items in random order 
	public Iterator<Item> iterator(){
		return null;
	}
	// for test
	private void checkSize(int shouldBeSize){
		if (shouldBeSize != n) throw new NullPointerException("shouldBeSize != n");
		if (n<0) throw new NullPointerException("n<0");
		if (n>arr.length) throw new NullPointerException("n>arr.length");
		if (n<arr.length/4) throw new NullPointerException("n<arr.length/4");
	}
	// for test
	private static boolean inTheQueue(String str, String[] shouldBeQueue){
		for (String s : shouldBeQueue){
			if (s != null && s.equals(str)) return true;
		}
		return false;
	}
	// unit testing (optional)
	public static void main(String[] args){
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		int shouldBeSize = 0;
		int MAX_ITEM = 20;
		int count = 0;
		// dealing with input file ? line
		int step_count = 0;
		String[] shouldBeQueue = new String[MAX_ITEM];
		String de = null;
		while (!StdIn.isEmpty()){
			step_count++;
			String item = StdIn.readString();
			if (item.equals("-")){
				de = q.dequeue();
				shouldBeSize--;
				if (!removeFromQueue(de,shouldBeQueue)) throw new NullPointerException("WTF? remove a not exist element:" + de+"[line:"+step_count+"]");
				if (de == null) throw new NullPointerException("dequeue is null");
				// if (!inTheQueue(de,shouldBeQueue)) throw new NullPointerException("dequeue item is wrong:" + de+"[line:"+step_count+"]");
			}else if(item.equals("#")){
				de = q.sample();
				if (de == null) throw new NullPointerException("sample is null");
				if (!inTheQueue(de,shouldBeQueue)) throw new NullPointerException("sample item is wrong:" + de+"[line:"+step_count+"]");
			}else{
				q.enqueue(item);
				shouldBeSize++;
				shouldBeQueue[count++] = item;
			}
			q.checkSize(shouldBeSize);			
		}// end while(read file)
		StdOut.println("Element(s) remains should be " + shouldBeSize);
	}// end test main()
}// end RandomizedQueue