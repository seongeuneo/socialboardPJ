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
				<a href="boardModify?board_id=${requestScope.boardDetail.board_id}">게시글
					수정하기</a>
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

		<div class="comments">
		    <form action="postComments" method="POST" class="addCommentsForm">
		        <label for="comment_content" class="boardWriter">작성자 : ${sessionScope.loginUser.useremail}</label>
		        <input type="hidden" id="useremail" value="${sessionScope.loginUser.useremail}" name="useremail">
		        <input type="text" id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." required>
		        <input type="hidden" id="board_id" value="${requestScope.boardDetail.board_id}" name="board_id">
		        <button type="submit">등록</button>
		    </form>

			<ul class="commentsList">
				<li>
				    <span class="comment-number">댓글 번호</span> 
				    <span class="comment-writer">작성자</span> 
				    <span class="comment-content">댓글 내용</span> 
				    <span class="comment-date">등록일</span>
				    <span class="buttons">
				        <span class="edit">수정</span> 
				        <span class="delete">삭제</span>
				        <span class="reply">답글달기</span>
				    </span>
				</li>
				<c:if test="${not empty requestScope.commentsList}">
					 <c:forEach var="c" items="${requestScope.commentsList}">
	                    <li style="margin-left:${c.comment_indent}rem">
	                       <span>${c.comment_id}</span>
	                       <span>${c.useremail}</span>
	                       <span class="comment-content">${c.comment_content}</span>
	                       <span>
	                          <input type="text" class="edit-comment" style="display: none;" value="${c.comment_content}">
	                       </span>
	                       <span class="comment-regdate">${c.comment_regdate}</span>
	                       
	                       <c:if test="${sessionScope.loginUser.useremail == c.useremail}">
	                          <span>
	                             <button data-idx="${c.comment_id}" class="edit-btn">수정</button>
	                          </span>
	                          <span>
	                             <button data-idx="${c.comment_id}" class="delete-btn">삭제</button>
	                          </span>
	                       </c:if>
	                       
	                       <a id="reply-btn" onclick="toggleReply(${c.comment_id})">답글달기</a>
	                    </li>

                     <li class="commentReplyLI" id="reply-${c.comment_id}" style="display: none;">
                        <form action="commentReply" method="post">
                           <span>${sessionScope.loginUser.useremail}</span>
                           <input type="hidden" id="board_id" name="board_id" value="${c.board_id}" />
                           <input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail }" />
                           <input type="hidden" id="comment_root" name="comment_root" value="${c.comment_id}" />
                           <input type="hidden" id="comment_steps" name="comment_steps" value="${c.comment_steps}" />
                           <input type="hidden" id="comment_indent" name="comment_indent" value="${c.comment_indent}" />
                           <input type="text" id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required />
                           <button>등록</button>
                        </form>
                     </li>
                  </c:forEach>

				</c:if>

				<c:if test="${empty requestScope.commentsList}">
					<li><span>게시글에 작성된 댓글이 없습니다.</span></li>
				</c:if>
			</ul>
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

		<div class="home-link">
			<a href="/home">Home</a>
		</div>
	</div>


</body>
</html>