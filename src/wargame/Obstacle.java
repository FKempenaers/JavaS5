package wargame;

import java.awt.Color;
import java.awt.Graphics;

/*la classe qui represente les Obstacle */
public class Obstacle extends Element implements IConfig, java.io.Serializable {

	private static final long serialVersionUID = 3074872363153736231L;

	public enum TypeObstacle {
		ROCHER(COULEUR_ROCHER), FORET(COULEUR_FORET), EAU(COULEUR_EAU);
		private final Color COULEUR;

		TypeObstacle(Color couleur) {
			COULEUR = couleur;
		}

		public static TypeObstacle getObstacleAlea() {
			return values()[(int) (Math.random() * values().length)];
		}

		public Color getCouleur() {
			return COULEUR;
		}
	}

	private TypeObstacle TYPE;
	private Position pos;

	Obstacle(TypeObstacle type, Position pos) {
		TYPE = type;
		this.pos = pos;
	}

	public Color getCouleur() {
		return TYPE.getCouleur();
	}

	public String toString() {
		return "" + TYPE;
	}
}