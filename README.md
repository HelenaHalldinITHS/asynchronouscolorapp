## Laboration 2 - Webservice och integrationer

Denna kod innehåller en implementation av ett web api för hantering av enkel information, i mitt fal om färger. Applikationen är skriven med hjälp av https://micronaut.io/, Graal vm och MongoDB. Det finns endast två enpoints:

- GET `localhost:8080/colors` som returnerar alla färger

- POST `localhost:8080/colors` som via ett JSON object sparar information om en färg, ex: 
```json
{
  "name" : "red",
  "specification" : "Dark and bright"
}
```

### Köra applikationen:

För att köra applikationen ange följande i din CLI (obs logga först in på docker desctop):

`docker network create mynetwork`

`docker run -d --name mongo --network mynetwork -p 27017:27017 mongo`

`docker run -d --name app --network mynetwork -p 8080:8080 -e MONGO_HOST=mongo helenahalldiniths/asynchronouscolorapp:0.1
`
