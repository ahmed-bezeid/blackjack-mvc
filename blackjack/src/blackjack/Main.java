package blackjack;

import blackjack.controleur.Controleur;
import blackjack.controleur.ControleurConsole;
import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueConsole;
import blackjack.vue.VueSwing;
import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        // --- ÉTAPE 1 : DEMANDER LE MODE D'ENTRÉE ---
        Scanner scanner = new Scanner(System.in);
        String mode = "";
        
        while (!mode.equals("1") && !mode.equals("2")) {
            System.out.println("=========================================");
            System.out.println("  Sélectionnez le mode d'interface : ");
            System.out.println(" [1] - Mode Graphique (Souris/Swing)");
            System.out.println(" [2] - Mode Console (Clavier)");
            System.out.print("Entrez votre choix (1 ou 2) : ");
            
            if (scanner.hasNextLine()) {
                mode = scanner.nextLine().trim();
            }
        }
        
         
        
        // 2. Création du Modèle
        JeuBlackJack modele = new JeuBlackJack();

        // --- ÉTAPE 2 : LANCER LA VUE/CONTRÔLEUR CORRESPONDANT ---
        
        if (mode.equals("1")) {
            // MODE SOURIS / GRAPHIQUE (SWING)
            System.out.println("\nLancement du mode Graphique (Souris)...");
            
            SwingUtilities.invokeLater(() -> {
                VueSwing vueSwing = new VueSwing(modele);
                modele.setObserver(vueSwing);
                // Le contrôleur SWING prend la responsabilité de lancer la partie
                new Controleur(modele, vueSwing); 
                vueSwing.onJeuUpdate(); 
            });

        } else if (mode.equals("2")) {
            // MODE CLAVIER / CONSOLE
            System.out.println("\nLancement du mode Console (Clavier)...");
            
            VueConsole vueConsole = new VueConsole(modele); 
            modele.setObserver(vueConsole);
            // Le contrôleur CONSOLE prend la responsabilité de lancer la partie
            new ControleurConsole(modele, vueConsole); 
        }
    }
}