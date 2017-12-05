package wargame;

public class Monstre extends Soldat {

	private final String NOM;
	private final TypesM TYPE;
	private static Heros target;
	private static int nbMonstres;

	public Monstre(Carte carte, TypesM type, String nom, Position pos, int numero) {
		super(carte, type.getPoints(), type.getPortee(), type.getPuissance(), type.getTir(), pos, numero);
		NOM = nom;
		TYPE = type;
	}
	
	public static int getnbMonstres() {
		return nbMonstres;
	}

	private Heros trouveTarget() {
		Heros cible = carte.trouveHeros();
		int i, j, x, y;
		if(cible == null)
			return null;
		Position posCible = cible.getPosition();
		Position newPos = new Position(0, 0);
		x = posCible.getX();
		y = posCible.getY();
		for (i = 0; i < IConfig.LARGEUR_CARTE; i++) {
			for (j = 0; j < IConfig.HAUTEUR_CARTE; j++) {
				newPos.setX(i);
				newPos.setY(j);
				if (carte.getElement(newPos) instanceof Heros) {
					// si le heros trouve est plus proche de (0,0) que la cible connue, il devient
					// la nouvelle cible;
					if (i < x && j < y) {
						cible = (Heros) carte.getElement(newPos);
						posCible = cible.getPosition();
						x = posCible.getX();
						y = posCible.getY();
					}
				}
			}
		}
		return cible;
	}

	private void seRapprocher(int x, int y) {
		Position pos = this.getPosition();
		Position newPos = new Position(0, 0);
		int thisX = pos.getX();
		int thisY = pos.getY();
		int nouvX = thisX;
		int nouvY = thisY;

		if (thisX < x)
			nouvX++;
		else if (thisX > x)
			nouvX--;
		if (thisY < y)
			nouvY++;
		else if (thisY > y)
			nouvY--;

		newPos.setX(nouvX);
		newPos.setY(nouvY);
		if (!this.seDeplace(newPos)) {
			nouvX = thisX;
			nouvY = thisY + 1;
			newPos.setX(nouvX);
			newPos.setY(nouvY);
			if (!this.seDeplace(newPos)) {
				nouvX = thisX + 1;
				nouvY = thisY;
				newPos.setX(nouvX);
				newPos.setY(nouvY);
			}
		}
	}

	public String toString() {
		String s = TYPE + super.toString();
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
		Monstre.target = trouveTarget();
		if(target == null) {
			super.tour = true;
			return;
		}
		Position tpos;
		Heros cible = trouveCible();
		tpos = target.getPosition();

		if (cible != null)
			combat(cible);
		else {
			seRapprocher(tpos.getX(), tpos.getY());
			cible = trouveCible();
			if (cible != null)
				combat(cible);
		}
		super.tour = true;
	}

	public Heros trouveCible() {
		int portee = getPortee();
		Position pos, npos;
		Heros cible = null;
		int i, j, x, y;
		pos = getPosition();
		x = pos.getX();
		y = pos.getY();
		npos = new Position(x, y);
		for (i = x - portee; (i <= x + portee) && cible == null; i++) {
			for (j = y - portee; (j <= y + portee) && cible == null; j++) {
				if (i > 0 && j > 0 && i < IConfig.LARGEUR_CARTE && j < IConfig.HAUTEUR_CARTE) {
					npos.setX(i);
					npos.setY(j);
					if (carte.getElement(npos) instanceof Heros) {
						cible = (Heros) carte.getElement(npos);
					}
				}
			}
		}
		return cible;
	}
	
	public void decrementnbMonstres() {
		nbMonstres--;
	}
	public static void initnbMonstres() {
		nbMonstres = IConfig.NB_MONSTRES;
	}

	@Override
	public void combat(Soldat soldat) {
		super.combat(soldat);

	}

	@Override
	public boolean seDeplace(Position newPos) {
		return super.seDeplace(newPos);
	}

}
