package dataManagement;

import java.util.ArrayList;

public class Single extends Person {

	public Single(String firstName, String lastName, Integer identifyingNumber, Object income) {
		super(firstName, lastName, identifyingNumber, income);
	}
	
	public Single() {
		super();
	}

	public Double calculateTaxBeforeReceipts() {
		
		Double income = getIncome();
		
		if( income >= 0 && income < 24680) {
			
			return ( income * 0.0535d );
			
		} else if ( income < 81080 ) {
			
			return ( 1320.38 + 0.0705*(income - 24680) );
		
		} else if ( income < 90000 ) {
			
			return ( 5296.58 + 0.0785*(income - 81080) );
		
		} else if ( income < 152540 ) {
			
			return ( 5996.80 + 0.0785*(income - 90000) );
		
		} else {
			
			return ( 10906.19 + 0.0985*(income - 152540) );
		
		}
	}
	
	@Override
	public String getPersonType() {
		return ("Single");
	}
}
