package wargame;

/*La classe des Heros joues le joueur, herite de Soldat */
public class Heros extends Soldat implements java.io.Serializable {

	private static final long serialVersionUID = 5476830968803461453L;
	private final String NOM;
	private final TypesH TYPE;
	private static int nbHeros;
	private static int nbHerosjoues;

	public Heros(Carte carte, TypesH type, String nom, Position pos, int numero) {
		super(carte, type.getPoints(), type.getPortee(), type.getPuissance(), type.getTir(), pos, numero);
		NOM = nom;
		TYPE = type;
	}

	public TypesH getType() {
		return TYPE;
	}
	/* Retourne le nombre de Heros en vie */
	public static int getnbHeros() {
		return nbHeros;
	}

	/* Retourne les infos du Heros */
	public String toString() {
		String s = TYPE + super.toString();
		return s;
	}

	public void soigner(Soldat s) {
		if(this.aPortee(s)){
			if(s.getPoints()+10 > s.getpointdeVieMax()) {
				s.setpointDeVie(s.getpointdeVieMax());
			}
			else {
				s.setpointDeVie(s.getPoints()+10);
			}
		}
		
	}
	/* Decremente le nombre de Heros en vie */
	public void decrementnbHeros() {
		nbHeros--;
	}

	/* Initialise le nombre de Heros en vie au nombre prevu dans IConfig */
	public static void initnbheros() {
		nbHeros = IConfig.NB_HEROS;
	}

	/* Initialise le nombre de Heros joues a 0 */
	public static void initnbherosjoues() {
		nbHerosjoues = 0;
	}

	/*
	 * Incremente le nombre de Heros joue, retourne true si celui-ci a atteint le
	 * nombre de Heros et false sinon
	 */
	public boolean incrementherosj() {
		nbHerosjoues += 1;
		if (nbHeros == nbHerosjoues) {
			return true;
		}
		return false;
	}

	/*
	 * Lors du chargement d'une partie, corrige les valeurs nbHeros et nbHerosJoues
	 */
	public static void compteHeros(Carte carte) {
		int i, j;
		Element e;
		Position pos = new Position(0, 0);
		nbHeros = 0;
		nbHerosjoues = 0;
		for (i = 0; i < IConfig.LARGEUR_CARTE; i++) {
			for (j = 0; j < IConfig.HAUTEUR_CARTE; j++) {
				pos.setX(i);
				pos.setY(j);
				e = carte.getElement(pos);
				if (e != null) {
					if (e instanceof Heros) {
						nbHeros++;
						if (((Heros) e).getTour())
							nbHerosjoues++;
					}
				}
			}
		}
	}

}
