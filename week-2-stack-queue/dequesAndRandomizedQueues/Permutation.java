/*
Permutation client. Write a client program Permutation.java that takes a command-line integer k; reads in a sequence of strings from standard input using StdIn.readString(); and prints exactly k of them, uniformly at random. Print each item from the sequence at most once. You may assume that 0 ≤ k ≤ n, where n is the number of string on standard input.

Sample:
---
% more distinct.txt 
A B C D E F G H I
% java Permutation 3 < distinct.txt
C
G
A
---
*/
import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;
public class Permutation {
	public static void main(String[] args){
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		int outCount = Integer.parseInt(args[0]);
		int inCount = 0;
		// StdOut.println(outCount);
		while (!StdIn.isEmpty()){
			String item = StdIn.readString();
			if (item != null && item.length() > 0){
				q.enqueue(item);
				inCount++;
			}
		}// end read file
		if (inCount < outCount){
			throw new NoSuchElementException("incoming element < need to be print out");
		}
		while(outCount>0){
			StdOut.println(q.dequeue());
			outCount--;
		}
	}	
}