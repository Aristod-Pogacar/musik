# 🎵 Muzik - Application de Streaming Audio

Bienvenue dans le projet **Muzik**. C'est une application web complète développée avec **Spring Boot** qui permet de gérer une bibliothèque musicale et d'écouter de la musique via une interface moderne.

## 🚀 Technologies Utilisées

*   **Backend** : Java, Spring Boot (Web, Data JPA, Security).
*   **Frontend** : Thymeleaf (Moteur de template), HTML5, CSS3 (Design moderne "Glassmorphism").
*   **Base de données** : MySQL.
*   **Stockage** : Système de fichiers local (dossier `uploads`).

---

## 📂 Structure du Code (De A à Z)

Voici une explication détaillée de l'organisation du code source dans `src/main/java/com/muzik/muzik`.

### 1. 🧱 Entity (Les Modèles de Données)
Ce package contient les classes qui représentent les tables de la base de données.
*   **`User.java`** : Représente un utilisateur. Il contient :
    *   `role` : Définit si c'est un "ADMIN" (Artiste) ou "USER" (Auditeur).
    *   `musics` : Liste des musiques créées par cet utilisateur.
*   **`Music.java`** : Représente une chanson.
    *   Contient le titre, l'artiste, l'album et le chemin du fichier audio (`file`).
    *   Liée à une `Category` et un `User`.
*   **`Category.java`** : Représente le genre musical (ex: Pop, Rock, Rap).

### 2. 💾 Repository (Accès aux Données)
Interfaces qui étendent `JpaRepository`. Elles permettent de faire des requêtes SQL sans écrire de SQL.
*   `UserRepository`, `MusicRepository`, `CategoryRepository`.
*   Exemple : `musicRepository.findByCategory(category)` trouve toutes les musiques d'une catégorie.

### 3. ⚙️ Service (Logique Métier)
Couche intermédiaire entre le Contrôleur et le Repository.
*   **`MusicService.java`** : Gère la création, la modification (avec le champ artiste) et la suppression des musiques.
*   **`UserService.java`** : Gère l'inscription et l'encodage des mots de passe.
*   **`CategoryService.java`** : Gestion des catégories.

### 4. 🎮 Controller (Gestion des Requêtes HTTP)
C'est ici que les URLs sont définies.
*   **`MusicController.java`** : Gère l'interface d'administration (`/music`). Permet d'ajouter/modifier des musiques et d'uploader des fichiers.
*   **`PlayerController.java`** : Gère l'interface publique (`/player`). Affiche le lecteur audio moderne.
*   **`UserController.java`** : Gère l'inscription (`/users/new`) et la liste des utilisateurs.
*   **`AuthController.java`** : Gère la connexion.

### 5. 🔒 Config (Configuration & Sécurité)
*   **`SecurityConfig.java`** : Le cerveau de la sécurité.
    *   Définit les pages publiques (`/player`, `/login`, `/uploads/**`).
    *   Protège les pages d'administration.
*   **`CustomAuthenticationSuccessHandler.java`** : Une classe intelligente qui redirige l'utilisateur après la connexion :
    *   Si **ADMIN** ➡️ Redirection vers `/music` (Gestion).
    *   Si **USER** ➡️ Redirection vers `/player` (Écoute).
*   **`WebConfig.java`** : Configure le serveur pour qu'il puisse servir les fichiers audio stockés dans le dossier `uploads` externe.

---

## 🎨 Frontend (Templates Thymeleaf)

Les fichiers se trouvent dans `src/main/resources/templates`.

### 1. 🎧 Partie Publique (`/player`)
*   **`player/index.html`** : Une interface "Single Page" moderne.
    *   Utilise JavaScript pour gérer la lecture audio (Play, Pause, Suivant, Précédent).
    *   Barre de progression interactive.
    *   Design sombre et immersif.

### 2. ⚙️ Partie Administration (`/music`, `/categories`)
*   **`fragments/navbar.html`** : Le menu de navigation réutilisable inclus sur toutes les pages admin.
*   **`music/list.html`** : Tableau de bord des musiques avec notifications (Succès/Erreur).
*   **`music/create.html`** & **`edit.html`** : Formulaires stylisés pour gérer le contenu.
*   **`users/create.html`** : Page d'inscription avec choix du rôle (Auditeur vs Artiste).

---

## 🛠️ Comment lancer le projet

1.  **Base de données** : Assurez-vous d'avoir MySQL lancé et une base de données nommée `muzik_db` (ou celle définie dans `application.properties`).
2.  **Configuration** : Vérifiez `src/main/resources/application.properties` pour les identifiants BDD.
3.  **Lancement** :
    ```bash
    mvn spring-boot:run
    ```
4.  **Accès** :
    *   Lecteur Audio : `http://localhost:8080/player`
    *   Connexion : `http://localhost:8080/login`

## 📝 Fonctionnalités Clés

*   **Upload de fichiers** : Les fichiers MP3 sont sauvegardés dans un dossier `uploads` à la racine du projet.
*   **Rôles dynamiques** : L'interface s'adapte selon si vous êtes un simple auditeur ou un créateur de contenu.
*   **Design Responsive** : L'application fonctionne sur mobile et ordinateur.

