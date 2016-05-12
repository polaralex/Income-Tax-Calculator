package dataInput;

import java.io.File;

public class TextFileParser extends InputFileParser {

	public TextFileParser(File filename) {
		super(filename);
	}

	protected void parseReceiptData() {

		consumeReceipt();

		while (isNextWordReceiptId()) {

			receiptId = checkForTagData("ID").trim();
			parseReceiptDataCommonCode();
		}
	}

	protected String checkForTagData(String tagElement) {

		getNextWord();
		String currentWord = "";

		if (word.equals(tagElement+":")){

			getNextWord();

			while(!(word.matches("\\w+:")) && !(word.equals("Receipt")) && !(isEndOfParsedWords())){
				currentWord = currentWord.concat(" " + word);
				getNextWord();
			}

			goToPreviousWord();
			return (currentWord);

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
}
