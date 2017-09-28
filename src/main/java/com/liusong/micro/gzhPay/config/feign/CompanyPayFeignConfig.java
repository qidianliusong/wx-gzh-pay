package com.liusong.micro.gzhPay.config.feign;

import feign.Client;
import feign.Feign;
import feign.hystrix.HystrixFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * 企业支付feign接口配置
 */
@Configuration
public class CompanyPayFeignConfig {

    private static Logger logger = LoggerFactory.getLogger(CompanyPayFeignConfig.class);

    @Value("${key.story.trust.path}")
    private String key_story_trust_path ; // truststore的路径
    private static final String KEY_STORE_TYPE_JKS = "jks"; // truststore的类型
    @Value("${key.store.trust.password}")
    private String key_store_trust_password; // truststore的密码
    @Value("${key.store.client.path}")
    private String key_store_client_path;
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    @Value("${key.store.password}")
    private String key_store_password;

    @Bean
    @Primary
    public Feign.Builder feignBuilderForSSL() {
        Client trustSSLSockets = null;
        try {
            trustSSLSockets = new Client.Default(getSSLSocketFactory(), null);
        } catch (Exception e) {
            logger.error("初始化ssl配置失败",e);
            System.exit(0);
        }
        return  HystrixFeign.builder().client(trustSSLSockets);
    }

    private SSLSocketFactory getSSLSocketFactory() throws Exception {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        // 设置truststore
        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_JKS);
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);

        try (InputStream ksIn = new FileInputStream(key_store_client_path)) {
            keyStore.load(ksIn, key_store_password.toCharArray());
        }
        try (InputStream tsIn = new FileInputStream(new File(key_story_trust_path))) {
            trustStore.load(tsIn, key_store_trust_password.toCharArray());
        }
        kmf.init(keyStore, key_store_password.toCharArray());
        tmf.init(trustStore);
        SSLContext sslcontext = SSLContext.getInstance("SSL");
        sslcontext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return sslcontext.getSocketFactory();
    }

}
