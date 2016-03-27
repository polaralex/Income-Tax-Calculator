package dataExport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import dataManagement.Person;
import dataManagement.Receipt;

public abstract class OutputFileEncoder {

	protected File xmlOutput;
	protected FileWriter fileWriter;
	protected Person person;
	protected StringBuilder stringBuilder = new StringBuilder();
	protected String totalOutput;
	
	public OutputFileEncoder (String fileOutputPath, Person inputPerson) {
		
		xmlOutput = new File(fileOutputPath);
		person = inputPerson;
	}
	
	protected abstract void writeTag (String tagName, String includedData);
	
	protected void personToTagConverter(Person person) {
		
		writeTag("Name", person.getFirstName()+" "+person.getLastName());
		writeTag("AFM", person.getIdentifyingNumber().toString());
		writeTag("Status", person.getPersonType());
		writeTag("Income", person.getIncome().toString());
	}
		
	protected void receiptsToTagConverter(ArrayList<Receipt> receiptsList) {
		
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
	}
	
	protected void personToLogConverter(Person person) {
		writeTag("Name", person.getFirstName()+" "+person.getLastName());
		writeTag("AFM", person.getIdentifyingNumber().toString());
		writeTag("Income", person.getIncome().toString());
		writeTag("Basic Tax", person.calculateTaxBeforeReceipts().toString());
		Double tempTaxIncread = person.calculateTax() - person.calculateTaxBeforeReceipts();
		writeTag("Tax Increase", tempTaxIncread.toString());
		writeTag("Total Tax", person.calculateTax().toString());
		writeTag("Total Receipts Gathered", person.calculateReceiptAmount().toString());
		writeTag("Basic", person.calculateReceiptAmount("Basic").toString());
		writeTag("Entertainment", person.calculateReceiptAmount("Entertainment").toString());
		writeTag("Travel", person.calculateReceiptAmount("Travel").toString());
		writeTag("Health", person.calculateReceiptAmount("Health").toString());
		writeTag("Other", person.calculateReceiptAmount("Other").toString());
	}
		
	protected void saveOutputToFile(File outputFile) {
		
		try {
			fileWriter = new FileWriter(outputFile,false);
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