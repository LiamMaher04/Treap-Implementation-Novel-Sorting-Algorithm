import java.util.Arrays;

public class SelectionSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {
        int temp;
        int length = input.length;
        int min;

        for (int i = 0; i < length - 1; i++) {
            min = i;

            for (int j = i + 1; j < length; j++) {
                if (input[min] > input[j]) {
                    min = j;
                }
            }

            temp = input[i];
            input[i] = input[min];
            input[min] = temp;
        }
        return input;
    }
    public static void main(String[] args) {
        int[] input = new SelectionSort().sampleArray();
        int[] sorted = new SelectionSort().timeSort(input);
        System.out.println(Arrays.toString(sorted));
    }

}
