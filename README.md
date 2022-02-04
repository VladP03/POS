# Programarea Orientata pe Servicii

## Laborator 2
* Create cele 3 entitati (carte, autor si carte-autor) conform structurii;
* Endpoint-urile necesare pentru gestionarea tabelelor books, authors si book_authors;
* Container de MySQL (folosind Docker) si schimbat portul implicit, cu 2 role-uri (db_manager si web_user);
* Generare si populare tabele folosind liquibase.


## Laborator 3
* Afisarea paginata si cautarea dupa gen literar, an sau/si o combinatie dintre acestea (folosind queryParam);
* Expunere resurse prin HATEOAS;
* Dockerfile;
* Swagger.


## Laborator 4
* Creat entitea de comanda conform structurii;
* Container de MongoDB (tot prin Dokcer), cu 2 role-uri (db_manager si web_user);
* Endpoint pentru a putea crea o comanda + validarile necesare;
* Dockerfile.


## Laborator 5
* Creat aplicatia de calculator pentru o prima introducere in SOAP.

## Laborator 6
* Creat entitatea de user;
* Container de MySQL (folosind Docker) si schimbat portul implicit, cu 2 role-uri (db_manager si web_user);
* Generare si populare tabele folosind liquibase;
* Creat JwtTokenUtil pentru a manevra token-ul;
* Endpoint-urile cerute si securitatea pe ele;
* Securitatea pe endpoint-urile de la Lab 2, 3 si 4;
* Dockerfile.

In plus: Modulul de Book si modulul de Order au interceptor si inainte de a intra pe endpoint se va valida token-ul primit prin header-ul de Authorization.
Modulele sunt dezvoltate cu scopul de a fi aproape de modelul arhitectura al microserviciilor, fiecare fiind containerizat.
