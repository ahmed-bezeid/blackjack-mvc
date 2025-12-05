package cartes.Vue;

import cartes.modele.Carte;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardView extends JPanel {
    private final Carte carte;
    private final boolean faceCachee;
    private final boolean estCliquable; // NOUVEL ATTRIBUT POUR LA LOGIQUE D'INTERACTION

    /**
     * Constructeur pour une vue de carte.
     * * @param carte La carte à dessiner.
     * @param faceCachee Vrai si seule le dos de la carte doit être affiché.
     * @param estCliquable Vrai si la carte doit réagir au survol (utilisé pour la pioche).
     */
    public CardView(Carte carte, boolean faceCachee, boolean estCliquable) { 
        this.carte = carte;
        this.faceCachee = faceCachee;
        this.estCliquable = estCliquable;
        
        setPreferredSize(new Dimension(80, 120));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setBackground(Color.WHITE);
        
        // Logique de cliquabilité (gestion du curseur et du survol)
        if (estCliquable) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                }
            });
        }
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
            // Dessin du dos de la carte (Bleu)
            g2d.setColor(new Color(0, 51, 102));
            g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("🃏", getWidth() / 2 - 15, getHeight() / 2 + 10);
        } else {
            // Dessin de la face visible
            String valeur = carte.getValeur().toUpperCase();
            String couleur = carte.getCouleur().toLowerCase();
            String symbole = getSymbole(couleur);

            // Rouge pour Cœur/Carreau, Noir pour Pique/Trèfle
            Color cardColor = (couleur.equals("coeur") || couleur.equals("carreau")) ? Color.RED.darker() : Color.BLACK;

            g2d.setColor(cardColor);
            
            // Valeur et symbole en haut à gauche
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString(valeur, 5, 20);
            g2d.drawString(symbole, 5, 40);

            // Symbole central
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.drawString(symbole, getWidth() / 2 - 15, getHeight() / 2 + 15);
        }
    }
}