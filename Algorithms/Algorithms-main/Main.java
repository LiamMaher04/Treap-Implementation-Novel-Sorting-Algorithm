import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    // Generate a random array of given size, with values in [-8, 8]
    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(17) - 8;
        }
        return array;
    }

    public static void main(String[] args) {
        // Test sizes and algorithms
        int[] testSizes = {10, 100, 1000, 10000, 100000};
        List<SortingAlgorithm> algorithms = Arrays.asList(
                new QuickSort(),
                new BubbleSort(),
                new BucketSort(),
                new MergeSort(),
                new HeapSort(),
                new InsertionSort(),
                new SelectionSort(),
                new ProposedAlgorithm()
        );

        // Pre-generate one master array per size
        Map<Integer,int[]> testArrays = new HashMap<>();
        for (int size : testSizes) {
            testArrays.put(size, generateRandomArray(size));
        }

        final int TRIALS = 30;
        String outFile = "30_large_sorting_benchmarks.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            // Write CSV header
            StringBuilder header = new StringBuilder("Algorithm,Array Size");
            for (int t = 1; t <= TRIALS; t++) {
                header.append(",Run ").append(t).append(" (ms)");
            }
            writer.write(header.toString());
            writer.newLine();

            // For each algorithm and size...
            for (SortingAlgorithm algo : algorithms) {
                String name = algo.getClass().getSimpleName();
                for (int size : testSizes) {
                    // Prepare a row
                    StringBuilder row = new StringBuilder();
                    row.append(name).append(",").append(size);

                    // Run 30 trials
                    for (int t = 0; t < TRIALS; t++) {
                        // Fresh copy of the master array
                        int[] input = Arrays.copyOf(testArrays.get(size), size);

                        long start = System.nanoTime();
                        algo.sort(input);
                        long end = System.nanoTime();

                        double ms = (end - start) / 1_000_000.0;
                        row.append(",").append(String.format("%.3f", ms));
                    }

                    // Write that row
                    writer.write(row.toString());
                    writer.newLine();
                }
            }

            System.out.println("Benchmark results saved to " + outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
