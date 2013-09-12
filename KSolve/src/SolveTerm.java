import java.util.ArrayList;
import java.util.Arrays;


public class SolveTerm {
	public Term term;
	int numVar;
	int[][] binaryTerm;
	String sln;
	ArrayList<Character> varArr;
	
	public SolveTerm(Term term, int numVar, ArrayList<Character> varArr){
		this.term = term;
		this.numVar = numVar;
		sln = "";
		binaryTerm = new int[term.term.size()][numVar];
		this.varArr = varArr;
		
	}
	
	public void termToBinary(){
		for (int i = 0; i < term.term.size(); i++) {
			for (int j = 0; j < numVar; j++) {
				binaryTerm[i][j] = convert(term.term.get(i),numVar)[j];
			}
		}
	}
	
	public String binaryToString(){
		String t = "(";
		for (int i = 0; i < 4; i++) {
			int check = checkCol(i);
			
			if(check==-1){
				//varArr[i] is canceled from the term.
			}
			else{ 
				if (check == 1){
					t+= varArr.get(i);
				}
				else if (check == 0){
					t+= "~"+varArr.get(i) ;
				}
			}
		}
		t+=")";
		return t;
	}

	public int checkCol(int j){
		int value = binaryTerm[0][j];
//		System.out.println("value: "+value);
		for (int i = 0; i < term.term.size(); i++) {
			if(binaryTerm[i][j]!=value){
//				System.out.println("removeVarFromTerm: "+ j);
				return -1;
			}			
		}
		return value;
	}
	
	public String toString() {
		String t="";
		for (int i = 0; i < term.term.size(); i++) {
			for (int j = 0; j < numVar; j++) {
				t+= binaryTerm[i][j];
			}
			t+="\n";
		}
		return t;
	}

	public int[] convert(int val, int numVar){
		if(val == 0){
			return new int[] {0,0,0,0};
		}
		else if(val == 1){
			return new int[] {0,0,0,1};
		}
		else if(val == 2){
			return new int[] {0,0,1,1};
		}
		else if(val == 3){
			return new int[] {0,0,1,0};
		}
		else if(val == 4){
			return new int[] {0,1,0,0};
		}
		else if(val == 5){
			return new int[] {0,1,0,1};
		}
		else if(val == 6){
			return new int[] {0,1,1,1};
		}
		else if(val == 7){
			return new int[] {0,1,1,0};
		}
		else if(val == 8){
			return new int[] {1,1,0,0};
		}
		else if(val == 9){
			return new int[] {1,1,0,1};
		}
		else if(val == 10){
			return new int[] {1,1,1,1};
		}
		else if(val == 11){
			return new int[] {1,1,1,0};
		}
		else if(val == 12){
			return new int[] {1,0,0,0};
		}
		else if(val == 13){
			return new int[] {1,0,0,1};
		}
		else if(val == 14){
			return new int[] {1,0,1,1};
		}
		else if(val == 15){
			return new int[] {1,0,1,0};
		}
		else
			return null;
	}	
}
