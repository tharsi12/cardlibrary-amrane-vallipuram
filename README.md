# Card Library Project - Architecture DevOps

Ce projet consiste en une application de gestion de cartes de collection et de membres, conçue selon une architecture microservices (Modular Monolith) et déployée sur un cluster Kubernetes.

**Binôme :** Tachfine AMRANE & Tharsikan VALLIPURAM  
**Dépôt Docker Hub :** [hub.docker.com/r/tharsi12/cardlibrary](https://hub.docker.com/r/tharsi12/cardlibrary)

---

## Architecture du Système

```text
├── cardlibrabry_amrane_vallipuram/ 
│   	├── src/       
│   		├── main/
│   			├── java/
│   				├── data/
│   					├── Card.java
│   					├── CardRepository.java
│   					├── Member.java
│   					├── MemberRepository.java
│   				├── exception/
│   					├── CardNotFoundException.java
│   					├── MemberNotFoundException.java
│   				├── service/
│   					├── CardClientService.java
│   					├── CardService.java
│   					├── MemberService.java
│   				├── web/
│   					├── CardWebService.java
│   					├── MemberWebService.java
│   				└── CardLibraryApplication.java
│   			├── ressources/
│   				├── application.properties
│   		├── test/   
│   			├── CardlibraryAmraneVallipuramApplicationTests.java
│   			├── CardServiceTest.java
│   			├── CardWebServiceTest.java
│   			├── MemberServiceTest.java
│   			├── MemberWebServiceTest.java
│	├── target/
│	├── cardlibrary-deployment.yaml
│	├── cardlibrary-ingress.yml
│	├── cardlibrary-service.yaml
│	├── docker-compose.yml 
│	├── Dockerfile    
│	├── mnvw        
│	├── mnvw.cmd       
│	├── pom.xml    
│	├── postgres-config.yaml    
│	├── postgres-secret.yaml 
│	├── postgres-storage.yaml   
│	├── postgres.yaml         
│	└── README.md
```

### Composants :
* **Gateway (Ingress) :** Point d'entrée unique via l'hôte `cardmarket`.
* **Application (Spring Boot) :** Gestion des cartes et des membres avec communication interne.
* **Base de données (PostgreSQL 15) :** Stockage des données métier.
* **Persistance :** Utilisation de `PersistentVolume` (PV) pour garantir que les données survivent au redémarrage des pods.
* **Sécurité (RBAC) :** Limitation des privilèges du pod applicatif via un `ServiceAccount` dédié.

---

## Stack Technique

* **Backend :** Java 17, Spring Boot, Spring Data JPA.
* **Database :** PostgreSQL 15.
* **DevOps :** Docker, Kubernetes (Minikube), Docker Hub.
* **Réseau :** Kubernetes Ingress Controller.

---

## Installation et Déploiement

### 1. Prérequis
* Minikube et Docker Desktop installés.
* Activer l'Ingress : `minikube addons enable ingress`.
* Lancer le tunnel : `minikube tunnel`.
* Ajouter l'hôte à votre fichier `hosts` : `127.0.0.1 myservice.info`.

### 2. Déploiement Kubernetes
Appliquez les fichiers de configuration dans l'ordre suivant pour respecter les dépendances :

```bash
# Configuration, Secrets et Stockage
kubectl apply -f postgres-storage.yaml
kubectl apply -f postgres-config.yaml
kubectl apply -f postgres-secret.yaml

# RBAC (Sécurité)
kubectl apply -f rbac.yaml

# Base de données
kubectl apply -f postgres.yaml

# Application et Ingress
kubectl apply -f cardlibrary-deployment.yaml
kubectl apply -f cardlibrary-service.yaml
kubectl apply -f cardlibrary-ingress.yml
