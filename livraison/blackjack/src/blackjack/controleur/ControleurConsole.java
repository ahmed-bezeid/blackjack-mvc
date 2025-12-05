/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.controleur;

/**
 *
 * @author mezine221
 */



import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueConsole;
import java.util.Scanner;

public class ControleurConsole {
    private final JeuBlackJack modele;
    private final VueConsole vue;
    private final Scanner scanner;

    public ControleurConsole(JeuBlackJack modele, VueConsole vue) {
        this.modele = modele;
        this.vue = vue;
        this.scanner = new Scanner(System.in);
        
        modele.nouvellePartie(); 
        lancerBoucleDeJeu();
    }

    private void lancerBoucleDeJeu() {
        String input;
        
        while (true) {
            
            try {
                input = scanner.nextLine().trim().toLowerCase(); 
            } catch (java.util.NoSuchElementException e) {
                break;
            }
            
            // NOUVEAU : Gérer la commande Quitter (Q)
            if (input.equals("q") || input.equals("quitter")) {
                System.out.println("Fermeture de l'application console...");
                System.exit(0); // Arrête le programme
            }

            if (modele.estPartieTerminee()) {
                if (input.equals("n") || input.equals("nouvelle partie")) {
                    modele.nouvellePartie();
                } else {
                    modele.status = "Partie terminée. cliquer sur 'Nouvellepartie' pour commencer une nouvelle partie, ou 'Q' pour quitter.";
                    vue.onJeuUpdate(); 
                }
                continue;
            }
            
            switch (input) {
                case "1": 
                case "t":
                case "tirer":
                    modele.joueurTireCarte();
                    break;
                case "2": 
                case "r":
                case "rester":
                    modele.joueurReste();
                    break;
                default:
                    modele.status = "⚠️ Commande invalide. Entrez '1' (Tirer) ou '2' (Rester).";
                    vue.onJeuUpdate();
                    break;
            }
        }
    }
}