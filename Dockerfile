FROM postgres:alpine

COPY src/main/resources/db-migrations/v1.sql/ /docker-entrypoint-initdb.d/