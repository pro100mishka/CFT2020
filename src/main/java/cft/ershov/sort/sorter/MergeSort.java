package cft.ershov.sort.sorter;

public interface MergeSort {
    void start();
    void getNext(int index);
    void bufferFilling();
    <E extends Comparable<E>>  void write(E temp, int index);
    <E extends Comparable<E>> boolean compare(E applicant, E temp);
    boolean getIncrease();
}
