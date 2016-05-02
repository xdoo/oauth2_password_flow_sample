### Test OAuth2 Server

To test the OAuth2 Server open a console, move to your server home directory and fire up those commands:
```bash
$ mvn clean package
$ java -jar password-flow-0.0.1-SNAPSHOT.jar
```

Wait for the server. Open a new console tab and let's check the OAuth server.
```bash
$ curl acme:acmesecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=user -d password=password
$ {"access_token":"232f6fe0-f117-4a0f-8dd6-9c7a7b5f1cd2","token_type":"bearer","refresh_token":"f9e87b15-f764-47b7-a34a-9665cd4d4967","expires_in":43199,"scope":"openid"}
$ TOKEN=232f6fe0-f117-4a0f-8dd6-9c7a7b5f1cd2
$ curl -H "Authorization: Bearer $TOKEN" localhost:9999/uaa/user
$ {"details":{"remoteAddress":"127.0.0.1","sessionId":null,"tokenValue":"232f6fe0-f117-4a0f-8dd6-9c7a7b5f1cd2","tokenType":"Bearer",.... 
```
### Test Resource Service

To test the resource service, keep the OAuth server running. Open a new console tap and start the resource service:

```bash
$ mvn clean package
$ java -jar oauth-resource-0.0.1-SNAPSHOT.jar
```

Wait for the server and go back to the previous tab. The Bearer token still exists, so we simply can call:
```bash
$ curl -H "Authorization: Bearer $TOKEN" localhost:8070/hello
$ Hello user
```

### Test the non Spring client

To test the non Spring client keep the servers above running. Swith to your IDE an run the Main Class ```com.example.NonSpringApplication``` from project 'client'. The application executes the following steps:
* aquire a new token
* call a protected resource
* aquire a fresh token
* call the protected resource with the fresh token
 
The stack trace shows some more details. You could do this using curl as welll:

```bash
$ curl acme:acmesecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=user -d password=password
$ ...
$ TOKEN=<PASTE TOKEN HERE>
$ curl -H "Authorization: Bearer $TOKEN" localhost:8070/hello
$ ...
$ curl acme:acmesecret@localhost:9999/uaa/oauth/token -d grant_type=refresh_token -d refresh_token=<PASTE REFRESH TOKEN HERE>
$ ...
$ TOKEN=<PASTE FRESH TOKEN HERE>
$ curl -H "Authorization: Bearer $TOKEN" localhost:8070/hello
$ ...
```