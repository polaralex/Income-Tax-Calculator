package dataInput;

import java.io.File;
import dataManagement.PeopleManager;
import dataManagement.Person;

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
			
			Person newPerson = inputFileParser.getPerson();
			newPerson.setFile(file);
			return (newPerson);
			
		} catch (Exception e) {
			// In the event of invalid data input a placeholder Person is returned:
			return (PeopleManager.createNewPerson("Single", "No Name Found", "", 0, 0d));
		}
	}
}
