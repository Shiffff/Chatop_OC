# Plateforme ChaTop API
ChaTop représente une plateforme en ligne qui facilite la mise en relation entre locataires et propriétaires pour des locations saisonnières. L'application se compose d'une interface utilisateur Angular pour la partie frontale et d'un système en Java pour la partie backend, comme décrit ci-dessous.

## Technologies Employées
- Java 17
- Spring Boot 3
- Spring Security
- Jwt
- Maven
- MySQL 
- Springdoc openAPI

## Installation et Déploiement
### Prérequis
- Java JDK 17
- Maven
- MySQL / MySQL Workbench
- Angular 14

### Backend
1. Cloner le dépôt https://github.com/Shiffff/Chatop_OC)
2. Importer le projet dans votre environnement de développement intégré (IDE).

### Base de données
- Assurez-vous que MySQL Server est installé.
- Créez une nouvelle base de données :
- Ensuite, utilisez le script SQL fourni à l'emplacement /src/main/resources/sql/script.sql

### Configuration
1. Ouvrez le fichier application.properties situé dans /src/main/resources
2. Mettez à jour les propriétés suivantes avec votre configuration MySQL :

spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
server.port=

Lancer le projet

### Frontend
Récupérez le code depuis ce dépôt et suivez les instructions.
ng serve

Il sera accessible à l'adresse localhost:4200

Documentation
Accédez à la documentation des différentes routes de l'API à l'adresse http://localhost:{PORT}/api/swagger-ui/index.html#/
