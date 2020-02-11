package cft.ershov.sort.reader;

import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
public class FileHandlerWithBuffer {

    private static final int DEFAULT_BUFFER_SIZE = 1024*1024;
    private static final int DEFAULT_MAX_LENGTH = 1024*1024;
    private int maxLineLength;
    private String name;
    private int bufferSize;
    private char[] buffer;
    private java.io.BufferedReader fileReader;
    private StringBuilder result;
    private int mark;
    private int currentIndex;
    private boolean close;
    private boolean stringIsNotEnd;

    public FileHandlerWithBuffer(String name) {
        this.name = name;
        this.bufferSize = DEFAULT_BUFFER_SIZE;
        this.maxLineLength = DEFAULT_MAX_LENGTH;
        init();
    }

    private void init()  {
        this.result = new StringBuilder();
        this.currentIndex = 0;
        this.close = true;
        try {
            this.fileReader = new java.io.BufferedReader(new FileReader(name));
        } catch (FileNotFoundException e) {
            log.error("File not found");
        }
    }


    public String readString() {
        result.setLength(0);
        while (close){
            if (buffer==null) readBuffer();
            while (currentIndex!=buffer.length){
                if (buffer[currentIndex]==0 || buffer[currentIndex] == ' '
                        || buffer[currentIndex] == '\r'||buffer[currentIndex] == '\n') {
                    skip();
                    break;
                } else if (result.length()<maxLineLength){
                    result.append(buffer[currentIndex]);
                    currentIndex++;
                    stringIsNotEnd = true;
                } else {
                    skip();
                    return result.toString();
                }
            }
            if (result.length()>0 && !stringIsNotEnd) return result.toString();
            if (currentIndex==bufferSize) readBuffer();
        }
        if (result.length()>0) return result.toString();
        return null;
    }

    public Integer readInteger(){
        String temp = readString();
        Integer result = null;
        if (temp!=null){
            try {
                result = Integer.parseInt(temp);
            } catch (Exception e){
                return readInteger();
            }
        }
        return result;
    }

    private boolean readBuffer(){
        try {
            buffer = new char[bufferSize];
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
