package cft.ershov.sort.reader;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StringFileReader extends FileHandler implements TypeFileReader {

    public StringFileReader(String name) {
        super(name);
    }

    @Override
    public String read() {
        long start = System.currentTimeMillis();
        String result = super.readString();
        long end = System.currentTimeMillis()-start;
        log.debug(end);
        return result;
    }
}
