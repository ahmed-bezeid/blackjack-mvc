package blackjack.vue;

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
        
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║ \u001B[34m♦️♣️ BLACKJACK - CONSOLE (Clavier) ♠️♥️\u001B[0m        ║"); 
        System.out.println("╠═══════════════════════════════════════════════════════╣");

        afficherMainCroupier(modele.getMainCroupier(), modele.isCroupierCache());
        System.out.println("╟───────────────────────────────────────────────────────╢");
        afficherMainJoueur(modele.getMainJoueur());
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        
        afficherStatus(modele.getStatus());
        
        if (!modele.estPartieTerminee()) {
            afficherOptions();
        } else {
            System.out.println("\n(Entrez 'N' pour commencer une nouvelle Partie ou Q pour Quitter)");
        }
        System.out.print("» "); 
    }

    private void afficherMainCroupier(Paquet mainCroupier, boolean cache) {
        System.out.print("║ 🏦 CROUPIER : ");
        List<Carte> cartesCroupier = mainCroupier.getCartes();
        
        if (cartesCroupier.isEmpty()) {
            System.out.println("Aucune carte");
            return;
        }

        if (cache) {
            String premiereCarte = cartesCroupier.size() > 0 ? carteToString(cartesCroupier.get(0)) : "";
            System.out.printf("%-40s ║\n", premiereCarte + " [CARTE CACHÉE]");
        } else {
            int total = modele.calculerValeur(mainCroupier);
            String main = cartesToString(cartesCroupier);
            System.out.printf("%-40s ║\n", main + " (Total: " + total + ")");
        }
    }

    private void afficherMainJoueur(Paquet mainJoueur) {
        int total = modele.calculerValeur(mainJoueur);
        List<Carte> cartesJoueur = mainJoueur.getCartes();
        String main = cartesToString(cartesJoueur);
        
        String totalMsg;
        if (total > 21) {
            totalMsg = "💥 BUST (" + total + ")";
        } else if (total == 21 && cartesJoueur.size() == 2) {
             totalMsg = "✨ BLACKJACK!";
        } else {
            totalMsg = "(Total: " + total + ")";
        }
        
        System.out.printf("║ 👤 VOUS     : %-40s ║\n", main + " " + totalMsg);
    }
    
    private void afficherOptions() {
        System.out.println("\n» Choisissez votre action :");
        System.out.println(" [1] - Tirer");
        System.out.println(" [2] - Rester");
        System.out.println(" [Q] - Quitter");
    }
    
    private void afficherStatus(String msg) {
        // Encadrement du message de statut pour le rendre plus visible
        System.out.println("\n--- [ RÈGLE APPLIQUÉE ] ---"); 
        System.out.println("\u001B[33m" + msg + "\u001B[0m"); 
        System.out.println("---------------------------\n");
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