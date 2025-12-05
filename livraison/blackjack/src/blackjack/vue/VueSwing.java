package blackjack.vue;

import blackjack.modele.JeuBlackJack;
import cartes.modele.Carte;
import cartes.Vue.CardView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter; // Nécessaire
import java.awt.event.MouseEvent; // Nécessaire
import java.util.List;

public class VueSwing extends JFrame implements IVueBlackJack { 
    
    // Constantes de classe
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    private static final int HORIZONTAL_OFFSET = 35; 
    private static final int VERTICAL_MARGIN = 10;
    
    private final JButton tirerBtn = new JButton("Tirer");
    private final JButton resterBtn = new JButton("Rester");
    private final JButton nouvellePartieBtn = new JButton("Nouvelle Partie");
    private final JButton quitterBtn = new JButton("Quitter");
    private final JTextArea zoneMessages = new JTextArea(3, 40);
    
    private final JPanel panelMains = new JPanel(new GridLayout(4, 1, 0, 5));
    private final JPanel panelMainCroupier = new JPanel(null); 
    private final JPanel panelMainJoueur = new JPanel(null); 
    private final JPanel panelPioche = new JPanel(null); 
    
    private final JeuBlackJack modele;

    public VueSwing(JeuBlackJack modele) {
        super("Blackjack - Mode Graphique (Souris)");
        this.modele = modele;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));

        panelMainJoueur.setLayout(null); 
        panelMainCroupier.setLayout(null); 
        
        panelMainJoueur.setPreferredSize(new Dimension(600, CARD_HEIGHT + 2 * VERTICAL_MARGIN));
        panelMainCroupier.setPreferredSize(new Dimension(600, CARD_HEIGHT + 2 * VERTICAL_MARGIN));

        // --- Zone de Messages (Haut) ---
        zoneMessages.setEditable(false);
        zoneMessages.setFont(new Font("Monospaced", Font.BOLD, 16)); 
        zoneMessages.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(zoneMessages);
        contentPane.add(scroll, BorderLayout.NORTH);

        // --- Panneau Central (Mains et Pioche) ---
        JPanel panelCenterContent = new JPanel(new BorderLayout(10, 10));
        
        panelMains.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelMains.add(new JLabel("🏦 CROUPIER :"));
        panelMains.add(panelMainCroupier);
        panelMains.add(new JLabel("👤 VOUS :"));
        panelMains.add(panelMainJoueur);
        
        panelCenterContent.add(panelMains, BorderLayout.CENTER);
        
        panelPioche.setPreferredSize(new Dimension(CARD_WIDTH + 40, 2 * CARD_HEIGHT));
        panelPioche.setBorder(BorderFactory.createTitledBorder("Pioche (Cliquer pour Tirer)"));
        panelCenterContent.add(panelPioche, BorderLayout.EAST);
        
        contentPane.add(panelCenterContent, BorderLayout.CENTER);

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
    
    // Getters publics
    public JButton getTirerBtn() { return tirerBtn; }
    public JButton getResterBtn() { return resterBtn; }
    public JButton getNouvellePartieBtn() { return nouvellePartieBtn; }
    public JButton getQuitterBtn() { return quitterBtn; }
    public JPanel getPanelMainJoueur() { return panelMainJoueur; } 
    public JPanel getPanelPioche() { return panelPioche; } 


    @Override
    public void onJeuUpdate() {
        updateHandsView();
        updateDeckView(); 
        updateStatusAndButtons();
        revalidate(); 
        repaint();
    }
    
    private void updateHandsView() {
        panelMainJoueur.removeAll();
        panelMainCroupier.removeAll();
        
        boolean croupierCache = modele.isCroupierCache();

        // 1. Afficher la main du joueur
        List<Carte> cartesJoueur = modele.getMainJoueur().getCartes();
        for (int i = 0; i < cartesJoueur.size(); i++) {
            CardView cardView = new CardView(cartesJoueur.get(i), false, false); 
            int x = VERTICAL_MARGIN + i * HORIZONTAL_OFFSET;
            int y = VERTICAL_MARGIN;
            panelMainJoueur.add(cardView);
            cardView.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT); 
            panelMainJoueur.setComponentZOrder(cardView, 0); 
        }

        // 2. Afficher la main du croupier
        List<Carte> cartesCroupier = modele.getMainCroupier().getCartes();
        for (int i = 0; i < cartesCroupier.size(); i++) {
            boolean cache = croupierCache && i == 1; 
            CardView cardView = new CardView(cartesCroupier.get(i), cache, false);
            int x = VERTICAL_MARGIN + i * HORIZONTAL_OFFSET;
            int y = VERTICAL_MARGIN;
            panelMainCroupier.add(cardView);
            cardView.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
            panelMainCroupier.setComponentZOrder(cardView, 0); 
        }
    }
    
    private void updateDeckView() {
        panelPioche.removeAll();
        
        if (!modele.estPartieTerminee() && modele.getPioche().getCartes().size() > 0) {
            
            CardView deckCard = new CardView(new Carte("AS", "PIQUE"), true, true); 
            
            // Attacher l'écouteur directement au CardView
            deckCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) { // UTILISATION DE MOUSEPRESSED
                    if (!modele.estPartieTerminee()) {
                        modele.joueurTireCarte();
                    }
                }
                // Optionnel: maintenir l'effet visuel du curseur
                @Override
                public void mouseEntered(MouseEvent e) {
                    deckCard.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    deckCard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                }
            });
            
            int centerX = (panelPioche.getWidth() - CARD_WIDTH) / 2;
            int centerY = (panelPioche.getHeight() - CARD_HEIGHT) / 2;

            panelPioche.add(deckCard);
            deckCard.setBounds(centerX, centerY, CARD_WIDTH, CARD_HEIGHT);
            
            // Le curseur est géré par la CardView elle-même
            panelPioche.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 

        } else {
            panelPioche.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            panelPioche.add(new JLabel("Paquet vide ou Partie terminée", SwingConstants.CENTER)).setBounds(10, 10, panelPioche.getWidth() - 20, 30);
        }
        panelPioche.revalidate();
        panelPioche.repaint();
    }
    
    private void updateStatusAndButtons() {
        int totalJoueur = modele.calculerValeur(modele.getMainJoueur());
        
        String msg = modele.getStatus();
        boolean partieTerminee = modele.estPartieTerminee();

        if (partieTerminee) {
            int totalCroupier = modele.calculerValeur(modele.getMainCroupier());
            
            String finalVerdict = modele.getStatus();
            String scoreSummary = String.format("🏁 Joueur: %d | Croupier: %d", totalJoueur, totalCroupier);
            String restartPrompt = "Cliquez sur 'Nouvelle Partie' pour rejouer."; 
            
            msg = "\n\n" + finalVerdict + "\n" + scoreSummary + "\n\n" + restartPrompt + "\n";
            
            resterBtn.setEnabled(false);
            nouvellePartieBtn.setEnabled(true);
        } else {
            int totalCroupierVisible = 0;
            if (modele.getMainCroupier().getCartes().size() > 0) {
                 totalCroupierVisible = modele.calculerValeur(modele.getMainCroupier().getCartes().subList(0, 1));
            }
            msg += String.format("\nVotre Total: %d | Croupier visible: %d", totalJoueur, totalCroupierVisible);
            resterBtn.setEnabled(true);
            nouvellePartieBtn.setEnabled(false);
        }
        
        tirerBtn.setVisible(false); 
        panelPioche.setVisible(!partieTerminee); 
        zoneMessages.setText(msg);
    }
}