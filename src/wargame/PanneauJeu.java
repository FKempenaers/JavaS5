package wargame;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JPanel;

import wargame.Obstacle.TypeObstacle;

public class PanneauJeu extends JPanel {
	private Element el,eh;
	private Position pos,posh;
	public Carte carte;
	private boolean heros_clic;

	public PanneauJeu() {
		heros_clic = false;
		pos = new Position(0,0);
		posh = new Position(0,0);
		carte = new Carte();
		addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent	e){
				pos.setX((int)Math.floor(e.getX()/IConfig.NB_PIX_CASE));
				pos.setY((int)Math.floor(e.getY()/IConfig.NB_PIX_CASE));
				el = carte.getElement(pos); 
				if(el instanceof Heros) {
					heros_clic = true;
					posh.setX(pos.getX());
					posh.setY(pos.getY());
					eh = el;
				}
				else if(heros_clic){
					carte.setElement(null, posh.getX(), posh.getY());
					carte.setElement(eh, pos.getX(), pos.getY());
					heros_clic = false;
				}
				repaint();
			}
		});
	}
	public void paintComponent(Graphics g) {
		carte.toutDessiner(g);

	}
}
