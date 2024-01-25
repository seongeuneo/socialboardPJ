package com.se.social.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.se.social.domain.OauthId;
import com.se.social.entity.User;
import com.se.social.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Log4j2
@Service
@RequiredArgsConstructor
public class NaverAPI {
	
	private final UserRepository repository;

   public String getAccessToken(String code) {

      try {
         String redirectURI = URLEncoder.encode("http://localhost:8080/social/nlogin", "UTF-8");
         String apiURL;
         apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
         apiURL += "client_id=" + "hospjNuWBFaE1sQr_t7B";
         apiURL += "&client_secret=" + "PfT6rr4e0C";
         apiURL += "&redirect_uri=" + "http://localhost:8080/social/nlogin";
         apiURL += "&code=" + code;
         apiURL += "&state=" + 1234;
         String access_token = "";
         String refresh_token = "";
         System.out.println("apiURL=" + apiURL);
         
         
         URL url = new URL(apiURL);
         HttpURLConnection con = (HttpURLConnection) url.openConnection();
         con.setRequestMethod("POST");
         int responseCode = con.getResponseCode();
         BufferedReader br;
         System.out.print("responseCode=" + responseCode);
         
         
         if (responseCode == 200) { // 정상 호출
             br = new BufferedReader(new InputStreamReader(con.getInputStream()));
         } else { // 에러 발생
             br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
             return null;
         }

         String inputLine;
         StringBuffer res = new StringBuffer();
         while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
         }

         if (responseCode == 200) {
            System.out.println(res.toString());
         }
         
         String result = res.toString(); // 변경: 읽은 내용을 String으로 변환
         System.out.println("response body : " + result);

         JsonParser parser = new JsonParser();
         JsonElement element = parser.parse(result);

         br.close();

         return element.getAsJsonObject().get("access_token").getAsString();
      } catch (Exception e) {
         System.out.println(e);
         
         return null;
      }

   }
   
// getUserInfo
   public Optional<User> getUserInfo(String accessToken) throws IOException {

	      // 네이버 로그인 접근 토큰;
	      String apiURL = "https://openapi.naver.com/v1/nid/me";
	      String headerStr = "Bearer " + accessToken; // Bearer 다음에 공백 추가
	      String result = requestToServer(apiURL, headerStr);
	      System.out.println("사용자 정보 " + result);
	      
	      
	      JsonParser parser = new JsonParser();
	      JsonElement element = parser.parse(result);
	      
	      JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
	      System.out.println("*****response: " + response);
	      
	      String token_id = response.getAsJsonObject().get("id").getAsString();
	      String nickname = response.getAsJsonObject().get("name").getAsString();
	      String email = response.getAsJsonObject().get("email").getAsString();
	      System.out.println("email" +  email);

	      Optional<User> opt_user = repository.findById(new OauthId("naver", token_id));
	      System.out.println("--------opt_user : " + opt_user);
	      
	      User user = new User();
	      if (opt_user.isPresent()) {
	         return opt_user;
	      }else {
	         user.setUseremail(email);
	         user.setUsername(nickname);
	         user.setOauthtype("naver");
	         user.setOauthtoken(token_id);
	         repository.save(user);
	         
	         return Optional.of(user);
	      }

	   }


	private String requestToServer(String apiURL, String headerStr) throws IOException {
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		System.out.println("header Str: " + headerStr);
		if (headerStr != null && !headerStr.equals("")) {
			con.setRequestProperty("Authorization", headerStr);
		}
		int responseCode = con.getResponseCode();
		BufferedReader br;
		System.out.println("responseCode=" + responseCode);
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else { // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer res = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			res.append(inputLine);
		}
		br.close();
		if (responseCode == 200) {
			return res.toString();
		} else {
			return null;
		}
	}
}