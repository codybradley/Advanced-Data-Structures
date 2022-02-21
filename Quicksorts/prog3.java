// Cody Bradley
// Comp 282, MoWe 2:00PM - 3:15PM
// Programming Assignment #3
// 16 November 2020
// This file implements several different quicksorts
// as well as insertion sort and 2 heapsorts

import java.math.*; // maybe not needed?
// or
import java.util.Random;

// use this class to contain everything related to your sorts
class ArraySorts {
    // Some sample driver method headers
    public static void insertionSort(int a[], int n) {
        // Insertion Sort
        for (int i = 1; i < n; i++) {//if array size is 0 or 1, it's already sorted
            int currentNum = a[i];
            int j = i - 1;
            while (j >= 0 && currentNum < a[j]) {
                a[j + 1] = a[j];
                j--;
            }//elements to the left of i that are greater than currentNum have been shifted
            a[j + 1] = currentNum;
        }
    }

    public static void QuickSort1(int a[], int n, int cutoff) {
        //outside-in partition, random pivot (driver)
        if (cutoff < 2)
            cutoff = 2;
        QuickSort1(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort1(int a[], int lf, int rt, int cutoff) {
        //outside-in partition, random pivot (recursive)
        int pivot, lfpt, rtpt;
        while ((rt - lf + 1) >= cutoff) {  // partition is big – sort it
            pivot = lf + (int) (Math.random() * (rt - lf + 1));
            pivot = outsideInPartition(a, lf, rt, pivot);

            lfpt = pivot + 1;
            rtpt = pivot - 1;

            if ((rtpt - lf) < (rt - lfpt)) { // find the smaller partition
                QuickSort1(a, lf, rtpt, cutoff);
                lf = lfpt;
            } else {
                QuickSort1(a, lfpt, rt, cutoff);
                rt = rtpt;
            }
        }

    }

    private static int outsideInPartition(int a[], int lf, int rt, int pivot) {
        swapElements(a, pivot, lf);//done swapping pivot to the far left

        int lfpt = lf + 1;
        int rtpt = rt;
        while (lfpt <= rtpt /*|| (lfpt == rtpt && a[rtpt] > a[lf])*/) {
            //if lfpt & rtpt are the same index, only done if
            //the element at that index is less than or equal to the pivot
            if (a[lfpt] > a[lf]) {//lfpt is ready to be swapped
                if (a[rtpt] < a[lf]) {//both pointers are ready to be swapped
                    swapElements(a, lfpt, rtpt);//swapped elements at lfpt & rtpt
                    lfpt++;
                    rtpt--;
                } else {//only lfpt is ready to be swapped
                    rtpt--;
                }
            } else if (a[rtpt] < a[lf]) {//only rtpt is ready to be swapped
                lfpt++;
            } else {//neither pointer is at an index that needs to be swapped
                lfpt++;
                rtpt--;
            }
        }//upon exiting loop, lfpt & rtpt have crossed

        swapElements(a, rtpt, lf);//pivot has been swapped back into correct location

        return rtpt;//pivot is at index rtpt
    }

    public static void QuickSort2(int a[], int n, int cutoff) {
        //left-to-right partition, random pivot (driver)
        if (cutoff < 2)
            cutoff = 2;
        QuickSort2(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort2(int a[], int lf, int rt, int cutoff) {
        //left-to-right partition, random pivot (recursive)
        int pivot, lfpt, rtpt;
        while ((rt - lf + 1) >= cutoff) {  // partition is big – sort it
            pivot = lf + (int) (Math.random() * (rt - lf + 1));
            pivot = leftRightPartition(a, lf, rt, pivot);

            lfpt = pivot + 1;
            rtpt = pivot - 1;

            if ((rtpt - lf) < (rt - lfpt)) { // find the smaller partition
                QuickSort2(a, lf, rtpt, cutoff);
                lf = lfpt;
            } else {
                QuickSort2(a, lfpt, rt, cutoff);
                rt = rtpt;
            }
        }

    }

    private static int leftRightPartition(int a[], int lf, int rt, int pivot) {
        swapElements(a, pivot, lf);//done swapping pivot to the far left

        int ls = lf;
        int fu = lf + 1;
        while (fu <= rt) {//exits loop when fu goes past the rightmost element
            if (a[fu] < a[lf]) {//fu is in the wrong partition
                ls++;//ls now points to the first element larger than pivot
                swapElements(a, ls, fu);//ls once again points to last element smaller than pivot
                fu++;
            } else if (a[fu] > a[lf])//fu is in the right partition
                fu++;
            else {//a[fu] == a[lf]
                //randomly choose which partition to put it in
                if ((int) (Math.random() * 2) == 0) {
                    //put it in partition less than pivot
                    ls++;
                    swapElements(a, ls, fu);
                    fu++;
                } else {//random == 1
                    //put it in partition greater than pivot
                    fu++;
                }
            }
        }

        swapElements(a, ls, lf);//pivot has been swapped back into correct location

        return ls;//pivot is at index ls
    }

    public static void QuickSort3(int a[], int n, int cutoff) {
        //2-pivots, both random (driver)
        if (cutoff < 2)
            cutoff = 2;
        QuickSort3(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort3(int a[], int lf, int rt, int cutoff) {
        //2-pivots, both random (recursive)
        int leftPivot, rightPivot, lfrtpt, mlfpt, mrtpt, rtlfpt, tempInt;
        int leftSize, middleSize, rightSize;
        pair pivots;
        while ((rt - lf + 1) >= cutoff) {  // partition is big – sort it
            leftPivot = lf + (int) (Math.random() * (rt - lf + 1));
            do {
                rightPivot = lf + (int) (Math.random() * (rt - lf + 1));
            } while (leftPivot == rightPivot);//make sure the pivots aren't the same index
            if (a[leftPivot] > a[rightPivot]) {//pivots are in wrong order, swap their pointers
                tempInt = leftPivot;
                leftPivot = rightPivot;
                rightPivot = tempInt;
            }
            pivots = new pair(leftPivot, rightPivot);

            //System.out.println("\nBefore Partition: Left Pivot: " + a[pivots.getLeft()] + " Right Pivot: " + a[pivots.getRight()]);
            //showArray(a, lf, rt);
            pivots = doublePivotPartition(a, lf, rt, pivots);
            //System.out.println("\nAfter Partition:");
            //showArray(a, lf, rt);

            lfrtpt = pivots.getLeft() - 1;//right end of left partition
            mlfpt = pivots.getLeft() + 1;//left end of middle partition
            mrtpt = pivots.getRight() - 1;//right end of middle partition
            rtlfpt = pivots.getRight() + 1;//left end of right partition

            // find the biggest partition and quicksort the other two
            leftSize = lfrtpt - lf;
            middleSize = mrtpt - mlfpt;
            rightSize = rt - rtlfpt;
            if ((leftSize) >= (middleSize) && (leftSize) >= (rightSize)) {
                //left partition is largest
                QuickSort3(a, mlfpt, mrtpt, cutoff);//quicksort middle partition
                QuickSort3(a, rtlfpt, rt, cutoff);//quicksort right partition
                rt = lfrtpt;//right end of left partition is the new far right element
            } else if ((middleSize) >= (leftSize) && (middleSize) >= (rightSize)) {
                //middle partition is largest
                QuickSort3(a, lf, lfrtpt, cutoff);//quicksort left partition
                QuickSort3(a, rtlfpt, rt, cutoff);//quicksort right partition
                lf = mlfpt;
                rt = mrtpt;//middle left and middle right are now the new left and right ends
            } else {//right partition is largest
                QuickSort3(a, lf, lfrtpt, cutoff);//quicksort left partition
                QuickSort3(a, mlfpt, mrtpt, cutoff);//quicksort middle partition
                lf = rtlfpt;//left end of right partition is the new far left element
            }
        }

    }

    private static pair doublePivotPartition(int a[], int lf, int rt, pair pivots) {
        int ls, fb, fu;

        if (pivots.getRight() == lf) {//swap right pivot first
            swapElements(a, pivots.getRight(), rt);
            if (pivots.getLeft() == rt)
                ;//pivots are in correct location, don't swap again
            else
                swapElements(a, pivots.getLeft(), lf);
        } else {//swap left pivot first
            swapElements(a, pivots.getLeft(), lf);
            swapElements(a, pivots.getRight(), rt);
        }
        //smaller pivot is now at far left and larger pivot is now at far right

        ls = lf;
        fb = rt;
        fu = lf + 1;

        while (fu < fb) {//exits loop when fu reaches fb, since there are no new unknowns
            if (a[fu] < a[lf]) {//fu needs to be in left partition
                ls++;//ls now points to the first element in middle partition
                swapElements(a, ls, fu);//ls once again points to last element smaller than left pivot
                fu++;
            } else if (a[fu] > a[lf] && a[fu] < a[rt])//fu is in the correct partition (middle partition)
                fu++;
            else if (a[fu] > a[rt]) {//fu needs to be in the right partition
                fb--;//fb now points to the last unknown element
                swapElements(a, fb, fu);//fb once again points to the first bigger than right pivot
                //after swapping, fu points to a new unknown (or same location as fb)
            } else if (a[fu] == a[lf]) {//randomly choose to put it in left or middle partition
                if ((int) (Math.random() * 2) == 0) {
                    //put it in left partition
                    ls++;
                    swapElements(a, ls, fu);
                    fu++;
                } else {//random == 1
                    //put it in middle partition
                    fu++;
                }
            } else {//a[fu]==a[rt]
                //randomly choose to put it in middle or right partition
                if ((int) (Math.random() * 2) == 0) {
                    //put it in middle partition
                    fu++;
                } else {//random == 1
                    //put it in right partition
                    fb--;
                    swapElements(a, fb, fu);
                }
            }
        }

        swapElements(a, ls, lf);
        swapElements(a, fb, rt);//pivots have been swapped back to correct locations

        pivots.setLeft(ls);
        pivots.setRight(fb);
        return pivots;//left & right pivots are at indices ls & fb respectively
    }

    public static void QuickSort4(int a[], int n, int cutoff) {
        //outside-in partition, pivot = a[lf] (driver)
        if (cutoff < 2)
            cutoff = 2;
        QuickSort4(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort4(int a[], int lf, int rt, int cutoff) {
        //outside-in partition, pivot = a[lf] (recursive)
        int pivot, lfpt, rtpt;
        while ((rt - lf + 1) >= cutoff) {  // partition is big – sort it
            pivot = outsideInPartition(a, lf, rt, lf);

            lfpt = pivot + 1;
            rtpt = pivot - 1;

            if ((rtpt - lf) < (rt - lfpt)) { // find the smaller partition
                QuickSort4(a, lf, rtpt, cutoff);
                lf = lfpt;
            } else {
                QuickSort4(a, lfpt, rt, cutoff);
                rt = rtpt;
            }
        }

    }

    public static void QuickSort5(int a[], int n, int cutoff) {
        //left-to-right partition, pivot = a[lf] (driver)
        if (cutoff < 2)
            cutoff = 2;
        QuickSort5(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort5(int a[], int lf, int rt, int cutoff) {
        //left-to-right partition, pivot = a[lf] (recursive)
        int pivot, lfpt, rtpt;
        while ((rt - lf + 1) >= cutoff) {  // partition is big – sort it
            pivot = leftRightPartition(a, lf, rt, lf);

            lfpt = pivot + 1;
            rtpt = pivot - 1;

            if ((rtpt - lf) < (rt - lfpt)) { // find the smaller partition
                QuickSort5(a, lf, rtpt, cutoff);
                lf = lfpt;
            } else {
                QuickSort5(a, lfpt, rt, cutoff);
                rt = rtpt;
            }
        }

    }

    public static void AlmostQS1(int a[], int n, int cutoff) {
        //Exact same driver as QuickSort1 except the call to InsertionSort is commented out
        if (cutoff < 2)
            cutoff = 2;
        QuickSort1(a, 0, n - 1, cutoff);
        //insertionSort(a, n);
    }

    public static void AlmostQS2(int a[], int n, int cutoff) {
        //Exact same driver as QuickSort2 except the call to InsertionSort is commented out
        if (cutoff < 2)
            cutoff = 2;
        QuickSort2(a, 0, n - 1, cutoff);
        //insertionSort(a, n);
    }

    public static void AlmostQS3(int a[], int n, int cutoff) {
        //Exact same driver as QuickSort3 except the call to InsertionSort is commented out
        if (cutoff < 2)
            cutoff = 2;
        QuickSort3(a, 0, n - 1, cutoff);
        //insertionSort(a, n);
    }

    public static void HeapSortBU(int a[], int n) {
        // heapsort with linear buildheap
        for (int i = n/2 - 1; i >= 0; i--) {
            //start at i=n/2-1 because if we start at i=n-1, the first n/2 loops do nothing
            trickleDown(a, a[i], i, n);
        }//heap has been built

        for (int i = n - 1; i > 0; i--) {
            swapElements(a, 0, i);//swap largest element (index 0) with last element
            //fix heap of size i by trickling down the out of place element at the top
            //size i excludes the element that was just swapped to the end of the array
            trickleDown(a, a[0], 0, i);
        }
    }

    private static void trickleDown(int a[], int savedValue, int curNode, int n) {
        int lf, rt, largestChild;
        lf = curNode * 2 + 1;//index of left child
        if (lf < n) {//left child exists
            largestChild = lf;
            rt = lf + 1;//index of right child
            if (rt < n && a[rt] > a[lf])//right child exists and is larger than left child
                largestChild = rt;
            //largest child holds the index of the child with the larger value
            if (a[largestChild] > savedValue) {//not correct spot yet
                //shift largest child up and trickle further
                a[curNode] = a[largestChild];
                trickleDown(a, savedValue, largestChild, n);
            } else//savedValue is larger than both children, put it here
                a[curNode] = savedValue;

        } else { //node doesn't have children
            //this is the correct location for the saved value
            a[curNode] = savedValue;
        }
    }

    public static void HeapSortTD(int a[], int n) {
        // heapsort with nlogn buildheap
        for (int i = 1; i < n; i++) {
            trickleUp(a, i);
        }//heap has been built

        for (int i = n - 1; i > 0; i--) {
            swapElements(a, 0, i);//swap largest element (index 0) with last element
            //fix heap of size i by trickling down the out of place element at the top
            //size i excludes the element that was just swapped to the end of the array
            trickleDown(a, a[0], 0, i);
        }
    }

    private static void trickleUp(int a[], int lastIndex) {
        int savedValue = a[lastIndex];
        int curNode = lastIndex;
        int parent = (lastIndex - 1) / 2;
        while (curNode > 0 && a[parent] < savedValue) {
            //parent is smaller than our saved value, need to shift parent down
            a[curNode] = a[parent];
            curNode = parent;//next we want to look at what was previously the parent
            parent = (curNode - 1) / 2;
        }//upon exiting loop, either curNode is index 0 or parent node is greater than saved value
        //in either case, saved value goes where curNode points to
        a[curNode] = savedValue;
    }

    private static void swapElements(int a[], int index1, int index2) {
        int tempInt = a[index1];
        a[index1] = a[index2];
        a[index2] = tempInt;
    }

    public static boolean testSetsWork() {
        return true;
    }

    public static String myName() {
        return "Cody Bradley";
    }

}

    // use this class to return the two pivot locations in the 2-pivot partition
    // algorithm

    class pair {
        public int left, right;

        public pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public void setLeft(int left){
            this.left = left;
        }

        public void setRight(int right){
            this.right=right;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }
    }
