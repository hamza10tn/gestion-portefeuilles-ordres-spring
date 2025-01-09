# gestion-portefeuilles-ordres-spring

## Description
Ce projet Spring Boot a été réalisé dans le cadre de l'examen 2025 pour la spécialité TWIN.  
Il s'agit d'une application pour la gestion des **ordres** et des **portefeuilles**. L'application permet :
- D'ajouter des ordres (achat ou vente) associés à un portefeuille.
- De consulter les actions disponibles et leurs symboles.
- D'exécuter des ordres selon des critères spécifiques.

## Fonctionnalités principales
- Gestion des **portefeuilles** et de leurs soldes.
- Gestion des **ordres d'achat et de vente** avec vérification des conditions (solde, volume, etc.).
- Planification automatique avec `@Scheduled` pour exécuter les ordres toutes les deux minutes.
- API REST pour récupérer les symboles d'actions dans une plage de dates spécifique.

---

## Prérequis
Avant de lancer l'application, assurez-vous d'avoir :
1. **Java 17+** installé : [Télécharger ici](https://adoptium.net/)
2. **Maven 3.3+** installé : [Télécharger ici](https://maven.apache.org/download.cgi)
3. Une base de données :
   - **MySQL** (configurable dans `application.properties`) ou
   - **H2** (intégrée et simple à utiliser pour les tests).

---

## Installation et exécution

1. **Cloner le dépôt GitHub :**
   ```bash
   git clone https://github.com/hamza10tn/gestion-portefeuilles-ordres-spring.git
   cd gestion-portefeuilles-ordres-spring
   
2. **Configurer la base de données :Modifier le fichier src/main/resources/application.properties pour y mettre vos informations MySQL :
**

3. **Lancer le projet avec Maven:**
   ```bash
   mvn spring-boot:run
