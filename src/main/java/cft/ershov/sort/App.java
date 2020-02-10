package cft.ershov.sort;

import cft.ershov.sort.reader.FileHandler;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        new FileHandler<String>("in1.txt");
    }
}
