import java.util.ArrayList;

public class KSolver {
	Function function;
	KMap kMap;
	ArrayList<Term> termList;

	public KSolver() {
		termList = new ArrayList<Term>();
	}

	public void truthSolve() {
		function.normalizeInput();
		function.readInput();
		function.fillResult();
	}

	public void kEvalMap(int num) {
		for (int i = 0; i < kMap.x; i++) {
			for (int j = 0; j < kMap.y; j++) {
				if (getValue(i, j) == num)
					kEvalCord(i, j);
			}
		}
		int cancleCheck = kListEval();
		
		
		System.out.println();
	}

	public void kEvalCord(int x, int y) {
		System.out.println("kEvalCord : " + x + "," + y);
		// RIGHT
		if (twoCheck(x, y, 1)) {
			// System.out.println("2x1 box add to list");
			Term twoTerm = new Term();
			twoTerm.addSetOfTwo(x, y, 1);
			addToTerm(twoTerm);
			// System.out.println("check for 4x1 box AND check square");
			if (fourLineCheck(x, y, 1)) {
				// System.out.println("4x1 box add to list");
				Term fourTerm = new Term();
				fourTerm.addFour(x, y, 1, kMap.numVar);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
			}
			// System.out.println(getValue(x, y));
			if (squareCheck(x, y, 1)) {
				Term fourTerm = new Term();
				fourTerm.addSquare(x, y, 1);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
				System.out.println("add square to list");
			}
		}
		// System.out.println("DOWN CHECK : "+x+","+y);

		// DOWN
		if (twoCheck(x, y, 2)) {
			// System.out.println("1x2 box add to list");
			Term twoTerm = new Term();
			twoTerm.addSetOfTwo(x, y, 2);
			addToTerm(twoTerm);
			// System.out.println("check for 1x4 box AND check square");
			if (fourLineCheck(x, y, 2)) {
				// System.out.println("1x4 box add to list");
				Term fourTerm = new Term();
				fourTerm.addFour(x, y, 2, kMap.numVar);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
			}
			if (squareCheck(x, y, 2)) {
				Term fourTerm = new Term();
				fourTerm.addSquare(x, y, 2);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
				System.out.println("add square to list");
			}
		}

		boolean oneCheck = true;
		for (Term tList : termList) {
			if (tList.term.contains(new CordPair(x, y))) {
				oneCheck = false;
			}
		}
		if (oneCheck) {
			addToTerm(new Term(x, y)); // add a term with just one pair
		}

	}

	public int kListEval(){
		for (int i = 0; i < termList.size(); i++) {
			boolean copyCheck = true;
			for (int j = 0; j < termList.get(i).term.size(); j++) {
				termList.get(i).term.get(j).hasTwo = listCompare(termList.get(i).term.get(j));
				if(copyCheck = false){}
				else{
					copyCheck = termList.get(i).term.get(j).hasTwo;
				}
				if(j == termList.get(i).term.size()-1 && copyCheck == true){
					return i;
				}
			}
		}
		return -1;	
	}
	
	public boolean listCompare(CordPair term) {
		int count = 0;
		for (int i = 0; i < termList.size(); i++) {
			for (int j = 0; j < termList.get(i).term.size(); j++) {
				if(termList.get(i).term.get(j).equals(term)){
					count++;
				}
				if (count >=2){
					return true;
				}
			}
		}
		return false;
	}

	
	
	public boolean twoCheck(int x, int y, int direction) {
		int value = getValue(x, y);
		// 1:RIGHT
		if (direction == 1) {
			if (value == getRight(x, y)) {
				return true;
			} else
				return false;
		}
		// 2:DOWN
		else if (direction == 2) {
			if (value == getDown(x, y)) {
				return true;
			} else
				return false;
		} else { // this should never happen
			System.out.println("ERROR: twoCheck");
			return true;
		}
	}

	public boolean fourLineCheck(int x, int y, int direction) {
		int value = getValue(x, y);
		// 1:Horizontal
		if (direction == 1) {
			for (int i = 0; i < kMap.numVar; i++) {
				System.out.println(x + "," + i);
				if (value != getValue(x, i)) {
					System.out.println("not a 4x1: return false");
					return false;
				}
			}
			System.out.println("4x1 confirmed : return true");
			return true;
		}
		// 2:Vertical
		else if (direction == 2) {
			for (int i = 0; i < kMap.numVar; i++) {
				System.out.println(i + "," + y);
				if (value != getValue(i, y)) {
					System.out.println("not a 1x4: return false");
					return false;
				}
			}
			System.out.println("1x4 confirmed : return true");
			return true;
		}
		// this should never happen
		else {
			System.out.println("Error:fourByOneCheck(): direction != 1 or 2");
			return false;
		}
	}

	public boolean squareCheck(int x, int y, int direction) {
		int value = getValue(x, y);
		// 2x1 seeking 2x2 (from right)
		if (direction == 1) {
			if (y == 3) {
				if (value != getDown(x, y) || value != getValue(x + 1, 0)) {
					System.out.println("not a square: return false");
					return false;
				}
			} 
			else if (x == 3) {
				System.out.println("hi");
				if (value != getDown(x, y) || value != getValue(0, y + 1)) {
					return false;
				}
			}

			else if (value != getDown(x, y) || value != getValue(x + 1, y + 1)) {
				System.out.println("not a square: return false");
				return false;
			}
		}
		// 1x2 seeking 2x2 (from down)
		else if (direction == 2) {
			if (y == 3) {
				if (value != getDown(x, y) || value != getValue(x, 0)) {
					System.out.println("not a square: return false");
					return false;
				}
			} 
			else if (x == 3) {
				System.out.println("hi");
				if (value != getDown(x, y) || value != getValue(0, y + 1)) {
					return false;
				}
			}
			else if (value != getDown(x, y) || value != getValue(x + 1, y + 1)) {
				System.out.println("not a square: return false");
				return false;
			}
		}
		return true;
	}

	public void removeTerm(int i) {
		termList.remove(i);
	}

	public void updateTermList() {
		for (Term t : termList) {
			System.out.println(t.term.toString());
		}
	}

	public void addToTerm(Term cordEvalList) {
		boolean test = true;

		if (termList.contains(cordEvalList)) {
			System.out.println("checking for copy");
			test = false;
		}

		if (test) {
			System.out.println("not a copy");
			termList.add(cordEvalList);
		}
	}

	public CordPair makeCord(int x, int y) {
		return new CordPair(x, y);
	}

	public void initKMapWithTable() {
		kMap = new KMap(function.getNumVar());
		kMap.fillMap(function.truthTable);
	}

	public int getValue(int x, int y) {
		return kMap.getValue(x, y);
	}

	public int getRight(int x, int y) {
		if (y < 3)
			return kMap.getValue(x, y + 1);
		else
			return kMap.grid[x][0];
	}

	public int getDown(int x, int y) {
		if (x < 3)
			return kMap.getValue(x + 1, y);
		else
			return kMap.grid[0][y];
	}

	public String termListToString() {
		String t = "";

		for (Term tList : termList) {
			t += tList.term.toString() + "\n";
		}
		return t;
	}

	public String toString() {
		return function.toString() + "\n" + kMap.toString() + "\n"
				+ termListToString();
	}

	public static void main(String[] args) {
		KSolver kSolve = new KSolver();
		kSolve.function = new Function(
				"A~B~CD+~A~BCD+~A~B~CD+~A~BC~D+~A~BC~D+~A~B~C~D+~ABCD+ABCD+A~BCD");
		kSolve.truthSolve();
		kSolve.initKMapWithTable();

		System.out.println(kSolve.toString());

		kSolve.kEvalMap(1);

		System.out.println(kSolve.toString());

	}
}

// \ 00,01,11,10
// \____________
// 00 | 1 1 1 1
// 01 | 1
// 11 | 1
// 10 | 1 1

