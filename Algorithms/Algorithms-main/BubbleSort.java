import java.util.Arrays;


public class BubbleSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {

        int length = input.length;
        for (int i = 0; i < length; ++i){
            for (int j = 0; j < length -i-1; ++j){
                if (input[j+1] < input[j]){
                    int temp = input[j];
                    input[j] = input[j+1];
                    input[j+1] = temp;
                }
            }
        }
        return input;
    }
    public static void main(String[] args) {
        int[] input = new BubbleSort().sampleArray();
        int[] sorted = new BubbleSort().timeSort(input);
        System.out.println(Arrays.toString(sorted));
    }
}
