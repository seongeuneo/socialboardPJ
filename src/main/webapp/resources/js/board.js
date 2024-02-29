
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
    var editButtons = document.querySelectorAll('.edit-btn');

    editButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            var row = button.closest('tr');
            var commentContentSpan = row.querySelector('.comment-content');
            var editCommentInput = row.querySelector('.edit-comment');
            
            if (button.classList.contains('update-btn')) {
                // 현재 버튼이 '수정 완료' 상태일 때
                var updatedComment = editCommentInput.value;
                commentContentSpan.textContent = updatedComment;
                commentContentSpan.style.display = 'inline';
                editCommentInput.style.display = 'none';
                button.textContent = '수정';
                button.classList.remove('update-btn');

                // Ajax를 사용하여 수정된 댓글을 서버로 전송
                var commentId = button.getAttribute('data-idx');
                updateCommentOnServer(commentId, updatedComment);
            } else {
                // 현재 버튼이 '수정' 상태일 때
                commentContentSpan.style.display = 'none';
                editCommentInput.style.display = 'block';
                button.textContent = '수정 완료';
                button.classList.add('update-btn');
            }
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
