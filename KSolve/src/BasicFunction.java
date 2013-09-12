/*
 * @author Michael VanWie
 * 
 * This class is used to evaluate the basic binary operators
 * used for KSolver. Binary operator |(OR) and &(AND)
 * 
 * Example: int result = (new BasicFunction(1,1,2)).evalBF();
 * 			This will call the AND function on 1 and 1
 * 			the value returned will be the result of 1&1
 * 						
 *   
 *  call more than once for 3 or more variables in a function
 *  A&(B&(C&(D&(E&F))))
 *	
 */

//TODO: note: the not operator explination.

public class BasicFunction {
	int a;
	int b;
	int flag;
	int result;

	public BasicFunction(int a, int b, int flag) {
		this.a = a;
		this.b = b;
		this.flag = flag;
	}

	public int evalBF() {
		if (flag == 1) {
			// System.out.println("evalBF(): A+B");
			result = a | b;
			// System.out.println("a,b,result: "+a+","+b+",b"+","+result);
		} 
		else if (flag == 2) {
			// System.out.println("A&B");
			result = a & b;
		} 
		else if (flag == 3) {
			// System.out.println("A^B");
			result = a ^ b;
		}
		return result;
	}

}
