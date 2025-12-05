package blackjack.modele;

import cartes.modele.Carte;
import cartes.modele.Factory;
import cartes.modele.Paquet;
import blackjack.vue.IVueBlackJack;
import java.util.List;

public class JeuBlackJack {
    private Paquet pioche;
    private final Paquet mainJoueur;
    private final Paquet mainCroupier;
    private IVueBlackJack observer;
    private boolean partieTerminee;
    private boolean croupierCache;
    public String status; 
    private boolean joueurHasStood;

    public JeuBlackJack() {
        this.pioche = Factory.creerPaquet52(true);
        this.mainJoueur = new Paquet();
        this.mainCroupier = new Paquet();
        this.observer = null;
        this.status = "Bienvenue au Blackjack ! Cliquez sur 'Nouvelle Partie' pour commencer.";
        this.partieTerminee = true;
        this.croupierCache = true;
        this.joueurHasStood = false;
    }

    public void nouvellePartie() {
        this.pioche = Factory.creerPaquet52(true);
        mainJoueur.vider();
        mainCroupier.vider();

        partieTerminee = false;
        croupierCache = true;
        joueurHasStood = false;

        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());
        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());

        status = "C'est votre tour. Vous pouvez Tirer ou Rester.";

        if (calculerValeur(mainJoueur) == 21) {
            joueurReste();
        }
        
        notifyObserver();
    }

    public void joueurTireCarte() {
        if (partieTerminee || joueurHasStood) return;

        mainJoueur.ajouter(pioche.piocher());
        status = "Vous avez tiré une carte.";
        
        if (calculerValeur(mainJoueur) > 21) {
            status = "❌ Vous avez dépassé 21 ! C'est perdu (BUST).";
            terminerPartie();
        }
        notifyObserver();
    }

    public void joueurReste() {
        if (partieTerminee || joueurHasStood) return;

        joueurHasStood = true;
        croupierCache = false; 
        
        // --- RÈGLE DU CROUPIER EN ÉTAPES VISIBLES ---
        status = "Vous restez. Le Croupier révèle sa carte cachée.";
        notifyObserver(); // 1. Affichage de la révélation

        // Petite pause pour la visibilité
        try { Thread.sleep(800); } catch (InterruptedException e) {}

        int valeurActuelle = calculerValeur(mainCroupier);
        
        // Boucle de tirage (Règle: Tirer sur 16 ou moins)
        while (valeurActuelle < 17) {
            
            status = "Le Croupier tire une carte car son total est inférieur à 17 (" + valeurActuelle + ").";
            mainCroupier.ajouter(pioche.piocher());
            valeurActuelle = calculerValeur(mainCroupier);
            notifyObserver(); // 2. Affichage de chaque carte tirée

            // Pause pour l'observation
            try { Thread.sleep(800); } catch (InterruptedException e) {}
        }
        
        // Fin du tour du Croupier
        status = "Le Croupier s'arrête à " + valeurActuelle + ". La manche est résolue.";

        terminerPartie();
        notifyObserver();
    }

    private void terminerPartie() {
        partieTerminee = true;
        croupierCache = false;
        status = determinerGagnant();
    }

    public int calculerValeur(List<Carte> cartes) {
        int total = 0;
        int nbAs = 0;
        for (Carte c : cartes) {
            String v = c.getValeur();
            switch (v.toLowerCase()) {
                case "as":
                    total += 11;
                    nbAs++;
                    break;
                case "roi":
                case "dame":
                case "valet":
                    total += 10;
                    break;
                default:
                    try {
                        total += Integer.parseInt(v);
                    } catch (NumberFormatException e) {
                        System.err.println("Valeur de carte invalide : " + v);
                    }
            }
        }
        while (total > 21 && nbAs > 0) {
            total -= 10;
            nbAs--;
        }
        return total;
    }

    public int calculerValeur(Paquet main) {
        return calculerValeur(main.getCartes());
    }

    public String determinerGagnant() {
        int valeurJoueur = calculerValeur(mainJoueur);
        int valeurCroupier = calculerValeur(mainCroupier);

        if (valeurJoueur > 21) {
            return "DÉFAITE : Vous avez brisé (total de " + valeurJoueur + "). Le Croupier gagne.";
        }
        if (valeurCroupier > 21) {
            return "VICTOIRE ! Le Croupier a brisé (total de " + valeurCroupier + ").";
        }
        
        if (valeurJoueur == 21 && mainJoueur.getCartes().size() == 2) {
             return "BLACKJACK ! Vous gagnez avec un 21 naturel !";
        }

        if (valeurJoueur == valeurCroupier) {
            return "ÉGALITÉ (Push) : Vous et le Croupier avez le même score (" + valeurJoueur + ").";
        }
        
        if (valeurJoueur > valeurCroupier) {
            return "VICTOIRE ! Votre score de " + valeurJoueur + " bat le Croupier (" + valeurCroupier + ").";
        } else {
            return "DÉFAITE : Le Croupier gagne avec " + valeurCroupier + " contre votre score de " + valeurJoueur + ".";
        }
    }

    public boolean estPartieTerminee() { return partieTerminee; }
    public boolean isCroupierCache() { return croupierCache; }
    public Paquet getMainJoueur() { return mainJoueur; }
    public Paquet getMainCroupier() { return mainCroupier; }
    public Paquet getPioche() { return pioche; }
    public String getStatus() { return status; } 

    private void notifyObserver() {
        if (observer != null) {
            observer.onJeuUpdate();
        }
    }

    public void setObserver(IVueBlackJack observer) { 
        this.observer = observer;
        notifyObserver();
    }
}