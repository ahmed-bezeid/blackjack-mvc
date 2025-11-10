/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes.modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aissani231@CAMPUS
 */
public class Factory {
    public static Paquet creerPaquet52() {
        Paquet paquet = new Paquet();
        List<Carte> cartes52 = new ArrayList<>();
        String[] valeurs = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "valet", "dame", "roi", "as"};
        String[] couleurs = {"pique", "cœur", "carreau", "trèfle"};

        for (String couleur : couleurs) {
            for (String valeur : valeurs) {
                cartes52.add(new Carte(valeur, couleur));
            }
        }
        paquet.ajouterCartes(cartes52);
        return paquet;
    }

    public static Paquet creerPaquet32() {
        Paquet paquet = new Paquet();
        List<Carte> cartes32 = new ArrayList<>();
        String[] valeurs = {"7", "8", "9", "10", "valet", "dame", "roi", "as"};
        String[] couleurs = {"pique", "cœur", "carreau", "trèfle"};

        for (String couleur : couleurs) {
            for (String valeur : valeurs) {
                cartes32.add(new Carte(valeur, couleur));
            }
        }
        paquet.ajouterCartes(cartes32);
        return paquet;
    }
}
