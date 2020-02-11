package cft.ershov.sort.reader;

public class IntegerFileReader extends FileHandlerWithBuffer implements TypeFileReader {


    public IntegerFileReader(String name) {
        super(name);
    }

    @Override
    public Integer read() {
        return super.readInteger();
    }
}
