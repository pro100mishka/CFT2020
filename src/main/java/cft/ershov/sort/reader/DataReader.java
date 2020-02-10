package cft.ershov.sort.reader;

public interface DataReader <E extends Comparable<E>> {
    E read();
    void close();
}
