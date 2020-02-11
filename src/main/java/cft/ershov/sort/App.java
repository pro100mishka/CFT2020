package cft.ershov.sort;

import cft.ershov.sort.sorter.MergeSorter;

public class App {

    public static void main(String[] args) {
        MergeSorter s = new MergeSorter(args);
        s.start();
    }
}
