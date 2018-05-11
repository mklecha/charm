package pl.michalklecha.sns.treeBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    //    private static final String PROPERTIES_FILE = "./app.properties";
    private static final String PROPERTIES_FILE = "C:\\Users\\michalklecha\\IdeaProjects\\charm\\treeBuilder\\src\\main\\resources\\app.properties";
    private final Logger logger = Logger.getLogger(getClass().getName());

    private Properties properties = new Properties();

    public Config() {
        try (FileInputStream file = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(file);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public String getInputFilename() {
        return properties.getProperty("charm.data.in.filename");
    }

    public String getStopwords() {
        return properties.getProperty("charm.data.in.stopwords");
    }

    public String getOutputFilename() {
        return properties.getProperty("charm.data.out.filename");
    }

    public int getCVLimit() {
        return Integer.parseInt(properties.getProperty("charm.cv_limit"));
    }

    public int getMinSupport() {
        return Integer.parseInt(properties.getProperty("charm.min_support"));
    }

    public int getThreadCount() {
        return Integer.parseInt(properties.getProperty("app.thread_count"));
    }

    public boolean getPrintResult() {
        return Boolean.parseBoolean(properties.getProperty("app.print_result"));
    }

    public int getTimeout() {
        return Integer.parseInt(properties.getProperty("charm.timeout"));
    }

    public boolean getShowResult() {
        return Boolean.parseBoolean(properties.getProperty("app.show_result"));
    }


}
