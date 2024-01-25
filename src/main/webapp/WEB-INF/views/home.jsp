<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${not empty sessionScope.loginUser}">
      ${sessionScope.loginUser.username}님 안녕하세요.
      <br/>
      <a href="social/logout">로그아웃</a>
      <a>게시판가기</a>
   </c:if>
   
   <c:if test="${empty sessionScope.loginUser}">
      <a href="social/loginPage">로그인</a>
   </c:if>
</body>
</html>