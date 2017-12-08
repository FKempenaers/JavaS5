package wargame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**Represente le panneau de jeu*/
public class PanneauJeu extends JPanel implements java.io.Serializable {

	private static final long serialVersionUID = -1931270646955506724L;
	private Element el, eh;
	private Position pos, posh;
	private Carte carte;
	private boolean heros_clic, monstre_clic;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	JFrame frame = new JFrame();
	private JButton b1 = new JButton("Fin de tour");
	private JButton b2 = new JButton("Recommencer");
	private JButton b3 = new JButton("Sauvegarder");
	private JButton b4 = new JButton("Charger partie");
	// private JPanel s1 = new JPanel();

	public PanneauJeu() {
		super(new BorderLayout());
		heros_clic = false;
		monstre_clic = false;
		pos = new Position(0, 0);
		posh = new Position(0, 0);
		carte = new Carte();
		this.setLayout(null);

		b4.setBounds(IConfig.NB_PIX_CASE * IConfig.LARGEUR_CARTE + 20, 140, 150, 60);
		add(b4);
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					final FileInputStream fichierIn = new FileInputStream("maclasse.ser");
					ois = new ObjectInputStream(fichierIn);
					carte = (Carte) ois.readObject();
					Heros.compteHeros(carte);
					Monstre.compteMonstre(carte);
					repaint();
				} catch (final java.io.IOException e) {
					e.printStackTrace();
				} catch (final ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						if (ois != null) {
							ois.close();
						}
					} catch (final IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		b3.setBounds(IConfig.NB_PIX_CASE * IConfig.LARGEUR_CARTE + 20, 210, 150, 60);
		add(b3);
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					final FileOutputStream fichierOut = new FileOutputStream("maclasse.ser");
					oos = new ObjectOutputStream(fichierOut);
					oos.writeObject(carte);
					oos.flush();
				} catch (final java.io.IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (oos != null) {
							oos.close();
						}
					} catch (final IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		b2.setBounds(IConfig.NB_PIX_CASE * IConfig.LARGEUR_CARTE + 20, 0, 150, 60);
		add(b2);
		/* Le bouton Recommencer cree une nouvelle carte */
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carte = new Carte();
				repaint();
			}
		});
		repaint();
		b1.setBounds(IConfig.NB_PIX_CASE * IConfig.LARGEUR_CARTE + 20, 70, 150, 60);
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
				if (e.getX() < IConfig.NB_PIX_CASE * IConfig.LARGEUR_CARTE
						&& e.getY() < IConfig.NB_PIX_CASE * IConfig.HAUTEUR_CARTE) {
					pos.setX((int) Math.floor(e.getX() / IConfig.NB_PIX_CASE));
					pos.setY((int) Math.floor(e.getY() / IConfig.NB_PIX_CASE));
					el = carte.getElement(pos);
					/* Si on a deja selectionne un Heros et qu'on vient de faire un clic droit */
					if (heros_clic && SwingUtilities.isRightMouseButton(e)) {
						/* Si l'element selectionne est un Monstre */
						if(((Heros)eh).getType() == ISoldat.TypesH.MAGICIEN && el instanceof Heros) {
							((Heros)eh).soigner((Soldat)el);
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
							((Heros) eh).setSelect(false);
						}
						if (el instanceof Monstre) {
							/* On le combat et signale que le Heros a joue son tour */
							if (((Soldat) eh).combat((Soldat) el)) {
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
								((Heros) eh).setSelect(false);
								
							}
						}
					}
					/*
					 * Si la case correspond a un heros qui a un tour a jouer on memorise sa
					 * position
					 */
					if (el instanceof Heros) {
						if (((Soldat) el).tour == false) {
							heros_clic = true;
							if(eh instanceof Heros){
								((Heros) eh).setSelect(false);
							}
							((Heros) el).setSelect(true);
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
							/*((Soldat) eh).tour = true;
							if (((Heros) eh).incrementherosj()) {
								carte.jouerMonstres();
								carte.resetTour();
							}*/
						}
						heros_clic = false;
						((Heros) eh).setSelect(false);
					} else if (el instanceof Monstre) {
						monstre_clic = true;
						eh = el;
					} else {
						monstre_clic = false;
						heros_clic = false;
						if(eh instanceof Heros){
							((Heros) eh).setSelect(false);
						}
					}
					repaint();
				}
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
