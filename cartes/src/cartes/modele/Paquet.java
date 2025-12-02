package cartes.modele;

import cartes.util.AbstracteModelEcoutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Représente un paquet de cartes, permettant de mélanger, piocher, ajouter et couper des cartes.
 */
public class Paquet extends AbstracteModelEcoutable {
    private final List<Carte> cartes;
    private final Random rand;

    public Paquet() {
        this.cartes = new ArrayList<>();
        this.rand = new Random();
    }

    /**
     * Crée une liste de toutes les cartes possibles (52 cartes).
     * @return une liste de 52 cartes
     */
    public static List<Carte> allCartes() {
        List<Carte> toutCartes = new ArrayList<>();
        String[] valeurs = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "valet", "dame", "roi", "as"};
        String[] couleurs = {"pique", "coeur", "carreau", "trefle"};

        for (String couleur : couleurs) {
            for (String valeur : valeurs) {
                toutCartes.add(new Carte(valeur, couleur));
            }
        }
        return toutCartes;
    }

    /**
     * Mélange les cartes du paquet.
     */
    public void melanger() {
        Collections.shuffle(cartes);
        fireChangement();
    }

    /**
     * Ajoute une liste de cartes au paquet.
     * @param cartes32 la liste de cartes à ajouter
     */
    public void ajouterCartes(List<Carte> cartes32) {
        cartes.addAll(cartes32);
        fireChangement();
    }

    /**
     * Ajoute une carte au paquet.
     * @param c1 la carte à ajouter
     */
    public void ajouter(Carte c1) {
        cartes.add(c1);
        fireChangement();
    }

    /**
     * Retourne une copie de la liste des cartes pour éviter les modifications externes.
     * @return une copie de la liste des cartes
     */
    public List<Carte> getCartes() {
        return new ArrayList<>(cartes);
    }

    /**
     * Ajoute une carte au-dessus du paquet.
     * @param carte la carte à ajouter
     */
    public void ajouterCarteAuDessus(Carte carte) {
        cartes.add(0, carte);
        fireChangement();
    }

    /**
     * Ajoute une carte en dessous du paquet.
     * @param carte la carte à ajouter
     */
    public void ajouterCarteEnDessous(Carte carte) {
        cartes.add(carte);
        fireChangement();
    }

    /**
     * Vide le paquet de toutes ses cartes.
     */
    public void vider() {
        cartes.clear();
        fireChangement();
    }

    /**
     * Pioche une carte aléatoire du paquet.
     * @return la carte piochée, ou null si le paquet est vide
     */
    public Carte piocher() {
        if (cartes.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(cartes.size());
        Carte c = cartes.remove(index);
        fireChangement();
        return c;
    }

    /**
     * Coupe le paquet en deux parties et les inverse.
     */
    public void couperPaquet() {
        if (cartes.size() < 7) return;
        int pos = 3 + rand.nextInt(cartes.size() - 6);
        List<Carte> haut = new ArrayList<>(cartes.subList(0, pos));
        List<Carte> bas = new ArrayList<>(cartes.subList(pos, cartes.size()));
        cartes.clear();
        cartes.addAll(bas);
        cartes.addAll(haut);
        fireChangement();
    }
}
