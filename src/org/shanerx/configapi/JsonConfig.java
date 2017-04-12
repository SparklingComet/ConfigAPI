package org.shanerx.configapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Lori00
 * @version 2.0.1
 * 
 * This small collection of methods has been created by @Lori00.
 * Creation date: 14-03-2017
 * 
 * This software is open source and free. You may use it for every need without explicit permission from the author. This includes adaptations, modifications and commercial use.
 */
public class JsonConfig {
	
	private File configFile;
	private File pluginDataFolder;
	private String name;
	
	
	/**
	 * @since 2.0.0
	 * @param pluginDataFolder The plugin's data directory, accessible with JavaPlugin#getDataFolder();
	 * @param name The name of the config file excluding file extensions.
	 * 
	 * Please notice that the constructor does not yet create the JSON-configuration file. To create the file on the disk, use {@link JsonConfig#createConfig()}.
	 */
	public JsonConfig(File pluginDataFolder, String name) {
		
		StringBuilder fileName = new StringBuilder();
		fileName.append(name).append(".json");
		this.name = fileName.toString();
		
		configFile = new File(pluginDataFolder, this.name);
		this.pluginDataFolder = pluginDataFolder;
	}
	
	
	/**
	 * @since 2.0.0
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
	 * @since 2.0.0
	 * @return The configuration file's directory. To get its name, use {@link JsonConfig#getName()} instead.
	 */
	public File getDirectory() {
		return pluginDataFolder;
	}
	
	
	/**
	 * @since 2.0.0
	 * @return The name of the configuration file, including file extensions.
	 * This returns the name of the configuration file with the .yml extension. To get the file's directory, use {@link JsonConfig#getDirectory()}.
	 */
	public String getName() {		
		return name;
	}
	
	
	/**
	 * @since 2.0.0
	 * @return The config file.
	 * This returns the actual File object of the config file.
	 */
	public File getFile() {		
		return configFile;
	}
	
	
	/**
	 * @since 2.0.0
	 * This deletes the config file.
	 */
	public void deleteFile() {
		configFile.delete();
	}
	
	
	/**
	 * @since 2.0.0
	 * This deletes the config file's directory and all it's contents.
	 */
	public void deleteParentDir() {
		this.getDirectory().delete();
	}
	
	
	/**
	 * @since 2.0.0
	 * This deletes and recreates the file, wiping all its contents.
	 */
	public void reset() {
		this.deleteFile();
		try {
			configFile.createNewFile();
			JSONObject obj = new JSONObject();
			PrintWriter write = new PrintWriter(configFile);
			write.write(obj.toJSONString());
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @since 2.0.0
	 * Wipe the config file's directory, including the file itself.
	 */
	public void wipeDirectory() {
		this.getDirectory().delete();
		this.pluginDataFolder.mkdir();
	}
	
	
	/**
	 * @since 2.0.0
	 * @param name The sub directory's name.
	 * @throws IOException If the entered string has a file extension or already exists.
	 * This will create a sub-directory in the plugin's data folder, which can be accessed with {@link JsonConfig#getDirectory()}.
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
		subDir.mkdir();
	}
	
	
	/**
	 * @since 2.0.0
	 * @throws IOException
	 * @throws FileNotFoundException If the file is invalid.
	 * @return true if the content of the file may be parsed as a {@link org.json.simple.JSONObject#JSONObject()}. If not, false will be returned.
	 * Checks whether or not the plain text inside the file may be parsed as a valid {@link org.json.simple.JSONObject#JSONObject()}.
	 * If the file contain a {@link org.json.simple.JSONArray#JSONArray()} , false will be returned. If the content does not contain a valid
	 * JSON structure, the function will return false as well.
	 */
	@SuppressWarnings("unused")
	public boolean isJSONObject() throws IOException, FileNotFoundException {
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * @since 2.0.0
	 * @throws IOException
	 * @throws FileNotFoundException If the file is invalid.
	 * @return true if the content of the file may be parsed as a {@link org.json.simple.JSONArray#JSONArray()}. If not, false will be returned.
	 * Checks whether or not the plain text inside the file may be parsed as a valid {@link org.json.simple.JSONArray#JSONArray()}.
	 * If the file contain a {@link org.json.simple.JSONObject#JSONObject()}, false will be returned. If the content does not contain a valid
	 * JSON structure, the function will return false as well.
	 */
	@SuppressWarnings("unused")
	public boolean isJSONArray() throws IOException, FileNotFoundException {
		try {
			JSONArray obj = (JSONArray) new JSONParser().parse(new FileReader(configFile));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * @since 2.0.0
	 * @throws IOException
	 * @throws FileNotFoundException If the file is invalid.
	 * @return the contents of the file as a {@link org.json.simple.JSONObject#JSONObject()}.
	 * @throws ParseException , IOException, FileNotFoundException if the file does not contain a valid {@link org.json.simple.JSONObject#JSONObject()} or cannot be found.
	 * This creates and returns a new {@link org.json.simple.JSONObject#JSONObject()}, with the contents of the file.
	 */
	public JSONObject toJSONObject() throws IOException, FileNotFoundException, ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
		return obj;
	}
	
	
	/**
	 * @since 2.0.0
	 * @throws IOException
	 * @throws FileNotFoundException If the file is invalid.
	 * @throws ParseException If the file does not contain a valid {@link org.json.simple.JSONArray#JSONArray()}.
	 * @return the contents of the file as a {@link org.json.simple.JSONArray#JSONArray()}.
	 * This creates and returns a new {@link org.json.simple.JSONArray#JSONArray()}, with the contents of the file.
	 */
	public JSONArray toJSONArray() throws IOException, FileNotFoundException, ParseException {
		JSONArray arr = (JSONArray) new JSONParser().parse(new FileReader(configFile));
		return arr;
	}
	
	
	/**
	 * @since 2.0.0
	 * @param k The key.
	 * @param v The value.
	 * @throws IOException
	 * @throws FileNotFoundException Thrown when the file cannot be found.
	 * @throws ParseException Thrown when the file does not contain a valid {@link org.json.simple.JSONObject#JSONObject()}.
	 * Adds a new key-value to the JSONObject inside {@link #getFile()}.
	 */
	@SuppressWarnings("unchecked")
	public void putInJSONObject(Object k, Object v) throws FileNotFoundException, IOException, ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
		obj.put(k, v);
		PrintWriter write = new PrintWriter(configFile);
		write.write(obj.toJSONString());
		write.close();
	}
	
	
	/**
	 * @since 2.0.0
	 * @param values The Map with all the values.
	 * @throws IOException
	 * @throws FileNotFoundException Thrown when the file cannot be found.
	 * @throws ParseException Thrown when the file does not contain a valid {@link org.json.simple.JSONObject#JSONObject()}.
	 * Adds all the keys and values of the Map to the JSONObject inside {@link #getFile()}.
	 */
	@SuppressWarnings("unchecked")
	public void putInJSONObject(Map<Object, Object> values) throws FileNotFoundException, IOException, ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
		obj.putAll(values);;
		PrintWriter write = new PrintWriter(configFile);
		write.write(obj.toJSONString());
		write.close();
	}
	
	
	/**
	 * @since 2.0.0
	 * @param obj The object to add to the JSONArray.
	 * @throws IOException
	 * @throws FileNotFoundException Thrown when the file cannot be found.
	 * @throws ParseException Thrown when the file does not contain a valid {@link org.json.simple.JSONArray#JSONArray()}.
	 * Adds a new key-value to the JSONArray inside {@link #getFile()}.
	 */
	@SuppressWarnings("unchecked")
	public void putInJSONArray(Object obj) throws FileNotFoundException, IOException, ParseException {
		JSONArray arr = (JSONArray) new JSONParser().parse(new FileReader(configFile));
		arr.add(obj);
		PrintWriter write = new PrintWriter(configFile);
		write.write(arr.toJSONString());
		write.close();
	}
	
	
	/**
	 * @since 2.0.0
	 * @param c A collection which holds the objects to add to the JSONArray.
	 * @throws IOException
	 * @throws FileNotFoundException Thrown when the file cannot be found.
	 * @throws ParseException Thrown when the file does not contain a valid {@link org.json.simple.JSONArray#JSONArray()}.
	 * Adds all the values of the collection to the JSONArray inside {@link #getFile()}.
	 */
	@SuppressWarnings("unchecked")
	public void putInJSONArray(Collection<Object> c) throws FileNotFoundException, IOException, ParseException {
		JSONArray arr = (JSONArray) new JSONParser().parse(new FileReader(configFile));
		arr.addAll(c);
		PrintWriter write = new PrintWriter(configFile);
		write.write(arr.toJSONString());
		write.close();
	}

}
