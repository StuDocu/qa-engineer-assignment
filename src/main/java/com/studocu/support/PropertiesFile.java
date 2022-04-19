package com.studocu.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesFile {

	private static Properties properties;

	/**
	 * Load property file by taking specified property file path
	 * @param propertyFilePath - path of property file path
	 */
	public PropertiesFile(String propertyFilePath){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	/**
	 * Get value of specified key which is available in properties path
	 * @param name - path of property file path
	 */
	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	public static String getPropertyByName(String name) {
		return properties.getProperty(name);
	}

      /**
       * set value  for specified key which is available in properties path
       * @param path - path of property file path
       */
        public static void setProperty(String path,String EnvName) throws ConfigurationException {
          PropertiesConfiguration properties = new PropertiesConfiguration(
                  new File(path));
          properties.setProperty("env", EnvName + "_");
          properties.save();
      }

	public static void setProperty(String path, String Object, String keyValue) throws ConfigurationException {
		PropertiesConfiguration properties = new PropertiesConfiguration(
				new File(path));
		properties.setProperty(Object, keyValue);
		properties.save();
	}

}
