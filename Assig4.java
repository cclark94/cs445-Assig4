import java.util.*;
import java.io.*;

public class Assig4
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter array size: ");
		int arrSize = sc.nextInt();
		System.out.print("Enter number of trials: ");
		int trials = sc.nextInt();
		System.out.print("Enter file name: ");
		String fileName = sc.next();
		
		String[] fillMethods = {"Random", "Sorted", "Reversed"};
		String[] algorithms = {"Simple QuickSort", "Median of Three (5)", 
							   "Median of Three (10)", "Median of Three (20)", 
							   "Random Pivot (5)"};
							   
		// Remove Simple QuickSort from the list of algorithms if the array size
		// is greater than 100K
		if (arrSize > 100000)
			algorithms  = Arrays.copyOfRange(algorithms, 1, 5);
		
		PrintWriter out = new PrintWriter(fileName);
		
		Integer[] arr = new Integer[arrSize];
		// Used to generate random numbers when randomly filling array
		Random r = new Random();
		// Used to store the algorithm names for keys and
		// sums of the trial times for values. This HashMap will get refilled
		// for each fillMethod
		HashMap<String, Float> times = new HashMap<>();
		
		// Loop over fillMethods
		for (String fillMethod : fillMethods)
		{
			// Initialize values in times (HashMap)
			for (String alg : algorithms)
			{
				times.put(alg, new Float(0));
			}
			
			// Loop over trials
			for (int i=0; i<trials; i++)
			{
				// Fill arr, using the ordering given in fillMethod
				fillArr(arr, r, fillMethod);
				
				// Loop over algorithms
				for (String alg : algorithms)
				{
					// Trace output mode
					if (arr.length <= 20)
					{
						System.out.println();
						System.out.println("Algorithm: " + alg);
						System.out.println("Array size: " + arr.length);
						System.out.println("Data configuration: " + fillMethod);
						System.out.println("Data in array prior to sorting: ");
						for (Integer n : arr)
						{
							System.out.print(n + " ");
						}
						System.out.println();
					}
					
					// Sort a copy of arr, so that arr itself is unchanged and can
					// be used for later iterations
					Integer[] temp = Arrays.copyOf(arr, arr.length);
					float time = sort(temp, alg);
					
					// Add the time for this trial to the sum
					times.put(alg, times.get(alg) + time);
					
					// Trace output mode (continued)
					if (arr.length <= 20)
					{
						System.out.println("Data in array after sorting: ");
						for (Integer n : temp)
						{
							System.out.print(n + " ");
						}
						System.out.println();
						System.out.println("Time required to sort: " + time);
					}
				}
			}
			
			// After finishing the trials, loop over algorithms again to get
			// average times and write data to file
			for (String alg : algorithms)
			{
				float average = times.get(alg)/trials;
				writeData(out, alg, arrSize, fillMethod, trials, average);
			}
			
		}
		
		out.close();
	}
	
	// Fills arr with Integers, according to the ordering given in fillMethod
	public static Integer[] fillArr(Integer[] arr, Random r, String fillMethod)
	{
		switch (fillMethod)
		{
			case "Random":
				for (int i=0; i<arr.length; i++)
				{
					arr[i] = r.nextInt();
				}
				break;
			
			case "Sorted":
				for (int i=0; i<arr.length; i++)
				{
					arr[i] = i + 1;
				}
				break;
			
			case "Reversed":
				for (int i=0; i<arr.length; i++)
				{
					arr[i] = arr.length - i;
				}
				break;
		}
		return arr;
	}
	
	// Sorts arr and returns the amount of time in seconds taken for the sort, 
	// using the given version of QuickSort. 
	public static float sort(Integer[] arr, String algorithm)
	{
		long start, finish, delta=0;
		switch (algorithm)
		{
			case "Simple QuickSort":
				start = System.nanoTime();
				Quick.quickSort(arr, arr.length);
				finish = System.nanoTime();
				delta = finish - start;
				break;
			
			case "Median of Three (5)":
				start = System.nanoTime();
				QuickMedian.quickSort(arr, arr.length, 5);
				finish = System.nanoTime();
				delta = finish - start;
				break;
			
			case "Median of Three (10)":
				start = System.nanoTime();
				QuickMedian.quickSort(arr, arr.length, 10);
				finish = System.nanoTime();
				delta = finish - start;
				break;
			
			case "Median of Three (20)":
				start = System.nanoTime();
				QuickMedian.quickSort(arr, arr.length, 20);
				finish = System.nanoTime();
				delta = finish - start;
				break;
			
			case "Random Pivot (5)":
				start = System.nanoTime();
				QuickRandom.quickSort(arr, arr.length);
				finish = System.nanoTime();
				delta = finish - start;
				break;
		}
		return delta/(float)1000000000;
	}
	
	// Writes the given data to out
	public static void writeData(PrintWriter out, String alg, int arrSize, String fillMethod, int trials, float time)
	{
		out.println("Algorithm: " + alg);
		out.println("Array size: " + arrSize);
		out.println("Order: " + fillMethod);
		out.println("Number of trials: " + trials);
		out.println("Average Time: " + time + " sec.");
		out.println();
	}
}