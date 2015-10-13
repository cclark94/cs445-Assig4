import java.util.*;

public class QuickRandom
{
	public static final int MIN_SIZE = 5;
	
	public static <T extends Comparable<? super T>> void quickSort(T[] array, int n)
	{
		quickSort(array, 0, n-1);
	} // end quickSort
	
	public static <T extends Comparable<? super T>> void quickSort(T[] array, int first, int last)
	{
		if (last - first + 1 < MIN_SIZE)
		{
			insertionSort(array, first, last);
		}
		
		else
		{
			// create the partition: Smaller | Pivot | Larger
			int pivotIndex = partition(array, first, last);
	      
			// sort subarrays Smaller and Larger
			quickSort(array, first, pivotIndex-1);
			quickSort(array, pivotIndex+1, last);
		} // end if
	}  // end quickSort	

	private static <T extends Comparable<? super T>>
	        int partition(T[] a, int first, int last)
	{
		// Get a random index between first and last, and then swap the item
		// at that index with the item at the end of the array, so it can be used
		// as a pivot.
		Random r = new Random();
		int pivotIndex = r.nextInt(last-first+1) + first;
		swap(a, pivotIndex, last);
		pivotIndex = last;
		T pivot = a[pivotIndex];

		// determine subarrays Smaller = a[first..endSmaller]
		//                 and Larger  = a[endSmaller+1..last-1]
		// such that elements in Smaller are <= pivot and 
		// elements in Larger are >= pivot; initially, these subarrays are empty

		int indexFromLeft = first; 
		int indexFromRight = last - 1; 

		boolean done = false;
		while (!done)
		{
			// starting at beginning of array, leave elements that are < pivot; 
			// locate first element that is >= pivot
			while (a[indexFromLeft].compareTo(pivot) < 0)
				indexFromLeft++;

			// starting at end of array, leave elements that are > pivot; 
			// locate first element that is <= pivot

			while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
				indexFromRight--;

			// Assertion: a[indexFromLeft] >= pivot and 
			//            a[indexFromRight] <= pivot.

			if (indexFromLeft < indexFromRight)
			{
				swap(a, indexFromLeft, indexFromRight);
				indexFromLeft++;
				indexFromRight--;
			}
			else 
				done = true;
		} // end while

		// place pivot between Smaller and Larger subarrays
		swap(a, pivotIndex, indexFromLeft);
		pivotIndex = indexFromLeft;

		// Assertion:
		// Smaller = a[first..pivotIndex-1]
		// Pivot = a[pivotIndex]
		// Larger  = a[pivotIndex + 1..last]

		return pivotIndex; 
	}  // end partition

	private static void swap(Object [] a, int i, int j)
	{
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp; 
	} // end swap

	public static <T extends Comparable<? super T>> void insertionSort(T[] a, int n)
	{
		insertionSort(a, 0, n - 1);
	} // end insertionSort

	public static <T extends Comparable<? super T>> void insertionSort(T[] a, int first, int last)
	{
		int unsorted, index;
		
		for (unsorted = first + 1; unsorted <= last; unsorted++)
		{   // Assertion: a[first] <= a[first + 1] <= ... <= a[unsorted - 1]
		
			T firstUnsorted = a[unsorted];
			
			insertInOrder(firstUnsorted, a, first, unsorted - 1);
		} // end for
	} // end insertionSort

	private static <T extends Comparable<? super T>> void insertInOrder(T element, T[] a, int begin, int end)
	{
		int index;
		
		for (index = end; (index >= begin) && (element.compareTo(a[index]) < 0); index--)
		{
			a[index + 1] = a[index]; // make room
		} // end for
		
		// Assertion: a[index + 1] is available
		a[index + 1] = element;  // insert
	} // end insertInOrder
}