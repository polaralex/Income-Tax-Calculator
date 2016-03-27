package dataInput;

import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dataManagement.Company;
import dataManagement.Receipt;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TextFileParser extends InputFileParser {
	
	public TextFileParser(File filename) {
		tokenizeInput(filename);
	    try {
			parsePersonData();
			parseReceiptData();
		} catch (Exception e) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Error: There is probably some problem with the input data. "
					+ "Please check the requirements for consistency.", "Parsing Engine Error", JOptionPane.ERROR_MESSAGE);
		}
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
		
		consumeReceiptsLabel();
		
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
		}
	}
	
	private String checkLabel(String tagElement) {
		
		getNextWord();

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
	
	private void consumeReceiptsLabel(){
		getNextWord();
	}
}