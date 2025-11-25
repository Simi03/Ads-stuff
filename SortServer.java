package ch.zhaw.ads;

import java.util.*;
import java.util.function.*;

public class SortServer implements CommandExecutor {
    private final int DATARANGE = 10000000;
    public int dataElems; // number of data

    public void swap(int[] a, int i, int j) {
        int h = a[i];
        a[i] = a[j];
        a[j] = h;
    }

    public void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean noswap = true;
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    swap(a, i, j);
                    noswap = false;
                }
            }
        }
    }

    public void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int current = a[i];
            int j;
            for (j = i; (j > 0 && a[j-1] > current); j--) {
                a[j] = a[j - 1];
            }
            a[j] = current;
        }
    }

    public void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIdx]) {
                    minIdx = j;
                }
            }
            swap(a, i, minIdx);
        }
    }

    public boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int[] randomData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * DATARANGE);
        }

        return a;
    }

    public int[] ascendingData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < dataElems; i++) {
            a[i] = i;
        }

        return a;
    }

    public int[] descendingData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < dataElems; i++) {
            a[dataElems - (i + 1)] = i;
        }

        return a;
    }

    // measure time of sorting algorithm
    // generator to generate the data
    // consumer sorts the data
    // return elapsed time in ms
    // if data is not sorted an exception is thrown
    public double measureTime(Supplier<int[]> generator, Consumer<int[]> sorter) throws Exception {
        long start = System.currentTimeMillis();
        long end;
        int count = 0;

        do {
            count++;
            end = System.currentTimeMillis();
        } while (end - start < 1000);


//         if (!isSorted(b)) throw new Exception ("ERROR not eorted");
        return (double) (end - start) / count;
    }

    public String execute(String arg) {
        // Java 9: use Map.of instead
        Map<String, Consumer<int[]>> sorter = new HashMap<>();
        sorter.put("BUBBLE", this::bubbleSort);
        sorter.put("INSERTION", this::insertionSort);
        sorter.put("SELECTION", this::selectionSort);

        Map<String, Supplier<int[]>> generator = new HashMap<>();
        generator.put("RANDOM", this::randomData);
        generator.put("ASC", this::ascendingData);
        generator.put("DESC", this::descendingData);

        String args[] = arg.toUpperCase().split("\\s+");
        dataElems = Integer.parseInt(args[2]);
        try {
            double time = measureTime(generator.get(args[1]), sorter.get(args[0]));
            return arg + " " + Double.toString(time) + " ms\n";
        } catch (Exception ex) {
            return arg + " " + ex.getMessage() + "\n";
        }
    }

    public static void main(String[] args) throws Exception {
        SortServer sorter = new SortServer();
        String sort;
        sort = "BUBBLE RANDOM 10000";
        System.out.print(sorter.execute(sort));
        sort = "SELECTION RANDOM 10000"; System.out.print(sorter.execute(sort));
        sort = "INSERTION RANDOM 10000"; System.out.print(sorter.execute(sort));

        sort = "BUBBLE ASC 10000"; System.out.print(sorter.execute(sort));
        sort = "SELECTION ASC 10000"; System.out.print(sorter.execute(sort));
        sort = "INSERTION ASC 10000"; System.out.print(sorter.execute(sort));
    }
}