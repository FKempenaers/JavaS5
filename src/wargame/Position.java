package wargame;

/**Represente une Position sur la carte */
public class Position implements IConfig, java.io.Serializable {

	private static final long serialVersionUID = 4128033442511717492L;
	private int x, y;

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
/**@return les coordonnes de la Position*/
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}