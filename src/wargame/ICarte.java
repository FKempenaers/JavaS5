package wargame;

import java.awt.Graphics;

public interface ICarte {
	Element getElement(Position pos);

	Position trouvePositionVide(); // Trouve aleatoirement une position vide sur la carte

	Position trouvePositionVide(Position pos); // Trouve une position vide choisie
	// aleatoirement parmi les 8 positions adjacentes de pos

	Heros trouveHeros(); // Trouve aleatoirement un heros sur la carte

	boolean deplaceSoldat(Position pos, Soldat soldat);

	void mort(Soldat perso);

	void toutDessiner(Graphics g);
}