package cartes.modele;

import cartes.util.AbstracteModelEcoutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Paquet extends AbstracteModelEcoutable {
    private final List<Carte> cartes;
    private final Random rand;

    public Paquet() {
        this.cartes = new ArrayList<>();
        this.rand = new Random();
    }

    public void melanger() {
        Collections.shuffle(cartes);
        fireChangement();
    }

    public void ajouterCartes(List<Carte> cartes32) {
        cartes.addAll(cartes32);
        fireChangement();
    }

    public void ajouter(Carte c1) {
        cartes.add(c1);
        fireChangement();
    }

    public List<Carte> getCartes() {
        return new ArrayList<>(cartes);
    }

    public void vider() {
        cartes.clear();
        fireChangement();
    }

    public Carte piocher() {
        if (cartes.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(cartes.size());
        Carte c = cartes.remove(index);
        fireChangement();
        return c;
    }
}