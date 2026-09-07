// benchmark/SortingBenchmark.java
package tree;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SortingBenchmark {
    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1000, 5000, 10000};
        String[] patterns = {"Random", "Nearly Sorted", "Reverse Sorted"};

        List<SortingAlgorithm> algorithms = Arrays.asList(
                new TreapSort(),
                new PQSort(),
                new JavaSort(),
                new QuickSort(),
                new MergeSort()
        );

        String[] names = {"TreapSort", "PQSort", "JavaSort", "QuickSort", "MergeSort"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sorting_benchmarks.csv"))) {
            writer.write("Algorithm,Size,Pattern,Trial,Time (ms),Memory (KB)\n");

            for (int i = 0; i < algorithms.size(); i++) {
                SortingAlgorithm algo = algorithms.get(i);
                String name = names[i];

                for (int size : sizes) {
                    for (String pattern : patterns) {
                        for (int trial = 1; trial <= 30; trial++) {
                            int[] input = generateArray(size, pattern);
                            Runtime runtime = Runtime.getRuntime();
                            runtime.gc(); // best effort GC

                            long memBefore = (runtime.totalMemory() - runtime.freeMemory()) / 1024;
                            long start = System.nanoTime();
                            algo.sort(Arrays.copyOf(input, input.length)); // ensure fresh input
                            long end = System.nanoTime();
                            long memAfter = (runtime.totalMemory() - runtime.freeMemory()) / 1024;

                            double timeMs = (end - start) / 1_000_000.0;
                            long memoryUsed = memAfter - memBefore;

                            writer.write(String.format(Locale.US, "%s,%d,%s,%d,%.4f,%d\n",
                                    name, size, pattern, trial, timeMs, memoryUsed));
                        }
                    }
                }
            }
        }

        System.out.println("Benchmark complete. Results saved to sorting_benchmarks.csv");
    }

    private static int[] generateArray(int size, String pattern) {
        int[] arr = new int[size];
        Random rand = new Random();

        switch (pattern) {
            case "Reverse Sorted":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Nearly Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                for (int i = 0; i < size / 10; i++) {
                    int a = rand.nextInt(size);
                    int b = rand.nextInt(size);
                    int tmp = arr[a];
                    arr[a] = arr[b];
                    arr[b] = tmp;
                }
                break;
            default: // Random
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(100000);
        }

        return arr;
    }
}
