import java.util.Arrays;

public class ProposedAlgorithm implements SortingAlgorithm {

    @Override
    public int[] sort(int[] input) {
        int[] result = input.clone(); // Work on a copy to preserve original
        int n = result.length;

        // Step 1: Find max and min
        int max = result[0], min = result[0];
        for (int value : result) {
            if (value > max) max = value;
            if (value < min) min = value;
        }

        int offset = -min; // To shift negatives into positive indices

        // Step 2: Declare arrays
        int zeroCount = 0;                      // For zero count
        int[] countp = new int[max + 1];           // For positive values
        int[] countn = new int[offset + 1];        // For negative values

        // Step 3: Process input values
        for (int x = 0; x < n; x++) {
            int val = result[x];

            if (val == 0) {
                zeroCount++;
            } else if (val > 0) {
                countp[val]++;
            } else { // val < 0
                int negIndex = val + offset;
                countn[negIndex]++;
            }
        }

        // Step 4: Create sorted array
        int index = 0;

        // Add sorted negative values from smallest to largest
        for (int i = 0; i <= offset; i++) {
            int value = -offset + i;  // Convert index back to original negative value
            for (int j = 0; j < countn[i]; j++) {
                result[index++] = value;
            }
        }

        // Add zeros
        for (int i = 0; i < zeroCount; i++) {
            result[index++] = 0;
        }

        // Add sorted positive values
        for (int i = 0; i <= max; i++) {
            for (int j = 0; j < countp[i]; j++) {
                result[index++] = i;
            }
        }

        return result;
    }
    public static void main(String[] args) {
        int[] input = new ProposedAlgorithm().sampleArray();
        int[] sorted = new  ProposedAlgorithm().timeSort(input);
        System.out.println(Arrays.toString(sorted));  
    }
}
