package wargame;

public class Monstre extends Soldat {

	private final String NOM;
	private final TypesM TYPE;

	public Monstre(Carte carte, TypesM type, String nom, Position pos) {
		super(carte, type.getPoints(), type.getPortee(), type.getPuissance(), type.getTir(), pos);
		NOM = nom;
		TYPE = type;
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
		Position pos,npos;
		Heros cible = trouveCible();
		pos = getPosition();
		
		if(cible != null)
		combat(cible);
		else {
			npos=carte.trouvePositionVide(pos);
			seDeplace(npos);
			cible = trouveCible();
			if(cible != null)
				combat(cible);
		}
	}
	
	public Heros trouveCible() {
		int portee = getPortee();
		Position pos;
		Heros cible = null;
		int i, j, x, y;
		pos = getPosition();
		x = pos.getX();
		y = pos.getY();
		for (i = x - portee; (i < x + portee) && cible == null; i++) {
			for (j = y - portee; (j < y + portee) && cible == null; j++) {
				if (i > 0 && j > 0 && i < IConfig.LARGEUR_CARTE && j < IConfig.HAUTEUR_CARTE) {
					pos.setX(i);
					pos.setY(j);
					if(carte.getElement(pos) instanceof Heros) {
						cible = (Heros)carte.getElement(pos);
					}
				}
			}
		}
		return cible;
	}

	@Override
	public void combat(Soldat soldat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void seDeplace(Position newPos) {
		super.seDeplace(newPos);
	}

}
