package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JTextArea;

/*La classe des Soldat dont heritent les Heros et les Monstre */
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

	/* Retourne une String contenant les infos du Soldat */
	public String toString() {
		String s = " vie : " + pointsDeVie + " Points de vie max : " + POINTS_DE_VIE_MAX + " portee : "
				+ this.getPortee() + " puissance corps a  corps : " + PUISSANCE + " puissance tir : " + TIR;
		return s;
	}

	/* Retourne true si le Soldat peut se deplacer, false sinon */
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
		pos = new Position(pos.getX(), pos.getY());
	}

	public int getPoints() {
		return pointsDeVie;
	}

	@Override
	/* Retourne true si le Soldat a joue ce tour et false sinon */
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
	/*
	 * Combat le Soldat soldat, si les deux Soldat sont adjacents on utilise les
	 * degats corps a corps, sinon les degats de tir si le Soldat vise est survit a
	 * l'attaque, il riposte avec une puissance divisee par deux. si les pointsDeVie
	 * de l'un des Soldat atteignent 0, il meurt.
	 */
	public void combat(Soldat soldat) {
		int d, puissancethis = 0, puissancesoldat = 0;

		if (adjacent(soldat)) {
			puissancethis = this.PUISSANCE;
			puissancesoldat = soldat.PUISSANCE;
		} else if (aPortee(soldat)) {
			puissancethis = this.TIR;
			puissancesoldat = soldat.TIR;
		}
		Random r = new Random();
		d = r.nextInt(puissancethis + 1);
		soldat.pointsDeVie -= d;
		if (soldat.pointsDeVie > 0) {
			d = r.nextInt(puissancesoldat / 2 + 1);
			this.pointsDeVie -= d;
			if (this.pointsDeVie < 1) {
				this.carte.mort(this);
			}
		} else {
			soldat.carte.mort(soldat);
		}
		this.tour = true;

	}

	/* Affiche les infos du Soldat */
	public void afficheinfo(Graphics g) {
		g.setColor(Color.BLACK);
		if (this instanceof Heros) {
			g.drawString(((Heros) this).toString(), 20, 630);
		} else {
			g.drawString(((Monstre) this).toString(), 20, 630);
		}
	}

	/* Un soldat qui se repose recupere au max 20% de ses hp */
	public void repos() {
		int valeur = (this.POINTS_DE_VIE_MAX * 20) / 100;
		if (this.pointsDeVie + valeur > this.POINTS_DE_VIE_MAX)
			this.pointsDeVie = this.POINTS_DE_VIE_MAX;
		else
			this.pointsDeVie += valeur;
		this.tour = true;
	}

	/*
	 * Le Soldat se deplace vers la Position newPos, retourne true si le deplacement
	 * est un succes et false sinon
	 */
	public boolean seDeplace(Position newPos) {
		return carte.deplaceSoldat(newPos, this);
	}

	/* Retourne true si le Soldat soldat est a portee de this et false sinon */
	public boolean aPortee(Soldat soldat) {
		int portee = getPortee();
		Position pos, posCible;
		int i, j, x, y;
		pos = getPosition();
		posCible = soldat.getPosition();
		x = pos.getX();
		y = pos.getY();
		i = posCible.getX();
		j = posCible.getY();
		if (i >= x - portee && i <= x + portee && j >= y - portee && j <= y + portee)
			return true;
		return false;
	}

	/* Retourne true si le Soldat soldat est adjacent a this et false sinon */
	public boolean adjacent(Soldat soldat) {
		int x = pos.getX();
		int y = pos.getY();
		Position p = soldat.getPosition();
		int i = p.getX();
		int j = p.getY();
		if (i >= x - 1 && i <= x + 1 && j >= y - 1 && j <= y + 1)
			return true;
		return false;

	}

}
