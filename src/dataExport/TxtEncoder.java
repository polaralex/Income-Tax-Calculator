package dataExport;

import dataManagement.Person;

public class TxtEncoder extends OutputFileEncoder {

	public TxtEncoder(String fileOutputPath, Person inputPerson) {
		super(fileOutputPath, inputPerson);
	}
	
	protected void filetypeSpecificEncodingProcess(){
		writeOpeningTag("Receipts");
		convertReceiptsToTag(person.getReceiptsList());
	}

	protected void writeTag(String tagName, String includedData) {

		stringBuilder.append(tagName + ":");
		stringBuilder.append(" " + includedData + " ");
		stringBuilder.append(String.format("%n"));
	}

	private void writeOpeningTag(String tagName) {

		stringBuilder.append(tagName + ":");
		stringBuilder.append(String.format("%n"));
	}
}
