package wargame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*La fenetre du jeu, utilise les variable de IConfig, ajoute le Panneau de Jeu et rend la fenetre visible */
public class FenetreJeu implements IConfig {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(POSITION_X, POSITION_Y);
		frame.setPreferredSize(new Dimension(NB_PIX_CASE * (LARGEUR_CARTE + 5), NB_PIX_CASE * (HAUTEUR_CARTE + 5)));

		JPanel pan = new JPanel();
		pan.setBackground(Color.GRAY);

		PanneauJeu panneau = new PanneauJeu();
		frame.getContentPane().add(panneau);
		frame.pack();
		frame.setVisible(true);
	}

}
