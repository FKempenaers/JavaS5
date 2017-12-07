package wargame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanneauJeu extends JPanel implements java.io.Serializable {

	private static final long serialVersionUID = -1931270646955506724L;
	private Element el, eh;
	private Position pos, posh;
	private Carte carte;
	private boolean heros_clic, monstre_clic;
	JFrame frame = new JFrame();
	private JButton b1 = new JButton("Fin de tour");
	private JButton b2 = new JButton("Recommencer");
	// private JPanel s1 = new JPanel();

	public PanneauJeu() {
		super(new BorderLayout());
		heros_clic = false;
		monstre_clic = false;
		pos = new Position(0, 0);
		posh = new Position(0, 0);
		carte = new Carte();

		b2.setPreferredSize(new Dimension(150, 60));
		add(b2, BorderLayout.EAST);
		/* Le bouton Recommencer cree une nouvelle carte */
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carte = new Carte();
				repaint();
			}
		});
		repaint();

		b1.setPreferredSize(new Dimension(40, 60));
		add(b1, BorderLayout.SOUTH);
		/*
		 * Bouton fin de tour, appelle : findetourrepos pour que les heros non joues se
		 * reposent jouerMonstres pour que les monstres jouent leur tour resetTour pour
		 * debuter le tour suivant
		 */
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carte.findetourrepos();
				carte.jouerMonstres();
				carte.resetTour();
				repaint();
			}
		});

		/* Gere les clics sur la carte */
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pos.setX((int) Math.floor(e.getX() / IConfig.NB_PIX_CASE));
				pos.setY((int) Math.floor(e.getY() / IConfig.NB_PIX_CASE));
				el = carte.getElement(pos);
				/* Si on a deja selectionne un Heros et qu'on vient de faire un clic droit */
				if (heros_clic && SwingUtilities.isRightMouseButton(e)) {
					/* Si l'element selectionne est un Monstre */
					if (el instanceof Monstre) {
						/* On le combat et signale que le Heros a joue son tour */
						((Soldat) eh).combat((Soldat) el);
						((Soldat) eh).tour = true;
						/*
						 * Si apres incrementation du nombre de Heros ayant joue ils ont tous fait leur
						 * tour On fait jouer les Monstres On prepare le prochain tour
						 */
						if (((Heros) eh).incrementherosj()) {
							carte.jouerMonstres();
							carte.resetTour();
						}
						heros_clic = false;
					}
				}
				/*
				 * Si la case correspond a un heros qui a un tour a jouer on memorise sa
				 * position
				 */
				if (el instanceof Heros) {
					if (((Soldat) el).tour == false) {
						heros_clic = true;
						posh.setX(pos.getX());
						posh.setY(pos.getY());
						eh = el;
					}
				}
				/*
				 * Sinon si on a deja selectionne un Heros et que celui ci peut bouger on le
				 * tente de le deplacer vers la case cliquee on fait appel a incrementherosj :
				 * si tous les Heros ont joue on fait jouer les Monstres et prepare le prochain
				 * tour
				 */
				else if (heros_clic && ((Soldat) eh).getmove()) {
					if (carte.deplaceSoldat(pos, (Soldat) eh)) {
						((Soldat) eh).setmove(false);
						((Soldat) eh).tour = true;
						if (((Heros) eh).incrementherosj()) {
							carte.jouerMonstres();
							carte.resetTour();
						}
					}
					heros_clic = false;
				} else if (el instanceof Monstre) {
					monstre_clic = true;
					eh = el;
				} else {
					monstre_clic = false;
					heros_clic = false;
				}
				repaint();
			}
		});

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * Si ce n'est pas la fin du jeu Si on a clique un Heros ou un Monstre, on
		 * affiche ses infos
		 * 
		 * Sinon on affiche un message de victoire ou defaite
		 */
		if (!carte.getfinjeu()) {
			carte.toutDessiner(g);
			if (heros_clic || monstre_clic) {
				((Soldat) eh).afficheinfo(g);
			}
		} else {
			if (Monstre.getnbMonstres() == 0) {
				g.drawString("Vous avez gagné!!!!!!!", 300, 300);
			} else {
				g.drawString("Vous avez perdu", 300, 300);
			}
		}
	}

}
