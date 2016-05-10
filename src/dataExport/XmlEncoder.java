package dataExport;

import dataManagement.Person;

public class XmlEncoder extends OutputFileEncoder {

	public XmlEncoder(String fileOutputPath, Person inputPerson) {
		super(fileOutputPath, inputPerson);
	}
	
	protected void filetypeSpecificEncodingProcess(){
		writeOpeningTag("Receipts");
		convertReceiptsToTag(person.getReceiptsList());
		writeClosingTag("Receipts");
	}

	protected void writeTag(String tagName, String includedData) {

		stringBuilder.append("<" + tagName + ">");
		stringBuilder.append(" " + includedData + " ");
		stringBuilder.append("</" + tagName + ">");
		stringBuilder.append(String.format("%n"));
	}

	private void writeOpeningTag(String tagName) {

		stringBuilder.append("<" + tagName + ">");
		stringBuilder.append(String.format("%n"));
	}

	private void writeClosingTag(String tagName) {

		stringBuilder.append("</" + tagName + ">");
		stringBuilder.append(String.format("%n"));
	}
}