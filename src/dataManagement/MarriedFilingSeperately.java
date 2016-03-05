package dataManagement;

public class MarriedFilingSeperately extends Person {

	MarriedFilingSeperately(String firstName, String lastName, Integer identifyingNumber, Object income) {
		super(firstName, lastName, identifyingNumber, income);
	}

public Double calculateTaxBeforeReceipts() {
		
		Double income = getIncome();
		
		if( income >= 0 && income < 18040) {
			
			return ( income * 0.0535d );
			
		} else if ( income < 71680 ) {
			
			return ( 965.14 + 0.0705*(income - 18040) );
		
		} else if ( income < 90000 ) {
			
			return ( 4746.76 + 0.0785*(income - 71680) );
		
		} else if ( income < 127120 ) {
			
			return ( 6184.88 + 0.0785*(income - 90000) );
		
		} else {
			
			return ( 9098.80 + 0.0985*(income - 127120) );
		
		}
	}
}
