package dataExport;

import java.io.File;
import dataExport.LogFileTxtEncoder;
import dataExport.LogFileXmlEncoder;
import dataExport.OutputFileEncoder;
import dataExport.TxtEncoder;
import dataExport.XmlEncoder;
import dataManagement.Person;

public class OutputManager {

	// This will check if the File to be saved needs TXT of XML format:
	public static void savePersonToFile(Person personToSave, File file) {
		
		if (getFileExtension(file).equals("txt")) {
			
			OutputFileEncoder txtEncoder = new TxtEncoder(file.getAbsolutePath(), personToSave);
			personToSave.setFile(file);
			
		} else if (getFileExtension(file).equals("xml")) {
			
			OutputFileEncoder xmlEncoder = new XmlEncoder(file.getAbsolutePath(), personToSave);
			personToSave.setFile(file);
		}
	}

	public static void savePersonToLogFile(Person personToSave, File file) {

		if (getFileExtension(file).equals("txt")) {
			
			OutputFileEncoder logFileTxtEncoder = new LogFileTxtEncoder(file.getAbsolutePath(), personToSave);
		
		} else if (getFileExtension(file).equals("xml")) {
			
			OutputFileEncoder logFileXmlEncoder = new LogFileXmlEncoder(file.getAbsolutePath(), personToSave);
		}
	}

	public static void updatePersonFile(Person personToSave) {

		// An update will happen only if a file has already been saved:
		if (!(personToSave.getFile() == null)) {
			savePersonToFile(personToSave, personToSave.getFile());
		}
	}
	
	public static String getPersonSuggestedFilename(Person personToSave, String type) {
		
		if (type.equals("xml")){
			return (personToSave.getIdentifyingNumber().toString() + "_INFO.xml");
		} else if (type.equals("txt")){
			return (personToSave.getIdentifyingNumber().toString() + "_INFO.txt");
		} else {
			return (personToSave.getIdentifyingNumber().toString() + "_LOG.txt");
		}
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return ("");
		}
	}
}
