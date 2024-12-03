// package com.yuyun.config;
//
// import org.apache.http.client.HttpClient;
// import org.apache.http.conn.HttpClientConnectionManager;
// import org.apache.http.conn.ssl.NoopHostnameVerifier;
// import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
// import org.apache.http.ssl.SSLContexts;
// import org.apache.http.ssl.TrustStrategy;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.client.ClientHttpRequestFactory;
// import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
// import org.springframework.web.client.RestTemplate;
//
// import javax.net.ssl.SSLContext;
// import java.security.KeyManagementException;
// import java.security.KeyStoreException;
// import java.security.NoSuchAlgorithmException;
//
/// **
// * @author
// */
//@Configuration
// public class RestTemplateConfig {
//
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory) {
//        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
//        restTemplate.setErrorHandler(new ResTemplateErrorHandler());
//        return restTemplate;
//    }
//
//    @Bean
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory(HttpClient httpClient) {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        // httpClient创建器
//        factory.setHttpClient(httpClient);
//        // 连接超时时间/毫秒（连接上服务器(握手成功)的时间，超出抛出connect timeout
//        factory.setConnectTimeout(15000);
//        // 数据读取超时时间(socketTimeout)/毫秒（务器返回数据(response)的时间，超过抛出read timeout）
//        factory.setReadTimeout(5000);
//        // 连接池获取请求连接的超时时间，不宜过长，必须设置/毫秒（超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool）
//        factory.setConnectionRequestTimeout(10 * 1000);
//        return factory;
//    }
//
//    @Bean
//    public HttpClient httpClient(HttpClientConnectionManager poolingHttpClientConnectionManager) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
//        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
//        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//
//        HttpClientBuilder httpClientBuilder = HttpClients.custom();
//
//        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
//        // 设置http连接管理器
//        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
//        // 设置重试次数
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
//        return httpClientBuilder.build();
//    }
//
//    @Bean
//    public HttpClientConnectionManager poolingHttpClientConnectionManager() {
//
//        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
//        // 最大连接数
//        poolingHttpClientConnectionManager.setMaxTotal(500);
//        // 同路由并发数（每个主机的并发）
//        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
//        return poolingHttpClientConnectionManager;
//    }
//
//    public static HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
//            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
//        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
//        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
//        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//
//        HttpClientBuilder httpClientBuilder = HttpClients.custom();
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler());
//        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
//        CloseableHttpClient httpClient = httpClientBuilder.build();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setHttpClient(httpClient);
//        return factory;
//    }
//}
