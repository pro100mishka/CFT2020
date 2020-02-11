package cft.ershov.sort.sorter;

import cft.ershov.sort.reader.IntegerFileReader;
import cft.ershov.sort.reader.StringFileReader;
import cft.ershov.sort.reader.TypeFileReader;
import cft.ershov.sort.separator.ArgSeparator;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class MergeSorter {

    private List<TypeFileReader> typeFileReaders;
    private List<Comparable> buffer;
    private boolean increase;
    private boolean first;
    private Comparable temp;
    private Comparable lastWriteValue;
    private BufferedWriter fileWriter;
    private ArgSeparator separator;

    public MergeSorter(String [] arg) {
        this.separator = new ArgSeparator(arg);
    }

    private boolean init(){
        if (separator.separation()){
            this.increase = separator.getSortMode();
            this.buffer = new ArrayList<>();
            this.first = true;
            this.typeFileReaders = new LinkedList<>();
            for (String s: separator.getInpFileNames()) {
                TypeFileReader typeFileReader = separator.getTypeMode() ? new StringFileReader(s) : new IntegerFileReader(s);
                typeFileReaders.add(typeFileReader);
            }
            try {
                this.fileWriter = new BufferedWriter(new FileWriter(separator.getOutFileName()));
            } catch (IOException e) {
                log.error("File can't save!");
                return false;
            }
            return true;
        }
        return false;
    }

    public void start() {
        if (!init()) return;
        long start = System.currentTimeMillis();
        bufferFilling();
        int index = 0;
        try {
            while (buffer.size()>0){
                log.debug("--------");
                index = checkToIndex(index);
                log.debug("B before"+buffer);
                log.debug("T "+temp);
                checkToWrite(index);
                temp=null;
                log.debug("LW "+lastWriteValue+" Index: "+index);
                log.debug("B after"+buffer);
            }
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                log.error("FileWriter closed error.");
            }
        }
        long end = System.currentTimeMillis()-start;
        log.info("Task execution time: "+ TimeUnit.MILLISECONDS.toSeconds(end));
    }

    private int checkToIndex(int index) {
        for (int i = 0; i <buffer.size() ; i++) {
            if (buffer.get(i) != null){
                if (temp == null) temp = buffer.get(i);
                if (compare(buffer.get(i),temp)){
                    index = i;
                    temp = buffer.get(index);
                }
            }
        }
        return index;
    }

    private void checkToWrite(int index) {
        if (temp!=null) {
            if (lastWriteValue==null) lastWriteValue =temp;
            if (compare(lastWriteValue,temp)) {
                lastWriteValue = temp;
                write(temp, index);
            }
            getNext(index);
        }
    }

    private void getNext(int index) {
        Comparable value = typeFileReaders.get(index).read();
        if (value == null){
            buffer.remove(index);
            typeFileReaders.get(index).close();
            typeFileReaders.remove(index);
        } else {
            buffer.set(index, value);
        }
    }

    private void bufferFilling() {
        for (TypeFileReader f: typeFileReaders) {
            buffer.add(f.read());
        }
    }


    private   void write(Comparable temp, int index) {
        try {
            if (first){
                fileWriter.write(temp.toString());
                this.first=false;
            } else {
                fileWriter.write("\n"+ temp.toString());
            }
        } catch (IOException e){
            e.fillInStackTrace();
        }
    }

    private  boolean compare(Comparable applicant, Comparable temp){
        if (increase){
            return applicant.compareTo(temp) <= 0;
        } else {
            return applicant.compareTo(temp) >= 0;
        }
    }
}
