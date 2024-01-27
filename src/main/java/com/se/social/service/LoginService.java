package com.se.social.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
public class LoginService {
	
	private final UserRepository repository;

   public String getAccessToken(String code, String gate) {

      try {
    	  String redirectURI = "";
    	  String apiURL = "";
    	  String access_token = "";
    	  String refresh_token = "";
    	  
    	  if("naver".equals(gate)) {
    		  redirectURI = URLEncoder.encode("http://localhost:8080/social/nlogin", "UTF-8");
    		  apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
    		  apiURL += "&client_id=" + "hospjNuWBFaE1sQr_t7B";
    		  apiURL += "&client_secret=" + "PfT6rr4e0C";
    		  apiURL += "&redirect_uri=" + "http://localhost:8080/social/nlogin";
    		  apiURL += "&code=" + code;
    		  apiURL += "&state=" + 1234;
    		  
    	  } else if("kakao".equals(gate)){
    		  redirectURI = URLEncoder.encode("http://localhost:8080/social/klogin", "UTF-8");
    		  apiURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code";
    		  apiURL += "&client_id=515f074d94d08d53795260fee493bc31";
    		  apiURL += "&client_secret=RVDWbm6lKZKGvIWciR483xICAK498B4d";
    		  apiURL += "&redirect_uri=http://localhost:8080/social/klogin";
    		  apiURL += "&code=" + code;
    		  
    	  } else if ("google".equals(gate)) {
              // 구글 로그인 정보
              redirectURI = URLEncoder.encode("http://localhost:8080/social/glogin", "UTF-8");
              apiURL = "https://oauth2.googleapis.com/token?grant_type=authorization_code";
              apiURL += "&client_id=" + "818157184722-b70nfa4smf81ib1orm36arjfgfspfujp.apps.googleusercontent.com";
              apiURL += "&client_secret=" + "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";
              apiURL += "&redirect_uri=" + "http://localhost:8080/social/glogin";
              apiURL += "&code=" + code;
          }
    	  
         System.out.println("apiURL=" + apiURL);
         
         
         URL url = new URL(apiURL);
         HttpURLConnection con = (HttpURLConnection) url.openConnection();
         con.setRequestMethod("POST");
         con.setRequestProperty("Content-Length", "1");
         con.setDoOutput(true);
         OutputStream outputStream = con.getOutputStream();
         outputStream.write(" ".getBytes());
         outputStream.flush();
         outputStream.close();
         
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
   public User getUserInfo(String accessToken, String gate) throws IOException {

	   String apiURL = "";

	   // 네이버 로그인 접근 토큰;
	   if("naver".equals(gate)) { 
		   apiURL = "https://openapi.naver.com/v1/nid/me";
	   } else if("kakao".equals(gate)) {
		   apiURL = "https://kapi.kakao.com/v2/user/me";
	   } else if("google".equals(gate)) {
		   apiURL = "https://www.googleapis.com/oauth2/v3/userinfo";
	   }
	   
	      String headerStr = "Bearer " + accessToken; // Bearer 다음에 공백 추가
	      String result = requestToServer(apiURL, headerStr);
	      System.out.println("사용자 정보 " + result);
	      
	      
	      JsonParser parser = new JsonParser();
	      JsonElement element = parser.parse(result);
	      
	      
	      
	      String token_id = "";
	      String nickname = "";
	      String email = "";
//	      JsonObject response = element.getAsJsonObject().get(JsonObject);
	      
	      if("naver".equals(gate)) { 
	    	  token_id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
	    	  nickname = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();
	    	  email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
	      } else if("kakao".equals(gate)) {
	    	  token_id = element.getAsJsonObject().get("id").getAsString();
	    	  nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
	    	  email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
	      } else if ("google".equals(gate)) {
	    	  token_id = element.getAsJsonObject().get("sub").getAsString();
	    	  nickname = element.getAsJsonObject().get("name").getAsString();
	    	  email = element.getAsJsonObject().get("email").getAsString();

	        }
	      
	      Optional<User> opt_user = repository.findById(new OauthId(gate, token_id));
	      System.out.println("--------opt_user : " + opt_user);
	      
	      User user = new User();
	      if (opt_user.isPresent()) {
	         return opt_user.orElse(null);
	      }else {
	         user.setUseremail(email);
	         user.setUsername(nickname);
	         user.setOauthtype(gate);
	         user.setOauthtoken(token_id);
	         repository.save(user);
	         
	         return user;
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