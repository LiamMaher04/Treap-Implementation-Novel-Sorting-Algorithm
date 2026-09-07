import java.util.Arrays;

public class HeapSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {
        int n = input.length;
        int[] result = Arrays.copyOf(input, n);

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(result, n, i);

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = result[0];
            result[0] = result[i];
            result[i] = temp;

            // Call heapify on reduced heap
            heapify(result, i, 0);
        }

        return result;
    }

    void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest])
            largest = left;

        if (right < n && arr[right] > arr[largest])
            largest = right;

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
    public static void main(String[] args) {
        int[] input = new HeapSort().sampleArray();
        int[] sorted = new HeapSort().timeSort(input);
        System.out.println(Arrays.toString(sorted));
    }

}
