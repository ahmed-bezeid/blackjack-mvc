package blackjack.vue;

import blackjack.modele.JeuBlackJack;
import cartes.modele.Carte;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueSwing extends JFrame implements IVueBlackJack { 
    
    private final JButton tirerBtn = new JButton("Tirer");
    private final JButton resterBtn = new JButton("Rester");
    private final JButton nouvellePartieBtn = new JButton("Nouvelle Partie");
    private final JButton quitterBtn = new JButton("Quitter");
    private final JTextArea zoneMessages = new JTextArea(3, 40);
    
    // Panneaux pour l'affichage des cartes
    private final JPanel panelMains = new JPanel();
    private final JPanel panelMainCroupier = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    private final JPanel panelMainJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    
    private final JeuBlackJack modele;

    public VueSwing(JeuBlackJack modele) {
        super("Blackjack - Mode Graphique (Souris)");
        this.modele = modele;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));

        // --- Zone de Messages (Haut) ---
        zoneMessages.setEditable(false);
        zoneMessages.setFont(new Font("Monospaced", Font.BOLD, 14));
        zoneMessages.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(zoneMessages);
        contentPane.add(scroll, BorderLayout.NORTH);

        // --- Panneau Central (Mains) ---
        panelMains.setLayout(new GridLayout(4, 1, 0, 5));
        panelMains.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelMains.add(new JLabel("🏦 CROUPIER :"));
        panelMains.add(panelMainCroupier);
        panelMains.add(new JLabel("👤 VOUS :"));
        panelMains.add(panelMainJoueur);
        
        contentPane.add(panelMains, BorderLayout.CENTER);

        // --- Panneau des Boutons (Bas) ---
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBtn.add(tirerBtn);
        panelBtn.add(resterBtn);
        panelBtn.add(nouvellePartieBtn);
        panelBtn.add(quitterBtn);
        contentPane.add(panelBtn, BorderLayout.SOUTH);

        pack(); 
        setVisible(true);
    }

    public JButton getTirerBtn() { return tirerBtn; }
    public JButton getResterBtn() { return resterBtn; }
    public JButton getNouvellePartieBtn() { return nouvellePartieBtn; }
    public JButton getQuitterBtn() { return quitterBtn; }

    @Override
    public void onJeuUpdate() {
        updateHandsView();
        updateStatusAndButtons();
        revalidate();
        repaint();
    }
    
    private void updateHandsView() {
        panelMainJoueur.removeAll();
        panelMainCroupier.removeAll();
        
        boolean croupierCache = modele.isCroupierCache();

        // Afficher la main du joueur
        for (Carte c : modele.getMainJoueur().getCartes()) {
            panelMainJoueur.add(new CardView(c, false));
        }

        // Afficher la main du croupier
        List<Carte> cartesCroupier = modele.getMainCroupier().getCartes();
        for (int i = 0; i < cartesCroupier.size(); i++) {
            Carte c = cartesCroupier.get(i);
            // Cacher la deuxième carte du croupier
            boolean cache = croupierCache && i == 1; 
            panelMainCroupier.add(new CardView(c, cache));
        }
    }
    
    private void updateStatusAndButtons() {
        int totalJoueur = modele.calculerValeur(modele.getMainJoueur());
        int totalCroupier = modele.calculerValeur(modele.getMainCroupier());
        
        String msg = modele.getStatus();
        
        if (modele.estPartieTerminee()) {
            msg += String.format("\n🏁 Joueur: %d | Croupier: %d", totalJoueur, totalCroupier);
            tirerBtn.setEnabled(false);
            resterBtn.setEnabled(false);
            nouvellePartieBtn.setEnabled(true);
        } else {
            // Afficher seulement la valeur visible du croupier
            int totalCroupierVisible = 0;
            if (modele.getMainCroupier().getCartes().size() > 0) {
                 totalCroupierVisible = modele.calculerValeur(modele.getMainCroupier().getCartes().subList(0, 1));
            }
            msg += String.format("\nVotre Total: %d | Croupier visible: %d", totalJoueur, totalCroupierVisible);
            tirerBtn.setEnabled(true);
            resterBtn.setEnabled(true);
            nouvellePartieBtn.setEnabled(false);
        }
        
        zoneMessages.setText(msg);
    }
}