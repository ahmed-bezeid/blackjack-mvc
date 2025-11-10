/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes;

/**
 *
 * @author mezine221
 */



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Paquet {
    private List<Carte> cartes;

    public Paquet(boolean jeuComplet) {
        cartes = new ArrayList<>();
        if (jeuComplet) {
            String[] valeurs = {"2","3","4","5","6","7","8","9","10","valet","dame","roi","as"};
            String[] couleurs = {"pique","trefle","carreau","coeur"};
            for (String c : couleurs)
                for (String v : valeurs)
                    cartes.add(new Carte(v, c));
            melanger();
        }
    }

    public void ajouter(Carte c) { cartes.add(c); }

    public Carte piocher() {
        if (cartes.isEmpty()) return null;
        return cartes.remove(cartes.size() - 1);
    }

    public void melanger() {
        Collections.shuffle(cartes);
    }

    public List<Carte> getCartes() { return cartes; }

    @Override
    public String toString() {
        return cartes.toString();
    }
}


