/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cartes.Vue;

import javax.swing.*;
import java.awt.*;
import cartes.modele.Paquet;

/**
 *
 * @author aissani231@CAMPUS
 */
public class VuePaquetCache extends JPanel implements cartes.util.EcouteurModele {
    private Paquet paquet;
    private final int hauteurParCarte = 5;

    public VuePaquetCache(Paquet paquet) {
        this.paquet = paquet;
        paquet.ajoutEcouteur(this);
        setPreferredSize(new Dimension(60, paquet.getCartes().size() * hauteurParCarte));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int hauteur = paquet.getCartes().size() * hauteurParCarte;
        g.setColor(Color.BLUE); // couleur du dos de la carte
        g.fillRect(10, 10, 40, hauteur); // dessine un rectangle bleu (dos du paquet)
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 40, hauteur);
    }

    public void modeleMisAJour(Object source) {
        setPreferredSize(new Dimension(60, paquet.getCartes().size() * hauteurParCarte));
        revalidate();
        repaint();
    }
}

