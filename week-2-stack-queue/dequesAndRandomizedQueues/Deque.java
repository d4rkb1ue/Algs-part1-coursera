/*
A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure.
*/

public class Deque<Item> implements Iterable<Item> {

	public Deque() // construct an empty deque 
	public boolean isEmpty() // is the deque empty?
	public int size() // return the number of items on the deque 
	public void addFirst(Item item) // add the item to the front 
	public void addLast(Item item) // add the item to the end 
	public Item removeFirst() // remove and return the item from the front 
	public Item removeLast() // remove and return the item from the end 
	public Iterator<Item> iterator() // return an iterator over items in order from front to end 
	public static void main(String[] args) // unit testing (optional)

}