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

    public static final int LARGEUR_CARTE = 60;
    public static final int HAUTEUR_CARTE = 90;
    public static final int ESPACEMENT_CARTES = 5;
    public static final int MARGE_HAUTEUR = 10;
    public static final int MARGE_LARGEUR_INITIALE = 10;
    private Carte carteSurvole;
    private boolean cacherPremiereCarte = false;

    public void setCacherPremiereCarte(boolean cacher) {
        this.cacherPremiereCarte = cacher;
        repaint();
    }

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
        int y = MARGE_HAUTEUR;
        int panelWidth = getWidth();

        for (Carte carte : paquet.getCartes()) {
            if (panelWidth > 0 && x + LARGEUR_CARTE + ESPACEMENT_CARTES > panelWidth && x > MARGE_LARGEUR_INITIALE) {
                x = MARGE_LARGEUR_INITIALE;
                y += HAUTEUR_CARTE + MARGE_HAUTEUR;
            }

            // Dessiner la carte
            g.setColor(Color.WHITE);
            g.fillRect(x, y, LARGEUR_CARTE, HAUTEUR_CARTE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, LARGEUR_CARTE, HAUTEUR_CARTE);

            // Afficher la valeur et le nom de la couleur
            String valeur = carte.getValeur();
            String couleur = carte.getCouleur();

            // Draw the value in black
            g.setColor(Color.BLACK);
            g.drawString(valeur, x + 10, y + 30);

            // Set color for the suit name
            if (couleur.equals("coeur") || couleur.equals("carreau")) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(couleur, x + 10, y + 60);

            g.setColor(Color.BLACK); // Reset color for the border of the next card

            x += LARGEUR_CARTE + ESPACEMENT_CARTES;
        }
    }

    @Override
    public void modeleMisAJour(Object source) {
        if (source == paquet) {
            getParent().revalidate();
            getParent().repaint();
            int componentWidth = getWidth();
            if (componentWidth == 0) {
                revalidate();
                repaint();
                return;
            }

            int numCards = paquet.getCartes().size();
            if (numCards == 0) {
                if (getPreferredSize().height != HAUTEUR_CARTE + MARGE_HAUTEUR * 2 || getPreferredSize().width != componentWidth) {
                    setPreferredSize(new Dimension(componentWidth, HAUTEUR_CARTE + MARGE_HAUTEUR * 2));
                }
            } else {
                int cardsPerRow = Math.max(1, (componentWidth - MARGE_LARGEUR_INITIALE * 2 + ESPACEMENT_CARTES) / (LARGEUR_CARTE + ESPACEMENT_CARTES));
                int numRows = (int) Math.ceil((double) numCards / cardsPerRow);
                int preferredHeight = numRows * (HAUTEUR_CARTE + MARGE_HAUTEUR) + MARGE_HAUTEUR;
                if (getPreferredSize().height != preferredHeight || getPreferredSize().width != componentWidth) {
                     setPreferredSize(new Dimension(componentWidth, preferredHeight));
                }
            }

            revalidate();
            repaint();
        }
    }

    public void setCarteSurvole(Carte carte) {
        this.carteSurvole = carte;
        repaint();
    }
}
