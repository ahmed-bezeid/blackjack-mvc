package cartes.Vue;

import cartes.modele.Carte;
import cartes.modele.Paquet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Vue pour un paquet de cartes visibles, alignées horizontalement.
 * Chaque carte est représentée par un rectangle avec son nom.
 */
public class VuePaquetVisible extends VuePaquet {

    public static final int LARGEUR_CARTE = 40;
    public static final int HAUTEUR_CARTE = 60;
    public static final int ESPACEMENT_CARTES = 5;
    public static final int MARGE_HAUTEUR = 10;
    public static final int MARGE_LARGEUR_INITIALE = 10;
    private Carte carteSurvole;

    public VuePaquetVisible(Paquet paquet) {
        super(paquet);
        setOpaque(false);
    }

    @Override
    protected void afficher() {
        // La mise à jour de la taille est gérée dans modeleMisAJour
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = MARGE_LARGEUR_INITIALE;
        for (Carte carte : paquet.getCartes()) {
            // Dessiner la carte
            g.setColor(Color.GREEN);
            g.fillRect(x, MARGE_HAUTEUR, LARGEUR_CARTE, HAUTEUR_CARTE);
            g.setColor(Color.BLACK);
            g.drawRect(x, MARGE_HAUTEUR, LARGEUR_CARTE, HAUTEUR_CARTE);
            // Afficher le nom de la carte
            String nomCarte = carte.getValeur() + " de " + carte.getCouleur();
            g.drawString(nomCarte, x + 5, MARGE_HAUTEUR + HAUTEUR_CARTE / 2);
            x += LARGEUR_CARTE + ESPACEMENT_CARTES;
        }
    }

    @Override
    public void modeleMisAJour(Object source) {
        if (source == paquet) {
            int largeurTotale = MARGE_LARGEUR_INITIALE * 2 + paquet.getCartes().size() * (LARGEUR_CARTE + ESPACEMENT_CARTES);
            setPreferredSize(new Dimension(largeurTotale, HAUTEUR_CARTE + MARGE_HAUTEUR * 2));
            revalidate();
            repaint();
        }
    }

    public void setCarteSurvole(Carte carte) {
        this.carteSurvole = carte;
        repaint();
    }
}
