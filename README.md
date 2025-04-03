# TEST

Réalisation d'une application exposant une API REST pour gérer le classement de joueurs lors d'un tournoi.
Les joueurs sont triés en fonction du nombre de points de chacun, du joueur ayant le plus de points à celui qui en a le
moins.
L'API devra permettre :

* d'ajouter un joueur (son pseudo)

* de mettre à jour le nombre de points du joueur

* de récupérer les données d'un joueur (pseudo, nombre de points et classement dans le tournoi)

* de retourner les joueurs triés en fonction de leur nombre de points

* de supprimer tous les joueurs à la fin du tournoi
  L'application devra être réalisée en Kotlin, pourra utiliser le framework d'injection Koin (Optionnel), et basée sur
  Ktor.
  L'application pourra utiliser la technologie de base de données de votre choix, de préférence MongoDB.
  Le service devra s'initialiser et se lancer par un script bash.

# Prérequis

* Java 21 ou supérieur
* Kotlin 2.1.10 ou supérieur
* MongoDB

# Installation

Pour lancer le projet via un script bash, lancer `./run_script.sh`.
Il peut être nécessaire de donner les droits d'exécution au script avec `chmod +x run_script.sh`.

# Utilisation

L'API est exposée sur le port 8080.

# Ce qu'il reste à faire

* Ajouter une Ci avec un sonar et le linter Detekt ?
* Ajouter jacoco pour une couverture de code ?
* que se passe-t-il lors du classement si deux joueurs ont le même nombre de points ?


