package cartes;

import cartes.modele.Factory;
import cartes.modele.Paquet;
import cartes.Vue.VuePaquetVisible;
import cartes.Vue.VuePaquetCache;
import cartes.controleur.ControleurChoixCarteVersPaquet;
import cartes.controleur.ControleurPiocheVersPaquet;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class FenetreTestCartes extends JFrame {
    private static Paquet pioche = new Paquet();
    private final Paquet main;
    private final Paquet defausse;

    public FenetreTestCartes() {
        // Initialisation des paquets
        pioche = Factory.creerPaquet52(true);
        main = new Paquet();
        defausse = new Paquet();

        // Configuration de la fenêtre principale
        setTitle("Test - Paquets de Cartes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new GridLayout(1, 3));

        // Création des vues
        VuePaquetCache vuePiocheCachee = new VuePaquetCache(pioche);
        VuePaquetVisible vueMain = new VuePaquetVisible(main);
        VuePaquetVisible vueDefausse = new VuePaquetVisible(defausse);

        // Ajout des vues à la fenêtre
        add(vuePiocheCachee);
        add(vueMain);
        add(vueDefausse);

        // Configuration des contrôleurs
        new ControleurPiocheVersPaquet(vuePiocheCachee, main); // Pioche → Main
        new ControleurChoixCarteVersPaquet(vueMain, defausse); // Main → Défausse
        new ControleurPiocheVersPaquet(vueDefausse, pioche); // Défausse → Pioche

        // Affichage de la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Fenêtre principale
        new FenetreTestCartes();

        // Deuxième fenêtre pour afficher la pioche en mode visible
        JFrame fenetrePiocheVisible = new JFrame("Pioche (Visible)");
        fenetrePiocheVisible.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetrePiocheVisible.setSize(400, 200);
        VuePaquetVisible vuePiocheVisible = new VuePaquetVisible(pioche);
        fenetrePiocheVisible.add(vuePiocheVisible);
        fenetrePiocheVisible.setVisible(true);
    }
}
