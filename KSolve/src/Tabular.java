import java.util.ArrayList;

public class Tabular {
	ArrayList<ArrayList<TTRow>> workTable;
	Function fun;
	
	public Tabular(Function fun){
		this.fun = fun;
		workTable = new ArrayList<>();
	}
	
	public void truthSolve() {
		fun.normalizeInput();
		fun.readInput();
		fun.fillResult();
	}
	
	public void popWork(int num){
		for (int i = 0; i < fun.numVar+1; i++) {
			ArrayList<TTRow> workRow = new ArrayList<>();
			workTable.add(workRow);
		}
		for (int i = 0; i < Math.pow(2, fun.numVar); i++) {
			if(fun.truthTable.grid[i].value == num)
			workTable.get(countRow(i,num)).add((fun.truthTable.grid[i]));
		}
	}
	
	public int countRow(int i,int num){
		int count = 0;
		for (int j = 0; j < fun.numVar; j++) {
//			System.out.println(fun.truthTable.grid[i].row[j]+":"+num);
			if(fun.truthTable.grid[i].row[j]==num){
				count++;
			}
		}
		return count;
	}

	public ArrayList<TTRow> findMatch(int a, int b,int num){
		System.out.println(a+" , "+b);
		ArrayList<TTRow> match = workTable.get(a);
		for (TTRow ttRowA : workTable.get(a)) {
			for (TTRow ttRowB : workTable.get(b)) {
				int diff = 0;
				TTRow diffMatch = new TTRow(fun.numVar);
				diffMatch.value = num;
				for (int i = 0; i < ttRowA.length; i++) {
					System.out.println(ttRowA.toString()+" , "+ttRowB.toString()+" , "+ttRowA.row[i]+":"+ttRowB.row[i]);
					if(ttRowA.row[i]==ttRowB.row[i]){
						diffMatch.row[i]=ttRowA.row[i];
					}
					else {
						diffMatch.row[i] = 2;
						diff++;
					}
				}
				if(diff == 1){
					match.remove(ttRowA);	
					match.add(diffMatch);
				}
			}
		}
		
		workTable.get(a).clear();
		for (TTRow ttRow : match) {
			workTable.get(a).add(ttRow)
		}
		
		return match;
	}
	
	public void printWorkTable(){
		for (int i = 0; i < workTable.size(); i++) {
			System.out.println("index: "+i+" row.size() :"+workTable.get(i).size() +" "  +workTable.get(i).toString());
		}
	}
	
	public static void main(String[] args) {
		String f = "(~A~B~C)+(~A~BC)+(ABC)";
		Tabular tab = new Tabular(new Function(f));
		tab.truthSolve();
		tab.popWork(1);
		
		System.out.println((tab.fun.toString()));
		tab.printWorkTable();
		
		System.out.println("cancle");
		
		
		for (int i = 0; i < tab.fun.numVar; i++) {
			System.out.println("next iteration "+i);
			tab.findMatch(i, i+1, 1);
			tab.printWorkTable();
//			ArrayList<ArrayList<TTRow>> tempWork = new ArrayList<>();
//		
//			for (int j = 0; j < tab.workTable.size()-1; j++) {
//				ArrayList<TTRow> temp = tab.findMatch(j,j+1, 1);
//				tempWork.add(temp);
//			}
//		
//			tab.workTable = tempWork;
//			tab.printWorkTable();
//			System.out.println();
		}
	}		
}

