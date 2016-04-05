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

public class InputManager {
	
	public static Person importPersonFromFile(File file) {
		
		String filename = file.getAbsolutePath();
		InputFileParser inputFileParser;
		
		if( filename.substring(filename.lastIndexOf(".") + 1).equals("xml") ){
			inputFileParser = new XmlParser(new File(filename));
		} else {
			inputFileParser = new TextFileParser(new File(filename));
		}
								
		try {
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
		} catch (Exception e) {
			// In the event of invalid data input a placeholder Person is returned:
			return (PeopleManager.createNewPerson("Single", "No Name Found", "", 0, 0d));
		}
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}