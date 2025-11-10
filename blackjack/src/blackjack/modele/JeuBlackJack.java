/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.modele;

/**
 *
 * @author mezine221
 */





import cartes.Carte;
import cartes.Paquet;

public class JeuBlackJack {
    private Paquet pioche;
    private Paquet mainJoueur;
    private Paquet mainCroupier;

    public JeuBlackJack() {
        pioche = new Paquet(true);   // true = jeu complet 52 cartes
        mainJoueur = new Paquet(false);
        mainCroupier = new Paquet(false);
    }

    public void distribuer() {
        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());
        mainJoueur.ajouter(pioche.piocher());
        mainCroupier.ajouter(pioche.piocher());
    }

    public int calculerValeur(Paquet main) {
        int total = 0, nbAs = 0;
        for (Carte c : main.getCartes()) {
            String v = c.getValeur();
            if ("as".equalsIgnoreCase(v)) { total += 11; nbAs++; }
            else if ("roi".equalsIgnoreCase(v) || "dame".equalsIgnoreCase(v) || "valet".equalsIgnoreCase(v))
                total += 10;
            else total += Integer.parseInt(v);
        }
        while (total > 21 && nbAs > 0) { total -= 10; nbAs--; }
        return total;
    }

    public Paquet getMainJoueur() { return mainJoueur; }
    public Paquet getMainCroupier() { return mainCroupier; }
    public Paquet getPioche() { return pioche; }

    public boolean joueurABrisque() { return calculerValeur(mainJoueur) > 21; }
    public boolean croupierDoitTirer() { return calculerValeur(mainCroupier) < 17; }
}


