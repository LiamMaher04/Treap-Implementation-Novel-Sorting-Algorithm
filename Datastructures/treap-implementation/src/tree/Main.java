package tree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Define data sizes and patterns
        int[] sizes = {100, 1000, 10000};
        String[] patterns = {"Random", "Sorted Ascending", "Sorted Descending", "Partially Sorted"};

        // Create adapters
        DataStructureTest avlAdapter = new AVLTreeMapAdapter();
        DataStructureTest treapAdapter = new TreapAdapter();
        DataStructureTest treeMapAdapter = new TreeMapAdapter();

        // Output CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("benchmark_results.csv"))) {
            // Header
            writer.write("Size,Pattern,,,,,,," +
                    ",,,," + // blank for spacing
                    "AVL Tree,,,,,,," +
                    ",,,," + // blank for spacing
                    "Treap,,,,,,," +
                    ",,,," + // blank for spacing
                    "TreeMap,,,,,,\n");

            writer.write(",,,,,,," +
                    ",,,," +
                    "Trial,Insert(ms),Batch(ms),Search(ms),Unsuccessful(ms),Delete(ms),Traversal(ms)" +
                    ",,,," +
                    "Trial,Insert(ms),Batch(ms),Search(ms),Unsuccessful(ms),Delete(ms),Traversal(ms)" +
                    ",,,," +
                    "Trial,Insert(ms),Batch(ms),Search(ms),Unsuccessful(ms),Delete(ms),Traversal(ms)\n");

            // Iterate over sizes and patterns
            for (int size : sizes) {
                for (String pattern : patterns) {
                    int[] testData = generateArray(size, pattern);
                    int[] batchData = generateArray(size / 10, "Random");
                    int nonExistentKey = Integer.MAX_VALUE;

                    // Accumulators for each data structure (per case)
                    double[] avlTotalInsert = new double[30];
                    double[] avlTotalBatchInsert = new double[30];
                    double[] avlTotalSearch = new double[30];
                    double[] avlTotalUnsuccessful = new double[30];
                    double[] avlTotalDelete = new double[30];
                    double[] avlTotalTraversal = new double[30];

                    double[] treapTotalInsert = new double[30];
                    double[] treapTotalBatchInsert = new double[30];
                    double[] treapTotalSearch = new double[30];
                    double[] treapTotalUnsuccessful = new double[30];
                    double[] treapTotalDelete = new double[30];
                    double[] treapTotalTraversal = new double[30];

                    double[] treeMapTotalInsert = new double[30];
                    double[] treeMapTotalBatchInsert = new double[30];
                    double[] treeMapTotalSearch = new double[30];
                    double[] treeMapTotalUnsuccessful = new double[30];
                    double[] treeMapTotalDelete = new double[30];
                    double[] treeMapTotalTraversal = new double[30];

                    // Run tests for each trial (30 trials per case)
                    for (int trial = 1; trial <= 30; trial++) {
                        // Run tests for each data structure
                        BenchmarkResult avlResult = runTest(avlAdapter, trial, testData, batchData, nonExistentKey);
                        BenchmarkResult treapResult = runTest(treapAdapter, trial, testData, batchData, nonExistentKey);
                        BenchmarkResult treeMapResult = runTest(treeMapAdapter, trial, testData, batchData, nonExistentKey);

                        // Store results for averaging
                        avlTotalInsert[trial - 1] = avlResult.insert;
                        avlTotalBatchInsert[trial - 1] = avlResult.batchInsert;
                        avlTotalSearch[trial - 1] = avlResult.search;
                        avlTotalUnsuccessful[trial - 1] = avlResult.unsuccessful;
                        avlTotalDelete[trial - 1] = avlResult.delete;
                        avlTotalTraversal[trial - 1] = avlResult.traversal;

                        treapTotalInsert[trial - 1] = treapResult.insert;
                        treapTotalBatchInsert[trial - 1] = treapResult.batchInsert;
                        treapTotalSearch[trial - 1] = treapResult.search;
                        treapTotalUnsuccessful[trial - 1] = treapResult.unsuccessful;
                        treapTotalDelete[trial - 1] = treapResult.delete;
                        treapTotalTraversal[trial - 1] = treapResult.traversal;

                        treeMapTotalInsert[trial - 1] = treeMapResult.insert;
                        treeMapTotalBatchInsert[trial - 1] = treeMapResult.batchInsert;
                        treeMapTotalSearch[trial - 1] = treeMapResult.search;
                        treeMapTotalUnsuccessful[trial - 1] = treeMapResult.unsuccessful;
                        treeMapTotalDelete[trial - 1] = treeMapResult.delete;
                        treeMapTotalTraversal[trial - 1] = treeMapResult.traversal;

                        // Write trial results
                        writer.write(String.format("%d,%s,,,,,,,", size, pattern) + // pattern and spacing
                                avlResult.toCsv(trial) + ",,,,," +                    // AVL result and spacing
                                treapResult.toCsv(trial) + ",,,,," +                  // Treap result and spacing
                                treeMapResult.toCsv(trial) + "\n");                   // TreeMap result
                    }

                    // Calculate averages per data structure after 30 trials
                    double avlAvgInsert = calculateAverage(avlTotalInsert);
                    double avlAvgBatchInsert = calculateAverage(avlTotalBatchInsert);
                    double avlAvgSearch = calculateAverage(avlTotalSearch);
                    double avlAvgUnsuccessful = calculateAverage(avlTotalUnsuccessful);
                    double avlAvgDelete = calculateAverage(avlTotalDelete);
                    double avlAvgTraversal = calculateAverage(avlTotalTraversal);

                    double treapAvgInsert = calculateAverage(treapTotalInsert);
                    double treapAvgBatchInsert = calculateAverage(treapTotalBatchInsert);
                    double treapAvgSearch = calculateAverage(treapTotalSearch);
                    double treapAvgUnsuccessful = calculateAverage(treapTotalUnsuccessful);
                    double treapAvgDelete = calculateAverage(treapTotalDelete);
                    double treapAvgTraversal = calculateAverage(treapTotalTraversal);

                    double treeMapAvgInsert = calculateAverage(treeMapTotalInsert);
                    double treeMapAvgBatchInsert = calculateAverage(treeMapTotalBatchInsert);
                    double treeMapAvgSearch = calculateAverage(treeMapTotalSearch);
                    double treeMapAvgUnsuccessful = calculateAverage(treeMapTotalUnsuccessful);
                    double treeMapAvgDelete = calculateAverage(treeMapTotalDelete);
                    double treeMapAvgTraversal = calculateAverage(treeMapTotalTraversal);

                    // Write averages to CSV
                    writer.write(String.format(",,,%s Averages,,,,,,,", pattern) + "\n");
                    writer.write(String.format(",,,Average,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f\n",
                            avlAvgInsert, avlAvgBatchInsert, avlAvgSearch, avlAvgUnsuccessful, avlAvgDelete, avlAvgTraversal));

                    writer.write(String.format(",,,Treap Averages,,,,,,,") + "\n");
                    writer.write(String.format(",,,Average,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f\n",
                            treapAvgInsert, treapAvgBatchInsert, treapAvgSearch, treapAvgUnsuccessful, treapAvgDelete, treapAvgTraversal));

                    writer.write(String.format(",,,TreeMap Averages,,,,,,,") + "\n");
                    writer.write(String.format(",,,Average,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f\n",
                            treeMapAvgInsert, treeMapAvgBatchInsert, treeMapAvgSearch, treeMapAvgUnsuccessful, treeMapAvgDelete, treeMapAvgTraversal));
                }
            }
        }
    }

    private static double calculateAverage(double[] totals) {
        double sum = 0;
        for (double val : totals) {
            sum += val;
        }
        return sum / totals.length;
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

        public String toCsv(int trial) {
            return String.format("%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f",
                    trial, insert, batchInsert, search, unsuccessful, delete, traversal);
        }
    }
}

