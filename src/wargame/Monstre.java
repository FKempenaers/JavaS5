package wargame;

public class Monstre extends Soldat{

	private final String NOM;
	private final TypesM TYPE;

	public Monstre(Carte carte, TypesM type, String nom, Position pos) {
		super(carte, type.getPoints(), type.getPortee(),
		type.getPuissance(), type.getTir(), pos);
		NOM = nom; TYPE = type;
		}

	@Override
	public int getPoints() {
		return super.getPoints();
	}

	@Override
	public int getTour() {
		return super.getTour();
	}

	@Override
	public int getPortee() {
		return super.getPortee();
	}

	@Override
	public void joueTour(int tour) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void combat(Soldat soldat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seDeplace(Position newPos) {
			carte.deplaceSoldat(newPos,this);
	}
	
}
