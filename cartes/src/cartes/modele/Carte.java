package cartes.modele;

public class Carte {
    private final String valeur;
    private final String couleur;

    public Carte(String valeur, String couleur) {
        if (valeur == null || valeur.trim().isEmpty() ||
            couleur == null || couleur.trim().isEmpty()) {
            throw new IllegalArgumentException("La valeur et la couleur ne peuvent pas être null ou vides.");
        }
        this.valeur = valeur;
        this.couleur = couleur;
    }
    public String getValeur() { return valeur; }
    public String getCouleur() { return couleur; }
}