package ch.zhaw.ads;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class QuickerSortServer extends RecursiveAction implements CommandExecutor {
    private final int DATARANGE = 10000000;
    public int dataElems; // number of data
    public static int insertion_threshold;

    // for FJ
    private final int SPLIT_THRESHOLD = 10000;
    private int[] a;
    private int left;
    private int right;
    ForkJoinPool forkJoinPool;

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

    public void swap(int[] a, int i, int j) {
        int h = a[i]; a[i] = a[j]; a[j] = h;
    }

    // insertion sort over interval
    void insertionSort(int[] a, int left, int right) {
        // TODO 12.1
        for (int i = left; i <= right; i++) {
            int current = a[i];
            int j;
            for (j = i; (j > left && a[j-1] > current); j--) {
                a[j] = a[j - 1];
            }
            a[j] = current;
        }
    }

    int partition(int arr[], int left, int right) {
        // TODO 12.1
        int pivot = arr[(left + right) / 2];

        while(left <= right) { // loop solange no overlap
            while(arr[left] < pivot) left ++;
            while(arr[right] > pivot) right --;

            if(left <= right) { // check again if already overlap
                swap(arr, left, right);
                left++;
                right--;
            }
        }
        return left;
    }

    void quickerSort(int arr[], int left, int right) {
        // TODO 12.1
        if(right - left <= insertion_threshold) {
            insertionSort(arr, left, right);
            return;
        }

        int pivotIndex = partition(arr, left, right);
        quickerSort(arr, left, pivotIndex - 1);
        quickerSort(arr, pivotIndex, right);
    }

    void quickerSort(int arr[]) {
        quickerSort(arr, 0, arr.length - 1);
    }

    public QuickerSortServer() {}

    public QuickerSortServer(int[] a, int left, int right) {
        this.a = a;
        this.left = left;
        this.right = right;
    }

    public void compute() {
        // TODO 12.2
        int pivot = 0;
        if(left < right) {
            pivot = partition(a, left, right);

            if(pivot - left > SPLIT_THRESHOLD && right - pivot > SPLIT_THRESHOLD) {
                ForkJoinTask task1 = new QuickerSortServer(a, left, pivot-1);
                ForkJoinTask task2 = new QuickerSortServer(a, pivot, right);
                invokeAll(task1, task2);
            } else {
                quickerSort(a, left, pivot-1);
                quickerSort(a, pivot, right);
            }
        }

    }

    void quickerSortFJ(int[] a) {
        // TODO 12.2
        int parallelism = Runtime.getRuntime().availableProcessors() * 2; // fixwert für pool size
        forkJoinPool = new ForkJoinPool(parallelism);
        forkJoinPool.invoke(new QuickerSortServer(a, 0, a.length - 1));

    }

    public double measureTime(Supplier<int[]> generator, Consumer<int[]> sorter) throws Exception {
        double elapsed = 0;

        int[] a = generator.get();
        int[] b = new int[dataElems];

        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        int count = 0;
        while (endTime < startTime + 1000) {
            System.arraycopy(a, 0, b, 0, a.length);
            sorter.accept(b);
            count++;
            endTime = System.currentTimeMillis();
        }
        elapsed = (double) (endTime - startTime) / count;
        if (!isSorted(b)) throw new Exception ("ERROR not eorted");
        return elapsed;
    }

    public String execute(String arg) {
        // Java 9: use Map.of instead
        Map<String,Consumer<int[]>> sorter =  new HashMap<>();
        sorter.put("QUICKER", this::quickerSort);
        sorter.put("QUICKERFJ", this::quickerSortFJ);

        Map<String,Supplier<int[]>> generator =  new HashMap<>();
        generator.put("RANDOM", this::randomData);

        String args[] = arg.toUpperCase().split("\\s+");
        dataElems = Integer.parseInt(args[2]);
        insertion_threshold = Integer.parseInt(args[3]);
        try {
            double time = measureTime(generator.get(args[1]), sorter.get(args[0]));
            return arg + " "+Double.toString(time)+" ms\n";
        } catch (Exception ex){
            return arg + " "+ ex.getMessage()+"\n";
        }
    }

    public static void main(String[] args) throws Exception {
        QuickerSortServer sorter = new QuickerSortServer();
        String sort;
        sort = "QUICKER RANDOM 1000000 35"; System.out.print(sorter.execute(sort));
        sort = "QUICKER RANDOM 1000000 36"; System.out.print(sorter.execute(sort));
        sort = "QUICKER RANDOM 1000000 37"; System.out.print(sorter.execute(sort));

        sort = "QUICKERFJ RANDOM 1000000 37"; System.out.print(sorter.execute(sort));

    }

}