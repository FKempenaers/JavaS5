package wargame;

public class Heros extends Soldat{
	
	private final String NOM;
	private final TypesH TYPE;
	private static int nbHeros;
	private static int nbHerosjoues;
	public Heros(Carte carte, TypesH type, String nom, Position pos) {
		super(carte, type.getPoints(), type.getPortee(),
		type.getPuissance(), type.getTir(), pos);
		NOM = nom; TYPE = type;
		}


	public void joueTour() {
		// TODO Auto-generated method stub
		
	}

	public static void initnbheros() {
		nbHeros = IConfig.NB_HEROS;
	}
	public static void initnbherosjouee() {
		nbHerosjoues = 0;
	}
	public boolean incrementherosj() {
		nbHerosjoues+=1;
		if(nbHeros == nbHerosjoues) {
			return true;
		}
		return false;
	}
	
	
}
