package core.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {
    private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

    /**
     * @param fileName
     * @return
     */
    public static Properties load(String fileName) {
        Properties r = new Properties();
        InputStream isMessage = null;
        try {
            isMessage = new FileInputStream(fileName);
            r.load(new InputStreamReader(isMessage, Charset.forName("UTF-8")));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (isMessage != null) {
                try {
                    isMessage.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return r;
    }

    /**
     * @param folder
     * @param regex
     * @return
     * @throws Exception
     */
    public static Properties loadAllPropertiesInJarFile(String folder, String regex) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties allProps = new Properties();
        List<String> lstFiles = new ArrayList<>();
        URI uri = loader.getResource(folder).toURI();
        if (uri == null) {
            return allProps;
        }
        try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.emptyMap()) : null)) {
            Path myPath = Paths.get(uri);
            Files.walkFileTree(myPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String filePath = file.toString();
                    if (filePath.endsWith(regex)) {
                        if (filePath.startsWith("/")) {
                            filePath = filePath.substring(1);
                        }
                        lstFiles.add(filePath);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        for (int i = 0; i < lstFiles.size(); i++) {
            allProps.putAll(loadInJarFile(lstFiles.get(i)));
        }
        return allProps;
    }

    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Properties loadInJarFile(String filePath) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties p = new Properties();
        InputStream is = loader.getResourceAsStream(filePath.replace("\\", "/"));
        if (is != null) {
            p.load(is);
        }
        return p;
    }
}
