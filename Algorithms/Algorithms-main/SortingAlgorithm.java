import java.util.Random;

public interface SortingAlgorithm {
    /**
     * Sort an array of integers
     * @param input The array to be sorted
     * @return The sorted array
     */
    int[] sort(int[] input);

    /**
     * Times the execution of the sorting algorithm
     * @param input The array to be sorted
     */
    default int[] timeSort(int[] input) {
        long startTime = System.nanoTime();
        int[] sorted = sort(input);
        long endTime = System.nanoTime();
        System.out.println ("Milliseconds: " + (endTime - startTime)/ 1_000_000); // Convert nanoseconds to milliseconds
        return sorted;
    }

    /**
     * sampleArray
     * @param input The array to be sorted
     */
// Original fixed sample array
    default int[] sampleArray() {
        return new int[] {4, -2, 1, 0, -3, 2, 0, -1, -8, 7};
    }

    // New: Generate random arrays (-8 to 8) for different sizes
    default int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000000) - 8; // -8 to 8 inclusive
        }
        return arr;
    }

    // Convenience methods for common sizes
    default int[] random10() {
        return generateRandomArray(10);
    }

    default int[] random100() {
        return generateRandomArray(100);
    }

    default int[] random1000() {
        return generateRandomArray(1000);
    }

    default int[] random10000() {
        return generateRandomArray(10000);
    }

    default int[] random100000() {
        return generateRandomArray(100000);
    }}
