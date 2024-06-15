FROM node:20.14.0-alpine3.20 as build
WORKDIR /app
COPY package*.json ./
RUN npm install
RUN npx ngcc --properties es2023 browser module main --first-only --create-ivy-entry-points
COPY . .
RUN npm run build

FROM nginx:1.27.0-alpine
COPY /nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/frontend /usr/share/nginx/html
EXPOSE 80
