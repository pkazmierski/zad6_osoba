package um.kazmierski;

import com.opencsv.bean.processor.StringProcessor;

public class ConvertNullToInt implements StringProcessor {
    private static final String NO_SCORE = "-1";

    @Override
    public String processString(String value) {
        if (value.trim().equals("NULL"))
            return NO_SCORE;
        else
            return value;
    }

    @Override
    public void setParameterString(String value) {

    }
}
