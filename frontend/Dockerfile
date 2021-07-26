FROM node:14.17.3-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
EXPOSE 8081 49153
CMD npm run start