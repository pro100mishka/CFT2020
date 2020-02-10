package cft.ershov.sort.separator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Data
@NoArgsConstructor
@Log4j2
public class ArgSeparator {
    private static final ArgSeparator separator = new ArgSeparator();
    private static final Boolean INCREASE_SORT_MODE = true;
    private static final Boolean DECREASE_SORT_MODE = false;
    private static final Boolean TYPE_STRING = true;
    private static final Boolean TYPE_INTEGER = false;
    private static final Pattern pattern = Pattern.compile("\\S+\\.txt");

    private static final Set<String> inpFileNames = new HashSet<>();
    private static Boolean sortMode;
    private static Boolean typeMode;
    private static String outFileName;

    public static boolean separation(String[] arg) {
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

    private static boolean checkModeAndType(String message, Boolean modeOrType, Boolean set){
        if (modeOrType==null) {
            log.debug("Set "+message+" success!");
            return set;
        }
        return modeOrType;
    }

    private static boolean fileNameCheck(String s){
        log.debug("File name verification: " + s);
        Matcher m = pattern.matcher(s);
        if (m.find()) return true;
        log.debug("File name incorrect: " + s);
        return false;
    }

    private static void addFileName(String s){
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
