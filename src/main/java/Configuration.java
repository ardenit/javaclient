import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Configuration {

    private static final String PROPERTIES_PATH = "./clientcfg.properties";

    static final int DEFAULT_BYTE_COUNT = 20;
    static final String DEFAULT_HOST = "127.0.0.1";
    static final int DEFAULT_SERVER_PORT = 54321;

    private final int byteCount;
    private final String host;
    private final int serverPort;

    Configuration(int byteCount, String host, int serverPort) {
        this.byteCount = byteCount;
        this.host = host;
        this.serverPort = serverPort;
    }

    static Configuration load() {
        try {
            FileInputStream propertiesInputStream = new FileInputStream(PROPERTIES_PATH);
            Configuration configuration = parsePropertiesFile(propertiesInputStream);
            propertiesInputStream.close();
            Log.log.info("Configuration is successfully loaded.");
            return configuration;
        } catch (FileNotFoundException ex) {
            Log.log.info("Configuration file not found. Default values will be used.");
            return new Configuration(DEFAULT_BYTE_COUNT, DEFAULT_HOST, DEFAULT_SERVER_PORT);
        } catch (IOException ex) {
            Log.log.info("Error while reading a configuration file. Default values will be used.");
            return new Configuration(DEFAULT_BYTE_COUNT, DEFAULT_HOST, DEFAULT_SERVER_PORT);
        }
    }

    static Configuration parsePropertiesFile(InputStream propertiesInputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(propertiesInputStream);
        int byteCount = loadIntProperty(properties, "byteCount", DEFAULT_BYTE_COUNT);
        String host = loadStringProperty(properties, "host", DEFAULT_HOST);
        int serverPort = loadIntProperty(properties, "serverPort", DEFAULT_SERVER_PORT);
        return new Configuration(byteCount, host, serverPort);
    }

    static int loadIntProperty(Properties properties, String key, int defaultValue) {
        try {
            String value = properties.getProperty(key, null);
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            Log.log.info(key + " configuration is not valid. Default value will be used.");
            return defaultValue;
        }
    }

    static String loadStringProperty(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getByteCount() {
        return byteCount;
    }

    public String getHost() {
        return host;
    }

    public int getServerPort() {
        return serverPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return byteCount == that.byteCount &&
                serverPort == that.serverPort &&
                Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteCount, host, serverPort);
    }
}
