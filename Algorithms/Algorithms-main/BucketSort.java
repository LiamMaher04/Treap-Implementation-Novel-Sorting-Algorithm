import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BucketSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {
        if (input.length == 0) {
            return input.clone();
        }

        int[] result = input.clone();

        // Find min/max values
        int minValue = result[0];
        int maxValue = result[0];
        for (int i = 1; i < result.length; i++) {
            if (result[i] < minValue) {
                minValue = result[i];
            } else if (result[i] > maxValue) {
                maxValue = result[i];
            }
        }

        // Calculate bucket parameters
        int offset = -minValue;
        int range = maxValue - minValue + 1;
        int bucketCount = Math.max(1, Math.min(range, result.length));

        // Initialize buckets
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribute elements into buckets
        for (int value : result) {
            int shiftedValue = value + offset;
            int bucketIndex = (int) ((long) bucketCount * shiftedValue / range);
            buckets.get(bucketIndex).add(value);
        }

        // Sort buckets and merge
        int index = 0;
        for (List<Integer> bucket : buckets) {
            insertionSort(bucket);
            for (int value : bucket) {
                result[index++] = value;
            }
        }

        return result;
    }

    // Insertion sort implementation for buckets
    private void insertionSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            int key = bucket.get(i);
            int j = i - 1;

            while (j >= 0 && bucket.get(j) > key) {
                bucket.set(j + 1, bucket.get(j));
                j--;
            }
            bucket.set(j + 1, key);
        }
    }

    public static void main(String[] args) {
        int[] input = new BucketSort().sampleArray();
        int[] sorted = new BucketSort().timeSort(input);
        System.out.println(Arrays.toString(sorted));
    }
}
