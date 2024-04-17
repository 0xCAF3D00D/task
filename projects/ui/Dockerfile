FROM node:20 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
RUN npm install -g @angular/cli ng-openapi-gen
COPY . .

RUN npm run generate

EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0"]
