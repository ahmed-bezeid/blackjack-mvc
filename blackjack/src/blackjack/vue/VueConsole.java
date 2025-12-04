/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.vue;

/**
 *
 * @author mezine221
 */





import blackjack.modele.JeuBlackJack;
import cartes.modele.Carte;
import cartes.modele.Paquet;
import java.util.List;

public class VueConsole implements IVueBlackJack {
    private final JeuBlackJack modele;

    public VueConsole(JeuBlackJack modele) {
        this.modele = modele;
    }
    
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    @Override
    public void onJeuUpdate() {
        clearConsole(); 
        
        System.out.println("==================================================");
        System.out.println("          BLACKJACK - CONSOLE (Clavier)         ");
        System.out.println("==================================================");

        afficherMainCroupier(modele.getMainCroupier(), modele.isCroupierCache());
        System.out.println("--------------------------------------------------");
        afficherMainJoueur(modele.getMainJoueur());
        System.out.println("--------------------------------------------------");
        
        afficherStatus(modele.getStatus());
        
        if (!modele.estPartieTerminee()) {
            afficherOptions();
        } else {
            System.out.println("\n(Entrez 'N' + Entrée pour une Nouvelle Partie)");
        }
        
        // AFFICHAGE DU PROMPT ICI, juste avant que le contrôleur n'attende la saisie
        System.out.print("> "); 
    }

    private void afficherMainCroupier(Paquet mainCroupier, boolean cache) {
        System.out.print("🏦 CROUPIER : ");
        List<Carte> cartesCroupier = mainCroupier.getCartes();
        
        if (cartesCroupier.isEmpty()) {
            System.out.println("Aucune carte");
            return;
        }

        if (cache) {
            if (cartesCroupier.size() > 0) {
                String premiereCarte = carteToString(cartesCroupier.get(0));
                System.out.println(premiereCarte + " [*** CACHÉE ***]");
            } else {
                 System.out.println(" [*** CACHÉE ***]");
            }
        } else {
            int total = modele.calculerValeur(mainCroupier);
            System.out.println(cartesToString(cartesCroupier) + " (Total: " + total + ")");
        }
    }

    private void afficherMainJoueur(Paquet mainJoueur) {
        int total = modele.calculerValeur(mainJoueur);
        List<Carte> cartesJoueur = mainJoueur.getCartes();
        
        System.out.print("👤 VOUS     : " + cartesToString(cartesJoueur));
        
        if (total > 21) {
            System.out.println(" 💥 (Total: " + total + " - BRISÉ)");
        } else if (total == 21 && cartesJoueur.size() == 2) {
             System.out.println(" ✨ (Total: BLACKJACK)");
        } else {
            System.out.println(" (Total: " + total + ")");
        }
    }
    
    private void afficherOptions() {
        System.out.println("\n❓ Que voulez-vous faire ?");
        System.out.println(" [1] - Tirer (Prendre une autre carte)");
        System.out.println(" [2] - Rester (Garder votre main)");
        System.out.println(" [Q] - Quitter le jeu");
        // Ligne de prompt enlevée d'ici pour la mettre à la fin de onJeuUpdate()
    }
    
    private void afficherStatus(String msg) {
        System.out.println("📢 Message : \u001B[33m" + msg + "\u001B[0m"); 
    }
    
    private String cartesToString(List<Carte> cartes) {
        if (cartes == null || cartes.isEmpty()) return "Aucune carte";
        
        StringBuilder sb = new StringBuilder();
        for (Carte c : cartes) {
            sb.append(carteToString(c)).append(" ");
        }
        return sb.toString().trim();
    }
    
    private String carteToString(Carte c) {
        return "[" + c.getValeur().toUpperCase() + " de " + c.getCouleur().toUpperCase() + "]"; 
    }
}