package com.chejixing.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MessagerClient {
	private static Logger logger = LoggerFactory.getLogger(MessagerClient.class);
	private String cpid;
	private String url;

	public MessagerClient(String cpid, String url) {
		super();
		this.cpid = cpid;
		this.url = url;
	}

	public String send(String tel, String content) throws IOException {
		logger.info("Preparing to send text message...");
		logger.debug("Tel：{}", tel);
		logger.debug("Text message content：{}", content);

		String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
		
		content += String.format("%tT", new Date());

		BufferedReader in = null;
		try {
			StringBuffer queryBuf = new StringBuffer("cpid=" + cpid + "&password=");
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			queryBuf.append(encoder.encodePassword("520530_" + currentTimestamp + "_topsky", null));
			queryBuf.append("&timestamp=");
			queryBuf.append(currentTimestamp);
			queryBuf.append("&channelid=6214&msg=");
			queryBuf.append(URLEncoder.encode(content, "gbk"));
			queryBuf.append("&tele=");
			queryBuf.append(tel);

			String urlString = url + "?" + queryBuf.toString();

			logger.info("Sending text message to url: {}", urlString);

			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			int responseCode = con.getResponseCode();
			logger.debug("Response code is:{}", responseCode);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			logger.debug("Receive the response content: {}", response);

			return response.toString();
		} finally {
			if (null != in) {
				in.close();
			}
		}
	}
}
