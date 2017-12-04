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
		int d,puissancethis,puissancesoldat;
		if(adjacent(soldat)) {
			puissancethis = this.PUISSANCE;
			puissancesoldat = soldat.PUISSANCE;
		}
		else if (aPortee(soldat)) {
			puissancethis = this.TIR;
			puissancesoldat = soldat.TIR;
		}
		Random r = new Random();
		d = r.nextInt(puissancethis);
		soldat.pointsDeVie -= d;
		if(soldat.pointsDeVie > 0) {
			d = r.nextInt(puissancesoldat);
			this.pointsDeVie -= d;
			if(this.pointsDeVie < 1) {
				this.carte.mort(this);
			}
		}
		else {
			soldat.carte.mort(soldat);
		}
		
	}

	@Override
	public void seDeplace(Position newPos) {
		// TODO Auto-generated method stub
		
	}
}
