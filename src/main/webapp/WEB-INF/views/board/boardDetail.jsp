<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소셜로그인 게시판</title>
<link rel="stylesheet" href="/resources/css/boardDetail.css">
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="/resources/js/board.js"></script>
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
					<th>조회수</th>
					<td>${requestScope.boardDetail.board_views}</td>
				</tr>
				<tr height="40">
					<th>게시글 수정일자</th>
					<td>${requestScope.boardDetail.board_moddate}</td>
				</tr>
			</c:if>
		</table>
		<c:if
			test="${sessionScope.loginUser.useremail == requestScope.boardDetail.useremail}">
			<div class="boardModify">
				<a href="boardModify?board_id=${requestScope.boardDetail.board_id}">게시글 수정하기</a>
			</div>
		</c:if>
		<div class="likes">
			<span id="likeButton" style="cursor: pointer;"
				onclick="toggleLikes(${requestScope.boardDetail.board_id},'${sessionScope.loginUser.useremail}')">❤︎</span>
			<span id="likeCount">${requestScope.boardDetail.board_likes}</span>
		</div>
		<div class="boardPage-link">
			<a href="boardPage">게시판 목록</a>
		</div>
		<div>
			<form action="postComments" method="POST">
				<table class="addComments">

					<tr height="40">
						<td class="boardWriter">작성자 : ${sessionScope.loginUser.useremail}<input
							type="hidden" id="useremail"
							value="${sessionScope.loginUser.useremail}" name="useremail"></td>
						<td><input type="text" id="comment_content"
							name="comment_content" required></td>
						<td><input type="hidden" id="board_id"
							value="${requestScope.boardDetail.board_id}" name="board_id"></td>
						<td colspan="2">
							<button type="submit" style="margin-left: 10px;">등록</button>
						</td>
					</tr>

				</table>
			</form>
			<div class="comments">
			<form action="updateComments" method="post">
				<table>
					<tr>
						<th>댓글 번호</th>
						<th>작성자</th>
						<th>댓글 내용</th>
						<th>등록일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>

					<c:if test="${not empty requestScope.commentsList}">
						<c:forEach var="c" items="${requestScope.commentsList}">

							<tr>
								<td><a>${c.comment_id}</a></td>
								<td><a>${c.useremail}</a></td>
								<td><span class="comment-content">${c.comment_content}</span>
									<input type="text" class="edit-comment" style="display: none;"
									value="${c.comment_content}"></td>
								<td><a>${c.comment_regdate}</a></td>
								<c:if test="${sessionScope.loginUser.useremail == c.useremail}">
									<td>
										<button data-idx="${c.comment_id}" class="edit-btn">수정</button>
									</td>
									<td>
										<button data-idx="${c.comment_id}" class="delete-btn">삭제</button>
									</td>
								</c:if>
							</tr>

						</c:forEach>
					</c:if>

					<c:if test="${empty requestScope.commentsList}">
						<tr>
							<th colspan="4">내용물 없음</th>
						</tr>
					</c:if>
				</table>
				</form>
			</div>
		</div>

		<div class="pageNation">
			<c:choose>
				<c:when test="${resultDTO.start != resultDTO.page}">
					<a class="firstB"
						href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.start}">처음</a>
					<a class="ltB"
						href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.page-1}">&LT;</a>
				</c:when>
				<c:otherwise>
					<span class="firstB">처음</span>
					<span class="ltB">&LT;</span>
				</c:otherwise>

			</c:choose>

			<c:forEach var="i" items="${resultDTO.pageList}">
				<c:if test="${i==resultDTO.page}">
					<span><strong>${i}</strong></span>&nbsp;
                      </c:if>
				<c:if test="${i!=resultDTO.page}">
					<a
						href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${i}">${i}</a>&nbsp;
                      </c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${resultDTO.end != resultDTO.page}">
					<a class="gtB"
						href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.page+1}">&GT;</a>
					<a class="lastB"
						href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.end}">마지막</a>
				</c:when>
				<c:otherwise>
					<span class="gtB">&GT;</span>
					<span class="lastB">마지막</span>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="home-link">
		<a href="/home">Home</a>
	</div>


</body>
</html>