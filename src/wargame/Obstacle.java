package wargame;

import java.awt.Color;

/** La classe qui represente les Obstacle */
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

	Obstacle(TypeObstacle type) {
		TYPE = type;
	}

	public Color getCouleur() {
		return TYPE.getCouleur();
	}

	public String toString() {
		return "" + TYPE;
	}
}