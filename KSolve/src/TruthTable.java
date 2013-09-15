/*
 * @author Michael VanWie
 * This class is holds a basic truth table used for solving a 
 * boolean function.
 * 
 * The number of variables can scales until out of memory. (my 
 * laptop runs out of memory at 2^15)
 *  
 */
public class TruthTable {
	TTRow[] grid;
	int numVar;
	int x;
	int y;

	public TruthTable(int numVar) {
		this.numVar = numVar;
		x = (int) Math.pow(2, numVar);
		grid = new TTRow[x];
		for (int i = 0; i < x; i++) {
			grid[i] = new TTRow(numVar);
		}
		fillTruthTable(x);
	}

	public String toString(){
		String t ="";
		for (TTRow ttRow : grid) {
			t+=ttRow.toString()+"\n";
			
		}
		return t;
	}
	
	
	public void fillTruthTable(int x) {
		fillStepOne();
	}

	public void fillStepOne() {
		for (int i = 0; i < x; i++) {
			grid[i].setLengthAndValue(i);
		}
	}
}
