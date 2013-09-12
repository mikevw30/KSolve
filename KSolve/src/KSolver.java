import java.util.ArrayList;

public class KSolver {
	Function function;
	KMap kMap;
	ArrayList<Term> termList;
	int value;
	String sln = "";

	public KSolver() {
		termList = new ArrayList<Term>();
	}

	public void truthSolve() {
		function.normalizeInput();
		function.readInput();
		function.fillResult();
	}

	public void kEvalMap(int num) {
		this.value = num;
		for (int pos : kMap.minMaxSet) {
			kEvalPos(pos);
		}
	}

	public void kEvalPos(int pos) {
		// System.out.println("kEvalPos : " + pos);
		if (twoCheck(pos, 1)) { // RIGHT
		// System.out.println("2x1 box add to list");
			Term twoTerm = new Term();
			twoTerm.addSetOfTwo(pos, 1);
			addToTerm(twoTerm);
			// System.out.println("check for 4x1 box AND check square");
			if (fourLineCheck(pos, 1)) {
				// System.out.println("4x1 box add to list");
				Term fourTerm = new Term();
				fourTerm.addFour(pos, 1);
				addToTerm(fourTerm);
				termList.remove(twoTerm);

			}
			if (squareCheck(pos, 1)) {
				// System.out.println(pos);
				Term fourTerm = new Term();
				fourTerm.addSquare(pos, 1);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
				if (eightCheck(pos, 1)) {

					Term eightTerm = new Term();
					eightTerm.addEight(pos, 1);
					addToTerm(eightTerm);
					termList.remove(fourTerm);
				}
				// System.out.println("add square to list");
			}
		}
		// System.out.println("DOWN CHECK : "+x+","+y);
		// DOWN
		if (twoCheck(pos, 2)) {
			// System.out.println("1x2 box add to list");
			Term twoTerm = new Term();
			twoTerm.addSetOfTwo(pos, 2);
			addToTerm(twoTerm);
			// System.out.println("check for 1x4 box AND check square");
			if (fourLineCheck(pos, 2)) {
				// System.out.println("1x4 box add to list");
				Term fourTerm = new Term();
				fourTerm.addFour(pos, 2);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
			}
			if (squareCheck(pos, 2)) {
				Term fourTerm = new Term();
				fourTerm.addSquare(pos, 2);
				addToTerm(fourTerm);
				termList.remove(twoTerm);
				if (eightCheck(pos, 2)) {
					Term eightTerm = new Term();
					eightTerm.addEight(pos, 2);
					addToTerm(eightTerm);
					termList.remove(fourTerm);
				}
				// System.out.println("add square to list");
			}
		}

		boolean oneCheck = true;
		for (Term tList : termList) {
			if (tList.term.contains(pos)) {
				oneCheck = false;
			}
		}
		if (oneCheck) {
			termList.add(new Term(pos)); // add a term with just one pair
		}
	}

	public void kListOp() {
		for (int i = 0; i < termList.size(); i++) {
			int count = termList.get(i).term.size();
			for (int j = 0; j < termList.get(i).term.size(); j++) {
				// System.out.println("The term "+termList.get(i).term.toString());
				for (int k = 0; k < termList.size(); k++) {
					if (i != k && termList.get(k).deleteFlag == false) {
						// System.out.println("count:"+count+" Is "+termList.get(i).term.get(j)+" in "+termList.get(k).term.toString()
						// +" : "+
						// termList.get(k).term.contains((termList.get(i).term.get(j))));
						if (termList.get(k).term.contains((termList.get(i).term
								.get(j)))) {
							count--;
							break;
						}
					}
				}
				if (count == 0) {
					termList.get(i).deleteFlag = true;
					// System.out.println("flag to remove");
				}
				// System.out.println();
			}
		}
		ArrayList<Term> updateList = new ArrayList<>();
		for (int i = 0; i < termList.size(); i++) {
			if (termList.get(i).deleteFlag == false) {
				updateList.add(termList.get(i));
			}
		}
		termList = updateList;
	}

	public void kBuildSln() {

		for (int i = 0; i < termList.size(); i++) {
			SolveTerm st = new SolveTerm(termList.get(i), function.numVar,
					function.varArr);
			st.termToBinary();
			// System.out.println(st.toString());
			if (i != termList.size() - 1)
				sln += st.binaryToString() + "+";
			else
				sln += st.binaryToString();
		}
	}

	public boolean twoCheck(int pos, int direction) {
		// 1:RIGHT
		if (direction == 1) {
			if (kMap.minMaxSet.contains(getRight(pos))) {
				return true;
			} else
				return false;
		}
		// 2:DOWN
		else if (direction == 2) {
			if (kMap.minMaxSet.contains(getDown(pos))) {
				return true;
			} 
			else
				return false;
		} 
		else { // this should never happen
			System.out.println("ERROR: twoCheck");
			return true;
		}
	}

	public boolean fourLineCheck(int pos, int direction) {
		// 1:Horizontal
		if (direction == 1) {
			if (pos % 4 == 0) {
				if (kMap.minMaxSet.contains(getRight(getRight(pos)))
						&& kMap.minMaxSet
								.contains(getRight(getRight(getRight(pos))))) {
					return true;
				}
			} 
			else {
				return false;
			}
		}
		// 2:Vertical
		else if (direction == 2) {
			if (pos >= 0 && pos <= 3) {
				if (kMap.minMaxSet.contains(getDown(getDown(pos)))
						&& kMap.minMaxSet
								.contains(getDown(getDown(getDown(pos))))) {
					return true;
				}
			} 
			else {
				return false;
			}
		} 
		else {// this should never happen
			System.out.println("Error:fourByOneCheck(): direction != 1 or 2");
			return false;
		}
		return false;
	}

	public boolean squareCheck(int pos, int direction) {
		// System.out.println("square check");
		// 2x1 seeking 2x2 (from right)
		if (direction == 1) {
			if (kMap.minMaxSet.contains(getDown(pos))
					&& kMap.minMaxSet.contains(getDown(getRight(pos)))) {
				return true;
			} 
			else {
				return false;
			}
		}
		// 1x2 seeking 2x2 (from down)
		else if (direction == 2) {
			if (kMap.minMaxSet.contains(getRight(pos))
					&& kMap.minMaxSet.contains(getRight(getDown(pos)))) {
				return true;
			} 
			else {
				return false;
			}
		} 
		else {
			return true;
		}
	}

	public boolean eightCheck(int pos, int direction) {
		// 1:Horizontal
		if (pos % 4 == 0 || pos <= 3) {
			// System.out.println("eight check: "+pos);
			if (direction == 1) {
				// System.out.println("Right: "+getRight(getRight(pos))+" , "+
				// getRight(getRight(getRight(pos)))+" , "+
				// getDown(getRight(getRight(pos)))+" , "+
				// getDown(getRight(getRight(getRight(pos)))));

				if (kMap.minMaxSet.contains(getRight(getRight(pos)))
						&& kMap.minMaxSet
								.contains(getRight(getRight(getRight(pos))))
						&& kMap.minMaxSet
								.contains(getDown(getRight(getRight(pos))))
						&& kMap.minMaxSet
								.contains(getDown(getRight(getRight(getRight(pos)))))) {
					// System.out.println("right True\n");
					return true;
				}
			}
			// 2:Vertical
			else if (direction == 2) {
				// System.out.println("Down: "+getDown(getDown(pos))+" , "+
				// getDown(getDown(getDown(pos)))+" , "+
				// getRight(getDown(getDown(pos)))+" , "+
				// getRight(getDown(getDown(getDown(pos)))));

				if (kMap.minMaxSet.contains(getDown(getDown(pos)))
						&& kMap.minMaxSet
								.contains(getDown(getDown(getDown(pos))))
						&& kMap.minMaxSet
								.contains(getRight(getDown(getDown(pos))))
						&& kMap.minMaxSet
								.contains(getRight(getDown(getDown(getDown(pos)))))) {
					// System.out.println("down true\n");
					return true;
				} 
				else {
					return false;
				}
			} 
			else {// this should never happen
				System.out
						.println("Error:fourByOneCheck(): direction != 1 or 2");
				return false;
			}
			return false;
		}
		return false;
	}

	public void removeTerm(int i) {
		termList.remove(i);
	}

	public void addToTerm(Term term) {
		boolean test = true;

		if (termList.contains(term)) {
			// System.out.println("checking for copy");
			test = false;
		}

		for (Term t : termList) {
			// System.out.println(t.term.toString() + " : " +
			// term.term.toString());

			if (t.isSubSet(term)) {
				// System.out.println("false");
				test = false;
				break;
			}
		}
		if (test) {
			termList.add(term);
		}
	}

	public void initKMapWithTable(int num) {
		kMap = new KMap(function.getNumVar());
		kMap.fillMap(function.truthTable);
		kMap.popMinMaxSet(num);
	}

	public int getRight(int pos) {
		return ((pos + 1) % 4) + (4 * getX(pos));
	}

	public int getX(int pos) {
		int count = -1;
		while (pos >= 0) {
			pos = pos - 4;
			count++;
		}
		return count;
	}

	public int getDown(int pos) {
		return ((pos + 4) % 16);
	}

	public String termListToString() {
		String t = "";

		for (Term tList : termList) {
			t += tList.term.toString() + "\n";
		}
		return t;
	}

	public String toString() {
		return function.toString() + "\n" + kMap.minMaxSet.toString() + "\n"
				+ kMap.toString() + "\n" + termListToString() + "\n" + sln
				+ "\n";
	}

	public static void main(String[] args) {
		KSolver kSolve = new KSolver();

		String fun = // "(~A~B~C~D)+"+ //0
		// 				"(~A~B~CD)+"+ //1
						"(~A~BCD)+" + // 2
						"(~A~BC~D)+" + // 3
						"(~AB~C~D)+" + // 4
						"(~AB~CD)+" + // 5
						"(~ABCD)+" + // 6
						"(~ABC~D)+" + // 7
						// "(AB~C~D)+"+ //8
						// "(AB~CD)+"+ //9
						"(ABCD)+" + // 10
						// "(ABC~D)+"+ //11
						"(A~B~C~D)+" + // 12
						"(A~B~CD)+" + // 13
						"(A~BCD)+" + // 14
						"(A~BC~D)"; // 15

		kSolve.function = new Function(fun);
		kSolve.truthSolve();

		kSolve.initKMapWithTable(1);

		// kSolve.kMap.minMaxSet.clear();
		// kSolve.kMap.minMaxSet.add(0);
		// kSolve.kMap.minMaxSet.add(1);
		// kSolve.kMap.minMaxSet.add(2);
		// kSolve.kMap.minMaxSet.add(3);
		// kSolve.kMap.minMaxSet.add(4);
		// kSolve.kMap.minMaxSet.add(5);
		// kSolve.kMap.minMaxSet.add(6);
		// kSolve.kMap.minMaxSet.add(7);
		// kSolve.kMap.minMaxSet.add(8);
		// kSolve.kMap.minMaxSet.add(9);
		// kSolve.kMap.minMaxSet.add(10);
		// kSolve.kMap.minMaxSet.add(11);
		// kSolve.kMap.minMaxSet.add(12);
		// kSolve.kMap.minMaxSet.add(13);
		// kSolve.kMap.minMaxSet.add(14);
		// kSolve.kMap.minMaxSet.add(15);

		kSolve.kEvalMap(1);
		kSolve.kListOp();
		kSolve.kBuildSln();

		System.out.println(kSolve.toString());
	}
}

// \ 00,01,11,10
// \____________
// 00 | 1 1 1 1
// 01 | 1
// 11 | 1
// 10 | 1 1

