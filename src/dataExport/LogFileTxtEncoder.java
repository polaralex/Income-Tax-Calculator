package dataExport;

import dataManagement.Person;

public class LogFileTxtEncoder extends OutputFileEncoder {
	
	public LogFileTxtEncoder (String fileOutputPath, Person inputPerson) {	
		
		super(fileOutputPath, inputPerson);
		doEncodingProcess();
	}
	
	private void doEncodingProcess(){
		
		personToLogConverter(person);
		totalOutput = stringBuilder.toString();
		saveOutputToFile(xmlOutput);
	}
	
	protected void writeTag (String tagName, String includedData) {
		
		stringBuilder.append(tagName+":");
		stringBuilder.append(" "+includedData+" ");
		stringBuilder.append(String.format("%n"));
	}
}