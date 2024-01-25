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
	
<div class="center-container">
	<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=hospjNuWBFaE1sQr_t7B&state=STATE_STRING&redirect_uri=http://localhost:8080/social/nlogin"><img src="/resources/images/naver_login.png" alt="네이버로그인"></a><br>
	<hr>
	<a href="">구글 로그인</a><br>
	<hr>
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=515f074d94d08d53795260fee493bc31&redirect_uri=http://localhost:8080/social/klogin&response_type=code"><img src="/resources/images/kakao_login_medium_wide.png" alt="카카오로그인"></a><br>
</div>

</body>
</html>