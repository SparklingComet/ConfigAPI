package org.shanerx.configapi;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Lori00
 * @version 1.0.0
 * 
 * This small collection of methods has been created by @Lori00.
 * Creation date: 22-12-2016
 * 
 * This software is open source and free. You may use it for every need without explicit permission from the author. This includes adaptations, modifications and commercial use.
 */
public class ConfigFile {

	private File configFile;
	private FileConfiguration config;
	private File pluginDataFolder;
	private String name;
	
	/**
	 * @since 1.0.0
	 * @param pluginDataFolder The plugin's data directory, accessible with JavaPlugin#getDataFolder();
	 * @param name The name of the config file excluding file extensions.
	 * 
	 * Please notice that the constructor does not yet create the YAML-configuration file. To create the file on the disk, use {@link ConfigFile#createConfig()}.
	 */
	public ConfigFile(File pluginDataFolder, String name) {
		
		StringBuilder fileName = new StringBuilder();
		fileName.append(name).append(".yml");
		this.name = fileName.toString();
		
		configFile = new File(pluginDataFolder, name);
		this.pluginDataFolder = pluginDataFolder;
		config = YamlConfiguration.loadConfiguration(configFile);
		
	}
	
	/**
	 * @since 1.0.0
	 * This creates the configuration file. If the data folder is invalid, it will be created along with the config file.
	 */
	public void createConfig() {
		
		if (! configFile.exists()) {
			
			if (! this.pluginDataFolder.exists()) {
				
				this.pluginDataFolder.mkdir();
			}
			
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * @since 1.0.0
	 * @return The configuration file's directory. To get its name, use {@link ConfigFile#getName()} instead.
	 */
	public File getDirectory() {
		
		return pluginDataFolder;
	}
	
	/**
	 * @since 1.0.0
	 * @return The name of the configuration file, including file extensions.
	 * This returns the name of the configuration file with the .yml extension. To get the file's directory, use {@link ConfigFile#getDirectory()}.
	 */
	public String getName() {
		
		return name;
	}
	
	/**
	 * @since 1.0.0
	 * @return The config file.
	 * This returns the actual File object of the config file.
	 */
	public File getFile() {
		
		return configFile;
	}
	
	/**
	 * @since 1.0.0
	 * @return The FileConfiguration object.
	 * This returns the actual FileConfiguration object of the config file.
	 */
	public FileConfiguration getConfig() {
		
		return config;
	}
	
	/**
	 * @since 1.0.0
	 * @param key The config key, including the path.
	 * @param value the config value.
	 * Set a default configuration value: if the entered key already exists (and it holds another value), it will do nothing. If it doesn't, it will create the key with the wanted value.
	 */
	public void addDefault(String key, String value) {
		
		if (config.getString(key) == null) {
			
			config.set(key, value);
			
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * @since 1.0.0
	 * @param defaults A map containing the default configuration keys and values.
	 * This sets the default configuration values as the ones contained in the map.
	 */
	public void addDefaults(Map<String,Object> defaults) {
		config.addDefaults(defaults);

	}
	
	/**
	 * @since 1.0.0
	 * This saves the configuration file. Saving is required every time you write to it.
	 */
	public void save() {
		
		try {
			config.save(configFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @since 1.0.0
	 * This reloads the configuration file, making Java acknowledge and load the new config and its values.
	 */
	public void reload() {
		
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	/**
	 * @since 1.0.0
	 * This deletes the config file.
	 */
	public void deleteFile() {
		
		configFile.delete();
	}
	
	/**
	 * @since 1.0.0
	 * This deletes the config file's directory and all it's contents.
	 */
	public void deleteDir() {
		
		this.getDirectory().delete();
	}
	
	/**
	 * @since 1.0.0
	 * This deletes and recreates the file, wiping all its contents.
	 */
	public void reset() {
		
		this.deleteFile();
		try {
			configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @since 1.0.0
	 * Wipe the config file's directory, including the file itself.
	 */
	public void wipeDirectory() {
		
		this.getDirectory().delete();
		this.pluginDataFolder.mkdir();
	}
	
	/**
	 * @since 1.0.0
	 * @param name The sub directory's name.
	 * @throws IOException If the entered string has a file extension or already exists.
	 * This will create a sub-directory in the plugin's data folder, which can be accessed with {@link ConfigFile#getDirectory()}.
	 * If the entered name is not a valid name for a directory or the sub-directory already exists or the data folder does not exist, an IOException will be thrown.
	 */
	public void createSubDirectory(String name) throws IOException {
		
		if (!pluginDataFolder.exists()) {
			throw new IOException("Data folder not found.");
		}
		
		File subDir = new File(pluginDataFolder, name);
		
		if (subDir.exists()) {
			throw new IOException("Sub directory already existing.");
		}
		
		if (!subDir.isDirectory()) {
			throw new IOException("The first argument is not a directory.");
		}
		
		subDir.mkdir();
	}
	
	/**
	 * @since 1.0.0
	 * @param value
	 * @return true or false
	 * This returns true if the config contains the given value.
	 */
	public boolean contains(String value) {

		return config.contains(value);
	}
		
}
