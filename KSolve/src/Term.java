import java.util.ArrayList;
import java.util.Collections;

public class Term {
	ArrayList<Integer> term;
	boolean deleteFlag = false;

	public Term() {
		term = new ArrayList<Integer>();
	}

	public Term(ArrayList<Integer> IntegerList) {
		this.term = IntegerList;
	}

	public Term(int pos) {
		term = new ArrayList<Integer>();
		term.add(pos);
	}

	public boolean isSubSet(Term t) {
		boolean result = true;
		for (Integer num : t.term) {
			if (!(term.contains(num))) {
				result = false;
				break;
			}
		}
		return result;
	}

	public void addSetOfTwo(int pos, int direction) {
		term.add(pos);
		if (direction == 1) { // right pair
			term.add(((pos + 1) % 4) + (4 * getX(pos)));
		} 
		else if (direction == 2) {
			term.add((pos + 4) % 16);
		}
		Collections.sort(term);
	}

	public int getX(int pos) {
		int count = -1;
		while (pos >= 0) {
			pos = pos - 4;
			count++;
		}
		return count;
	}

	public void addFour(int pos, int direction) {
		// 1:Horizontal
		if (direction == 1) {
			term.add(pos);
			term.add(getRight(pos));
			term.add(getRight(getRight(pos)));
			term.add(getRight(getRight(getRight(pos))));
		}
		// 2:Vertical
		else if (direction == 2) {
			term.add(pos);
			term.add(getDown(pos));
			term.add(getDown(getDown(pos)));
			term.add(getDown(getDown(getDown(pos))));
		}
		Collections.sort(term);
	}

	public int getRight(int pos) {
		return ((pos + 1) % 4) + (4 * getX(pos));
	}

	public int getDown(int pos) {
		return ((pos + 4) % 16);
	}

	public void addSquare(int pos, int direction) {
		// 2x1 seeking 2x2 (from right)
		term.add(pos);
		if (direction == 1) {
			term.add(getRight(pos));
			term.add(getDown(pos));
			term.add(getDown(getRight(pos)));
		}
		// 1x2 seeking 2x2 (from down)
		else if (direction == 2) {
			term.add(getDown(pos));
			term.add(getRight(pos));
			term.add(getRight(getDown(pos)));
		}
		Collections.sort(term);
	}

	public void addEight(int pos, int direction) {
		term.add(pos);
		if (direction == 1) {
			term.add(getRight(pos));
			term.add(getDown(pos));
			term.add(getDown(getRight(pos)));
			term.add(getRight(getRight(pos)));
			term.add(getRight(getRight(getRight(pos))));
			term.add(getDown(getRight(getRight(pos))));
			term.add(getDown(getRight(getRight(getRight(pos)))));
		}
		// 1x2 seeking 2x2 (from down)
		else if (direction == 2) {
			term.add(getDown(pos));
			term.add(getRight(pos));
			term.add(getRight(getDown(pos)));
			term.add(getDown(getDown(pos)));
			term.add(getDown(getDown(getDown(pos))));
			term.add(getRight(getDown(getDown(pos))));
			term.add(getRight(getDown(getDown(getDown(pos)))));
		}
		Collections.sort(term);
	}

	// public static void main(String[] args) {
	// Term eList = new Term();
	// for (int i = 0; i < 4; i++) {
	// for (int j = 0; j < 4; j++) {
	// Integer addThis = new Integer(i, j);
	// eList.term.add(addThis);
	// }
	// }
	//
	// System.out.println(eList.term.toString());
	// System.out.println(eList.term.contains(new Integer(-1, 0)));
	// }

}
