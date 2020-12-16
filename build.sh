./mvnw clean package -DskipTests=true
docker rmi --force $(docker images | grep 'training')
docker rmi --force $(docker images | grep 'none')
docker build -t training/admin-server admin-server