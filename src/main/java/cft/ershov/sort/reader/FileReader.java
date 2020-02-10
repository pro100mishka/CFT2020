package cft.ershov.sort.reader;

public interface FileReader <E extends Comparable<E>> {
    E read();
    void close();
}
