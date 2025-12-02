package cartes.modele;

import java.util.ArrayList;

import java.util.List;

/**
 * Fabrique de paquets de cartes, permettant de créer des paquets standard (52 ou 32 cartes).
 * Les paquets peuvent être mélangés dès leur création si nécessaire.
 */
public class Factory {

    private static final String[] COULEURS = {"pique", "coeur", "carreau", "trefle"};
    private static final String[] VALEURS_52 = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "valet", "dame", "roi", "as"};
    private static final String[] VALEURS_32 = {"7", "8", "9", "10", "valet", "dame", "roi", "as"};

    /**
     * Crée un paquet de 52 cartes.
     * @param melanger si vrai, le paquet est mélangé avant d'être retourné
     * @return un paquet contenant toutes les cartes standard
     */
    public static Paquet creerPaquet52(boolean melanger) {
    Paquet paquet = new Paquet();
    List<Carte> cartes = genererCartes(new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "valet", "dame", "roi", "as"});
    System.out.println("Cartes générées : " + cartes.size()); // Devrait afficher 52
    paquet.ajouterCartes(cartes);
    if (melanger) paquet.melanger();
    return paquet;
}


    /**
     * Crée un paquet de 32 cartes (valeurs supérieures ou égales à 7).
     * @param melanger si vrai, le paquet est mélangé avant d'être retourné
     * @return un paquet contenant 32 cartes
     */
    public static Paquet creerPaquet32(boolean melanger) {
        Paquet paquet = new Paquet();
        paquet.ajouterCartes(genererCartes(VALEURS_32));
        if (melanger) paquet.melanger();
        return paquet;
    }

    /**
     * Génère une liste de cartes à partir des valeurs et couleurs données.
     * @param valeurs les valeurs des cartes à générer
     * @return la liste des cartes générées
     */
    private static List<Carte> genererCartes(String[] valeurs) {
    List<Carte> cartes = new ArrayList<>();
    String[] couleurs = {"pique", "coeur", "carreau", "trefle"};
    for (String couleur : couleurs) {
        for (String valeur : valeurs) {
            cartes.add(new Carte(valeur, couleur));
        }
    }
    System.out.println("Nombre de cartes générées : " + cartes.size()); // Devrait afficher 52
    return cartes;
}

}
