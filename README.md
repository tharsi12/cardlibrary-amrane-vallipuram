# 🃏 Card Library Project - Architecture DevOps

Ce projet consiste en une application de gestion de cartes de collection et de membres, conçue selon une architecture microservices (Modular Monolith) et déployée sur un cluster Kubernetes.

**Binôme :** Amrane & Vallipuram  
**Dépôt Docker Hub :** [hub.docker.com/r/tharsi12/cardlibrary](https://hub.docker.com/r/tharsi12/cardlibrary)

---

## 🏗️ Architecture du Système

L'application est structurée pour démontrer les principes fondamentaux du DevOps : conteneurisation, orchestration, persistance et sécurité des accès.



### Composants :
* **Gateway (Ingress) :** Point d'entrée unique via l'hôte `myservice.info`.
* **Application (Spring Boot) :** Gestion des cartes et des membres avec communication interne.
* **Base de données (PostgreSQL 15) :** Stockage des données métier.
* **Persistance :** Utilisation de `PersistentVolume` (PV) pour garantir que les données survivent au redémarrage des pods.
* **Sécurité (RBAC) :** Limitation des privilèges du pod applicatif via un `ServiceAccount` dédié.

---

## 🛠️ Stack Technique

* **Backend :** Java 17, Spring Boot, Spring Data JPA.
* **Database :** PostgreSQL 15.
* **DevOps :** Docker, Kubernetes (Minikube), Docker Hub.
* **Réseau :** Kubernetes Ingress Controller.

---

## 📦 Installation et Déploiement

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