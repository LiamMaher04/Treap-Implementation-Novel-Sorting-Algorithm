// File: TreapBenchmark.java
package tree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TreapBenchmark {
    public static void main(String[] args) throws IOException {
        // Test setup for Treap
        DataStructureTest treapAdapter = new TreapAdapter();

        // Define the size and pattern for the array
        int size = 10000;
        String pattern = "Random";

        // Prepare output CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("treap_benchmark_results.csv"))) {
            // Write header
            writer.write("Trial,Insert(ms),Batch Insert(ms),Search(ms),Unsuccessful Search(ms),Delete(ms),In-Order Traversal(ms)\n");

            double totalInsert = 0, totalBatchInsert = 0, totalSearch = 0, totalUnsuccessfulSearch = 0, totalDelete = 0, totalTraversal = 0;

            // Run the tests for 30 trials
            for (int trial = 1; trial <= 30; trial++) {
                // Generate test data
                int[] testData = generateArray(size, pattern);
                int[] batchData = generateArray(size / 10, "Random");
                int nonExistentKey = Integer.MAX_VALUE;

                // Run the test
                BenchmarkResult treapResult = runTest(treapAdapter, trial, testData, batchData, nonExistentKey);

                // Write the result for the trial
                writer.write(String.format("%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f\n",
                        trial, treapResult.insert, treapResult.batchInsert, treapResult.search,
                        treapResult.unsuccessful, treapResult.delete, treapResult.traversal));

                // Accumulate totals for averaging later
                totalInsert += treapResult.insert;
                totalBatchInsert += treapResult.batchInsert;
                totalSearch += treapResult.search;
                totalUnsuccessfulSearch += treapResult.unsuccessful;
                totalDelete += treapResult.delete;
                totalTraversal += treapResult.traversal;
            }

            // Calculate and print the averages
            double avgInsert = totalInsert / 30;
            double avgBatchInsert = totalBatchInsert / 30;
            double avgSearch = totalSearch / 30;
            double avgUnsuccessfulSearch = totalUnsuccessfulSearch / 30;
            double avgDelete = totalDelete / 30;
            double avgTraversal = totalTraversal / 30;

            writer.write(String.format("\nAverage,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f\n", avgInsert, avgBatchInsert, avgSearch, avgUnsuccessfulSearch, avgDelete, avgTraversal));
        }
    }

    private static BenchmarkResult runTest(DataStructureTest adapter, int trial, int[] data, int[] batchData, int nonExistentKey) throws IOException {
        adapter.setup(data);

        long t1 = System.nanoTime();
        for (int key : data) adapter.insert(key, key);
        long t2 = System.nanoTime();
        double insert = (t2 - t1) / 1_000_000.0;

        t1 = System.nanoTime();
        adapter.batchInsert(batchData);
        t2 = System.nanoTime();
        double batchInsert = (t2 - t1) / 1_000_000.0;

        t1 = System.nanoTime();
        for (int key : data) adapter.search(key);
        t2 = System.nanoTime();
        double search = (t2 - t1) / 1_000_000.0;

        t1 = System.nanoTime();
        adapter.search(nonExistentKey);
        t2 = System.nanoTime();
        double unsuccessful = (t2 - t1) / 1_000_000.0;

        t1 = System.nanoTime();
        for (int key : data) adapter.delete(key);
        t2 = System.nanoTime();
        double delete = (t2 - t1) / 1_000_000.0;

        t1 = System.nanoTime();
        adapter.inOrderTraversal();
        t2 = System.nanoTime();
        double traversal = (t2 - t1) / 1_000_000.0;

        adapter.clear();
        return new BenchmarkResult(insert, batchInsert, search, unsuccessful, delete, traversal);
    }

    private static int[] generateArray(int size, String pattern) {
        int[] arr = new int[size];
        Random rand = new Random();
        switch (pattern) {
            case "Sorted Ascending":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Sorted Descending":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Partially Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                for (int i = 0; i < size / 10; i++) {
                    int a = rand.nextInt(size);
                    int b = rand.nextInt(size);
                    int temp = arr[a];
                    arr[a] = arr[b];
                    arr[b] = temp;
                }
                break;
            default: // Random
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(100000);
        }
        return arr;
    }

    // Helper to hold benchmark results
    static class BenchmarkResult {
        double insert, batchInsert, search, unsuccessful, delete, traversal;

        public BenchmarkResult(double insert, double batchInsert, double search, double unsuccessful, double delete, double traversal) {
            this.insert = insert;
            this.batchInsert = batchInsert;
            this.search = search;
            this.unsuccessful = unsuccessful;
            this.delete = delete;
            this.traversal = traversal;
        }
    }
}
