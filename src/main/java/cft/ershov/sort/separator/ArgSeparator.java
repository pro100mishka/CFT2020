package cft.ershov.sort.separator;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Data
@Log4j2
public class ArgSeparator {
    private final Boolean INCREASE_SORT_MODE = true;
    private final Boolean DECREASE_SORT_MODE = false;
    private final Boolean TYPE_STRING = true;
    private final Boolean TYPE_INTEGER = false;
    private final Pattern pattern = Pattern.compile("\\S+\\.txt");

    private final List<String> inpFileNames = new LinkedList<>();
    private Boolean sortMode;
    private Boolean typeMode;
    private String outFileName;

    private String [] arg;

    public ArgSeparator(String[] arg) {
        this.arg = arg;
    }

    public boolean separation() {
        log.info("Separation of arguments begins: " + Arrays.toString(arg));
        for (String s : arg) {
            switch (s) {
                case "-a":
                    sortMode = checkModeAndType("increase",sortMode,INCREASE_SORT_MODE);
                    break;
                case "-d":
                    sortMode = checkModeAndType("decrease",sortMode,DECREASE_SORT_MODE);
                    break;
                case "-s":
                    typeMode = checkModeAndType("type string",typeMode,TYPE_STRING);
                    break;
                case "-i":
                    typeMode = checkModeAndType("type integer",typeMode,TYPE_INTEGER);
                    break;
                default:
                    if (fileNameCheck(s)) addFileName(s);
                    break;
            }
        }
        if (sortMode==null) sortMode = INCREASE_SORT_MODE;
        if (typeMode==null){
            log.error("Sort data type error");
            return false;
        } else if (outFileName ==null){
            log.error("Missing output file name");
            return false;
        } else if (inpFileNames.isEmpty()){
            log.error("Missing source file names");
            return false;
        }
        log.info("Argument separation done! "+
                " Sort mode: increase(true)/decrease(false): " + sortMode+
                ", sort type: string(true)/integer(false): " + typeMode+
                ", output filename: " + outFileName+
                ", input filenames: " + inpFileNames.toString());
        return true;
    }

    private boolean checkModeAndType(String message, Boolean modeOrType, Boolean set){
        if (modeOrType==null) {
            log.debug("Set "+message+" success!");
            return set;
        }
        return modeOrType;
    }

    private boolean fileNameCheck(String s){
        log.debug("File name verification: " + s);
        Matcher m = pattern.matcher(s);
        if (m.find()) return true;
        log.debug("File name incorrect: " + s);
        return false;
    }

    private void addFileName(String s){
        if (outFileName !=null){
            inpFileNames.add(s);
            log.debug("Add input file: " + s);
        }
        else{
            outFileName = s;
            log.debug("Add output file: " + s);
        }
    }

}
