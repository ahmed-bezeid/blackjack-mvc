/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.vue;

/**
 *
 * @author mezine221
 */





import blackjack.modele.JeuBlackJack;
import cartes.Carte;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueSwing extends JFrame {
    private JButton tirerBtn = new JButton("Tirer");
    private JButton resterBtn = new JButton("Rester");
    private JButton nouvellePartieBtn = new JButton("Nouvelle Partie");
    private JTextArea zoneTexte = new JTextArea(12, 35);
    private JeuBlackJack modele;

    public VueSwing(JeuBlackJack modele) {
        super("Blackjack");
        this.modele = modele;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        JPanel panelBtn = new JPanel();
        panelBtn.add(tirerBtn);
        panelBtn.add(resterBtn);
        panelBtn.add(nouvellePartieBtn);

        zoneTexte.setEditable(false);
        JScrollPane scroll = new JScrollPane(zoneTexte);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelBtn, BorderLayout.SOUTH);
        getContentPane().add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    public JButton getTirerBtn() { return tirerBtn; }
    public JButton getResterBtn() { return resterBtn; }
    public JButton getNouvellePartieBtn() {return nouvellePartieBtn;}

    public void afficherEtat() {
        zoneTexte.setText("");
        zoneTexte.append("Main du joueur : " + cartesToString(modele.getMainJoueur().getCartes()) +
                         " (total = " + modele.calculerValeur(modele.getMainJoueur()) + ")\n");
        zoneTexte.append("Main du croupier : " + cartesToString(modele.getMainCroupier().getCartes()) +
                         " (total = " + modele.calculerValeur(modele.getMainCroupier()) + ")\n");
    }

    public void afficherMessage(String msg) {
        zoneTexte.append(msg + "\n");
    }

    private String cartesToString(List<Carte> cartes) {
        StringBuilder sb = new StringBuilder();
        for (Carte c : cartes) sb.append(c.toString()).append(", ");
        return sb.toString();
    }

    public void setModele(JeuBlackJack modele) {
        this.modele = modele;
    }
}

