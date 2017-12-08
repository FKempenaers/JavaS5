package wargame;

import java.awt.Color;

public interface IConfig {
	int LARGEUR_CARTE = 25;
	int HAUTEUR_CARTE = 15; // en nombre de cases
	int NB_PIX_CASE = 40;
	int POSITION_X = 100;
	int POSITION_Y = 50; // Position de la fenetre
	int NB_HEROS = 6;
	int NB_MONSTRES = 15;
	int NB_OBSTACLES = 20;
	Color COULEUR_VIDE = Color.white, COULEUR_BROUILLARD = Color.lightGray;
	Color COULEUR_TEXTE = Color.black, COULEUR_MONSTRES = Color.black;
	Color COULEUR_HEROS = Color.red, COULEUR_HEROS_DEJA_JOUE = Color.pink,COULEUR_HEROS_DEJA_BOUGE = Color.magenta,COULEUR_HEROS_SELECTIONNE = Color.orange;
	Color COULEUR_EAU = Color.blue, COULEUR_FORET = Color.green, COULEUR_ROCHER = Color.darkGray;
}