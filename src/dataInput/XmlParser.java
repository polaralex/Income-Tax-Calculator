package dataInput;

import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dataManagement.Company;
import dataManagement.Receipt;
import java.util.Locale;

public class XmlParser extends InputFileParser {

	public XmlParser(File filename) {
		
		tokenizeInput(filename);
	    parsePersonData();
	    parseReceiptData();
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

	void parseReceiptData() {

		consumeReceipt();

		while (isNextWordReceiptId()) {

			String receiptId = checkInsideTag("ReceiptID").trim();
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
	}

	private String checkInsideTag(String tagElement) {

		getNextWord();

		String currentWord = "";

		if (word.equals("<" + tagElement + ">")) {

			getNextWord();

			while (!(word.equalsIgnoreCase("</" + tagElement + ">"))) {
				currentWord = currentWord + " " + word;
				getNextWord();
			}

			return currentWord;

		} else {
			return null;
		}
	}

	private Boolean isNextWordReceiptId() {
		getNextWord();
		if (word.equals("<ReceiptID>")) {
			parsedWordsIterator = parsedWordsIterator - 2;
			getNextWord();
			return (true);
		} else {
			return (false);
		}
	}

	private void consumeReceipt() {
		getNextWord();
	}
}