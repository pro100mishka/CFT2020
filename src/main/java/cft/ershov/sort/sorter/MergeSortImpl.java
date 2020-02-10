package cft.ershov.sort.sorter;

import cft.ershov.sort.reader.FileReader;
import cft.ershov.sort.reader.FileReaderImp;
import cft.ershov.sort.separator.ArgSeparator;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.List;

@Log4j2
public class MergeSortImpl implements MergeSort {

    private ArgSeparator separator;
    private String[] arg;
    private List<FileReader> fileReaders;

    public MergeSortImpl(String[] arg) {
        this.arg = arg;
    }

    private boolean prepare(){
        separator = new ArgSeparator(arg);
        if (separator.separation()){
            fileReaders = new LinkedList<>();
            for (String s: separator.getInpFileNames()) {
                fileReaders.add(new FileReaderImp(s,separator.getTypeMode()));
            }
        }
        if (fileReaders.isEmpty()){
            log.error("Incoming files do not exist. Program interrupted");
            return false;
        }

        return false;
    }

    @Override
    public void start() {
    }

    @Override
    public void getNext(int index) {

    }

    @Override
    public void bufferFilling() {

    }

    @Override
    public <E extends Comparable<E>> void write(E temp, int index) {

    }

    @Override
    public <E extends Comparable<E>> boolean compare(E applicant, E temp) {
        return false;
    }

    @Override
    public boolean getIncrease() {
        return false;
    }
}
