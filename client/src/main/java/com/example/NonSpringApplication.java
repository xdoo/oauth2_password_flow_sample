package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author straubec
 */
public class NonSpringApplication {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws Exception {
        NonSpringApplication app = new NonSpringApplication();
        app.run();
    }

    private void run() throws URISyntaxException, IOException {
        this.getToken();
    }
    
    private String getToken() throws URISyntaxException, IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope("localhost", AuthScope.ANY_PORT), new UsernamePasswordCredentials("acme", "acmesecret"));

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(new HttpHost("localhost", 9999, "http"), basicAuth);

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(9999)
                .setPath("/uaa/oauth/token")
                .setParameter("grant_type", "password")
                .setParameter("username", "user")
                .setParameter("password", "password")
                .build();
        HttpPost httpPost = new HttpPost(uri);

        LOG.info("Executing request " + httpPost.getRequestLine());

        CloseableHttpResponse response = httpclient.execute(httpPost, context);
        LOG.info(EntityUtils.toString(response.getEntity()));
        response.close();
        httpclient.close();
        
        return "";
    }

}
