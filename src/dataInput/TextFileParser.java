package dataInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dataManagement.Company;
import dataManagement.HeadOfHousehold;
import dataManagement.MarriedFilingJointly;
import dataManagement.MarriedFilingSeperately;
import dataManagement.PeopleManager;
import dataManagement.Person;
import dataManagement.Receipt;
import dataManagement.Single;

import java.util.ArrayList;
import java.util.Locale;

public class TextFileParser extends InputFileParser {
	
	public TextFileParser(File filename) {
		
		tokenizeInput(filename);
	    parsePersonData();
	    parseReceiptData();
	    
	}
	
	void parsePersonData() {
			
		// Parse the personal data one-by-one:
		name = checkLabel("Name").trim();
		afm = checkLabel("AFM").trim();
		status = checkLabel("Status").trim();
		income = checkLabel("Income").trim();

		// Preparing name for processing:
		String[] splittedName = name.split("\\s+");
		
		// Encoding to Person Object compatible types:
		firstname = splittedName[0];
		lastname = splittedName[1];
		afmFinal = Integer.parseInt(afm);
		incomeFinal = Double.parseDouble(income);

	}
	
	void parseReceiptData() {
		
		consumeReceipts();
		
		while ( isNextWordReceiptId() ) {
						
			String receiptId = checkLabel("ID").trim();
			String date = checkLabel("Date").trim();
			String kind = checkLabel("Kind").trim();
			String amount = checkLabel("Amount").trim();
			String company = checkLabel("Company").trim();
			String country = checkLabel("Country").trim();
			String city = checkLabel("City").trim();
			String street = checkLabel("Street").trim();
			String addressNumber = checkLabel("Number").trim();
			
			// Encode to types compatible with the Receipt Object:
			Integer receiptIdFinal = Integer.parseInt(receiptId);
			Date dateFinal = new Date();
			DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
			try {
				dateFinal = format.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String categoryFinal = kind;
			Double amountFinal = Double.valueOf(amount);
			Company companyFinal = new Company(company, street + " " + addressNumber + ", " + city + ", " + country);
			
			receiptsList.add(new Receipt(receiptIdFinal, dateFinal, categoryFinal, amountFinal, companyFinal));
			
			// Debugging:
				//System.out.println(receiptId+" "+date+" "+kind+" "+amount+" "+company+" "+country+" "+city+" "+street+" "+addressNumber);
				//System.out.println("Receipts List Size: "+receiptsList.size());
		}
	}
	
	private String checkLabel(String tagElement) {
		
		getNextWord();
		System.out.println("Searching for: "+ tagElement+ ", Current word: "+ word +", Parsed Words Iterator: " + parsedWordsIterator);
		String currentWord = "";
		
		if(word.equals(tagElement+":")){
			
			getNextWord();
			
			while( !(word.matches("\\w+:")) && !(word.equals("Receipt")) && !(isEndOfParsedWords()) ){
				System.out.println("Current word: "+word);
				System.out.println("Current word building: "+currentWord);

				currentWord = currentWord.concat(" " + word);
				getNextWord();
			}
			
			goToPreviousWord();
			
			System.out.println("After End of Loop Current Word: "+currentWord);
			
			return currentWord;
			
		} else {
			return null;
		}
	}

	private Boolean isNextWordReceiptId() {
		getNextWord();
		if (word.equals("Receipt")){
			
			getNextWord();
			
			if(word.equals("ID:")){
				goToPreviousWord();
				return (true);
			}	
		} else {
			return (false);
		}
		return (false);
	}
	
	private void consumeReceipts(){

		getNextWord();
		
		if( word.equals("Receipts:")){
			System.out.println("'Receipts' correctly consumed");
		}
	}
}