import java.util.ArrayList;

/*
 * @author Michael VanWie
 * This class holds each row of a truth table.
 */

public class TTRow {
	public  boolean deleteFlag = false;
	public int length;
	public int row[];
	public int value;
	int count;
	int decimalValue;
	boolean checked;
	int inc;
	ArrayList<Integer> set;

	public TTRow(int length) {
		this.length = length;
		row = new int[length];
		for (int i = 0; i < row.length; i++) {
			row[i] = 0;
		}
		value = 0;
		set = new ArrayList<>();
		checked = false;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setLengthAndValue(int value) {
		this.value = value;
		decimalValue = value;
		setBinaryVal();
	}

	public void setBinaryVal(){
		int temp = value;
		for (int i = length; i >= 0; i--){
			int powerOfTwo = (int) Math.pow(2, i); // 2^i
			if (temp - powerOfTwo >= 0){
				row[i] = 1;
				temp -= powerOfTwo;
				count++;
			}
		}
	}

	public String toString() {
		String t = decimalValue+":\t";
		for (int i = length - 1; i >= 0; i--) {
			t += row[i];
		}
		t += "\t: " + value;
		return t;
	}
}
