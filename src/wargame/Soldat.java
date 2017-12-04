package wargame;

public abstract class Soldat extends Element implements ISoldat {

	private final int POINTS_DE_VIE_MAX, PUISSANCE, TIR, PORTEE_VISUELLE;
	private int pointsDeVie;
	private Position pos;
	protected Carte carte;

	Soldat(Carte carte, int pts, int portee, int puiss, int tir, Position pos) {
		POINTS_DE_VIE_MAX = pointsDeVie = pts;
		PORTEE_VISUELLE = portee;
		PUISSANCE = puiss;
		TIR = tir;
		this.carte = carte;
		this.pos = pos;

	}
	public Position getPosition() {
		return pos;
	}
	public void setPosition(Position pos) {
		pos = new Position(pos.getX(),pos.getY());
	}
	
	public int getPoints() {
		return pointsDeVie;
	}

	@Override
	public int getTour() {
		return 0;
	}

	@Override
	public int getPortee() {
		return PORTEE_VISUELLE;
	}
	@Override
	public void combat(Soldat soldat) {
				
	}

	@Override
	public void seDeplace(Position newPos) {
		carte.deplaceSoldat(newPos, this);
	}
	
	public boolean aPortee(Soldat soldat) {
		int portee = getPortee();
		Position pos,posCible;
		int i, j, x, y;
		pos = getPosition();
		posCible = soldat.getPosition();
		x = pos.getX();
		y = pos.getY();
		i = posCible.getX();
		j = posCible.getY();
		if(i>= x-portee && i<= x+portee && j>= y-portee && j<= y+portee)
			return true;
		return false;
	}
	
	
	public boolean adjacent(Soldat soldat) {
		int x = pos.getX();
		int y = pos.getY();
		Position p = soldat.getPosition();
		int i = p.getX();
		int j = p.getY();
			if(i>= x-1 && i<= x+1 && j>= y-1 && j<= y+1)
				return true;
		return false;

	}
	
}
