import java.util.ArrayList;

public class Term {
	ArrayList<CordPair> term;
	boolean copy = false;

	public Term() {
		term = new ArrayList<CordPair>();
	}

	public Term(ArrayList<CordPair> cordPairList) {
		this.term = cordPairList;
	}

	public Term(int x, int y) {
		term = new ArrayList<CordPair>();

		term.add(new CordPair(x, y));

	}

	public void addSetOfTwo(int x, int y, int direction) {
		CordPair one = new CordPair(x, y);
		term.add(one);

		if (direction == 1) { // right pair
			if (y < 3){
				term.add(new CordPair(x, y + 1));
			}
			else{
				term.add(new CordPair(x, 0));
			}
		}
		// 2:DOWN
		else if (direction == 2) {
			if (x < 3){
				term.add(new CordPair(x + 1, y));
			}
			else{
				term.add(new CordPair(0, y));
			}
		}
	}

	public void addFour(int x, int y, int direction, int numVar) {
		if (direction == 1) {
			for (int i = 0; i < numVar; i++) {
				// if(value != getValue(x, i))
				term.add(new CordPair(x, i));
			}
		}
		// 2:Vertical
		else if (direction == 2) {
			for (int i = 0; i < numVar; i++) {
				// if(value != getValue(x, i))
				term.add(new CordPair(i, y));
			}
		}
	}

	public void addSquare(int x, int y, int direction) {
		// 2x1 seeking 2x2 (from right)
		term.add(new CordPair(x, y));
		if (direction == 1) {
			if (y == 3) {					
				term.add(new CordPair(x+1,y));
				term.add(new CordPair(x+1,0));
				term.add(new CordPair(x  ,0));
			} 
			else if (x == 3) {
				term.add(new CordPair(x  ,y+1));
				term.add(new CordPair(0  ,y+1));
				term.add(new CordPair(0  ,y));
			}

			else {
				term.add(new CordPair(x+1, y+1));
				term.add(new CordPair(x  , y+1));
				term.add(new CordPair(x+1, y));
			}
		}
		// 1x2 seeking 2x2 (from down)
		else if (direction == 2) {
			if (y == 3) {					
				term.add(new CordPair(x+1,y));
				term.add(new CordPair(x+1,0));
				term.add(new CordPair(x  ,0));
			} 
			else if (x == 3) {
				term.add(new CordPair(x  ,y+1));
				term.add(new CordPair(0  ,y+1));
				term.add(new CordPair(0  ,y));
			}

			else {
				term.add(new CordPair(x+1, y+1));
				term.add(new CordPair(x  , y+1));
				term.add(new CordPair(x+1, y));
			}
		}
	}

	public static void main(String[] args) {
		Term eList = new Term();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				CordPair addThis = new CordPair(i, j);
				eList.term.add(addThis);
			}
		}

		System.out.println(eList.term.toString());
		System.out.println(eList.term.contains(new CordPair(-1, 0)));
	}

}
