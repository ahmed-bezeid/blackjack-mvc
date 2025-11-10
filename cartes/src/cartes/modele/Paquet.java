/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes.modele;

import cartes.util.AbstracteModelEcoutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author aissani231
 */
public class Paquet extends AbstracteModelEcoutable{
    private List<Carte> cartes;
    private Random rand = new Random();
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
        fireChangement();
    }
    
    public void ajouterCartes(List<Carte> nouvellesCartes) {
        cartes.addAll(nouvellesCartes);
        fireChangement();
    }

    public List<Carte> getCartes() {
        return cartes;
    }
    
    public void ajouterCarteAuDessus(Carte carte){
        cartes.add(0, carte); // ajoute la carte à l'index 0, donc au début
        fireChangement();
    }

    public void ajouterCarteEnDessous(Carte carte){
        cartes.add(carte);
        fireChangement();
    }

    public Carte tirerCarteAuHasard(){
        if (cartes.isEmpty()) {
            return null;
        } 
        int index = rand.nextInt(cartes.size());// indice aléatoire entre 0 et size-1
        Carte c = cartes.remove(index);// remove retourne la carte enlevée
        fireChangement();
        return c;
    }

    public void couperPaquet() {
        if (cartes.size() < 7) return; // impossible de couper comme demandé

        int pos = 3 + rand.nextInt(cartes.size() - 6); // position entre 3 et size-3 exclus

        List<Carte> haut = new ArrayList<>(cartes.subList(0, pos));
        List<Carte> bas = new ArrayList<>(cartes.subList(pos, cartes.size()));

        cartes.clear();
        cartes.addAll(bas);
        cartes.addAll(haut);
        fireChangement();
    }


}
