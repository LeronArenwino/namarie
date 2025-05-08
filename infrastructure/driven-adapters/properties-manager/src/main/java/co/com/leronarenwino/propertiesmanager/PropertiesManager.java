package co.com.leronarenwino.propertiesmanager;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

import static co.com.leronarenwino.setting.SettingsSingleton.PATH_TO_SETTINGS;
import static co.com.leronarenwino.setting.SettingsSingleton.defaultSettingsProperties;


/** This class
 * @author Francisco Dueñas
 */
public class PropertiesManager {

    // Create a Logger
    private static final Logger logger
            = Logger.getLogger(
            PropertiesManager.class.getName());

    private PropertiesManager(){throw new IllegalStateException("Utility class");
    }

    public static Properties loadProperties() {

        Properties properties = new Properties();

        try {

            if (!new File(PATH_TO_SETTINGS).exists()) {
                saveProperties(defaultSettingsProperties());
            }

            FileReader reader = new FileReader(PATH_TO_SETTINGS);

            properties.load(reader);


        } catch (IOException e) {
            logger.log(Level.WARNING, "Reader error in PropertiesManager class");
        }

        return properties;

    }

    public static void saveProperties(Properties properties) {

        try {
            properties.store(new FileWriter(PATH_TO_SETTINGS), "Namarie - settings");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Properties store error in PropertiesManager class");
        }

    }

}