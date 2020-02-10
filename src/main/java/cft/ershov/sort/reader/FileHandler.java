package cft.ershov.sort.reader;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Log4j2

public class FileHandler {

    private static final int DEFAULT_BUFFER_SIZE = 1024*1024;
    private static final int DEFAULT_MAX_LENGTH = DEFAULT_BUFFER_SIZE;
    private String name;
    private char[] buffer;
    private BufferedReader fileReader;
    private StringBuilder result;
    private int mark;
    private int currentIndex;
    private boolean close;
    private boolean stringIsNotEnd;

    public FileHandler(String name) {
        this.name = name;
        init();
    }

    private void init() {
        this.result = new StringBuilder();
        this.currentIndex = 0;
        this.close = true;
        try {
            this.fileReader = new BufferedReader(new FileReader(name));
        } catch (FileNotFoundException e) {
            log.error("File by filename: ("+name+") - not found");
        }
    }

    public String getData() {
        result.setLength(0);
        while (close){
            if (buffer==null) readBuffer();
            while (currentIndex!=buffer.length){
                if (buffer[currentIndex]==0 || buffer[currentIndex] == ' '
                        || buffer[currentIndex] == '\r'||buffer[currentIndex] == '\n') {
                    skip();
                    break;
                } else if (result.length()<DEFAULT_MAX_LENGTH){
                    result.append(buffer[currentIndex]);
                    currentIndex++;
                    stringIsNotEnd = true;
                } else {
                    skip();
                    return result.toString();
                }
            }
            if (result.length()>0 && !stringIsNotEnd) return result.toString();
            if (currentIndex==DEFAULT_BUFFER_SIZE) readBuffer();
        }
        if (result.length()>0) return result.toString();
        return null;
    }


    private boolean readBuffer(){
        try {
            buffer = new char[DEFAULT_BUFFER_SIZE];
            mark=fileReader.read(buffer);
            currentIndex=0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mark != -1;
    }

    private void skip(){
        while (close){
            while (currentIndex!=buffer.length){
                if (buffer[currentIndex]==0 ) {
                    close = readBuffer();
                    return;
                }
                if (buffer[currentIndex++]=='\n'){
                    stringIsNotEnd = false;
                    return;
                }
            }
            close = readBuffer();
        }
    }

    public void close() {
        try  {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
