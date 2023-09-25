Pour accéder au site tapez : **http://51.20.67.63/**
### Gestion des Logs 
La journalisation (logging) en Java est un processus essentiel pour enregistrer des informations et des événements dans une application Java. Elle permet aux développeurs de collecter des données sur le comportement de l'application en cours d'exécution, ce qui est précieux pour le débogage, la surveillance, la maintenance et la compréhension du flux de travail de l'applicationVoici une description détaillée de la journalisation en Java :

1. Objectif de la journalisation : La journalisation en Java vise à collecter des informations sur le fonctionnement de l'application en cours d'exécution, telles que les messages d'erreur, les avertissements, les événements importants, les statistiques, les exceptions, etc.

2. Avantages de la journalisation :
 
     - Débogage : Facilite la détection et la correction des erreurs et des bogues dans l'application.
     - Surveillance : Permet de surveiller le comportement de l'application en temps réel.
     - Maintenance : Facilite la maintenance continue de l'application en identifiant les problèmes potentiels.
     - Analyse des performances : Aide à évaluer les performances de l'application en collectant des métriques clés.
     - Audit : Permet de garder une trace de qui a effectué quelles actions dans l'application.
3. Principaux composants de la journalisation en Java :

    - Logger (journaliseur) : Un composant central qui gère l'enregistrement des messages de journalisation. Les bibliothèques courantes pour la journalisation en Java incluent SLF4J et Log4j.
    - Appender (destinataire) : Spécifie où les messages de journalisation doivent être envoyés, par exemple, la console, un fichier, une base de données ou un service de journalisation en nuage comme Logz.io.
    - Niveaux de journalisation : Les messages de journalisation sont associés à des niveaux tels que DEBUG, INFO, WARN, ERROR et FATAL, qui déterminent l'importance du message. Les niveaux de journalisation permettent de filtrer les messages en fonction de leur gravité.
### Application  

Dans cette application j'ai utilisé logz.io qui est un service de gestion de journaux (logs) en ligne permet aux organisations de collecter, stocker, analyser et visualiser les journaux générés par leurs applications, systèmes et infrastructures
   1. Exemple : Voila queleques logs et exception gérer par notre application spring boot et les visuliser dans le platform logz.io  
      Logs spring boot
      ![logzSpring](https://github.com/moetez1233/Gestion_des_logs/assets/57545701/a6f83a2c-7e2b-4ee8-b35b-a61bcbd57ccf)
      
      Interface des logs
      
  ![logs](https://github.com/moetez1233/Gestion_des_logs/assets/57545701/c06a7cb5-d1fd-4b53-9b60-bf6bd535bee4)


