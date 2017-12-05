package wargame;

public class Monstre extends Soldat {

	private final String NOM;
	private final TypesM TYPE;

	public Monstre(Carte carte, TypesM type, String nom, Position pos) {
		super(carte, type.getPoints(), type.getPortee(), type.getPuissance(), type.getTir(), pos);
		NOM = nom;
		TYPE = type;
	}
	public String toString(){
		String s = TYPE+super.toString();
		return s;
	}
	@Override
	public int getPoints() {
		return super.getPoints();
	}

	@Override
	public boolean getTour() {
		return super.getTour();
	}

	@Override
	public int getPortee() {
		return super.getPortee();
	}

	@Override
	public void joueTour() {
		Position pos,npos;
		Heros cible = trouveCible();
		pos = getPosition();
		npos = null;
		
		if(cible != null)
		combat(cible);
		else {
			npos=carte.trouvePositionVide(pos);
			if(npos != null)
			seDeplace(npos);
			cible = trouveCible();
			if(cible != null)
				combat(cible);
		}
		super.tour=true;
	}
	
	public Heros trouveCible() {
		int portee = getPortee();
		Position pos,npos;
		Heros cible = null;
		int i, j, x, y;
		pos = getPosition();
		x = pos.getX();
		y = pos.getY();
		npos = new Position(x,y);
		for (i = x - portee; (i < x + portee) && cible == null; i++) {
			for (j = y - portee; (j < y + portee) && cible == null; j++) {
				if (i > 0 && j > 0 && i < IConfig.LARGEUR_CARTE && j < IConfig.HAUTEUR_CARTE) {
					npos.setX(i);
					npos.setY(j);
					if(carte.getElement(npos) instanceof Heros) {
						cible = (Heros)carte.getElement(npos);
					}
				}
			}
		}
		return cible;
	}

	@Override
	public void combat(Soldat soldat) {
		super.combat(soldat);

	}

	@Override
	public void seDeplace(Position newPos) {
		super.seDeplace(newPos);
	}


}
