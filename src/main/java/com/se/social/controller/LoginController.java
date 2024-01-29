package com.se.social.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.social.entity.User;
import com.se.social.service.LoginService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping(value = "/social")
@Controller
public class LoginController {

	private LoginService loginService;

	@GetMapping("loginPage")
	public void getLogin() {
	}

	@GetMapping("/klogin")
	public String klogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println(code);
		String access_token = loginService.getAccessToken(code, "kakao");
		
		System.out.println("controller access_token : " + access_token);
		
		try {
			User userInfo = loginService.getUserInfo(access_token, "kakao");
			System.out.println("*****************" + userInfo);

			if (userInfo != null) {
				session.setAttribute("loginUser", userInfo);
				return "redirect:/home";
			} else {
				return "redirect:/social/loginPage";
			}

		} catch (Exception e) {

			return "redirect:/social/loginPage";
		}

	} // klogin
	

	@GetMapping("/nlogin")
	public String nlogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println(code);

		String access_token = loginService.getAccessToken(code, "naver");

		try {
			User userInfo = loginService.getUserInfo(access_token, "naver");
			System.out.println("*****************" + userInfo);

			if (userInfo != null) {
				session.setAttribute("loginUser", userInfo);
				return "redirect:/home";
			} else {
				return "redirect:/social/loginPage";
			}

		} catch (Exception e) {

			return "redirect:/social/loginPage";
		}

	} // nlogin
	

	@GetMapping("/glogin")
	public String glogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println(code);

		String access_token = loginService.getAccessToken(code, "google");

		try {
			User userInfo = loginService.getUserInfo(access_token, "google");
			System.out.println("*****************" + userInfo);

			if (userInfo != null) {
				session.setAttribute("loginUser", userInfo);
				return "redirect:/home";
			} else {
				return "redirect:/social/loginPage";
				// 수정
			}

		} catch (Exception e) {

			return "redirect:/social/loginPage";
		}

	} // glogin

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:/home";
	} // logout

}
