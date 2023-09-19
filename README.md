# spring-boot-invoice-app
# Setup Database Using Docker
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