import java.util.Arrays;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {
        int length = input.length;
        for (int i = 0; i < length; ++i){
            int current = input[i];
            int j = i-1;
            while (j >= 0 && (input[j] >= current)) {
                input[j+1] = input[j];
                j--;
            }
            input[++j] = current;
        }
        return input;
    }
    public static void main(String[] args) {
        int[] input = new InsertionSort().sampleArray();
        int[] sorted = new InsertionSort().timeSort(input);
        System.out.println(Arrays.toString(sorted));
    }

}
