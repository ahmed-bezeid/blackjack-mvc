package cartes.util;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstracteModelEcoutable implements ModeleEcoutable {
    private List<EcouteurModele> écouteurs = new ArrayList<>();

    @Override
    public void ajoutEcouteur(EcouteurModele e) {
        écouteurs.add(e);
    }

    @Override
    public void retraitEcouteur(EcouteurModele e) {
        écouteurs.remove(e);
    }
    
    public void fireChangement(){
        for (EcouteurModele e : écouteurs){
            e.modeleMisAJour(this);
        }
    }
}