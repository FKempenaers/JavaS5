package wargame;

import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Obstacle.TypeObstacle;

public class PanneauJeu extends JPanel {
	private Carte carte;

	public PanneauJeu() {
		carte = new Carte();
		/*
		 * carte.setElement(new Heros(carte, ISoldat.TypesH.getTypeHAlea(), "etudiant",
		 * new Position(4, 4)), 4, 4); carte.setElement(new Monstre(carte,
		 * ISoldat.TypesM.getTypeMAlea(), "prof de POO", new Position(5, 5)), 5, 5);
		 * carte.setElement(new Obstacle(TypeObstacle.getObstacleAlea(), new Position(6,
		 * 6)), 6, 6);
		 */
		initCarte();
	}

	public void initCarte() {
		int heros, monstres, obstacles;
		Position p;

		for (heros = 0; heros < IConfig.NB_HEROS; heros++) {
			do {
				p = carte.trouvePositionVide();
			} while (p.getX() < 10 || p.getY() < 20);
			carte.setElement(new Heros(carte, ISoldat.TypesH.getTypeHAlea(), "HerosAuPif", p), p.getX(), p.getY());
		}
		for (monstres = 0; monstres < IConfig.NB_MONSTRES; monstres++) {
			do {
				p = carte.trouvePositionVide();
			} while (p.getX() > 5 || p.getY() > 5);
			carte.setElement(new Monstre(carte, ISoldat.TypesM.getTypeMAlea(), "MonstreAuPif", p), p.getX(), p.getY());
		}
		for (obstacles = 0; obstacles < IConfig.NB_OBSTACLES; obstacles++) {
			p = carte.trouvePositionVide();
			carte.setElement(new Obstacle(TypeObstacle.getObstacleAlea(), p), p.getX(), p.getY());
		}
	}

	public void paintComponent(Graphics g) {
		carte.toutDessiner(g);

	}
}
