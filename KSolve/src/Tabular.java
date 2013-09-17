import java.sql.Time;
import java.util.ArrayList;

public class Tabular {
	int num;
	Function fun;
	ArrayList<TTRow> minMaxList;
	ArrayList<TTRow> primeImp;
	String sln = "";
	
	public Tabular(Function fun, int num){
		this.fun = fun;
		this.num = num;
		minMaxList = new ArrayList<>();
		primeImp = new ArrayList<>();
	}
	
	public void truthSolve() {
		fun.normalizeInput();
		fun.readInput();
		fun.fillResult();
	}
	
	
	public void buildMinMaxList(int num){
		for (int i = 0; i < Math.pow(2, fun.truthTable.numVar); i++) {
			if(fun.truthTable.grid[i].value==num){
				fun.truthTable.grid[i].checked=false;
				minMaxList.add(fun.truthTable.grid[i]);
			}
		}
	}
	
	public void sortMinMaxList(){
		ArrayList<TTRow> temp = new ArrayList<>();
		for (int i = 0; i < fun.numVar+1; i++) {
			for (int j = 0; j < minMaxList.size(); j++) {
				if(minMaxList.get(j).count==i){
					temp.add(minMaxList.get(j));
				}
			}
		}
		minMaxList = temp;
		printMinMaxList();
	}
	
	public void buildPrimes(){
		TTRow holder = new TTRow(0);
		while(!(minMaxList.isEmpty())){
			holder = minMaxList.get(0);
			for (int i = 0; i < minMaxList.size(); i++) {
				int numDiff = 0;
				TTRow temp = new TTRow(fun.numVar);
				temp.checked=false;
				temp.decimalValue = holder.decimalValue;
				for (Integer num : holder.set) {
					addToTemp(num, temp.set);
				}
				addToTemp(holder.decimalValue, temp.set);
				if(holder.count+1 == minMaxList.get(i).count){
					for (int j = 0; j < fun.numVar; j++) {
						if(numDiff>=2){
							break;
						}
						if(holder.row[j] == 2){
							temp.row[j] = 2;
							if(minMaxList.get(i).row[j]==2){
							}
							else{
								numDiff++;
							}
						}
						else if(holder.row[j] == minMaxList.get(i).row[j]){
							temp.row[j] = holder.row[j];
						}
						else{
							numDiff++;
							temp.row[j] = 2;
							for (Integer num : minMaxList.get(i).set) {
								addToTemp(num, temp.set);
							}
						}
					}
				}
				if(numDiff==1){
					holder.checked=true;
					minMaxList.get(i).checked=true;
					addToTemp(minMaxList.get(i).decimalValue, temp.set);
					temp.count = countTemp(temp);
					minMaxList.add(temp);
				}
			}
			if(holder.checked == false){
				primeImp.add(holder);
				primeImpListOp();
			}
			minMaxList.remove(holder);
		}
	}
	
	
	public int countTemp(TTRow temp){
		int count = 0;
		for (int i = 0; i < temp.length; i++) {
			if(temp.row[i]==1){
				count++;
			}
		}
		return count;
	}
	
	public void addToTemp(int num, ArrayList<Integer> temp){
		if(!temp.contains(num)){
			temp.add(num);
		}
	}
	
	
	public void printMinMaxList(){
		for (int i = 0; i < minMaxList.size(); i++) {
			System.out.println(minMaxList.get(i));
		}
	}
	
	public void printPrimeImp(){
//		for (int i = 0; i < primeImp.size(); i++) {
//			System.out.println(primeImp.get(i).toString());
//		}
		for (int i = 0; i < primeImp.size(); i++) {
			System.out.println(primeImp.get(i).set.toString());
		}
	}
	
	public void primeImpListOp() {
		for (int i = 0; i < primeImp.size(); i++) {
			int count = primeImp.get(i).set.size();
			for (int j = 0; j < primeImp.get(i).set.size(); j++) {
				// System.out.println("The term "+termList.get(i).term.toString());
				for (int k = 0; k < primeImp.size(); k++) {
					if (i != k && primeImp.get(k).deleteFlag == false) {
						// System.out.println("count:"+count+" Is "+termList.get(i).term.get(j)+" in "+termList.get(k).term.toString()
						// +" : "+
						// termList.get(k).term.contains((termList.get(i).term.get(j))));
						if (primeImp.get(k).set.contains((primeImp.get(i).set.get(j)))) {
							count--;
							break;
						}
					}
				}
				if (count == 0) {
					primeImp.get(i).deleteFlag = true;
					// System.out.println("flag to remove");
				}
				// System.out.println();
			}
		}
		ArrayList<TTRow> updateList = new ArrayList<>();
		for (int i = 0; i < primeImp.size(); i++) {
			if (primeImp.get(i).deleteFlag == false) {
				updateList.add(primeImp.get(i));
			}
		}
		primeImp = updateList;
	}
	
	public void kBuildSln() {

		for (int i = 0; i < primeImp.size(); i++) {
			SolveTerm st = new SolveTerm(primeImp.get(i), fun.numVar,fun.varArr);
			st.primeToBinary();
			// System.out.println(st.toString());
			String t = st.primeToString();
			if(t.equals("()")){
				
			}
			else{
				if (i != primeImp.size() - 1){
					sln += t + "+";
				}
				else{
					sln += t;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		String 	f ="(~A~BC~DEF)+(~A~BCD)+(~AB~C~D)+(~AB~CD)+(~ABCD)+(ABCD)+(A~B~C~D)+(A~B~CD)+(A~BC~D)+(A~BCD)+(~ABC~D)";
//		String f = "A+B+C+~D";
//		String f = "(ABD)+(CD)";
		Tabular tab = new Tabular(new Function(f),1);
		tab.truthSolve();
//		tab.popMinMax(1);
		
		System.out.println((tab.fun.toString()));
		
		
		tab.buildMinMaxList(1);
		tab.sortMinMaxList();
		
		tab.buildPrimes();
		System.out.println("The primes are:");
		tab.primeImpListOp();
		tab.printPrimeImp();
		
		tab.kBuildSln();
		System.out.println(tab.sln.toString());
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println(duration/Math.pow(10, 9));
	}		
}