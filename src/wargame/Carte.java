package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import wargame.Obstacle.TypeObstacle;

public class Carte implements ICarte, IConfig {
	private Element[][] carte;

	/*
	 * la carte est un tableau d'Element (Obstacle,Soldat ou null si case vide),
	 * chaque Element est place dans la case qui correspond a sa Position
	 */
	public Carte() {
		this.carte = new Element[LARGEUR_CARTE][HAUTEUR_CARTE];
		initCarte();
	}

	public void initCarte() {
		int heros, monstres, obstacles;
		Position p;
		for (int i = 0; i < IConfig.LARGEUR_CARTE; i++) {
			for (int j = 0; j < IConfig.HAUTEUR_CARTE; j++) {
				setElement(null, i, j);
			}
		}

		for (heros = 0; heros < IConfig.NB_HEROS; heros++) {
			do {
				p = trouvePositionVide();
			} while (p.getX() < LARGEUR_CARTE - 5 || p.getY() < HAUTEUR_CARTE - 5);
			setElement(new Heros(this, ISoldat.TypesH.getTypeHAlea(), "HerosAuPif", p), p.getX(), p.getY());
		}
		for (monstres = 0; monstres < IConfig.NB_MONSTRES; monstres++) {
			do {
				p = trouvePositionVide();
			} while (p.getX() > 5 || p.getY() > 5);
			setElement(new Monstre(this, ISoldat.TypesM.getTypeMAlea(), "MonstreAuPif", p), p.getX(), p.getY());
		}
		for (obstacles = 0; obstacles < IConfig.NB_OBSTACLES; obstacles++) {
			p = trouvePositionVide();
			setElement(new Obstacle(TypeObstacle.getObstacleAlea(), p), p.getX(), p.getY());
		}
	}

	@Override
	/* retourne l'Element qui est a la Position passee en parametre */
	public Element getElement(Position pos) {
		return carte[pos.getX()][pos.getY()];
	}

	@Override
	/* retourne une Position correspondant a une case vide trouvee aleatoirement */
	public Position trouvePositionVide() {
		int x, y;
		x = y = 0;
		Random r = new Random();
		do {
			x = r.nextInt(LARGEUR_CARTE);
			y = r.nextInt(HAUTEUR_CARTE);
		} while (carte[x][y] != null);
		return new Position(x, y);
	}

	@Override
	/*
	 * retourne une Position correspondant a une case vide autour de la Position
	 * passee en parametre, retourne null si toutes les Position adjacentes sont
	 * occupees
	 */
	public Position trouvePositionVide(Position pos) {
		int x = pos.getX();
		int y = pos.getY();
		for (int i = x - 1; (i <= x + 1) && (i < LARGEUR_CARTE); i++) {
			for (int j = y - 1; (j <= y + 1) && (j < HAUTEUR_CARTE); j++) {
				if ((i != x && j != y) && (carte[i][j] == null))
					return new Position(i, j);
			}
		}
		return null;
	}

	@Override
	/* retourne un Heros trouve aleatoirement sur la carte */
	public Heros trouveHeros() {
		int x, y;
		x = y = 0;
		Random r = new Random();
		Element e;
		do {
			x = r.nextInt(LARGEUR_CARTE);
			y = r.nextInt(HAUTEUR_CARTE);
			e = carte[x][y];
		} while (!(e instanceof Heros));
		return (Heros) e;
	}

	@Override
	/*
	 * retourne un Heros adjacent a la Position passee en parametre et null s'il n'y
	 * en a pas
	 */
	public Heros trouveHeros(Position pos) {
		int x = pos.getX();
		int y = pos.getY();
		for (int i = x - 1; (i <= x + 1) && (i < LARGEUR_CARTE); i++) {
			for (int j = y - 1; (j <= y + 1) && (j < HAUTEUR_CARTE); j++) {
				if ((i != x && j != y) && (carte[i][j] instanceof Heros))
					return (Heros) carte[i][j];
			}
		}
		return null;

	}

	@Override
	public boolean deplaceSoldat(Position pos, Soldat soldat) {
		int i, j, x, y;
		i = pos.getX();
		j = pos.getY();
		x = soldat.getPosition().getX();
		y = soldat.getPosition().getY();
		if (i < LARGEUR_CARTE && j < HAUTEUR_CARTE) {
			if ((i != x || j != y) && (((i >= x - 1) && (i <= x + 1)) && ((j >= y - 1) && (j <= y + 1)))) {
				if (carte[i][j] == null) {
					carte[i][j] = soldat;
					soldat.getPosition().setX(i);
					soldat.getPosition().setY(j);
					carte[x][y] = null;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void mort(Soldat perso) {
		// on retire le soldat de sa position dans le tableau
		carte[perso.getPosition().getX()][perso.getPosition().getY()] = null;
		// on met son pointeur a null
		perso = null;
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
		int i, j;
		for (j = 0; j < HAUTEUR_CARTE; j++) {
			for (i = 0; i < LARGEUR_CARTE; i++) {
				/* couleur de la case suivant type d'element */
				if (carte[i][j] == null) {
					g.setColor(COULEUR_VIDE);
				} else {
					if (carte[i][j] instanceof Heros)
						g.setColor(COULEUR_HEROS);
					if (carte[i][j] instanceof Monstre)
						g.setColor(COULEUR_MONSTRES);
					if (carte[i][j] instanceof Obstacle)
						g.setColor(((Obstacle) carte[i][j]).getCouleur());
				}
				/* dessin de la case */
				g.fillRect(i * NB_PIX_CASE, j * NB_PIX_CASE, NB_PIX_CASE, NB_PIX_CASE);
			}
		}
		/* trace de la grille de jeu */
		g.setColor(Color.DARK_GRAY);
		for (i = 0; i <= LARGEUR_CARTE; i++)
			g.drawLine(i * NB_PIX_CASE, 0, i * NB_PIX_CASE, HAUTEUR_CARTE * NB_PIX_CASE);
		for (j = 0; j <= HAUTEUR_CARTE; j++)
			g.drawLine(0, j * NB_PIX_CASE, LARGEUR_CARTE * NB_PIX_CASE, j * NB_PIX_CASE);
	}

	/* Pour test : place un element dans la case passee en param */
	public void setElement(Element e, int x, int y) {
		carte[x][y] = e;
	}

}