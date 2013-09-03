import java.io.ObjectInputStream.GetField;


public class KSolver {
	Function function;
	KMap kMap;
	
	public KSolver(){
		//init gui here
	}
	
	public void truthSolve(){
		function.normalizeInput();
		function.readInput(); 
		function.fillResult();
	}
	
	public void KEval(int x, int y){
		//RIGHT
		if(twoCheck(x,y,1)){
			System.out.println("2x1 box add to list");
			System.out.println("check for 4x1 box AND check square");
			if(fourLineCheck(x,y,1)){
				System.out.println("4x1 box add to list");
			}
			System.out.println(getValue(x, y));
			if(squareCheck(x, y, 1)){
				System.out.println("add square to list");
			}
		}
		System.out.println("\nDOWN CHECK TIME\n");
		
		//DOWN	
		if(twoCheck(x,y,2)){
			System.out.println("1x2 box add to list");
			System.out.println("check for 1x4 box AND check square");
			if(fourLineCheck(x,y,2)){
				System.out.println("1x4 box add to list");
			}
			if(squareCheck(x, y, 2)){
				System.out.println("add square to list");
			}
		}
		System.out.println("Evaluate List");
	}
	
		
	
	
	
	public boolean twoCheck(int x, int y, int direction){
		int value = getValue(x, y);
		//1:RIGHT
		if(direction==1){
			if(value == getRight(x, y)){
			return true;
			}
			else
				return false;
		}
		//2:DOWN
		else if(direction==2){
			if(value == getDown(x, y)){
			return true;
			}
			else
				return false;
		}
		else{ //this should never happen	
			System.out.println("ERROR: twoCheck");
			return true;
		}
	}
	
	public boolean fourLineCheck(int x, int y, int direction){
		int value = getValue(x, y);
		//1:Horizontal
		if(direction == 1){
			for (int i = 0; i < kMap.numVar; i++) {
				System.out.println(x+","+i);
				if(value != getValue(x, i)){
					System.out.println("not a 4x1: return false");
					return false;
				}
			}
			System.out.println("4x1 confirmed : return true");
			return true;
		}
		//2:Vertical
		else if(direction == 2){
			for (int i = 0; i < kMap.numVar; i++) {
				System.out.println(i+","+y);
				if(value != getValue(i, y)){
					System.out.println("not a 1x4: return false");
					return false;
				}
			}
			System.out.println("1x4 confirmed : return true");
			return true;
		}
		//this should never happen
		else {
			System.out.println("Error:fourByOneCheck(): direction != 1 or 2");
			return false;
		}
	}
	
	public boolean squareCheck(int x, int y, int direction){
		int value = getValue(x, y);
		//2x1 seeking 2x2 (from right)
		if(direction == 1){
				if(y==3){
					System.out.println(getDown(x, y)+" " + getValue(x+1, 0));
					if(value != getDown(x, y) || value != getValue(x+1, 0)){
						System.out.println("not a square: return false");
						return false;
					}
				}
			
				else if(value != getDown(x, y) || value != getValue(x+1, y+1)){
					System.out.println("not a square: return false");
					return false;
				}	
		}
		//1x2 seeking 2x2 (from down)
		else if(direction == 2){
			if(y==3){
				if(value != getDown(x, y) || value != getValue(x, 0)){
					System.out.println("not a square: return false");
					return false;
				}
			}
			else if(value != getDown(x, y) || value != getValue(x+1, y+1)){
				System.out.println("not a square: return false");
				return false;
			}	
		}
		return true;
	}
		
	public void initKMapWithTable(){
		kMap = new KMap(function.getNumVar());
		kMap.fillMap(function.truthTable);
	}
	
	public int getValue(int x, int y){
		return kMap.getValue(x, y);
	}
		
	public int getRight(int x, int y){
		if(y<3)
			return kMap.getValue(x, y+1);
		else
			return kMap.grid[x][0];
	}
	
	public int getDown(int x, int y){
		if(x<3)
			return kMap.getValue(x+1, y);
		else
			return kMap.grid[x][0];
	}
	
	
	
	
	public String toString(){
		return function.toString()+"\n"+ kMap.toString();
	}
	
	public static void main(String[] args) {
		/*
		 * truth tables work for 1-15var
		 * TODO: 3 ,5 and 6 var kmapFill.
		 * TODO:KFindSln()	
		 */
		
		KSolver kSolve = new KSolver();
		//kSolve.function = new Function("(~AB~CD)+(~ABCD)+(AB~CD)+(ABCD)"); //EDIT THE FUNCTION HERE!!!
		kSolve.function = new Function("~ABC~D+ABC~D+AB~C~D+~AB~C~D"); //EDIT THE FUNCTION HERE!!!
		kSolve.truthSolve();
		kSolve.initKMapWithTable();
		
		//prints truth table
		//System.out.println((kSolve.function.toString()));
		
		//prints truth table and kmap.
		System.out.println(kSolve.toString());
		
		
		
//		get adj. test
		int x =1;
		int y =3;
		
		System.out.println("Doing eval on: "+x+","+y);
		
//		System.out.println("  "+kSolve.getUp(x,y));
//		System.out.println(kSolve.getLeft(x,y)+" "+kSolve.getValue(x,y)+" "+kSolve.getRight(x,y));
//		System.out.println("  "+kSolve.getDown(x,y));
		System.out.println(kSolve.getValue(x, y));
		kSolve.KEval(x, y);
	}
}
//No zeros allowed.
//No diagonals.
//Only power of 2 number of cells in each group.
//Groups should be as large as possible.
//Every one must be in at least one group.
//Overlapping allowed.
//Wrap around allowed.
//Fewest number of groups possible.

//Group adjacent 1's together in square or rectangular groups of 2, 4,
//8, or 16, such that the total number of groups and isolated 1's is
//minimized, while using as large groups as possible. Groups may
//overlap, so that a particular cell may be included in more than one group

//	   \  00,01,11,10
//		\____________
//	00	|
//	01	|
//	11	|
//	10	|