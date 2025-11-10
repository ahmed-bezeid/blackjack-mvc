/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.controleur;

/**
 *
 * @author mezine221
 */




import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueSwing;
import cartes.Carte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurBlackjack {
    private JeuBlackJack modele;
    private VueSwing vue;

    public ControleurBlackjack(JeuBlackJack modele, VueSwing vue) {
        this.modele = modele;
        this.vue = vue;

        modele.distribuer();
        vue.afficherEtat();

        vue.getTirerBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Carte c = modele.getPioche().piocher();
                modele.getMainJoueur().ajouter(c);
                vue.afficherMessage("Vous tirez : " + c);
                vue.afficherEtat();

                if (modele.joueurABrisque()) {
                    vue.afficherMessage("💥 Vous dépassez 21 ! Perdu.");
                    vue.getTirerBtn().setEnabled(false);
                    vue.getResterBtn().setEnabled(false);
                }
            }
        });

        vue.getResterBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vue.getTirerBtn().setEnabled(false);
                vue.getResterBtn().setEnabled(false);
                tourCroupier();
            }
        });
        vue.getNouvellePartieBtn().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                nouvellePartie();
            }
        });
    }
    
    private void nouvellePartie(){
        vue.afficherMessage("\n Nouvelle partie!");
        
        // recréer une nouvelle partie
        modele = new JeuBlackJack();
        
        // reconnect la vue ao nouveau modele
        vue.setModele(modele);
        
        // distribuer les cartes
        modele.distribuer();   
        
        // reactive les boutons
        vue.getTirerBtn().setEnabled(true);
        vue.getResterBtn().setEnabled(true);
        
        //affiche un nouvel état
        vue.afficherEtat();
        
    }

    private void tourCroupier() {
        vue.afficherMessage("\nTour du croupier...");
        while (modele.croupierDoitTirer()) {
            Carte c = modele.getPioche().piocher();
            modele.getMainCroupier().ajouter(c);
            vue.afficherMessage("Le croupier tire : " + c);
        }
        vue.afficherEtat();

        int valJ = modele.calculerValeur(modele.getMainJoueur());
        int valC = modele.calculerValeur(modele.getMainCroupier());

        if (valC > 21 || valJ > valC)
            vue.afficherMessage("🎉 Vous gagnez !");
        else if (valC == valJ)
            vue.afficherMessage("🤝 Égalité !");
        else
            vue.afficherMessage("😔 Le croupier gagne !");
    }
}

