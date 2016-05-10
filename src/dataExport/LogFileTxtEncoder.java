package dataExport;

import dataManagement.Person;

public class LogFileTxtEncoder extends OutputFileEncoder {
	
	public LogFileTxtEncoder (String fileOutputPath, Person inputPerson) {	
		
		super(fileOutputPath, inputPerson);
	}
	
	protected void filetypeSpecificEncodingProcess(){
		personToLogConverter(person);
	}
	
	protected void writeTag (String tagName, String includedData) {
		
		stringBuilder.append(tagName+":");
		stringBuilder.append(" "+includedData+" ");
		stringBuilder.append(String.format("%n"));
	}
}