package cft.ershov.sort;

import cft.ershov.sort.reader.FileReader;
import cft.ershov.sort.reader.FileReaderImp;
import cft.ershov.sort.separator.ArgSeparator;

import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args)  {
        ArgSeparator separator = new ArgSeparator(args);
        separator.separation();
        List<FileReader> fileReaders = new LinkedList<>();
        for (String s: separator.getInpFileNames()) {
            fileReaders.add(new FileReaderImp(s,separator.getTypeMode()));
        }

        for (FileReader f: fileReaders) {
            Comparable test;
            do {
                test = f.read();
                if (test!=null) System.out.println(test);
            } while (test!=null);
            System.out.println();
        }
    }
}
