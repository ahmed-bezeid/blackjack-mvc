/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.vue;

/**
 *
 * @author mezine221
 */




import cartes.modele.Carte;
import javax.swing.*;
import java.awt.*;

public class CardView extends JPanel {
    private final Carte carte;
    private final boolean faceCachee;

    public CardView(Carte carte, boolean faceCachee) {
        this.carte = carte;
        this.faceCachee = faceCachee;
        setPreferredSize(new Dimension(80, 120));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setBackground(Color.WHITE);
    }

    private String getSymbole(String couleur) {
        switch (couleur.toLowerCase()) {
            case "coeur": return "♥";
            case "carreau": return "♦";
            case "pique": return "♠";
            case "trefle": return "♣";
            default: return "";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (faceCachee) {
            g2d.setColor(new Color(0, 51, 102));
            g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("🃏", getWidth() / 2 - 15, getHeight() / 2 + 10);
        } else {
            String valeur = carte.getValeur().toUpperCase();
            String couleur = carte.getCouleur().toLowerCase();
            String symbole = getSymbole(couleur);

            Color cardColor = (couleur.equals("coeur") || couleur.equals("carreau")) ? Color.RED.darker() : Color.BLACK;

            g2d.setColor(cardColor);
            
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString(valeur, 5, 20);
            g2d.drawString(symbole, 5, 40);

            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.drawString(symbole, getWidth() / 2 - 15, getHeight() / 2 + 15);
        }
    }
}
