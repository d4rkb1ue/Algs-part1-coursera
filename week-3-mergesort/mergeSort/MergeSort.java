/**
* MergeSort
* 
* author: d4rkb1ue 
* site: drkbl.com
* date: 2017-02-15
**/

public class MergeSort{
	/**
	* Interface
	**/
	public static void mergeSort(Comparable[] a){
		Comparable[] aux = new Comparable[a.length];
		mergeSort(a, aux, 0, a.length -1);
	}
	/**
	* Recursion method
	*
	* Parameters:
	* a: data
	* aux: buffer
	* lo: sort start at
	* hi: sort end with
	**/
	private static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi){
		if (lo >= hi) return;
		int mid = (lo + hi) / 2;
		p("mergeSort: [" + lo + ", " + mid + "]");
		mergeSort(a, aux, lo, mid);
		p("mergeSort: [" + (mid+1) + ", " + hi + "]");
		mergeSort(a, aux, mid + 1, hi);
		p("merge: [" + lo + ", " + mid + "] + [" + (mid+1) + ", " + hi + "] -> [" + lo + ", " + hi + "]");
		// max item of first half is smaller than right half, so it is already sorted
		if (less(a[mid], a[mid + 1])) return;
		merge(a, aux, lo, mid, hi);
	}
	/**
	* Buttom-up version of mergeSort
	* remove the recursive
	*
	**/
	public static void mergeSortWithoutRecursive(Comparable[] a){
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		for (int sz = 1; sz < N; sz *= 2){
			for (int lo = 0; lo < N - sz; lo += sz*2)
				merge(a, aux, lo, lo+sz-1, Math.min(lo+2*sz-1, N-1));
		}// end for size
	}

	/**
	* Merge two sub-arrays into one: aux[lo, mid] + aux[mid + 1, hi] -> arr[lo, hi]
	* 
	* Parameters:
	* a: target data
	* aux: source data, buffer
	* lo: source 1 start at
	* mid: source 1 end with, source 2 start at mid+1
	* hi: source 2 end with
	**/
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
		for (int i = lo; i <= hi; i++){
			aux[i] = a[i];
		}
		// count: point to target array
		int count = lo;
		// lm: point to arr[lo, mid]
		int lm = lo;
		// mh: point to arr[mid+1, hi]
		int mh = mid + 1;

		while(count <= hi){
			if (lm > mid) 						a[count++] = aux[mh++];
			else if (mh > hi)					a[count++] = aux[lm++];
			else if (less(aux[lm], aux[mh]))	a[count++] = aux[lm++];
			else								a[count++] = aux[mh++];
			// == else if (aux[lo] > aux[mid + 1])
		}
	}
	public static boolean less(Comparable a, Comparable b){
		return a.compareTo(b) < 0;
	}
	// unit test
	public static void main(String[] args){
		// int[] will cause error, since int is not Comparable. Auto-boxing do not cover this case
		Integer[] a = {4,2,52,3,5,1,2,23,9,20,0};
		// mergeSort(a);
		mergeSortWithoutRecursive(a);
		for (int i : a){
			System.out.print(i + ", ");
		}
		System.out.println();
	}
	public static void p(String s){
		System.out.println(s);
	}
	/**
	* improvements for merge sort
	**/
	/*
	private static void cutOffSort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		int CUTOFF = 7; // 7 is fine
		// length = hi - lo + 1
		if (hi - lo + 1 <= CUTOFF) {
			Insertion.sort(a, lo, hi);
			return; 
		}
		int mid = lo + (hi - lo) / 2; 
		sort (a, aux, lo, mid);
		sort (a, aux, mid+1, hi); 
		merge(a, aux, lo, mid, hi);
	}
	*/
}