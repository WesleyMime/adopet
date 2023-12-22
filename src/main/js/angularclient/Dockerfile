FROM node:14 as builder

WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build --prod

# Use a lightweight web server as a runtime image
FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf
# Copy the built Angular app to the nginx public directory
COPY --from=builder /app/dist/angularclient /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
