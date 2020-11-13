package core.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileUtil {
    static final Logger LOGGER = Logger.getLogger(ReadFileUtil.class);

    public static String getTextContent(String filePath) {
        String result = "";
        try {
            if (filePath != null) {
                File f = new File(filePath);
                if (f.exists() && !f.isDirectory()) {
                    result = new String(Files.readAllBytes(Paths.get(filePath)),
                            StandardCharsets.UTF_8);
                }else {
                    LOGGER.error("File not exist: " + filePath);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
}
