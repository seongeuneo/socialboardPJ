
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
$(document).on('click', '.edit-btn', function() {
	let commentId = $(this).data('idx');
	let contentSpan = $(`tr:has(button[data-idx="${commentId}"]) td span.comment-content`);
	let contentInput = $(`tr:has(button[data-idx="${commentId}"]) td input.edit-comment`);

	// 현재 댓글 내용을 input에 설정
	contentInput.val(contentSpan.text());

	// span을 숨기고 input을 보이게 변경
	contentSpan.hide();
	contentInput.show();

	// 수정 버튼을 수정 완료 버튼으로 변경
	$(this).text('완료');
	// 클래스를 'edit-btn'에서 'complete-btn'으로 변경 (이벤트 핸들러 변경)
	$(this).removeClass('edit-btn').addClass('complete-btn');
});

// 완료 버튼 클릭 시, 수정된 내용을 서버로 전송하여 업데이트
$(document).on('click', '.complete-btn', function() {
	let commentId = $(this).data('idx');
	let contentInput = $(`tr:has(button[data-idx="${commentId}"]) td input.edit-comment`);
	let url = "/board/updateComments";

	// 수정된 내용 서버로 전송
	axios.post(url, {
		"comment_id": commentId,
		"content": contentInput.val()
	})
		.then(response => {
			// 성공 시, 서버에서 온 데이터로 업데이트
			// (서버에서 업데이트된 댓글 정보를 반환하는 것으로 가정)
			let updatedContent = response.data.comment_content;
			let contentSpan = $(`tr:has(button[data-idx="${commentId}"]) td span.comment-content`);

			// span을 보이고 input을 숨기게 변경
			contentSpan.text(updatedContent).show();
			contentInput.hide();

			// 완료 버튼을 수정 버튼으로 변경
			$(`.complete-btn[data-idx="${commentId}"]`).text('수정');
			// 클래스를 'complete-btn'에서 'edit-btn'으로 변경 (이벤트 핸들러 변경)
			$(`.complete-btn[data-idx="${commentId}"]`).removeClass('complete-btn').addClass('edit-btn');
		})
		.catch(error => {
			alert('리뷰 수정 실패');
		});
});
