//Cody Bradley
//Comp 282, MoWe 2:00PM - 3:15PM
//Programming Assignment #1
//4 September 2020
//This class is used to find magic squares using
//three methods with varying degrees of randomness

import java.util.Random;

class MagicSquare {

    private int size;
    private int[][] square;

    // constructor -- set the size and instantiate the array
    public MagicSquare(int size) {
        if (size < 1) {//can't have size be 0 or negative
            System.out.println("Size of the magic square must be positive. Setting size to 1.");
            size = 1;//if size is 0 or negative, size defaults to 1
        }
        this.size = size;
        square = new int[size][size];//array has "size" rows and "size" columns
    }

    // purely random -- give up after "tries" tries
    public int purelyRandom(int tries) {
        boolean found = false;
        int tryCt = 0;

        int maxNum = size * size;//highest number that will go in the magic square
        int[] allNums = new int[maxNum];
        //allNums will hold all possible numbers
        //that will go in the magic square
        for (int i = 0; i < maxNum; i++) {
            allNums[i] = i + 1;
            //if size=4, allNums will be filled with numbers 1 through 16
        }
        //creation of allNums[] doesn't have to be in the while loop
        //because order of the elements doesn't matter at the beginning of each loop

        Random random = new Random();

        while (!found && tryCt < tries) {
            int maxIndex = maxNum - 1;
            //maxIndex indicates the highest usable index for this iteration through the loop
            //maxIndex starts at maxNum-1 and decrements to zero
            for (int row = 0; row < size; row++)
                for (int col = 0; col < size; col++) {
                    int randomIndex = random.nextInt(maxIndex + 1);
                    //randomIndex is a random int from 0 through maxIndex
                    //if size=4, nextInt will give a number 0 through 15
                    //then 0 through 14, then 0 through 13, etc
                    square[row][col] = allNums[randomIndex];

                    //swapping the randomIndex and the last index that was usable this iteration
                    swapArrayEls(allNums, randomIndex, maxIndex);
                    maxIndex--;//one less usable element in allNums after the swap
                }

            found = magic();
            tryCt++;
        }

        if (!found)
            tryCt = -1;
        return tryCt;
    }

    // force last number in each row
    public int endOfRow(int tries) {
        boolean found = false;
        int tryCt = 0;

        int maxNum = size * size;
        int[] allNums = new int[maxNum];
        int magicSum = (size * (maxNum + 1)) / 2;

        for (int i = 0; i < maxNum; i++) {
            allNums[i] = i + 1;
        }//allNums[] has been filled

        Random random = new Random();

        while (!found && tryCt < tries) {
            boolean ok = true;
            int pick;
            int maxIndex = maxNum - 1;

            //If at any time a row cannot be finished,
            //there’s no point in continuing to later rows.

            //fills rows one at a time
            for (int row = 0; row < size && ok; row++) {
                int rowSum = 0;//new row, reset rowSum
                // put random values in all but the last spot
                for (int col = 0; col < size - 1; col++) {
                    int randomIndex = random.nextInt(maxIndex + 1);

                    square[row][col] = allNums[randomIndex];
                    rowSum += allNums[randomIndex];

                    //swapping the randomIndex and the last index that was usable this loop
                    swapArrayEls(allNums, randomIndex, maxIndex);
                    maxIndex--;//one less usable element in allNums[] after the swap
                }

                // pick points to the needed element in the allNums array
                pick = find(magicSum - rowSum, allNums, maxIndex);
                if (pick == -1)
                    ok = false;
                else {
                    //put element at index "pick" into last column of the current row
                    square[row][size - 1] = allNums[pick];

                    //swap element at index "pick" with element at last usable index in allNums
                    swapArrayEls(allNums, pick, maxIndex);
                    maxIndex--;//one less usable element in allNums[] after the swap
                }
            }
            if (ok)//if "ok" is false, leave "found" as false
                found = magic();
            tryCt++;
        }//end while loop

        if (!found)
            tryCt = -1;
        return tryCt;
    }

    private static int find(int remainingNum, int[] allNums, int maxIndex) {
        int remainingIndex = -1;//index that points to remainingNum
        boolean found = false;

        for (int i = 0; !found && i <= maxIndex; i++) {
            if (remainingNum == allNums[i]) {
                remainingIndex = i;
                found = true;
            }
        }

        return remainingIndex;
    }

    //put pairs of numbers in rows
    //only works if magic square size is even
    //if magic square size is odd, returns -1
    public int pairs(int tries) {
        boolean found = false;
        int tryCt = 0;

        //skip main part of the method and return -1 if size of square is odd
        if (size % 2 == 0) {
            int maxNum = size * size;
            int[] allNums = new int[maxNum];
            //filledIndexes will be used to keep track of
            // which columns in a row have been filled
            int[] filledIndexes = new int[size];
            int pairSum = maxNum + 1;//each pair should add up to this

            for (int i = 0; i < maxNum; i++) {
                allNums[i] = i + 1;
            }//allNums has been filled with numbers 0 through maxNum
            for (int i = 0; i < size; i++) {
                filledIndexes[i] = i;
            }//filledIndexes has been filled with numbers 0 through size-1

            Random random = new Random();

            while (!found && tryCt < tries) {
                //reset maxIndex and maxFilledIndex at start of each iteration
                int maxIndex = maxNum - 1;
                int pick;

                //fill rows 1 at a time, 1 pair at a time
                for (int row = 0; row < size; row++) {
                    //maxFilledIndex keeps track of last usable index in filledIndexes[]
                    int maxFilledIndex = size - 1;
                    for (int i = 0; i < size / 2; i++) {//if the size is 10, we will choose 5 pairs
                        //choose a random index from the array of numbers to go in the square
                        int randomIndex = random.nextInt(maxIndex + 1);
                        //choose a random column for the number to go into
                        int randomCol = random.nextInt(maxFilledIndex + 1);
                        square[row][filledIndexes[randomCol]] = allNums[randomIndex];

                        //swap the random number index with the last usable one
                        swapArrayEls(allNums, randomIndex, maxIndex);
                        //decrement number of usable indexes
                        maxIndex--;

                        //swap the random column index with the last usable one
                        swapArrayEls(filledIndexes, randomCol, maxFilledIndex);
                        //decrement number of usable indexes
                        maxFilledIndex--;
                        //first number of a pair is done being added

                        //first number in the pair is now in allNums[maxIndex+1]
                        //pairSum-allNums[maxIndex+1] is the number we need to complete the pair
                        pick = find(pairSum - allNums[maxIndex + 1], allNums, maxIndex);
                        //no need to check if(pick == -1) because the number to complete
                        //the pair will always be in the usable part of the array,
                        //given the square size is even
                        randomCol = random.nextInt(maxFilledIndex + 1);
                        square[row][filledIndexes[randomCol]] = allNums[pick];

                        swapArrayEls(allNums, pick, maxIndex);
                        maxIndex--;
                        swapArrayEls(filledIndexes, randomCol, maxFilledIndex);
                        maxFilledIndex--;
                        //a complete pair has been added to the square
                    }
                }

                found = magic();
                tryCt++;
            }//end while loop
        }//end if(size is even)

        if (!found)
            tryCt = -1;
        return tryCt;
    }

    //basic swap of elements in an array
    private static void swapArrayEls(int[] array, int index1, int index2) {
        int tempInt = array[index1];
        array[index1] = array[index2];
        array[index2] = tempInt;
    }

    // determine if a magic square has been created
    // i.e., check all rows, columns,
    // and diagonals sum to the same value
    private boolean magic() {
        boolean isMagic = true;
        int magicSum = (size * (size * size + 1)) / 2;
        int tempSum1 = 0;
        int tempSum2 = 0;

        for (int i = 0; i < size; i++) {
            tempSum1 += square[i][i];//sum of main diagonal
            tempSum2 += square[i][size - 1 - i];//sum of other diagonal
        }
        //if sum of diagonals doesn't match magicSum
        if (magicSum != tempSum1 || magicSum != tempSum2)
            isMagic = false;

        //checks first row & column simultaneously
        //then checks second row & column, etc
        //exits for loop if a row or column doesn't match the magicSum
        //or if all rows and columns have been checked
        for (int i = 0; isMagic && i < size; i++) {
            tempSum1 = 0;//reset sums before checking next row/col
            tempSum2 = 0;
            for (int j = 0; j < size; j++) {
                tempSum1 += square[i][j];//adds elements in row i
                tempSum2 += square[j][i];//adds elements in a column i
            }
            if (magicSum != tempSum1 || magicSum != tempSum2)
                isMagic = false;//if row i or column i doesn't match the magicSum
        }

        return isMagic;
    }

    // output the magic square (or whatever is in the array if it is not)
    public void out() {
        int row, col;
        for (row = 0; row < size; row++) {
            for (col = 0; col < size; col++) {
                System.out.print(String.format("%3d", square[row][col]));
            }
            System.out.println();
        }
    }

    // change to false if this algorithm was not implemented
    public boolean rowLastImplemented() {
        return true;
    }

    // change to false if this algorithm was not implemented
    public boolean pairsImplemented() {
        return true;
    }

    // put your name here
    public static String myName() {
        return "Cody Bradley";
    }

}