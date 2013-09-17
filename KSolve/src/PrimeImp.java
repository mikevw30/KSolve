import java.util.ArrayList;

public class PrimeImp {
	ArrayList<Integer> list;
	int inc;
	int value;
	boolean checked;
	
	public PrimeImp(int value,int inc){
		list = new ArrayList<>();
		this.value = value;
		this.inc = inc;
		checked = false;
	}
	public String toString(){
		return inc+list.toString()+checked;
	}
}
