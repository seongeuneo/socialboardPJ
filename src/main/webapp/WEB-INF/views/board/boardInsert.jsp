<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소셜로그인 게시판</title>
</head>
<body>
	<div id="wrap">
		<h2>게시판 새글 등록</h2>

		<form action="boardInsert" method="post">
			<table>

				<tr height="40">
					<th>User email</th>
					<td>${sessionScope.loginUser.useremail}<input type="hidden"
						id="useremail" value="${sessionScope.loginUser.useremail}"
						name="useremail"></td>
				</tr>

				<tr height="40">
					<th>Title</th>
					<td><input type="text" id="board_title" name="board_title" required></td>
				</tr>
				<tr height="40">
					<th>Content</th>
					<td><textarea name="board_content" id="board_content" required></textarea></td>
				</tr>
				<tr height="40">
					<td colspan="2">
						<button type="reset">취소</button>
						<button type="submit">등록</button>
					</td>
				</tr>

			</table>
		</form>
		<%-- 	<c:if test="${not empty requestScope.message}">
=> ${requestScope.message}
</c:if> --%>
		<div class="home-link">
			<a href="boardPage">게시판 목록</a>
		</div>
		<div class="home-link">
			<a href="/home">Home</a>
		</div>

	</div>

</body>
</html>