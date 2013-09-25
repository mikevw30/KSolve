import java.util.ArrayList;
import java.util.Iterator;

public class Tabular {
	int num;
	Function fun;
	ArrayList<TTRow> minMaxList;
	ArrayList<TTRow> primeImp;
	int solveFor;
	String sln = "";
	boolean invertOutput = false;

	public Tabular(Function fun, int num) {
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

	public void buildMinMaxList(int num) {
		solveFor = num;
		for (int i = 0; i < Math.pow(2, fun.truthTable.numVar); i++) {
			if (fun.truthTable.grid[i].value == num) {
				fun.truthTable.grid[i].checked = false;
				minMaxList.add(fun.truthTable.grid[i]);
			}
		}
	}

	public void sortMinMaxList() {
		ArrayList<TTRow> temp = new ArrayList<>();
		for (int i = 0; i < fun.numVar + 1; i++) {
			for (int j = 0; j < minMaxList.size(); j++) {
				if (minMaxList.get(j).count == i) {
					temp.add(minMaxList.get(j));
				}
			}
		}
		minMaxList = temp;
//		printMinMaxList();
	}

	public void buildPrimes() {
//		int count=0;
		
		TTRow holder = new TTRow(0);
		while (!(minMaxList.isEmpty())) {
//			count++;
			holder = minMaxList.get(0);
			if(holder.set.size()==0){
				holder.set.add(holder.decimalValue);
			}
			
//			System.out.println(count+","+minMaxList.size()+","+holder.set.toString());
			for (int i = 0; i < minMaxList.size(); i++) {
				int numDiff = 0;
				TTRow temp = new TTRow(fun.numVar);
				temp.checked = false;
				temp.decimalValue = holder.decimalValue;
				for (Integer num : holder.set) {
					addToTemp(num, temp.set);
				}
				addToTemp(holder.decimalValue, temp.set);
				if (Math.abs(holder.count - minMaxList.get(i).count) >= 2) {
					break; // the checks dont need to be done.
				}

				if (holder.count + 1 == minMaxList.get(i).count) {
					for (int j = 0; j < fun.numVar; j++) {
						if (numDiff >= 2) {
							break;
						}
						if (holder.row[j] == 2) {
							temp.row[j] = 2;
							if (minMaxList.get(i).row[j] == 2) {
							} 
							else {
								numDiff++;
							}
						} 
						else if (holder.row[j] == minMaxList.get(i).row[j]) {
							temp.row[j] = holder.row[j];
						} 
						else {
							numDiff++;
							temp.row[j] = 2;
							for (Integer num : minMaxList.get(i).set) {
								addToTemp(num, temp.set);
							}
						}
					}
				}
				if (numDiff == 1) {
					holder.checked = true;
					minMaxList.get(i).checked = true;
					addToTemp(minMaxList.get(i).decimalValue, temp.set);
					temp.count = countTemp(temp);
					
					if(holder.set.size()>=2){
						addToMinMaxList(temp);
					}
					else{
						minMaxList.add(temp);
					}
				}
			}
			if (holder.checked == false) {
				boolean add = true;
				for (int i = 0; i < primeImp.size(); i++) {
					if (holder.set.size() == primeImp.get(i).set.size()	&& primeImp.get(i).set.containsAll(holder.set)) {
						add = false;
						break;
					}
				}
				if (add){
					if(holder.set.size()==0){
						holder.set.add(holder.decimalValue);
					}
					primeImp.add(holder);
				}
			}
			minMaxList.remove(holder);
		}
	}

	public int countTemp(TTRow temp) {
		int count = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp.row[i] == 1) {
				count++;
			}
		}
		return count;
	}

	public void addToTemp(int num, ArrayList<Integer> temp) {
		if (!temp.contains(num)) {
			temp.add(num);
		}
	}
	
	public void addToMinMaxList(TTRow temp){
		boolean add = true;
		for (int i = 0; i < minMaxList.size(); i++) {
			if(minMaxList.get(i).set.size()==temp.set.size()){
//				System.out.println(minMaxList.get(i).set.toString()+" , "+temp.set.toString());
				if(!(temp.set.containsAll(minMaxList.get(i).set))){
					
				}
				else{
					add = false;
				}
			}
		}
		if(add){
			minMaxList.add(temp);
		}
	}

	public void printMinMaxList() {
		for (int i = 0; i < minMaxList.size(); i++) {
			System.out.println(minMaxList.get(i));
		}
	}

	public void printPrimeImp() {
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
						if (primeImp.get(k).set.contains((primeImp.get(i).set.get(j)))) {
							count--;
							break;
						}
					}
				}
				if (count == 0) {
					primeImp.get(i).deleteFlag = true;
				}
			}
		}

		Iterator<TTRow> it = primeImp.iterator();
		while (it.hasNext()) {
			TTRow object = it.next();
			if (object.deleteFlag == true) {
				it.remove();
			}
		}
	}

	public void kBuildSln() {
		for (int i = 0; i < primeImp.size(); i++) {
			SolveTerm st = new SolveTerm(primeImp.get(i), fun.numVar,fun.varArr);
			st.primeToBinary();
			// System.out.println(st.toString());
			String t = st.primeToString(solveFor);
			if (t.equals("()")) {

			} 
			else if(solveFor == 0){
				sln+=t;
			}
			else {
				if (i != primeImp.size() - 1) {
					sln += t + "+";
				} 
				else {
					sln += t;
				}
			}
		}
	}

//	public void buildCountBasedMinMax(int solveFor){
//		int count = 0;
//		for (int i = 0; i < Math.pow(2,fun.numVar); i++) {
//			if(fun.truthTable.grid[i].value==solveFor){
//				count++;
//			}
//		}		
//		if(count >= Math.pow(2,fun.numVar-1)){
//			if(solveFor ==1){
//				System.out.println("0");
//				buildMinMaxList(0);
//			}
//			else{
//				buildMinMaxList(1);
//			}
//			
//			invertOutput = true;
//			
//		}
//		else{
//			buildMinMaxList(solveFor);
//		}
//		System.out.println(count);
//		System.out.println("solveFor = "+solveFor);
//		System.out.println("invert = "+invertOutput);
//	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		String f = "(JIHGFE~DCB)+(~BA)+(~CA)+(~DA)";
//		String f = "(~D+~C+~B)(B+A)(C+A)(A+E)(A+F)(A+G)(A+H)";
//		String f = "~W+B";
		
		
		//invertSln 0 to 1 test
//		String f = "(~E+~D+~C+~B+~A)";
//		String f = "~(ABCDE)";
		
		
		Tabular tab = new Tabular(new Function(f), 1);
		tab.truthSolve();

		System.out.println(tab.fun.toString());

		long startBuildSort = System.nanoTime();
		
//		tab.buildCountBasedMinMax(1);
//		tab.buildMinMaxList(1);
		tab.buildMinMaxList(0);
		
		
		tab.sortMinMaxList();
		long endBuildSort = System.nanoTime();

		
		long startBuildPrimes = System.nanoTime();
		tab.buildPrimes();
		long endBuildPrimes = System.nanoTime();
	
//		tab.printPrimeImp();
		tab.primeImpListOp();
//		System.out.println("The sorted primes are:");
//		tab.printPrimeImp();
		
		tab.kBuildSln();
		
		System.out.println(tab.sln.toString());
		long endTime = System.nanoTime();
		
		System.out.print("buildSortMinMaxList() : \t");
		System.out.println((endBuildSort - startBuildSort )/ Math.pow(10, 9));
		System.out.print("buildPrimes() : \t\t");
		System.out.println((endBuildPrimes - startBuildPrimes )/ Math.pow(10, 9));
		
		
		
		
		System.out.print("TotalRun Time: \t\t\t");
 		System.out.println((endTime - startTime)/ Math.pow(10, 9));
	}
}