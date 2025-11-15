package blackjack.modele;

import cartes.modele.Carte;
import cartes.modele.Factory;
import cartes.modele.Paquet;
import blackjack.vue.VueSwing;

public class JeuBlackJack {
    private Paquet pioche;
    private final Paquet mainJoueur;
    private final Paquet mainCroupier;
    private VueSwing observer;
    private boolean partieTerminee;
    private boolean croupierCache;

    public JeuBlackJack(VueSwing observer) {
        this.pioche = Factory.creerPaquet52(true);
        this.mainJoueur = new Paquet();
        this.mainCroupier = new Paquet();
        this.observer = observer;
        this.partieTerminee = false;
        this.croupierCache = true;
        nouvellePartie(); // Appelle notifyObserver() si observer n'est pas null
    }


    public void nouvellePartie() {
        mainJoueur.getCartes().clear();
        mainCroupier.getCartes().clear();
        if (pioche.getCartes().size() < 10) {
            pioche = Factory.creerPaquet52(true);
        }
        distribuer();
        partieTerminee = false;
        notifyObserver();
    }

    public void distribuer() {
        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());
        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());
        croupierCache = true;
        notifyObserver();
    }

    public void joueurTireCarte() {
        if (!partieTerminee) {
            mainJoueur.ajouter(pioche.piocher());
            if (joueurABrisque()) {
                partieTerminee = true;
                observer.afficherMessage("Le joueur a brisé !");
            }
            notifyObserver();
        }
    }

    public void joueurReste() {
        if (!partieTerminee) {
            partieTerminee = true;
            croupierCache = false;
            while (croupierDoitTirer()) {
                mainCroupier.ajouter(pioche.piocher());
            }
            observer.afficherMessage(determinerGagnant());
            notifyObserver();
        }
    }

    public int calculerValeur(Paquet main) {
        int total = 0;
        int nbAs = 0;
        for (Carte c : main.getCartes()) {
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

    public boolean joueurABrisque() {
        return calculerValeur(mainJoueur) > 21;
    }

    public boolean croupierDoitTirer() {
        return calculerValeur(mainCroupier) < 17;
    }

    public String determinerGagnant() {
        int valeurJoueur = calculerValeur(mainJoueur);
        int valeurCroupier = calculerValeur(mainCroupier);
        if (valeurJoueur > 21) return "Le croupier gagne (joueur a brisé) !";
        if (valeurCroupier > 21) return "Le joueur gagne (croupier a brisé) !";
        if (valeurJoueur > valeurCroupier) return "Le joueur gagne !";
        if (valeurJoueur < valeurCroupier) return "Le croupier gagne !";
        return "Égalité !";
    }

    public boolean estPartieTerminee() {
        return partieTerminee;
    }

    public boolean isCroupierCache() {
        return croupierCache;
    }

    public Paquet getMainJoueur() {
        return mainJoueur;
    }

    public Paquet getMainCroupier() {
        return mainCroupier;
    }

    public Paquet getPioche() {
        return pioche;
    }

    public int getValeurMainJoueur() {
        return calculerValeur(mainJoueur);
    }

    public int getValeurMainCroupier() {
        return calculerValeur(mainCroupier);
    }

    private void notifyObserver() {
        if (observer != null) {
            observer.onJeuUpdate();
        }
    }


    public void setObserver(VueSwing observer) {
        this.observer = observer;
        notifyObserver(); // Met à jour la vue immédiatement
    }
}
