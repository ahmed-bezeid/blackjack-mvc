package blackjack.controleur;

import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueSwing;

public class Controleur {
    private final JeuBlackJack modele;
    private final VueSwing vue;

    public Controleur(JeuBlackJack modele, VueSwing vue) {
        this.modele = modele;
        this.vue = vue;
        vue.getTirerBtn().addActionListener(e -> modele.joueurTireCarte());
        vue.getResterBtn().addActionListener(e -> modele.joueurReste());
        vue.getNouvellePartieBtn().addActionListener(e -> modele.nouvellePartie());
    }
}
