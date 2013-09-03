/*
 * @author Michael VanWie
 * This class holds each row of a truth table.
 */

public class TTRow {
	public int length;
	public int row[];
	public int value;
	
	public TTRow(int length){
		this.length = length;
		row = new int[length];
		for (int i = 0; i < row.length; i++) {
			row[i]=0;
		}
		value = 0;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setLengthAndValue(int value){
		this.value = value;
		setBinaryVal();
	}
	
	/*
	 * setBinaryVal() :
	 * takes the decimal value of TTRow and converts that value into 
	 * the binary representation with in the row array.
	 */
	public void setBinaryVal(){
		int temp = value;
		for (int i = length; i >=0; i--) {
			int powerOfTwo = (int) Math.pow(2, i); //2^i
			if(temp-powerOfTwo>=0){
				row[i]=1;
				temp-=powerOfTwo; 
			}
		}
	}
	public String toString(){
		String t="";
			for (int i = length-1; i >= 0; i--) {
				t+=row[i];
			}
			t+="\t: "+value;
		return t;
	}
	
//	public String toString(){
//		String t="";
//			for (int i =0; i <length; i++) {
//				t+=row[i];
//			}
//			t+="\t: "+value;
//		return t;
//	}
	
}
