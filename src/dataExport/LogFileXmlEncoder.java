package dataExport;

import dataManagement.Person;

public class LogFileXmlEncoder extends OutputFileEncoder {
	
	public LogFileXmlEncoder (String fileOutputPath, Person inputPerson) {	
		
		super(fileOutputPath, inputPerson);
	}
	
	protected void filetypeSpecificEncodingProcess(){
		personToLogConverter(person);
	}
	
	protected void writeTag (String tagName, String includedData) {
		
		stringBuilder.append("<"+tagName+">");
		stringBuilder.append(" "+includedData+" ");
		stringBuilder.append("</"+tagName+">");
		stringBuilder.append(String.format("%n"));
	}
}