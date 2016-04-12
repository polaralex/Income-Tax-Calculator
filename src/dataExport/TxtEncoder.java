package dataExport;

import dataManagement.Person;

public class TxtEncoder extends OutputFileEncoder {
	
	public TxtEncoder (String fileOutputPath, Person inputPerson) {	
		super(fileOutputPath, inputPerson);
		doEncodingProcess();
	}
	
	private void doEncodingProcess() {
		
		personToTagConverter(person);
		
		writeOpeningTag("Receipts");
		convertReceiptsToTag(person.getReceiptsList());
		totalOutput = stringBuilder.toString();
		saveOutputToFile(xmlOutput);
	}
	
	protected void writeTag (String tagName, String includedData) {
				
		stringBuilder.append(tagName+":");
		stringBuilder.append(" "+includedData+" ");
		stringBuilder.append(String.format("%n"));
	}
	
	private void writeOpeningTag (String tagName) {

		stringBuilder.append(tagName+":");
		stringBuilder.append(String.format("%n"));
	}
}