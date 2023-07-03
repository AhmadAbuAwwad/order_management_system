docker-compose up -d
sleep 60
docker exec -i  backend  mysql --user="user" --database="order_management" --password="user" < ddl.sql