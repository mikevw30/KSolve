
public class CordPair {
	int x;
	int y;
	boolean hasTwo;
	
	public CordPair(int x, int y){
		this.x = x;
		this.y = y;
		hasTwo = false;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "CordPair : " + x + "," + y + "," + hasTwo + "]";
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CordPair other = (CordPair) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}

