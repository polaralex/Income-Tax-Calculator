package dataInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dataManagement.Company;
import dataManagement.Receipt;

public abstract class InputFileParser {
	
	// Person data in String form:
	protected String name = null;
	protected String afm = null;
	protected String status = null;
	protected String income = null;
	
	// Used for the only different spelling of a tag
	// between the two inherited classes:
	protected String receiptId;
	
	protected ArrayList<String> parsedWords = new ArrayList<String>();
	protected Integer parsedWordsIterator = 0;
	protected String word;
	protected String readWords;

	// Data to Export for Person:
	protected String firstname = "empty";
	protected String lastname = "empty";
	protected Integer afmFinal = 00000;
	protected Double incomeFinal = 00000d;
	
	// Data to Export for Receipts:
	protected ArrayList<Receipt> receiptsList = new ArrayList<Receipt>();
	
	public InputFileParser(File filename) {
		
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
	
	private void parseInputLines(BufferedReader in, File textFile) throws IOException{
	    in = new BufferedReader(new FileReader(textFile));
	    String readLine = "";
	    
	    while ( (readLine = in.readLine()) != null ) {
	    	concatenateInputWordsToBuffer(readLine);
	    }
	    
	    in.close();
	}
	
	protected abstract String checkInsideTag(String tagElement);
	
	abstract void parseReceiptData();
	
	protected void parseReceiptDataCommonCode() {
		
		String date = checkInsideTag("Date").trim();
		String kind = checkInsideTag("Kind").trim();
		String amount = checkInsideTag("Amount").trim();
		String company = checkInsideTag("Company").trim();
		String country = checkInsideTag("Country").trim();
		String city = checkInsideTag("City").trim();
		String street = checkInsideTag("Street").trim();
		String addressNumber = checkInsideTag("Number").trim();

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
	
	protected void consumeReceipt() {
		getNextWord();
	}
	
	void parsePersonData() {

		name = checkInsideTag("Name").trim();
		afm = checkInsideTag("AFM").trim();
		status = checkInsideTag("Status").trim();
		income = checkInsideTag("Income").trim();

		// Preparing name for processing:
		String[] splittedName = name.split("\\s+");

		// Encoding to Person Object compatible types:
		firstname = splittedName[0];
		lastname = splittedName[1];
		afmFinal = Integer.parseInt(afm);
		incomeFinal = Double.parseDouble(income);
	}
	
	protected void tokenizeInput (File textFile) {
		
		BufferedReader in = null;
		try {
			parseInputLines(in, textFile);
			splitInputLinesToWords();
		    closeInputFile(in);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private void closeInputFile(BufferedReader in){
		try {
	        in.close();
	    } catch (Exception e) {
	    }
	}
	
	private void splitInputLinesToWords(){
	    if ( readWords != null) {
	    	String[] splited;
	        splited = readWords.split("\\s+");
	        for (String word : splited) {
	            if (word != null) { 
	            	parsedWords.add(word);
	            }
	        }
	    }
	}
	
	private void concatenateInputWordsToBuffer(String readLine) {
    	if (readWords == null) {
    		readWords = readLine;
    	} else {
    		readWords = readWords+" "+readLine;
    	}
	}
	
	protected void getNextWord() {
		if (parsedWordsIterator < parsedWords.size()) {
			
			word = parsedWords.get(parsedWordsIterator);
			parsedWordsIterator++;
			
		} else {
			word = "\0";
		}
	}
	
	protected void goToPreviousWord() {
		parsedWordsIterator--;
		word = parsedWords.get(parsedWordsIterator);
	}
	
	protected Boolean isEndOfParsedWords() {
		if(parsedWordsIterator >= parsedWords.size()){
			return(true);
		} else {
			return(false);
		}
	}
	
	public ArrayList<Receipt> getReceiptsList() {
		return receiptsList;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Integer getAfm() {
		return afmFinal;
	}

	public Double getIncome() {
		return incomeFinal;
	}

	public String getCategory() {
		return status;
	}
}