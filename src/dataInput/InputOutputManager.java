package dataInput;

import java.io.File;
import java.util.ArrayList;

import dataManagement.PeopleManager;
import dataManagement.Person;
import dataManagement.Receipt;

public class InputOutputManager {
		
	// This will check if the File to be saved needs to be in TXT of XML format:
	public static void savePersonToFile (Person personToSave, File file) {
		
		if ( getFileExtension(file).equals("txt") ) {
			savePersonToTXTFile(personToSave, file);
		} else if ( getFileExtension(file).equals("xml") ) {
			savePersonToXMLFile(personToSave, file);
		}
	}
	
	public static void updatePersonFile (Person personToSave) {
		
		// An update will happen only if a file already exists (has been saved previously).
		//I use the .length() method, because otherwise it would throw a null pointer exception:
		if (!(personToSave.getFile() == null)){
			savePersonToFile(personToSave, personToSave.getFile());
		}
	}
	
	public static void savePersonToXMLFile (Person personToSave, File file) {
		
		XmlEncoder xmlEncoder = new XmlEncoder(file.getAbsolutePath(), personToSave);
		personToSave.setFile(file);
	}
	
	public static void savePersonToTXTFile (Person personToSave, File file) {
		
		// TODO: Change this to TxtEncoder
		XmlEncoder xmlEncoder = new XmlEncoder(file.getAbsolutePath(), personToSave);
		personToSave.setFile(file);
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
	
	public static String getPersonSuggestedTXTFilename (Person personToSave) {
		return (personToSave.getIdentifyingNumber().toString()+"_INFO.txt");
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}