/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes;

/**
 *
 * @author mezine221
 */
public class Carte {
    private String valeur;
    private String couleur;

    public Carte(String valeur, String couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    public String getValeur() { return valeur; }
    public String getCouleur() { return couleur; }

    @Override
    public String toString() {
        return valeur + " de " + couleur;
    }
}

