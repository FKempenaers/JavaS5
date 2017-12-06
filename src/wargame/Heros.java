package wargame;

/*La classe des Heros joues le joueur, herite de Soldat */
public class Heros extends Soldat {

	private final String NOM;
	private final TypesH TYPE;
	private static int nbHeros;
	private static int nbHerosjoues;

	public Heros(Carte carte, TypesH type, String nom, Position pos, int numero) {
		super(carte, type.getPoints(), type.getPortee(), type.getPuissance(), type.getTir(), pos, numero);
		NOM = nom;
		TYPE = type;
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

}
