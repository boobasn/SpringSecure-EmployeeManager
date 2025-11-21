# Étape 1 : Utiliser une image Node.js pour la construction
# Il est préférable d'utiliser une version LTS (Long-Term Support)
# Remplacer par la version Node.js que vous souhaitez, par exemple 20-alpine
FROM node:20-alpine AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers package.json et package-lock.json (ou yarn.lock)
# pour installer les dépendances
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier le reste du code source
COPY . .

# Construire l'application (en général, vous utiliseriez 'ng build' pour la production)
# Mais pour le développement avec 'ng serve', on ne fait que configurer.
# L'application sera lancée avec 'ng serve' dans le docker-compose.yml
# Si vous aviez besoin de l'étape de construction pour la PROD: RUN ng build --configuration production

# Exposer le port par défaut d'Angular, même si vous en utilisez un autre
# dans votre sortie (52804), c'est une bonne pratique.
EXPOSE 4200

# Commande par défaut pour lancer l'application en développement
# Nous allons remplacer cette commande par une plus spécifique dans le docker-compose.yml
# CMD ["npm", "start"]