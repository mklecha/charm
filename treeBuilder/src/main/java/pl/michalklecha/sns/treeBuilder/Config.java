package pl.michalklecha.sns.treeBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public String getCharmOutputDirectory() {
        return properties.getProperty("charm.data.directory");
    }

    public int getCVLimit() {
        return Integer.parseInt(properties.getProperty("charm.cv_limit"));
    }

    public int getMinSupportPercent() {
        return Integer.parseInt(properties.getProperty("charm.min_support"));
    }

    public int getTimeout() {
        return Integer.parseInt(properties.getProperty("charm.timeout"));
    }

    public int getThreadCount() {
        return Integer.parseInt(properties.getProperty("charm.thread_count"));
    }

    public Boolean getSaveTreeWithIds() {
        return Boolean.parseBoolean(properties.getProperty("sns.save_tree_with_ids"));
    }

    public List<String> getTreeSubject() {
        List<String> subject = Arrays.asList(properties.getProperty("sns.tree_subject").split(","));
        return subject.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public String getOutputFilename() {
        return properties.getProperty("sns.data.out.filename");
    }

    public boolean getPrintResult() {
        return Boolean.parseBoolean(properties.getProperty("app.print_result"));
    }

    public boolean getShowResult() {
        return Boolean.parseBoolean(properties.getProperty("app.show_result"));
    }


}
