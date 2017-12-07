package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

import wargame.Obstacle.TypeObstacle;

public class Carte implements ICarte, IConfig, java.io.Serializable{

	private static final long serialVersionUID = 5937244890881069262L;
	private Element[][] carte;
	private boolean finjeu;

	/*
	 * la carte est un tableau d'Element (Obstacle,Soldat ou null si case vide),
	 * chaque Element est place dans la case qui correspond a sa Position finjeu
	 * vaut false quand la partie est en cours, true quand elle est terminee
	 */
	public Carte() {
		finjeu = false;
		this.carte = new Element[LARGEUR_CARTE][HAUTEUR_CARTE];
		initCarte();
	}

	public boolean getfinjeu() {
		return finjeu;
	}

	/*
	 * Initialise la carte Les cases sont d'abord mises a null. Puis on ajoute des
	 * Heros dans un carre de 5x5 dans le coin inferieur droit de la carte tant que
	 * le nombre voulu n'est pas atteint. De meme pour les Monstres dans le coin
	 * superieur gauche. Les Obstacles sont ensuite ajoutes sur toute la carte. Les
	 * variables nbHeros et nbMonstres sont initialisees.
	 */
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
			setElement(new Heros(this, ISoldat.TypesH.getTypeHAlea(), "HerosAuPif", p, heros + 1), p.getX(), p.getY());
		}
		for (monstres = 0; monstres < IConfig.NB_MONSTRES; monstres++) {
			do {
				p = trouvePositionVide();
			} while (p.getX() > 5 || p.getY() > 5);
			setElement(new Monstre(this, ISoldat.TypesM.getTypeMAlea(), "MonstreAuPif", p, monstres + 1), p.getX(),
					p.getY());
		}
		for (obstacles = 0; obstacles < IConfig.NB_OBSTACLES; obstacles++) {
			p = trouvePositionVide();
			setElement(new Obstacle(TypeObstacle.getObstacleAlea(), p), p.getX(), p.getY());
		}
		Heros.initnbheros();
		Monstre.initnbMonstres();
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
				if ((i >= 0) && (j >= 0) && (i != x && j != y) && (carte[i][j] == null))
					return new Position(i, j);
			}
		}
		return null;
	}

	@Override
	/*
	 * retourne un Heros trouve aleatoirement sur la carte, la fonction a 2 fois le
	 * nombre de cases de la carte tentatives pour trouver un Heros, elle retourne
	 * null si elle a epuise le nombre de tentatives.
	 */
	public Heros trouveHeros() {
		int compteur = 2 * LARGEUR_CARTE * HAUTEUR_CARTE;
		int x, y;
		x = y = 0;
		Random r = new Random();
		Element e;
		do {
			if (compteur == 0)
				return null;
			x = r.nextInt(LARGEUR_CARTE);
			y = r.nextInt(HAUTEUR_CARTE);
			e = carte[x][y];
			compteur--;
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
	/*
	 * Deplace le Soldat soldat vers la Position pos sous reserve qu'elle soit vide
	 * (retourne false sinon) Les cases de carte correspondant � l'ancienne Position
	 * et � la nouvelle sont mises � jour tout comme la Position de soldat.
	 */
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
	/*
	 * gere la mort du Soldat perso : sa case et son pointeur passent a null. On
	 * decremente nbMonstres ou nbHeros selon la classe de perso, si le nombre de
	 * Monstre ou de Heros atteint 0, on passe finjeu a true pour declencher la fin
	 * de la partie.
	 */
	public void mort(Soldat perso) {
		carte[perso.getPosition().getX()][perso.getPosition().getY()] = null;
		if (perso instanceof Heros) {
			((Heros) perso).decrementnbHeros();
			if (Heros.getnbHeros() == 0) {
				finjeu = true;
			}
		} else {
			((Monstre) perso).decrementnbMonstres();
			if (Monstre.getnbMonstres() == 0) {
				finjeu = true;
			}
		}
		perso = null;
	}

	/* Dessin des elements de la carte */
	public void toutDessiner(Graphics g) {

		// TODO Auto-generated method stub
		int[][] carte_b;
		carte_b = new int[LARGEUR_CARTE+1][HAUTEUR_CARTE+1];
		int i, j,e,y,p,e1,y1;
		char num;
		String s;
		for (j = 0; j < HAUTEUR_CARTE; j++) {
			for (i = 0; i < LARGEUR_CARTE; i++) {

				carte_b[i][j] = 0;
			}
		}
		for (j = 0; j < HAUTEUR_CARTE; j++) {
			for (i = 0; i < LARGEUR_CARTE; i++) {
				if (carte[i][j] instanceof Heros) {
					p = ((Soldat)carte[i][j]).getPortee();
					if(j-p < 0) {
						e = 0;
					}
					else e = j-p;
					if(j+p >HAUTEUR_CARTE) {
						e1 = HAUTEUR_CARTE;
					}
					else e1 = j+p;
					while (e < e1+1) {
						if(i-p < 0) {
							y = 0;
						}
						else y = i-p;
						if(i+p > LARGEUR_CARTE) {
							y1 = LARGEUR_CARTE;
						}
						else y1 = i+p;
						while (y < y1+1) {
							carte_b[y][e] = 1;
							y++;
						}
						e++;
					}
				}
			}
		}
		for (j = 0; j < HAUTEUR_CARTE; j++) {
			for (i = 0; i < LARGEUR_CARTE; i++) {
				/* couleur de la case suivant type d'element */
				if(carte_b[i][j] == 0) {
					g.setColor(COULEUR_INCONNU);
				}
				else {
					if (carte[i][j] == null) {
						g.setColor(COULEUR_VIDE);
					} else {
						if (carte[i][j] instanceof Heros) {
							/* Les Heros qui ont joue ont une couleur differente */
							if (((Heros) carte[i][j]).getTour())
								g.setColor(COULEUR_HEROS_DEJA_JOUE);
							else
								g.setColor(COULEUR_HEROS);
						}
						if (carte[i][j] instanceof Monstre)
							g.setColor(COULEUR_MONSTRES);
						if (carte[i][j] instanceof Obstacle)
							g.setColor(((Obstacle) carte[i][j]).getCouleur());
					}
					/* dessin de la case */
					g.fillRect(i * NB_PIX_CASE, j * NB_PIX_CASE, NB_PIX_CASE, NB_PIX_CASE);
				}
			}
		}

		/* Ajout des numeros des Monstre et des lettres des Heros */
		for (j = 0; j < HAUTEUR_CARTE; j++) {
			for (i = 0; i < LARGEUR_CARTE; i++) {
				if (carte[i][j] != null) {
					if (carte[i][j] instanceof Heros) {
						g.setColor(Color.black);
						num = (char) ((((Soldat) carte[i][j]).getNum()) + 'A');
						s = Character.toString(num);
						g.drawString(s, i * NB_PIX_CASE + NB_PIX_CASE / 2, j * NB_PIX_CASE + NB_PIX_CASE / 2);
					}
					if (carte[i][j] instanceof Monstre && carte_b[i][j] == 1) {
						g.setColor(Color.white);
						s = Integer.toString(((Soldat) carte[i][j]).getNum());
						g.drawString(s, i * NB_PIX_CASE + NB_PIX_CASE / 2, j * NB_PIX_CASE + NB_PIX_CASE / 2);
					}
				}
			}
		}

		/* trace de la grille de jeu */
		g.setColor(Color.DARK_GRAY);
		for (i = 0; i <= LARGEUR_CARTE; i++)
			g.drawLine(i * NB_PIX_CASE, 0, i * NB_PIX_CASE, HAUTEUR_CARTE * NB_PIX_CASE);
		for (j = 0; j <= HAUTEUR_CARTE; j++)
			g.drawLine(0, j * NB_PIX_CASE, LARGEUR_CARTE * NB_PIX_CASE, j * NB_PIX_CASE);
	}

	/* Place un element dans la case passee en param */
	public void setElement(Element e, int x, int y) {
		carte[x][y] = e;
	}

	/*
	 * Parcourt la carte, si on trouve un Monstre qui n'a pas joue son tour, il joue
	 * son tour
	 */
	public void jouerMonstres() {
		int i, j;
		Monstre m;
		for (i = 0; i < IConfig.LARGEUR_CARTE; i++) {
			for (j = 0; j < IConfig.HAUTEUR_CARTE; j++) {
				if (carte[i][j] instanceof Monstre) {
					m = (Monstre) carte[i][j];
					if (!m.getTour())
						m.joueTour();
				}
			}
		}

	}

	/* Parcourt la carte, les Heros n'ayant pas joue leur tour se reposent */
	public void findetourrepos() {
		int i, j;
		for (j = 0; j < HAUTEUR_CARTE; j++) {
			for (i = 0; i < LARGEUR_CARTE; i++) {
				if (carte[i][j] instanceof Heros) {
					if (!((Heros) carte[i][j]).tour) {
						((Soldat) carte[i][j]).repos();
					}
				}
			}
		}
	}

	/*
	 * Parcourt la carte, les Soldat peuvent a nouveau jouer un tour et se deplacer,
	 * le nombre de Heros ayant joue est remis � 0
	 */
	public void resetTour() {
		int i, j;
		Soldat s;

		for (i = 0; i < IConfig.LARGEUR_CARTE; i++) {
			for (j = 0; j < IConfig.HAUTEUR_CARTE; j++) {
				if (carte[i][j] instanceof Soldat) {
					s = (Soldat) carte[i][j];
					s.tour = false;
					s.setmove(true);
				}
			}
		}
		Heros.initnbherosjoues();
	}

}
