package cartes.modele;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    private static List<Carte> genererCartes(String[] valeurs) {
        List<Carte> cartes = new ArrayList<>();
        String[] couleurs = {"pique", "coeur", "carreau", "trefle"};
        
        for (String couleur : couleurs) {
            for (String valeur : valeurs) {
                cartes.add(new Carte(valeur, couleur));
            }
        }
        return cartes;
    }

    public static Paquet creerPaquet52(boolean melanger) {
        Paquet paquet = new Paquet();
        String[] VALEURS_52 = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "valet", "dame", "roi", "as"};
        
        paquet.ajouterCartes(genererCartes(VALEURS_52));
        if (melanger) paquet.melanger();
        return paquet;
    }
}