/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

import blackjack.modele.JeuBlackJack;
import blackjack.vue.VueSwing;
import blackjack.controleur.ControleurBlackjack;

public class MainClass {
    public static void main(String[] args) {
        JeuBlackJack modele = new JeuBlackJack();
        VueSwing vue = new VueSwing(modele);           // ⚡ important : passer le modèle
        ControleurBlackjack controleur = new ControleurBlackjack(modele, vue);
    }
}
