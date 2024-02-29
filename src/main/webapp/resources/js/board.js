
// 게시글 삭제
function axboardDelete(board_id) {
	let url = "/board/deleteBoard/" + board_id;

	if (confirm("삭제하시겠습니까?")) {
		axios.post(url
		).then(response => {
			window.location.href = "/board/boardPage";
			alert("삭제되었습니다.");

		}).catch(err => {
			if (err.response && err.response.status === 502) {
				alert("[삭제 오류]" + err.response.data);
			} else {
				alert("[시스템 오류]" + err.message);
			}
		});
	} else {
		alert("취소되었습니다.");
	}
}

// 좋아요
function toggleLikes(board_id, useremail) {
	let url = "/board/toggle";

	axios.post(url, {
		useremail: useremail,
		board_id: parseInt(board_id)
	}, { headers: { 'Content-Type': 'application/json' } }

	).then(response => {
		let likeCountElement = document.getElementById('likeCount');
		let currentLikeCount = parseInt(likeCountElement.textContent);

		if (response.status === 200) {
			likeCountElement.textContent = currentLikeCount + 1; // 좋아요 추가
		} else if (response.status === 204) {
			likeCountElement.textContent = currentLikeCount - 1; // 좋아요 삭제
		}
	})
}

// 댓글 수정
    document.addEventListener('DOMContentLoaded', function () {
        // 모든 수정 버튼에 이벤트 리스너 추가
        var editButtons = document.querySelectorAll('.edit-btn');
        editButtons.forEach(function (button) {
            button.addEventListener('click', function () {
                // 부모 행 가져오기
                var row = button.closest('tr');

                // span 및 input 요소의 표시 전환
                var commentContentSpan = row.querySelector('.comment-content');
                var editCommentInput = row.querySelector('.edit-comment');
                commentContentSpan.style.display = 'none';
                editCommentInput.style.display = 'block';

                // 버튼 텍스트를 '수정 완료'로 변경
                button.textContent = '수정 완료';
                // 버튼을 업데이트 버튼으로 식별하기 위해 클래스 변경
                button.classList.add('update-btn');

                // 업데이트 버튼에 이벤트 리스너 추가
                button.addEventListener('click', function () {
                    // 입력에서 업데이트된 댓글 내용 가져오기
                    var updatedComment = editCommentInput.value;

                    // span의 내용 업데이트
                    commentContentSpan.textContent = updatedComment;

                    // span 및 input 요소의 표시 전환
                    commentContentSpan.style.display = 'inline';
                    editCommentInput.style.display = 'none';

                    // 버튼 텍스트를 다시 '수정'으로 변경
                    button.textContent = '수정';
                    // 'update-btn' 클래스 제거
                    button.classList.remove('update-btn');
                });
            });
        });
    });

// 완료 버튼 클릭 시, 수정된 내용을 서버로 전송하여 업데이트
   document.addEventListener('DOMContentLoaded', function () {
        var editButtons = document.querySelectorAll('.edit-btn');
        editButtons.forEach(function (button) {
            button.addEventListener('click', function (event) {
                event.preventDefault();

                var row = button.closest('tr');
                var commentContentSpan = row.querySelector('.comment-content');
                var editCommentInput = row.querySelector('.edit-comment');
                commentContentSpan.style.display = 'none';
                editCommentInput.style.display = 'block';
                button.textContent = '수정 완료';
                button.classList.add('update-btn');

                button.addEventListener('click', function () {
                    var updatedComment = editCommentInput.value;
                    commentContentSpan.textContent = updatedComment;
                    commentContentSpan.style.display = 'inline';
                    editCommentInput.style.display = 'none';
                    button.textContent = '수정';
                    button.classList.remove('update-btn');

                    // Ajax를 사용하여 수정된 댓글을 서버로 전송
                    var commentId = button.getAttribute('data-idx');

                    updateCommentOnServer(commentId, updatedComment);
                });
            });
        });

        function updateCommentOnServer(commentId, updatedComment) {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/board/updateComments', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            // 수정된 댓글 데이터를 서버로 전송
            var formData = 'comment_id=' + commentId + '&comment_content=' + encodeURIComponent(updatedComment);  
            xhr.send(formData);
        }
    });
