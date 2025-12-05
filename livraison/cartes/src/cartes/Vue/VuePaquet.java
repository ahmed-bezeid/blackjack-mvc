package cartes.Vue;

import cartes.modele.Paquet;
import cartes.util.EcouteurModele;
import javax.swing.JPanel;

/**
 * Vue abstraite d'un paquet de cartes, se mettant à jour automatiquement
 * lorsque le modèle change.
 */
public abstract class VuePaquet extends JPanel implements EcouteurModele {

    /** Le paquet de cartes à afficher. */
    public final Paquet paquet;

    /**
     * Crée une nouvelle vue pour le paquet donné.
     * @param paquet le paquet de cartes à afficher
     * @throws IllegalArgumentException si le paquet est null
     */
    public VuePaquet(Paquet paquet) {
        if (paquet == null) {
            throw new IllegalArgumentException("Le paquet ne peut pas être null.");
        }
        this.paquet = paquet;
        this.paquet.ajoutEcouteur(this);
    }

    /**
     * Méthode abstraite à implémenter pour afficher le paquet.
     */
    protected abstract void afficher();

    @Override
    public void modeleMisAJour(Object source) {
        if (source == paquet) {
            afficher();
            repaint();
        }
    }
}
