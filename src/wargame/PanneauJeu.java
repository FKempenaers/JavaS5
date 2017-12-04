package wargame;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
				if(heros_clic && SwingUtilities.isRightMouseButton(e)) {
					((Soldat)eh).combat((Soldat)el);
					((Soldat)eh).tour = true;
					if(((Heros)eh).incrementherosj()){
						carte.jouerMonstres();
					}
					heros_clic =false;
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
						((Soldat)eh).setmove();
						((Soldat)eh).tour = true;
						if(((Heros)eh).incrementherosj()){
							carte.jouerMonstres();
						}
					}
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
