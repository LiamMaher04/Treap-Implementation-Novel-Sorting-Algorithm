import java.util.Arrays;

public class MergeSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {
        int length = input.length;

        if (length < 2) return input;

        int middle = length / 2;
        int[] leftHalf = Arrays.copyOfRange(input, 0,middle);
        int[] rightHalf = Arrays.copyOfRange(input, middle ,length);

        new MergeSort().sort(leftHalf);
        new MergeSort().sort(rightHalf);

        merge(input, leftHalf, rightHalf);
        return input;
    }

    public void merge (int[] data, int[] leftHalf, int[] rightHalf){
        int lSize = leftHalf.length;
        int rSize = rightHalf.length;
        int lIndex = 0, rIndex = 0, dIndex = 0;
        while( (lIndex < lSize) && (rIndex < rSize )) {
            if(leftHalf[lIndex] < (rightHalf[rIndex])) {
                data[dIndex] = leftHalf[lIndex];
                lIndex++;
            } else {
                data[dIndex] = rightHalf[rIndex];
                rIndex++;
            }
            dIndex++;
        }

        while(lIndex < lSize) {
            data[dIndex] = leftHalf[lIndex];
            lIndex++;
            dIndex++;
        }

        while(rIndex < rSize) {
            data[dIndex] = rightHalf[rIndex];
            rIndex++;
            dIndex++;
        }
    }

    public static void main(String[] args) {
        int[] input = new MergeSort().sampleArray();
        int[] sorted = new MergeSort().timeSort(input);
        System.out.println(Arrays.toString(sorted));
    }

}
