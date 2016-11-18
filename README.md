# CountDownWebApp
Application de compteur pour le cours de CAW

# Côté Java

modele.Countdown
  -> L'objet décrivant le compte à rebours

org.teniere.CountDown
  -> Servlet

org.communication.Ws
  -> Communication en WebSocket

util.Util
  -> Contient des méthodes utilitaires (getCookie, setCookie,diff //différence entre 2 dates)

# Côté Web

ui.js
  -> Contient les fonctions javascript permetant de modifier l'interface pour ajouter/modifier un compteur.

# Note

Les cookies sont stockés sous le format d'une chaîne de caractère JSON.
