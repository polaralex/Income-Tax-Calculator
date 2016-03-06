package dataManagement;

public class HeadOfHousehold extends Person {

	HeadOfHousehold(String firstName, String lastName, Integer identifyingNumber, Object income) {
		super(firstName, lastName, identifyingNumber, income);
	}
	
	public HeadOfHousehold() {
		super();
	}
	
public Double calculateTaxBeforeReceipts() {
		
		Double income = getIncome();
		
		if( income >= 0 && income < 30390) {
			
			return ( income * 0.0535d );
			
		} else if ( income < 90000 ) {
			
			return ( 1624.87 + 0.0705*(income - 30390) );
		
		} else if ( income < 122110 ) {
			
			return ( 5828.38 + 0.0705*(income - 90000) );
		
		} else if ( income < 203390 ) {
			
			return ( 8092.13 + 0.0785*(income - 122110) );
		
		} else {
			
			return ( 14472.61 + 0.0985*(income - 203390) );
		
		}
	}

	@Override
	public String getPersonType() {
		return ("Head of Household");
	}
}