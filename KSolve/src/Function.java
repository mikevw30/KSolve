import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/*
 * @author Michael VanWie
 * Take in a Function in a formated String
 * break down that String to check for a valid function
 * if the function is valid fill truth tables and evaluate
 * hold this data for the kmap
 */

public class Function {
	String input;
	String postFix = "";
	int numVar;
	TruthTable truthTable;
	Stack<Character> funS = new Stack<Character>();
	ArrayList<Character> varArr = new ArrayList<Character>();

	int resultArr[];

	public Function(String input) {
		this.input = input;
	}

	public void normalizeInput() {
		String checkedInput = "";
		int index = 0;
		int end = input.length();
		char prevChar = input.charAt(index);
		char charAtIndex = input.charAt(index);
		while (index != end) {
			charAtIndex = input.charAt(index);
			if (((charAtIndex >= 'A' && charAtIndex <= 'Z') || (charAtIndex == '~'))
					&& (prevChar >= 'A' && prevChar <= 'Z') && index != 0) {
				checkedInput += '&';
			}
			if ((charAtIndex >= 'A' && charAtIndex <= 'Z') && prevChar == ')'
					&& index != 0) {
				checkedInput += '&';
			}
			if (charAtIndex == '('
					&& (prevChar == ')' || prevChar >= 'A' && prevChar <= 'Z')
					&& index != 0) {
				checkedInput += '&';
			}// prevChar =='(' ||
			if (charAtIndex == '~' && prevChar == ')' && index != 0) {
				checkedInput += '&';
			}
			prevChar = charAtIndex;
			checkedInput += charAtIndex;
			index++;
		}
		input = checkedInput;
	}

	/*
	 * Pseudo code for readInput() found below
	 * http://faculty.cs.niu.edu/~hutchins/csci241/eval.htm
	 */
	public void readInput() {
		int index = 0;
		int end = input.length();
		char charAtIndex = input.charAt(index);
		while (index != end) { // while we arent at the end of the input
			charAtIndex = input.charAt(index);
			if (charAtIndex >= 'A' && charAtIndex <= 'Z') { // check for valid
															// operand (capital
															// letters that
															// represent
															// variables)
				postFix += charAtIndex; // add charAtIndex to postFix
				if (!varArr.contains(charAtIndex)) { // get unique operators and
														// count them
					varArr.add(charAtIndex);
					numVar++;
				}
			}
			if (charAtIndex == '(') {// if left paren
				// System.out.println("found a ("+index+": "+charAtIndex);
				funS.push('('); // push it to stack
			}
			if (charAtIndex == ')') { // if right paren
				// System.out.println("found a )"+index+": "+charAtIndex);
				while (!funS.isEmpty() && !funS.peek().equals('(')) {  															 
																		// while the stack isn't empty and stack.peek is NOT a left paren
																		
					postFix += funS.pop();// pop the stack and add that value to
											// postFix
				}
				funS.pop(); // get rid of the left paren.
			}
			if (charAtIndex == '~' || charAtIndex == '+' || charAtIndex == '&'
					|| charAtIndex == '^') {
				// System.out.println("found a operator"+index+": "+charAtIndex);
				if (funS.isEmpty() || funS.peek().equals('(')) { // if the stack
																	// is empty
																	// or peek
																	// == (
					funS.push(charAtIndex);
				} 
				else {
					while (!funS.isEmpty() && !funS.peek().equals('(')
							&& checkStack(charAtIndex, funS.peek())) {
						// System.out.println(charAtIndex+","+funS.peek());
						postFix += funS.pop();
					}
					funS.push(charAtIndex);
				}
			}
			index++;
		}
		while (!funS.isEmpty()) {
			postFix += funS.pop();
		}
		truthTable = new TruthTable(numVar); // init the truth table.
		Collections.sort(varArr); // alpha sort the variables in the truth
									// table.
	}

	public boolean checkStack(char charAtIndex, char top) { // checking order of
															// operations.
		if (charAtIndex == '+' && (top == '~' || top == '&' || top == '^' || (top >= 'A' && top <= 'Z'))) {
			return true;
		} 
		else if (charAtIndex == '^' && (top == '~' || top == '&' || (top >= 'A' && top <= 'Z'))) {
			return true;
		}
		else if (charAtIndex == '&' && (top == '~' || (top >= 'A' && top <= 'Z'))) {
			return true;
		} 
		else if (charAtIndex == '~' && (top >= 'A' && top <= 'Z')) {
			return true;
		} 
		else {
			return false;
		}
	}

	public int evelInput(int gridRow) {
		Stack<Integer> evalS = new Stack<Integer>();
		int index = 0;
		int end = postFix.length();

		while (index != end) {
			char charAtIndex = postFix.charAt(index);
			if (charAtIndex >= 'A' && charAtIndex <= 'Z') {// an operand is
															// found
				// System.out.println("found a var: "+charAtIndex);
				int i = 0;
				for (i = 0; i < numVar; i++) {
					if (charAtIndex == varArr.get(i)) {
						break;
					}
				}
				evalS.push(truthTable.grid[gridRow].row[(numVar - 1) - i]);
			} 
			else {// an operator is found
				int result = -1;

				if (charAtIndex == '&') {
					result = (new BasicFunction(evalS.pop(), evalS.pop(), 2))
							.evalBF();
				} 
				else if (charAtIndex == '^') {
					result = (new BasicFunction(evalS.pop(), evalS.pop(), 3))
							.evalBF();
				} 
				else if (charAtIndex == '+') {
					result = (new BasicFunction(evalS.pop(), evalS.pop(), 1))
							.evalBF();
				} 
				else if (charAtIndex == '~') {
					result = evalS.pop();
					if (result == 1){
						result = 0;
					}
					else{
						result = 1;
					}
				}
				evalS.push(result);
			}
			index++;
		}
		return evalS.pop();
	}

	public void fillResult() {
		for (int i = 0; i < Math.pow(2, numVar); i++) {
			int temp = evelInput(i);
			truthTable.grid[i].setValue(temp);
		}
	}

	public String toString() {
		String t = input + "\n";
		t+= "#:\t";
		for (int i = 0; i < varArr.size(); i++) {
			t += varArr.get(i);
		}
		t += "\t: sln\n";

		for (int i = 0; i < Math.pow(2, numVar); i++) {
			t += truthTable.grid[i].toString() + "\n";
		}

		return t;
	}

	public int getNumVar() {
		return numVar;
	}

}
