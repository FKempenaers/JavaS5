package wargame;

public class Heros extends Soldat{
	
	private final String NOM;
	private final TypesH TYPE;
	private static int nbHeros;
	private static int nbHerosjoues;
	public Heros(Carte carte, TypesH type, String nom, Position pos, int numero) {
		super(carte, type.getPoints(), type.getPortee(),
		type.getPuissance(), type.getTir(), pos, numero);
		NOM = nom; TYPE = type;
		}


	public void joueTour() {
		// TODO Auto-generated method stub
		
	}
	public String toString(){
		String s = TYPE+super.toString();
		return s;
	}
	public void decrementnbHeros() {
		nbHeros--;
	}
	public static void initnbheros() {
		nbHeros = IConfig.NB_HEROS;
	}
	public static void initnbherosjoues() {
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
