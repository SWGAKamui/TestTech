docker pull mongo:latest
docker run -d -p 27017:27017 --name=mongo-example mongo:latest

mvn clean package
kotlin -classpath target/ClassementAPI-1.0-SNAPSHOT.jar org.example.ClassementApi
#TODO dockerisé la partie kotlin ?