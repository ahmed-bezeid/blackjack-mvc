/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes;

/**
 *
 * @author aissani231
 */
public class Carte {
    private String valeur;
    private String couleur;

    /**
     * Crée une nouvelle carte avec la valeur et la couleur spécifiées.
     * @param valeur La valeur de la carte (ex : "as", "roi", "10")
     * @param couleur La couleur de la carte (ex : "pique", "cœur")
     */
    public Carte(String valeur, String couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return "Carte{valeur=" + valeur + ", couleur=" + couleur + "}";
    }
}
