### Test Oauth2 Server

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

