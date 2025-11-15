package cartes.modele;

/**
 * Représente une carte à jouer avec une valeur et une couleur.
 */
public class Carte {
    private final String valeur;
    private final String couleur;

    /**
     * Crée une nouvelle carte avec la valeur et la couleur spécifiées.
     *
     * @param valeur  La valeur de la carte (ex : "as", "roi", "10")
     * @param couleur La couleur de la carte (ex : "pique", "cœur")
     * @throws IllegalArgumentException si la valeur ou la couleur est null ou vide
     */
    public Carte(String valeur, String couleur) {
        if (valeur == null || valeur.trim().isEmpty() ||
            couleur == null || couleur.trim().isEmpty()) {
            throw new IllegalArgumentException("La valeur et la couleur ne peuvent pas être null ou vides.");
        }
        this.valeur = valeur;
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return valeur + " de " + couleur;
    }

    public String getValeur() {
        return valeur;
    }

    public String getCouleur() {
        return couleur;
    }
}
