package com.tiny.web.controller.base;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClientUtils {

	public static BasicCookieStore		cookieStore;

	public static CloseableHttpClient	httpclient;

	public static RequestConfig			requestConfig;

	public static SSLContext			sslContext;

	static {

		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
																													throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
																													throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, null);
		} catch (Exception localException) {
			localException.printStackTrace();
		}

		cookieStore = new BasicCookieStore();
		httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setSslcontext(sslContext).build();
		requestConfig = RequestConfig.custom().build();
		requestConfig = RequestConfig.custom().setProxy(new HttpHost("proxy.xxxx.com", 80, "http")).build();

	}

	public static void logout() {
		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
