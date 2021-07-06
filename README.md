# without-active-transaction

1. start database

        docker run -d -e POSTGRES_USER=hibernate -e POSTGRES_PASSWORD=hibernate -e POSTGRES_DB=hibernate_db -e POSTGRES_PORT=5432 --name mydb -p 5432:5432 postgres
2. start kafka

        docker-compose up
3. start quarkus

        mvn clean quarkus:dev
