package wargame;

import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Obstacle.TypeObstacle;

public class PanneauJeu extends JPanel {
	public Carte carte;

	public PanneauJeu() {
		carte = new Carte();
	}
	public void paintComponent(Graphics g) {
		carte.toutDessiner(g);

	}
}
