package wargame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreJeu implements IConfig{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(POSITION_X,POSITION_Y);
		frame.setPreferredSize(new Dimension(NB_PIX_CASE*(LARGEUR_CARTE+5), NB_PIX_CASE*(HAUTEUR_CARTE+5)));
		
		JPanel pan = new JPanel();
		pan.setBackground(Color.GRAY);

		PanneauJeu panneau = new PanneauJeu();
		frame.getContentPane().add(panneau);
		frame.pack();
		frame.setVisible(true);
	}

}
