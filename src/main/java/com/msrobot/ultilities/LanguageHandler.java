package com.msrobot.ultilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageHandler {
	private String language = "en";
	private String country = "US";
	private String propertiesPath;
	private String propertiesFileName;
	private ClassLoader loader;
	
	public LanguageHandler(String propertiesPath, String propertiesFileName,String currentLanguage) throws MalformedURLException {
		File file = new File(propertiesPath);
		URL[] urls={file.toURI().toURL()};
		loader = new URLClassLoader(urls);
		this.language = currentLanguage;
		this.propertiesPath= propertiesPath;
		this.propertiesFileName = propertiesFileName;
		setLanguageCountry();
	}
	
	public LanguageHandler(String propertiesPath,String propertiesFileName,String language, String country) throws MalformedURLException {
		File file = new File(propertiesPath);
		URL[] urls={file.toURI().toURL()};
		loader = new URLClassLoader(urls);
		this.language = language;
		this.country = country;
		this.propertiesPath= propertiesPath;
		this.propertiesFileName = propertiesFileName;
	}
	
	public String getProperty(String key){
		String value = null;		
		Locale currentLocale = new Locale(language,country);
		ResourceBundle resource = ResourceBundle.getBundle(propertiesPath+propertiesFileName,currentLocale);
		value = resource.getString(key);
		return value;
	}
	
	public void setLanguageCountry() {
		if(language.contains("_")) {
			String[] languageCountry = language.split("_");
			this.language = languageCountry[0];
			this.country = languageCountry[1];
		}
	}

}
