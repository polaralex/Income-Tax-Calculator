package dataInput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dataManagement.Person;

public class XmlEncoder {

	private File xmlOutput;
	private FileWriter fileWriter;
	private Person person;
	private String totalOutput = "";
	
	public XmlEncoder (String fileOutputPath, Person inputPerson) {
		
		xmlOutput = new File(fileOutputPath);
		person = inputPerson;
		personToTagConverted(person);
		saveOutputToFile(xmlOutput);
	}
	
	private void personToTagConverted(Person person) {
		
		System.out.println("Before conversion, First Name is: "+person.getFirstName());
		System.out.println("Before conversion, Last Name is: "+person.getLastName());
		
		writeTag("Name", person.getFirstName()+" "+person.getLastName(), totalOutput);
		writeTag("Afm", person.getIdentifyingNumber().toString(), totalOutput);
		writeTag("Status", person.getPersonType(), totalOutput);
		writeTag("Income", person.getIncome().toString(), totalOutput);
		
		System.out.println("After conversion, total Output is: "+totalOutput);
	}
	
	private void writeTag (String tagName, String includedData, String totalOutput) {
		
		totalOutput.concat("<"+tagName+">");
		totalOutput.concat(" "+includedData+" ");
		totalOutput.concat("</"+tagName+">");
		totalOutput.concat(String.format("%n"));
	}
	
	private void saveOutputToFile(File outputFile) {
		
		try {
			fileWriter = new FileWriter(outputFile,true);
			fileWriter.append(totalOutput);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}