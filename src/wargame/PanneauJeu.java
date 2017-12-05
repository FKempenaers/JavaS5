package wargame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import wargame.Obstacle.TypeObstacle;

public class PanneauJeu extends JPanel {
	private Element el,eh;
	private Position pos,posh;
	private Carte carte;
	private boolean heros_clic,monstre_clic;
	JFrame frame = new JFrame();
	private JButton b1 = new JButton("Fin de tour");
	private JButton b2 = new JButton("Recommencer");
	//private JPanel s1 = new JPanel();
	
	public PanneauJeu() {
		super(new BorderLayout());
		heros_clic = false;
		monstre_clic = false;
		pos = new Position(0,0);
		posh = new Position(0,0);
		carte = new Carte();
		
		b2.setPreferredSize(new Dimension(150,60));
		add(b2,BorderLayout.EAST);
		b2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				carte = new Carte();
				repaint();
			}
		});
		repaint();
		
		b1.setPreferredSize(new Dimension(40,60));
		add(b1,BorderLayout.SOUTH);
		b1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				carte.findetourrepos();
				carte.jouerMonstres();
				carte.resetTour();
				repaint();
			}
		});
		
		
		
		addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent	e){
				pos.setX((int)Math.floor(e.getX()/IConfig.NB_PIX_CASE));
				pos.setY((int)Math.floor(e.getY()/IConfig.NB_PIX_CASE));
				el = carte.getElement(pos); 
				if(heros_clic && SwingUtilities.isRightMouseButton(e)) {
					if(el instanceof Monstre) {
						((Soldat)eh).combat((Soldat)el);
						((Soldat)eh).tour = true;
						if(((Heros)eh).incrementherosj()){
							carte.jouerMonstres();
							carte.resetTour();
						}
						heros_clic =false;
					}
				}
				if(el instanceof Heros) {
					if(((Soldat)el).tour == false) {
						heros_clic = true;
						posh.setX(pos.getX());
						posh.setY(pos.getY());
						eh = el;
					}
				}
				else if(heros_clic && ((Soldat)eh).getmove()){
					if(carte.deplaceSoldat(pos, (Soldat)eh)) {
						((Soldat)eh).setmove(false);
						((Soldat)eh).tour = true;
						if(((Heros)eh).incrementherosj()){
							carte.jouerMonstres();
							carte.resetTour();
						}
					}
					heros_clic = false;
				}
				else if(el instanceof Monstre) {
					monstre_clic = true;
					eh = el;
				}
				else {
					monstre_clic = false;
					heros_clic = false;
				}
				repaint();
			}
		});
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!carte.getfinjeu())
		{
			carte.toutDessiner(g);
			if(heros_clic || monstre_clic) {
				((Soldat)eh).afficheinfo(g);
			}
		}
		else {
			if(Monstre.getnbMonstres() == 0){
				g.drawString("Vous avez gagné!!!!!!!", 300, 300);
			}
			else {
				g.drawString("Vous avez perdu", 300, 300);
			}
			}
		}
		
}
