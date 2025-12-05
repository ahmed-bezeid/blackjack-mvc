package cartes.controleur;

import cartes.modele.Carte;
import cartes.modele.Paquet;
import cartes.Vue.VuePaquet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControleurPiocheVersPaquet extends MouseAdapter {
    private final VuePaquet vueSource;
    private final Paquet destination;

    public ControleurPiocheVersPaquet(VuePaquet vueSource, Paquet destination) {
        this.vueSource = vueSource;
        this.destination = destination;
        this.vueSource.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!vueSource.paquet.getCartes().isEmpty()) {
            Carte cartePiochee = vueSource.paquet.piocher();
            if (cartePiochee != null) {
                destination.ajouter(cartePiochee);
            }
        }
    }
}
