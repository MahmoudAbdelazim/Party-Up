FROM node:latest

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

RUN npm install -g @angular/cli@13.2.0
RUN npm install -g @angular-devkit/build-angular

COPY . /usr/src/app

CMD ng serve --host 0.0.0.0 --port 4200