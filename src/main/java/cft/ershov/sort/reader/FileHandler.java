package cft.ershov.sort.reader;

import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@Log4j2
public class FileHandler {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int DEFAULT_MAX_LENGTH = 100;
    private MappedByteBuffer buffer;
    private RandomAccessFile file;
    private StringBuilder result;
    private FileChannel inChannel;
    private long pos;

    FileHandler(String name) {
        try {
            this.file = new RandomAccessFile(name, "r");
            this.inChannel = file.getChannel();
            long size;
            if (inChannel.size()>DEFAULT_BUFFER_SIZE) {
                size = DEFAULT_BUFFER_SIZE;
            } else size = inChannel.size();
            buffer = inChannel.map(FileChannel.MapMode.READ_ONLY,0,size);
            result = new StringBuilder();
        } catch (FileNotFoundException e) {
            log.error("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readString()  {
        result.setLength(0);
        if (!buffer.isLoaded()) buffer.load();
        try {
            while (pos!=inChannel.size()){
                checkBuffer();
                char temp = (char) buffer.get();
                pos++;
                if (temp=='\r'||temp==' ' || result.length()==DEFAULT_MAX_LENGTH){
                    skip();
                    break;
                } else if (temp=='\n'){
                    break;
                } else result.append(temp);
            }
            if (result.length()==0 && pos!=inChannel.size()) readString();
        } catch (IOException e){
            e.fillInStackTrace();
        }
        return result.length()>0 ? result.toString() : null;
    }

    Integer readInteger(){
        String temp = readString();
        Integer result = null;
        if (temp!=null){
            try {
                result = Integer.parseInt(temp);
            } catch (NumberFormatException e){
                return readInteger();
            }
        }
        return result;
    }

    public void close() {
        try {
            file.close();
            inChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void skip() throws IOException {
        do {
            checkBuffer();
            char temp = (char) buffer.get();
            pos++;
            if (temp=='\n') return;
        } while (pos!=inChannel.size());
    }

    private void checkBuffer() throws IOException {
        if (buffer.position()==buffer.limit()){
            if (inChannel.size()-pos<DEFAULT_BUFFER_SIZE) buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, pos,inChannel.size()-pos);
            else buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, pos,DEFAULT_BUFFER_SIZE);
            buffer.load();
        }
    }


}
