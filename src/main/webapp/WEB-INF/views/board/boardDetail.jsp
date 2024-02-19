<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소셜로그인 게시판</title>
<link rel="stylesheet" href="/resources/css/boardPage.css">
</head>
<body>
	<div id="wrap">
		<h2>게시글 상세페이지</h2>
		<table>
			<c:if test="${not empty requestScope.boardDetail}">
				<tr height="40">
					<th>게시글 번호</th>
					<td>${requestScope.boardDetail.board_id}</td>
				</tr>
				<tr height="40">
					<th>게시글 제목</th>
					<td>${requestScope.boardDetail.board_title}</td>
				</tr>
				<tr height="40">
					<th>게시글 내용</th>
					<td>${requestScope.boardDetail.board_content}</td>
				</tr>
				<tr height="40">
					<th>작성자</th>
					<td>${requestScope.boardDetail.useremail}</td>
				</tr>
				<tr height="40">
					<th>등록일</th>
					<td>${requestScope.boardDetail.board_regdate}</td>
				</tr>
				<tr height="40">
					<th>좋아요수</th>
					<td>${requestScope.boardDetail.board_likes}</td>
				</tr>
				<tr height="40">
					<th>조회수</th>
					<td>${requestScope.boardDetail.board_views}</td>
				</tr>
				<tr height="40">
					<th>게시글 수정일자</th>
					<td>${requestScope.boardDetail.board_moddate}</td>
				</tr>
			</c:if>
		</table>
		<div>
			<a href="boardModify?board_id=${requestScope.boardDetail.board_id}">수정하기</a>
		</div>
		<div class="boardPage-link">
			<a href="boardPage">게시판 목록</a>
		</div>
		<div class="home-link">
			<a href="/home">Home</a>
		</div>

	</div>

</body>
</html>