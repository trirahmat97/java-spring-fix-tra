FROM node:7
WORKDIR /apps
COPY package.json /apps
RUN npm install
COPY . /apps
CMD node server.js
EXPOSE 3001