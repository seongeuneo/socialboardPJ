<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/loginpagecss.css">
</head>
<body>
	
<div id="wrap">
	<a class="home" href="/home">HOME</a>	
	<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=hospjNuWBFaE1sQr_t7B&state=STATE_STRING&redirect_uri=http://localhost:8080/social/nlogin"><img src="/resources/images/naver_login.png" alt="네이버로그인"></a><br>
	<a href="https://accounts.google.com/o/oauth2/auth?client_id=818157184722-b70nfa4smf81ib1orm36arjfgfspfujp.apps.googleusercontent.com&redirect_uri=http://localhost:8080/social/glogin&response_type=code&scope=email%20profile%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile%20openid&authuser=0&prompt=consent"><img src="/resources/images/google_login.png" alt="구글로그인"></a><br>
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=515f074d94d08d53795260fee493bc31&redirect_uri=http://localhost:8080/social/klogin&response_type=code"><img src="/resources/images/kakao_login_medium_wide.png" alt="카카오로그인"></a><br>
</div>

</body>
</html>