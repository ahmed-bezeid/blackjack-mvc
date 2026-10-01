\# Jeu de Blackjack — Projet de Programmation Avancée (L3)



Projet réalisé en groupe de 4 (Aissani Ciril, Mezine Lounes, Gustavo Vieira Dos Santos, 

Ahmed Bezeid Mohamed El Mamy) à l'Université de Caen Normandie, encadré par M. Yann Mathet.



\## Architecture

Le projet est basé sur le motif \*\*Modèle-Vue-Contrôleur (MVC)\*\*, complété par le motif 

\*\*Observateur/Observable\*\* : le modèle (`Paquet`, `JeuBlackJack`) notifie automatiquement 

les vues (console et Swing) de tout changement d'état.



\## Ma contribution (Ahmed Bezeid)

\- Conception et implémentation de la \*\*librairie générique de jeu de cartes\*\* 

&#x20; (`cartes.modele` et `cartes.util`) : classes `Carte`, `Paquet` (conteneur observable), 

&#x20; et `Factory` pour l'instanciation normalisée d'un jeu de 52 cartes — développée en amont, 

&#x20; avant l'intégration dans le module Blackjack

\- Révision et correction de fonctions dans le module `blackjack` (logique de jeu)



Note : le travail en groupe a parfois été partagé directement entre membres (y compris via 

un compte Git partagé), donc l'historique de commits ne reflète pas précisément la 

répartition individuelle de tout le code.



Voir `rapport.pdf` pour le détail complet de l'architecture et des choix techniques.

