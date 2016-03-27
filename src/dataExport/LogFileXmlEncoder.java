package dataExport;

import dataManagement.Person;

public class LogFileXmlEncoder extends OutputFileEncoder {
	
	public LogFileXmlEncoder (String fileOutputPath, Person inputPerson) {	
		
		super(fileOutputPath, inputPerson);
		doEncodingProcess();
	}
	
	private void doEncodingProcess(){
		
		personToLogConverter(person);
		totalOutput = stringBuilder.toString();
		saveOutputToFile(xmlOutput);
	}
	
	protected void writeTag (String tagName, String includedData) {
		
		stringBuilder.append("<"+tagName+">");
		stringBuilder.append(" "+includedData+" ");
		stringBuilder.append("</"+tagName+">");
		stringBuilder.append(String.format("%n"));
	}
}