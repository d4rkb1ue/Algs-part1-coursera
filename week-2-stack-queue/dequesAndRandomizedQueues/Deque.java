/*
A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure.
*/
import edu.princeton.cs.algs4.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;

public class Deque<Item> implements Iterable<Item> {
	private class Node{
		Item item;
		Node previous;
		Node next;
	}
	// number of items
	private int n;
	private Node first;
	private Node last;
	// the last but one
	// private Node last2;

	// construct an empty deque 
	public Deque(){
		n = 0;
		first = last = null;
	}
	// is the deque empty?
	public boolean isEmpty(){
		return n == 0;
	}
	// return the number of items on the deque 
	public int size(){
		return n;
	}

	private void assertNull(Item item){
		if ( item == null ){
			throw new NullPointerException("Item is null");
		}
	}
	private void assertEmpty(){
		if (isEmpty()){
			throw new NoSuchElementException("Stack underflow");
		}
	}
	// add the item to the front 
	public void addFirst(Item item){
		assertNull(item);
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		first.previous = null;
		if(oldFirst != null){
			oldFirst.previous = first;	
		}
		n++;
		if(last == null){
			last = first;
		}
	}
	// add the item to the end 
	public void addLast(Item item){
		assertNull(item);
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.previous = oldLast;
		if (oldLast != null){
			oldLast.next = last;
		}
		n++;
		if (first == null){
			first = last;
		}
		
	}
	// remove and return the item from the front 
	public Item removeFirst(){
		assertEmpty();
		Item ret = first.item;
		first = first.next; 
		if(first != null){
			first.previous = null;
		}else{
			last = null;
		}
		n--;
		return ret;
	}
	// remove and return the item from the end 
	public Item removeLast(){
		assertEmpty();
		Item ret = last.item;
		last = last.previous;
		if (last != null){
			last.next = null;	
		}else{
			first = null;
		}
		n--;
		return ret;
	}
	private class DequeIterator implements Iterator<Item> {
		// one ahead of the removed
		private Node current = first;
		public boolean hasNext(){
			return current != null;
		}
		public void remove(){
			throw new UnsupportedOperationException();
		}
		public Item next(){
			if (!hasNext()){
				throw new NoSuchElementException("Deque underflow");
			}
			Item ret = current.item;
			current = current.next;
			return ret;
		}
	}

	// return an iterator over items in order from front to end 
	public Iterator<Item> iterator(){
		return new DequeIterator();
	}

	// for test
	public void check(int shouldBeSize){
		int count = 0;
		Node pointer = first;
		if (first == null && last != null || last == null && first != null){
			throw new NullPointerException("first/null == null, but the other one do not");
		}
		while (pointer != null){
			pointer = pointer.next;
			count++;
		}
		if (count != shouldBeSize){
			throw new NullPointerException("count != shouldBeSize");
		}
		if (n != shouldBeSize){
			throw new NullPointerException("n != shouldBeSize");
		}
		if (count != n){
			throw new NullPointerException("count != n");
		}
	}
	// unit testing (optional)
	public static void main(String[] args){
		Deque<String> deque = new Deque<String>();
		int shouldBeSize = 0;
		String shouldBeString, removed;
		while (!StdIn.isEmpty()){
			String item = StdIn.readString();
			// StdOut.println("read: "+item);
			if (item.startsWith("[")){
				shouldBeSize++;
				deque.addFirst(item.substring(1));	
			}else if (item.startsWith("]")){
				shouldBeSize++;
				deque.addLast(item.substring(1));
			}else if (item.startsWith("-[")){
				shouldBeSize--;
				removed = deque.removeFirst();
				shouldBeString = item.substring(2);
				if (!(shouldBeString.equals(removed))){
					throw new NullPointerException("Removed string is not correct");
				}
			}else if (item.startsWith("-]")){
				shouldBeSize--;
				removed = deque.removeLast();
				shouldBeString = item.substring(2);
				if (!(shouldBeString.equals(removed))){
					throw new NullPointerException("Removed string is not correct");
				}
			}
			try{
				deque.check(shouldBeSize);
			}catch (Exception e) {
				StdOut.print(e);
			}
		}// end input(while)
		// StdOut.println(deque.removeLast());
		StdOut.println("Element(s) remains should be " + shouldBeSize);
		for (String s : deque){
			StdOut.println(s);
		}
		// Iterator<String> iterator = deque.iterator();
		// StdOut.println(iterator.hasNext());

	}// end main
}