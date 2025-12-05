package cartes.Vue;

import cartes.modele.Paquet;
import java.awt.*;

public class VuePaquetCache extends VuePaquet {
    private static final int LARGEUR_PAQUET = 40;
    private static final int MARGE_LATERALE = 10;
    private static final int MARGE_SUPERIEURE = 10;
    private static final int HAUTEUR_PAR_CARTE = 5;

    public VuePaquetCache(Paquet paquet) {
        super(paquet);
        setOpaque(false);
        mettreAJourDimension();
    }

    private void mettreAJourDimension() {
        int hauteur = paquet.getCartes().size() * HAUTEUR_PAR_CARTE;
        setPreferredSize(new Dimension(LARGEUR_PAQUET + 2 * MARGE_LATERALE, hauteur + 2 * MARGE_SUPERIEURE));
    }

    @Override
    protected void afficher() {
        // Pas besoin d'implémentation ici, car la mise à jour est gérée par paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int hauteur = paquet.getCartes().size() * HAUTEUR_PAR_CARTE;
        g.setColor(Color.BLUE);
        g.fillRect(MARGE_LATERALE, MARGE_SUPERIEURE, LARGEUR_PAQUET, hauteur);
        g.setColor(Color.BLACK);
        g.drawRect(MARGE_LATERALE, MARGE_SUPERIEURE, LARGEUR_PAQUET, hauteur);
    }

    @Override
    public void modeleMisAJour(Object source) {
        if (source == paquet) {
            mettreAJourDimension();
            revalidate();
            repaint();
        }
    }
}
