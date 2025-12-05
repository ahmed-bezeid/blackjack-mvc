package cartes.controleur;

import cartes.modele.Carte;
import cartes.modele.Paquet;
import cartes.Vue.VuePaquetVisible;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ControleurChoixCarteVersPaquet extends MouseAdapter {
    private final VuePaquetVisible vueSource;
    private final Paquet destination;

    public ControleurChoixCarteVersPaquet(VuePaquetVisible vueSource, Paquet destination) {
        this.vueSource = vueSource;
        this.destination = destination;
        this.vueSource.addMouseListener(this);
        this.vueSource.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int carteIndex = (x - VuePaquetVisible.MARGE_LARGEUR_INITIALE) /
                                 (VuePaquetVisible.LARGEUR_CARTE + VuePaquetVisible.ESPACEMENT_CARTES);
                if (carteIndex >= 0 && carteIndex < vueSource.paquet.getCartes().size()) {
                    vueSource.setCarteSurvole(vueSource.paquet.getCartes().get(carteIndex));
                } else {
                    vueSource.setCarteSurvole(null);
                }
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int carteIndex = (x - VuePaquetVisible.MARGE_LARGEUR_INITIALE) /
                         (VuePaquetVisible.LARGEUR_CARTE + VuePaquetVisible.ESPACEMENT_CARTES);
        if (carteIndex >= 0 && carteIndex < vueSource.paquet.getCartes().size()) {
            Carte carteSelectionnee = vueSource.paquet.getCartes().get(carteIndex);
            vueSource.paquet.getCartes().remove(carteIndex);
            destination.ajouter(carteSelectionnee);
            vueSource.setCarteSurvole(null);
        }
    }
}
