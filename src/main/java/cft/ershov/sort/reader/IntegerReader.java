package cft.ershov.sort.reader;

public class IntegerReader extends FileHandler implements DataReader<Integer> {

    public IntegerReader(String name) {
        super(name);
    }

    @Override
    public Integer read() {
        String temp = super.getData();
        Integer result = null;
        if (temp!=null){
            try {
                result = Integer.parseInt(temp);
            } catch (Exception e){
                return read();
            }
        }
        return result;
    }
}
