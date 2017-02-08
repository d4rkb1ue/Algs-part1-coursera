/*
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure. 
*/
public class RandomizedQueue<Item> implements Iterable<Item> {

	public RandomizedQueue() // construct an empty randomized queue 
	public boolean isEmpty() // is the queue empty?
	public int size() // return the number of items on the queue 
	public void enqueue(Item item) // add the item 
	public Item dequeue() // remove and return a random item 
	public Item sample() // return (but do not remove) a random item 
	public Iterator<Item> iterator() // return an independent iterator over items in random order 
	public static void main(String[] args) // unit testing (optional)

}