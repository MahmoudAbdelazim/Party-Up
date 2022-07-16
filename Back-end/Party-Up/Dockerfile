FROM maven
WORKDIR /party-up
COPY . .
RUN mvn install
CMD mvn spring-boot:run