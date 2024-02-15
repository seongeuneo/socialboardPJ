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
		<div>
			<table>
				<tr>
					<th>글번호</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>제목</th>
					<th>좋아요</th>
					<th>조회수</th>
				</tr>

				<c:if test="${not empty requestScope.selectList}">
					<c:forEach var="b" items="${requestScope.selectList}">
						<tr>
							<td>${b.board_id}</td>
							<td>${b.useremail}</td>
							<td>${b.board_regdate}</td>
							<td>${b.board_title}</td>
							<td>${b.board_likes}</td>
							<td>${b.board_views}</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>

			<div class="pageNation">
				<c:choose>
					<c:when test="${resultDTO.start != resultDTO.page}">
						<a class="firstB"
							href="boardPage?page=${resultDTO.start}&searchType=${searchType}&keyword=${keyword}">처음</a>
						<a class="ltB"
							href="boardPage?page=${resultDTO.page-1}&searchType=${searchType}&keyword=${keyword}">&LT;</a>
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
							href="boardPage?page=${i}&searchType=${searchType}&keyword=${keyword}">${i}</a>&nbsp;
                  </c:if>
				</c:forEach>

				<c:choose>
					<c:when test="${resultDTO.end != resultDTO.page}">
						<a class="gtB"
							href="boardPage?page=${resultDTO.page+1}&searchType=${searchType}&keyword=${keyword}">&GT;</a>
						<a class="lastB"
							href="boardPage?page=${resultDTO.end}&searchType=${searchType}&keyword=${keyword}">마지막</a>
					</c:when>
					<c:otherwise>
						<span class="gtB">&GT;</span>
						<span class="lastB">마지막</span>
					</c:otherwise>
				</c:choose>
			</div>


		</div>




	</div>

</body>
</html>