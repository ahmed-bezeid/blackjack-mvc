package blackjack.vue;

import blackjack.modele.JeuBlackJack;
import cartes.modele.Carte;
import cartes.modele.Paquet;
import cartes.Vue.VuePaquetVisible;
import cartes.Vue.VuePaquetCache;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueSwing extends JFrame {
    private final JButton tirerBtn = new JButton("Tirer");
    private final JButton resterBtn = new JButton("Rester");
    private final JButton nouvellePartieBtn = new JButton("Nouvelle Partie");
    private final JTextArea zoneTexte = new JTextArea(10, 40);

    // Vues pour les paquets de cartes
    private VuePaquetVisible vueMainJoueur;
    private VuePaquetVisible vueMainCroupier;
    private VuePaquetCache vuePioche;

    private JeuBlackJack modele;

    public VueSwing(JeuBlackJack modele) {
        super("Blackjack");
        this.modele = modele;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Initialisation des vues des paquets
        Paquet mainJoueur = modele.getMainJoueur();
        Paquet mainCroupier = modele.getMainCroupier();
        Paquet pioche = modele.getPioche();
        
        vueMainJoueur = new VuePaquetVisible(mainJoueur);
        vueMainCroupier = new VuePaquetVisible(mainCroupier);
        vuePioche = new VuePaquetCache(pioche);
        
        // Panneau pour les vues des paquets
        JPanel panelVues = new JPanel(new GridLayout(3, 1));
        panelVues.add(vuePioche);
        panelVues.add(vueMainJoueur);
        panelVues.add(vueMainCroupier);
        
        // Panneau pour les boutons
        JPanel panelBtn = new JPanel();
        panelBtn.add(tirerBtn);
        panelBtn.add(resterBtn);
        panelBtn.add(nouvellePartieBtn);
        
        // Zone de texte pour les messages
        zoneTexte.setEditable(false);
        JScrollPane scroll = new JScrollPane(zoneTexte);
        
        // Ajout des composants à la fenêtre
        add(panelVues, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);
        add(scroll, BorderLayout.NORTH);
        
        setVisible(true);
    }


    // Getters pour les boutons
    public JButton getTirerBtn() { return tirerBtn; }
    public JButton getResterBtn() { return resterBtn; }
    public JButton getNouvellePartieBtn() { return nouvellePartieBtn; }

    // Affiche l'état du jeu
    public void afficherEtat() {
        zoneTexte.setText("=== BLACKJACK ===\n\n");

        // Affiche les cartes du joueur
        List<Carte> cartesJoueur = modele.getMainJoueur().getCartes();
        zoneTexte.append("Joueur : " + cartesToString(cartesJoueur) +
                " (total = " + modele.calculerValeur(modele.getMainJoueur()) + ")\n");

        // Affiche les cartes du croupier (la première carte est cachée au début)
        List<Carte> cartesCroupier = modele.getMainCroupier().getCartes();
        if (modele.isCroupierCache()) {
            zoneTexte.append("Croupier : [Carte cachée], " + cartesToString(cartesCroupier.subList(1, cartesCroupier.size())) + "\n");
        } else {
            zoneTexte.append("Croupier : " + cartesToString(cartesCroupier) +
                    " (total = " + modele.calculerValeur(modele.getMainCroupier()) + ")\n");
        }

        zoneTexte.append("==================\n");
    }

    // Affiche un message dans la zone de texte
    public void afficherMessage(String msg) {
        zoneTexte.append(msg + "\n");
    }

    // Convertit une liste de cartes en chaîne de caractères
    private String cartesToString(List<Carte> cartes) {
        if (cartes == null || cartes.isEmpty()) {
            return "Aucune carte";
        }
        StringBuilder sb = new StringBuilder();
        for (Carte c : cartes) {
            sb.append(c.toString()).append(", ");
        }
        sb.setLength(sb.length() - 2); // Enlève la dernière virgule
        return sb.toString();
    }

    // Met à jour le modèle
    public void setModele(JeuBlackJack modele) {
        this.modele = modele;
    }

    // Met à jour la vue lorsque le modèle change
    public void onJeuUpdate() {
        afficherEtat();
        repaint();
    }
}
