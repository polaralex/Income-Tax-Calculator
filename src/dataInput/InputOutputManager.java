package dataInput;

import java.io.File;
import java.util.ArrayList;

import dataExport.LogFileTxtEncoder;
import dataExport.LogFileXmlEncoder;
import dataExport.OutputFileEncoder;
import dataExport.TxtEncoder;
import dataExport.XmlEncoder;
import dataManagement.PeopleManager;
import dataManagement.Person;
import dataManagement.Receipt;

public class InputOutputManager {
		
	// This will check if the File to be saved needs to be in TXT of XML format:
	public static void savePersonToFile (Person personToSave, File file) {
		
		if ( getFileExtension(file).equals("txt") ) {
			savePersonToTXTFile(personToSave, file);
		} else if ( getFileExtension(file).equals("xml") ) {
			savePersonToXmlFile(personToSave, file);
		}
	}
	
	public static void savePersonToLogFile (Person personToSave, File file) {
		
		if ( getFileExtension(file).equals("txt") ) {
			saveLogAsTxtFile(personToSave, file);
		} else if ( getFileExtension(file).equals("xml") ) {
			saveLogAsXmlFile(personToSave, file);
		}
	}
	
	public static void updatePersonFile (Person personToSave) {
		
		// An update will happen only if a file already exists (has been saved previously):
		if (!(personToSave.getFile() == null)){
			savePersonToFile(personToSave, personToSave.getFile());
		}
	}
	
	public static void savePersonToXmlFile (Person personToSave, File file) {
		
		OutputFileEncoder xmlEncoder = new XmlEncoder(file.getAbsolutePath(), personToSave);
		personToSave.setFile(file);
	}
	
	public static void savePersonToTXTFile (Person personToSave, File file) {
		
		OutputFileEncoder txtEncoder = new TxtEncoder(file.getAbsolutePath(), personToSave);
		personToSave.setFile(file);
	}
	
	public static void saveLogAsXmlFile (Person personToSave, File file) {
			
		OutputFileEncoder logFileXmlEncoder = new LogFileXmlEncoder(file.getAbsolutePath(), personToSave);
	}

	public static void saveLogAsTxtFile (Person personToSave, File file) {
		
		OutputFileEncoder logFileTxtEncoder = new LogFileTxtEncoder(file.getAbsolutePath(), personToSave);
	}
	
	public static Person importPersonFromFile(File file) {
		
		String filename = file.getAbsolutePath();
		InputFileParser inputFileParser;
		
		if( filename.substring(filename.lastIndexOf(".") + 1).equals("xml") ){
			inputFileParser = new XmlParser(new File(filename));
		} else {
			inputFileParser = new TextFileParser(new File(filename));
		}
								
		String firstname = inputFileParser.getFirstname();
		String lastname = inputFileParser.getLastname();
		String category = inputFileParser.getCategory();
		Integer afm = inputFileParser.getAfm();
		Double income = inputFileParser.getIncome();
		ArrayList<Receipt> receiptList = inputFileParser.getReceiptsList();
		
		Person newPerson = PeopleManager.createNewPerson(category, firstname, lastname, afm, income);
		newPerson.setFile(file);
		
		if (!receiptList.equals(null)){
			newPerson.addReceiptsList(receiptList);
		}
		
		return newPerson;
	}
	
	public static String getPersonSuggestedXMLFilename (Person personToSave) {
		return (personToSave.getIdentifyingNumber().toString()+"_INFO.xml");
	}
	
	public static String getPersonSuggestedTxtFilename (Person personToSave) {
		return (personToSave.getIdentifyingNumber().toString()+"_INFO.txt");
	}
	
	public static String getPersonSuggestedTxtLogFilename (Person personToSave) {
		return (personToSave.getIdentifyingNumber().toString()+"_LOG.txt");
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}