# spring-boot-invoice-app

## Setup Database Using Docker
```shell
docker run --rm \
--name=invoice-db \
-e MYSQL_DATABASE=invoicedb \
-e MYSQL_USER=naruto \
-e MYSQL_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
-e MYSQL_ROOT_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
-e TZ=Asia/Jakarta \
-p 6603:3306 \
-v "$PWD/docker/invoice-db/conf.d":/etc/mysql/conf.d \
-v "$PWD/storage/docker/invoicedb-data":/var/lib/mysql \
mysql:8
```

## Run Open Rewrite
```shell
mvn rewrite:run
```
- thymeleaf-extras-springsecurity5 -> thymeleaf-extras-springsecurity6 
- java.version 11 -> 17
- spring boot 2 -> 3.1.3
- and more chenge for active recipes in here https://docs.openrewrite.org/recipes/java

## Run App
```shell
mvn clean install spring-boot:run
```