package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JTextArea;

public abstract class Soldat extends Element implements ISoldat {

	private final int POINTS_DE_VIE_MAX, PUISSANCE, TIR, PORTEE_VISUELLE;
	private int pointsDeVie;
	private Position pos;
	protected Carte carte;
	protected boolean tour;
	private boolean move;
	private int numero;

	Soldat(Carte carte, int pts, int portee, int puiss, int tir, Position pos, int numero) {
		POINTS_DE_VIE_MAX = pointsDeVie = pts;
		PORTEE_VISUELLE = portee;
		PUISSANCE = puiss;
		TIR = tir;
		tour = false;
		move = true;
		this.carte = carte;
		this.pos = pos;
		this.numero = numero;
	}
	public String toString(){
		String s = " vie : "+pointsDeVie+" Point de vie max : "+POINTS_DE_VIE_MAX+" portée : "+this.getPortee()+" puissance corp à corp : "+PUISSANCE+" puissance tir : "+TIR;
		return s;
	}
	public boolean getmove() {
		return this.move;
	}
	public void setmove(boolean b) {
		this.move = b;
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
	public boolean getTour() {
		return tour;
	}
	public int getNum() {
		return numero;
	}

	@Override
	public int getPortee() {
		return PORTEE_VISUELLE;
	}
	@Override
	public void combat(Soldat soldat) {
		int d,puissancethis = 0,puissancesoldat = 0;

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
		this.tour = true;
		
	}
	
	public void afficheinfo(Graphics g) {
		g.setColor(Color.BLACK);
		if(this instanceof Heros) {
			g.drawString(((Heros)this).toString() , 20 , 630 );
		}
		else {
			g.drawString(((Monstre)this).toString() , 20 , 630 );
		}
	}

	/*un soldat qui se repose recupere au max 20% de ses hp*/
	public void repos() {
		int valeur = (this.POINTS_DE_VIE_MAX*20)/100;
		if(this.pointsDeVie+valeur > this.POINTS_DE_VIE_MAX)
			this.pointsDeVie = this.POINTS_DE_VIE_MAX;
		else
			this.pointsDeVie += valeur;
		this.tour = true;
	}

	public boolean seDeplace(Position newPos) {
		return carte.deplaceSoldat(newPos, this);
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
