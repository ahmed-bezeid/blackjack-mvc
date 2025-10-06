/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author aissani231
 */
public class Paquet{
    private List<Carte> cartes;
    
    public Paquet(){
        cartes = new ArrayList<>();
    }
    
    public static List<Carte> allCartes(){
        List<Carte> toutCartes = new ArrayList<>();
        String[] valeurs = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "valet", "dame", "roi", "as"};
        String[] couleurs = {"pique", "cœur", "carreau", "trèfle"};
        
        for (String couleur : couleurs) {
            for (String valeur : valeurs) {
                toutCartes.add(new Carte(valeur, couleur));
            }
        }
        return toutCartes;
    }
    
    public void melanger(){
        Collections.shuffle(cartes);
    }
    
    public void ajouterCartes(List<Carte> nouvellesCartes) {
        cartes.addAll(nouvellesCartes);
    }

    public List<Carte> getCartes() {
        return cartes;
    }
}