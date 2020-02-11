package cft.ershov.sort.reader;

public class StringFileReader extends FileHandlerWithBuffer implements TypeFileReader {

    public StringFileReader(String name) {
        super(name);
    }

    @Override
    public String read() {
        return super.readString();
    }
}
