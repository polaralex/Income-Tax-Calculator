package dataInput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import dataManagement.Person;
import dataManagement.Receipt;

public class XmlEncoder {

	private File xmlOutput;
	private FileWriter fileWriter;
	private Person person;
	private StringBuilder stringBuilder = new StringBuilder();
	private String totalOutput;
	
	public XmlEncoder (String fileOutputPath, Person inputPerson) {
		
		xmlOutput = new File(fileOutputPath);
		person = inputPerson;
		personToTagConverted(person);
		receiptsToTagConverted(person.getReceiptsList());
		totalOutput = stringBuilder.toString();
		saveOutputToFile(xmlOutput);
	}
	
	private void personToTagConverted(Person person) {
		
		writeTag("Name", person.getFirstName()+" "+person.getLastName());
		writeTag("AFM", person.getIdentifyingNumber().toString());
		writeTag("Status", person.getPersonType());
		writeTag("Income", person.getIncome().toString());
		
	}
	
	private void receiptsToTagConverted(ArrayList<Receipt> receiptsList) {
		
		writeOpeningTag("Receipts");
		
		if ((receiptsList != null) && (receiptsList.isEmpty() == false)) {
			
			for(Receipt receipt : receiptsList) {
				
				writeTag("ReceiptID", receipt.getReceiptId().toString());
				
				DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
				writeTag("Date", format.format(receipt.getDateOfIssue()));
				
				writeTag("Kind", receipt.getCategory());
				writeTag("Amount", receipt.getAmount().toString());
				writeTag("Company", receipt.getCompany().getName());
				writeTag("Country", " "); // TODO: CHECK THE ADDRESS THING OF THE COMPANY OBJECT
				writeTag("City", " ");
				writeTag("Street", receipt.getCompany().getAddress());
				writeTag("Number", " ");
			}
		}
		
		writeClosingTag("Receipts");
	}
	
	private void writeTag (String tagName, String includedData) {
				
		stringBuilder.append("<"+tagName+">");
		stringBuilder.append(" "+includedData+" ");
		stringBuilder.append("</"+tagName+">");
		stringBuilder.append(String.format("%n"));
	}
	
	private void writeOpeningTag (String tagName) {

		stringBuilder.append("<"+tagName+">");
		stringBuilder.append(String.format("%n"));
	}
	
	private void writeClosingTag (String tagName) {

		stringBuilder.append("</"+tagName+">");
		stringBuilder.append(String.format("%n"));
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