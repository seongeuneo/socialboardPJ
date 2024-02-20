<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소셜로그인 게시판</title>
<link rel="stylesheet" href="/resources/css/boardModify.css">
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/resources/js/board.js"></script>
</head>
<body>
	<div id="wrap">
		<h2>게시글 상세페이지</h2>
		<!-- 수정할 내용 입력 폼 -->
		<form action="updateBoard" method="post" enctype="multipart/form-data">
			<table>

				<tr>
					<th>게시글 제목</th>
					<td><input type="text" name="board_title"
						value="${requestScope.boardModify.board_title}"> <input
						type="hidden" name="board_id"
						value="${requestScope.boardModify.board_id}"></td>
				</tr>
				<tr>
					<th>게시글 내용</th>
					<td><textarea rows="5" cols="50" id="board_content"
							name="board_content">${requestScope.boardModify.board_content}</textarea>
				</tr>
				<tr height="40">
					<th>작성자 (수정불가)</th>
					<td><input type="hidden" name="useremail"
						value="${requestScope.boardModify.useremail}">${requestScope.boardModify.useremail}</td>
				</tr>
				<tr height="40">
					<th>등록일 (수정불가)</th>
					<td><input type="hidden" name="board_regdate"
						value="${requestScope.boardModify.board_regdate}">${requestScope.boardModify.board_regdate}</td>
				</tr>
				<tr height="40">
					<th>좋아요수</th>
					<td><input type="hidden" name="board_likes"
						value="${requestScope.boardModify.board_likes}">${requestScope.boardModify.board_likes}</td>
				</tr>
				<tr height="40">
					<th>조회수</th>
					<td><input type="hidden" name="board_views"
						value="${requestScope.boardModify.board_views}">${requestScope.boardModify.board_views}</td>
				</tr>
				<tr height="40">
					<th>게시글 수정일자</th>
					<td><input type="hidden" name="board_moddate"
						value="${requestScope.boardModify.board_moddate}">${requestScope.boardModify.board_moddate}</td>
				</tr>
				<tr>
					<td><input type="submit" value="수정"></td>
				</tr>
			</table>
			<div class="boardPage-link">
				<a href="boardPage">게시판 목록</a>
			</div>
			<div class="home-link">
				<a href="/home">Home</a>
			</div>
		</form>
		  <form action="deleteBoard" method="post">
            <input type="hidden" name="board_id" value="${requestScope.boardModify.board_id}">
            <input type="hidden" name="board_delyn" value="${requestScope.boardModify.board_delyn}">
            <button onclick="axboardDelete(${requestScope.boardModify.board_id})" id="${requestScope.boardModify.board_id}">게시글 삭제하기</button>
        </form>
	</div>

</body>
</html>