package cft.ershov.sort.reader;

public class StringReader extends FileHandler implements DataReader<String> {

    public StringReader(String name) {
        super(name);
    }

    @Override
    public String read() {
        return super.getData();
    }
}
