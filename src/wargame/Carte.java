package wargame;

import java.awt.Graphics;
import java.util.Random;

public class Carte implements ICarte, IConfig{
	private Element[][] carte;
	
	public Carte() {
	this.carte = new Element[LARGEUR_CARTE][HAUTEUR_CARTE];
	}
	
	@Override
	public Element getElement(Position pos) {
		return carte[pos.getX()][pos.getY()];
	}

	@Override
	public Position trouvePositionVide() {
		int x,y;
		x = y = 0;
		Random r = new Random();
		Element e = null;
		while(e == null) {
			x = r.nextInt(LARGEUR_CARTE);
			y = r.nextInt(HAUTEUR_CARTE);
			e = carte[x][y];		
		}
		return new Position(x,y);
	}

	@Override
	public Position trouvePositionVide(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Heros trouveHeros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Heros trouveHeros(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deplaceSoldat(Position pos, Soldat soldat) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mort(Soldat perso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean actionHeros(Position pos, Position pos2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void jouerSoldats(PanneauJeu pj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toutDessiner(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
