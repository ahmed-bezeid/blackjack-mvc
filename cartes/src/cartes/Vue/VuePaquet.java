/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes.Vue;

import cartes.modele.*;
import cartes.util.*;

import javax.swing.*;

/**
 *
 * @author aissani231@CAMPUS
 */
public abstract class VuePaquet extends JPanel implements EcouteurModele {
    protected Paquet paquet;

    public VuePaquet(Paquet paquet) {
        this.paquet = paquet;
        paquet.ajoutEcouteur(this);  // abonnement aux changements du modèle
    }

    // méthode abstraite à implémenter dans les sous-classes pour afficher la vue
    protected abstract void afficher();

    @Override
    public void modeleMisAJour(Object source) {
        afficher();
        repaint();
    }
}
