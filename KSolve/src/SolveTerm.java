import java.util.ArrayList;

public class SolveTerm {
	int numVar;
	int[][] binaryTerm;
	String sln;
	ArrayList<Character> varArr;
	TTRow prime;


	public SolveTerm(TTRow prime, int numVar, ArrayList<Character> varArr) {
		this.prime = prime;
		this.numVar = numVar;
		sln = "";
		binaryTerm = new int[prime.set.size()][numVar];
		this.varArr = varArr;
	}

	public void primeToBinary() {
		for (int i = 0; i < prime.set.size(); i++) {
				binaryTerm[i] = prime.row;
		}
	}

	public String primeToString(int solveFor) {
		String t = "(";
		for (int i = 0; i < numVar; i++) {
			int check = checkPrimeCol(i);
			if (check == -1) {
				// varArr[i] is canceled from the term.
			} 
			else {
				if(solveFor ==0){
					if (check == 0) {
						t += varArr.get(numVar-1-i)+"+";
					} 
					else if (check == 1) {
						t += "~" + varArr.get(numVar-1-i)+"+";
					}				
				}
				else{
					if (check == 1) {
						t += varArr.get(numVar-1-i);
					} 
					else if (check == 0) {
						t += "~" + varArr.get(numVar-1-i);
					}
				}
			}
		}
		t += ")";
		
		if(t.charAt(t.length()-2) == '+'){
			t = t.substring(0, t.length()-2) +")";
		}
		
		return t;
	}
	
	public int checkPrimeCol(int j) {
			
		int value = binaryTerm[0][j];
		// System.out.println("value: "+value);
		for (int i = 0; i < prime.set.size(); i++) {
			if (binaryTerm[i][j] != value) {
				// System.out.println("removeVarFromTerm: "+ j);
				return -1;
			}
		}
		return value;
	}

	public String invert(String t){
		System.out.println("CALL MY INVERT METHOD");
		String result = "";
//		if(!t.equals("")){
//			char prevChar = t.charAt(0);
//			for (int i = 0; i < t.length(); i++) {
//				if(t.charAt(i) == '(' || t.charAt(i) == ')' ){
//					result += t.charAt(i);
//				}
//				
//				if(t.charAt(i) >=  'A' && t.charAt(i) <= 'Z'){
//					if(prevChar == '~'){
//						if(i!=t.length() && t.charAt(i+1)!=')'){
//							result += t.charAt(i)+"+";
//						}
//						else{
//							result += t.charAt(i);
//						}
//					}
//					else{
//						if(i!=t.length() && t.charAt(i+1)!=')'){
//							result += "~"+t.charAt(i)+"+";
//						}
//						else{
//							result +=  "~"+t.charAt(i);
//						}
//					}
//				}
//				prevChar = t.charAt(i);
//			}
//		}
		result = t;
		return result;
	}
	
	
	
//	public String toString() {
//		String t = "";
//		for (int i = 0; i < term.term.size(); i++) {
//			for (int j = 0; j < numVar; j++) {
//				t += binaryTerm[i][j];
//			}
//			t += "\n";
//		}
//		return t;
//	}
}
