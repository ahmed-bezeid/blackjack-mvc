package blackjack.controleur;

import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueSwing;
import java.awt.event.MouseAdapter; // Import essentiel
import java.awt.event.MouseEvent; // Import essentiel

public class Controleur {
    private final JeuBlackJack modele;
    private final VueSwing vue;

    public Controleur(JeuBlackJack modele, VueSwing vue) {
        this.modele = modele;
        this.vue = vue;
        
        // 1. Connexion des boutons
        vue.getResterBtn().addActionListener(e -> modele.joueurReste());
        vue.getNouvellePartieBtn().addActionListener(e -> modele.nouvellePartie());
        vue.getQuitterBtn().addActionListener(e -> System.exit(0)); 
        
        // Le bouton Tirer n'est plus visible (sa fonction est reprise par la pioche)
        vue.getTirerBtn().setVisible(false);

        // 🟢 FIX : Attacher l'écouteur directement au panneau Pioche 
        // (getPanelPioche doit exister dans VueSwing)
        vue.getPanelPioche().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // S'assurer que le jeu est en cours avant de tirer
                if (!modele.estPartieTerminee()) {
                    modele.joueurTireCarte();
                }
            }
        });

        // Démarrage automatique de la partie
        modele.nouvellePartie();
    }
}