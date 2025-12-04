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
    private boolean croupierHasStood;

    public JeuBlackJack() {
        this.pioche = Factory.creerPaquet52(true);
        this.mainJoueur = new Paquet();
        this.mainCroupier = new Paquet();
        this.observer = null;
        this.status = "Bienvenue au Blackjack ! Entrez 'N' pour commencer une nouvelle partie.";
        this.partieTerminee = true;
        this.croupierCache = true;
        this.joueurHasStood = false;
        this.croupierHasStood = false;
    }

    public void nouvellePartie() {
        this.pioche = Factory.creerPaquet52(true);
        mainJoueur.vider();
        mainCroupier.vider();

        partieTerminee = false;
        croupierCache = true;
        joueurHasStood = false;
        croupierHasStood = false;

        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());
        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());

        status = "À vous de jouer. Tirez une carte (1) ou restez (2).";

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
        status = "Vous restez. Au tour du croupier.";

        while (calculerValeur(mainCroupier) < 17) {
            mainCroupier.ajouter(pioche.piocher());
        }

        terminerPartie();
        notifyObserver();
    }

    private void terminerPartie() {
        partieTerminee = true;
        croupierCache = false;
        status = determinerGagnant();
    }

    // NOUVELLE méthode surchargée : Calcule la valeur à partir d'une liste de Cartes
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

    // Méthode principale : Calcule la valeur à partir d'un Paquet
    public int calculerValeur(Paquet main) {
        return calculerValeur(main.getCartes());
    }
    
    public String determinerGagnant() {
        int valeurJoueur = calculerValeur(mainJoueur);
        int valeurCroupier = calculerValeur(mainCroupier);

        if (valeurJoueur > 21) {
            return "😭 Joueur brise (" + valeurJoueur + "). Le croupier gagne.";
        }
        if (valeurCroupier > 21) {
            return "🎉 Croupier brise (" + valeurCroupier + "). Le joueur gagne !";
        }
        if (valeurJoueur == 21 && mainJoueur.getCartes().size() == 2) {
             return "♠️ BLACKJACK ! Le joueur gagne (21) !";
        }
        if (valeurJoueur == valeurCroupier) {
            return "🤝 Égalité / Push (" + valeurJoueur + ").";
        }
        if (valeurJoueur > valeurCroupier) {
            return "🏆 Le joueur gagne (" + valeurJoueur + " contre " + valeurCroupier + ") !";
        } else {
            return "🏦 Le croupier gagne (" + valeurCroupier + " contre " + valeurJoueur + ").";
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