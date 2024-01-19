package com.se.social.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.se.social.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
@RequiredArgsConstructor
public class NaverAPI {

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
   
   public String getUserInfo(String access_token) {
      
      
      return "성공";
   }

}