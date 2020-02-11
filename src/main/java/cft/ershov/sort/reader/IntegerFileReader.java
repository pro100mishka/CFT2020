package cft.ershov.sort.reader;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IntegerFileReader extends FileHandler implements TypeFileReader {


    public IntegerFileReader(String name) {
        super(name);
    }

    @Override
    public Integer read() {
        long start = System.currentTimeMillis();
        Integer result = super.readInteger();
        long end = System.currentTimeMillis()-start;
        log.debug(end);
        return result;
    }
}
