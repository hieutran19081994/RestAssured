package core.configs;

import core.utils.PropertiesUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Properties;

public class Configuration {
    //Configuration keys
    public static final String WEB_REMOTE_URL = "web.remote.url";
    public static final String WEB_DRIVER_PATH = "web.driver.path";
    public static final String WEB_BROWSER_NAME = "web.browser.name";
    public static final String OBJECT_WAIT_TIMEOUT = "object.wait.timeout";
    public static final String WEB_CAPABILITIES_FILE = "web.capabilities.file";
    public static final String PACKAGE_DESCRIPTION = "package.description";
    public static final String FEATURE_FOLDER_PATH = "feature.folder.path";
    public static final String OBJ_REPO_FOLDER = "obj.repo.folder";
    public static final String BROWSER_BINARY_PATH = "browser.binary.path";
    public static final String MOBILE_REMOTE_URL = "mobile.remote.url";
    public static final String MOBILE_PLATFORM_NAME = "mobile.platform.name";
    public static final String MOBILE_CAPABILITIES_FILE = "mobile.capabilities.file";
    public static final String REST_ASSURED_CLASS = "rest.assured.class";
    public static final String DB_CONNECTIONS_FILE = "db.connections.file";
    public static final String SSH_CONNECTIONS_FILE = "ssh.connection.file";
    public static final String DATA_FOLDER = "data.folder";
    //Default values
    public static final String PROPERTIES_EXTENSION_NAME = ".properties";
    public static final String DEFAULT_CONFIG = "config/config.properties";
    public static final String DEFAULT_CONFIG_FOLDER = "config";
    public static final String DEFAULT_CONFIG_FILE = "config.properties";
    public static final String DEFAULT_DATA_FOLDER = "data";
    public static final String DEFAULT_DATA_FILE = "data.properties";
    static final Logger LOGGER = Logger.getLogger(Configuration.class);
    private static Configuration config;
    Properties props;
    Properties dataProps;
    File dataFolder;

    private Configuration() {
        props = new Properties();
        dataProps = new Properties();
        dataFolder = new File(DEFAULT_DATA_FOLDER);
        loadConfiguration(DEFAULT_CONFIG);
    }

    /**
     * @param configFile
     */
    public void loadConfiguration(String configFile) {
        try {
            File f = new File(configFile);

            //load from jar file first
            props.putAll(PropertiesUtil.loadInJarFile(configFile));

            //load from external file
            if (f.exists() && !f.isDirectory()) {
                props.putAll(PropertiesUtil.load(configFile));
            }
            props.putAll(System.getProperties());

            loadData(configFile);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @param configFile
     */
    public void loadData(String configFile) {
        if (configFile == null || configFile.isEmpty()) {
            return;
        }

        try {
            //get data folder value in config file
            String dataFolderValue = getValue(DATA_FOLDER);
            if (dataFolderValue != null && !dataFolderValue.isEmpty()) {
                dataFolder = new File(dataFolderValue);
            } else if (!DEFAULT_CONFIG.equalsIgnoreCase(configFile)) {
                String fileName = FilenameUtils.getBaseName(configFile);
                dataFolder = new File(DEFAULT_DATA_FOLDER, fileName);
            }


            File dataPropertiesFile = new File(dataFolder, DEFAULT_DATA_FILE);
            //load from jar file first
            dataProps.putAll(PropertiesUtil.loadInJarFile(dataPropertiesFile.getPath()));

            //load from external file
            if (dataPropertiesFile.exists() && !dataPropertiesFile.isDirectory()) {
                dataProps.putAll(PropertiesUtil.load(dataPropertiesFile.getAbsolutePath()));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @param key
     * @return
     */
    public String getValue(String key) {
        return props.getProperty(key);
    }

    public static void main(String args[]) {
        Configuration.instance().loadConfiguration("config/preprod.properties");
    }

    public static Configuration instance() {
        if (config == null) {
            config = new Configuration();
        }
        return config;
    }

    public String getDataFolder() {
        return dataFolder.getAbsolutePath() + "/";
    }

    /**
     * @return
     */
    public String getDataValue(String key) {
        return dataProps.getProperty(key);
    }
}
