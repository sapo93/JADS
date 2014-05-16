package Sort;

import java.lang.reflect.Array;


public class MergeSort <T extends Comparable<T>> {
	
	private T[] arraySort;
	
		
	/**
	 * 
	 * Constructor With Generic Array Parameter
	 * 
	 * @param Array Array To Sort
	 * 
	 */
	public MergeSort(T[] Array, int leftBound, int rightBound) {
		
		this.arraySort = Array;
		
		this.mergeSort(leftBound, rightBound);		
		
	}
	
	
	
	/** 
	 * MergeSort Implementation:
	 * 
	 * <p><pre>
	 * Worst Case: O(nlogn)
	 * Average Case: O(nlogn)
	 * Best Case: O(nlogn)
	 * </pre><p>
	 * 
	 * The Algorithms Works By Splicing The Input In Two And Calling Recursively
	 * MergeSort On The Two Lists. If The List Has Only One Elements Stops The
	 * Recursion, Otherwise Calls The Merge Function On The Two Sublists,
	 * That Fuses The Two Ordered Lists Into A Single Ordered One.
	 * 
	 * @param leftBound Left Limit For The Range Of Array's Values To Sort
	 * @param rightBound Right Limit For The Range Of Array's Values To Sort
	 * 
	 * 
	 */
	private void mergeSort (int leftBound, int rightBound) {
		
		if ((rightBound - leftBound) == 0) { // One Element, Already Sorted
			
			return;
			
		} else {
			
			int center =(int) ((leftBound + rightBound)/2);
			
			this.mergeSort(leftBound, center);
			
			this.mergeSort(center+1, rightBound);
			
			this.merge(leftBound, center, rightBound);
			
		}
		
	}

	
	
	/**
	 * 
	 * Merge function To Fuse Two Lists Generated By MergeSort
	 * 
	 * @param leftBound Left Limit For The Range Of Array's Values To Sort
	 * @param center center that separate the two lists
	 * @param rightBound right limit
	 * 
	 */
	private void merge (int leftBound, int center, int rightBound) {
		
		// Create Left Array
		@SuppressWarnings("unchecked")
		T[] L = (T[]) Array.newInstance(this.arraySort[0].getClass(), (center - leftBound + 1));
		
		// Create Right Array
		@SuppressWarnings("unchecked")
		T[] R = (T[]) Array.newInstance(this.arraySort[0].getClass(), (rightBound - center));
		
		// Fill Left Array
		for (int i = 0; i <= (center-leftBound); i++) {
			
			L[i] = this.arraySort[leftBound+i];
			
		}
		
		// Fill Right Array
		for (int i = 0; i < (rightBound-center); i++) {
			
			R[i] = this.arraySort[center+1+i];			
			
		}
		
		int rValue = 0;
		int lValue = 0;
		int arrSortedValue = leftBound;
		
		while (rValue < (rightBound-center) && lValue <= (center-leftBound)) {
		
			if (L[lValue].compareTo(R[rValue]) <= 0) { // Min Is In L
				
				this.arraySort[arrSortedValue] = L[lValue];
				
				lValue++;
				
				arrSortedValue++;
				
			} else { // Min Is In R
				
				this.arraySort[arrSortedValue] = R[rValue];
				
				rValue++;
				
				arrSortedValue++;
				
			}			
			
		}
		
		// Filling The Sorted Array With The R Remaining Items
		while (lValue <= (center-leftBound)) {
					
			this.arraySort[arrSortedValue] = L[lValue];
					
			lValue++;
					
			arrSortedValue++;
					
		}
				
		// Filling The Sorted Array With The R Remaining Items
		while (rValue < rightBound-center) {
			
			this.arraySort[arrSortedValue] = R[rValue];
						
			rValue++;
			
			arrSortedValue++;
			
		}
		
	}
	
	
	
	/**
	 * 
	 * Return The Sorted Array
	 * 
	 * @return The Array
	 * 
	 */
	public T[] getArraySort() {
		
		return arraySort;
	
	}

	
}

