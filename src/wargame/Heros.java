package wargame;

public class Heros extends Soldat{
	
	private final String NOM;
	private final TypesH TYPE;
	private static int nbHeros;
	private static int nbHerosjouee;
	public Heros(Carte carte, TypesH type, String nom, Position pos) {
		super(carte, type.getPoints(), type.getPortee(),
		type.getPuissance(), type.getTir(), pos);
		NOM = nom; TYPE = type;
		}


	public void joueTour() {
		// TODO Auto-generated method stub
		
	}

	public boolean incrementherosj() {
		nbHerosjouee+=1;
		if(nbHeros == nbHerosjouee) {
			return true;
		}
		return false;
	}
	
	
}
