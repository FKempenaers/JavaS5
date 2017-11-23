package wargame;

import java.awt.Graphics;

import javax.swing.JPanel;


public class PanneauJeu extends JPanel{
	private Carte carte;
	public PanneauJeu() {
		carte = new Carte();
		carte.setElement(new Heros(carte, ISoldat.TypesH.ELF, "etudiant", new Position(0,0)),0,0);
		carte.setElement(new Monstre(carte, ISoldat.TypesM.TROLL, "prof de POO", new Position(0,0)),1,1);
	};

	public void paintComponent(Graphics g) {
		carte.toutDessiner(g);

	}
}
