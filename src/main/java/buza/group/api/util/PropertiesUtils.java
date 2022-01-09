package buza.group.api.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class PropertiesUtils {

    private static Properties properties;

    private static String PROGRAM_NAME;

    static {
        properties = new Properties();
        InputStream inStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            Reader reader = new InputStreamReader(inStream, "UTF-8");
            properties.load(reader);
            PROGRAM_NAME = properties.getProperty("PROGRAM_NAME");

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { inStream.close(); } catch (Exception e1) {}
        }
    }

    public static String getProgramName() {
        return PROGRAM_NAME;
    }

    public static void setProgramName(String programName) {
        PROGRAM_NAME = programName;
    }

}
