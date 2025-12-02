package blackjack;

import blackjack.controleur.Controleur;
import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueSwing;

public class Main {
    public static void main(String[] args) {
        // 1. Création du modèle
        JeuBlackJack modele = new JeuBlackJack();

        // 2. Création de la vue avec le modèle
        VueSwing vue = new VueSwing(modele);

        // 3. Mise à jour du modèle avec la vue comme observateur
        modele.setObserver(vue);

        // 4. Création du contrôleur
        new Controleur(modele, vue);

        // 5. Mise a jour de la vue
        vue.onJeuUpdate();
    }
}
