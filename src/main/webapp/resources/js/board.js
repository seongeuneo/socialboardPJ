
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

function toggleLikes(board_id, useremail) {
	let url = "/board/toggle";

	axios.post(url, {
		useremail: useremail,
		board_id: parseInt(board_id)
	}, { headers: { 'Content-Type': 'application/json' }  }

	).then(response => {
		let likeCountElement = document.getElementById('likeCount');
		let currentLikeCount = parseInt(likeCountElement.textContent);

		if (response.status === 200) {
			likeCountElement.textContent = currentLikeCount + 1; // 좋아요 추가
		} else if (response.status === 204) {
			likeCountElement.textContent = currentLikeCount - 1; // 좋아요 삭제
		}


	}).catch(err => {
		if (err.response && err.response.status === 502) {
			alert("[삭제 오류]" + err.response.data);
			alert("[시스템 오류]" + err.message);
		}
	});

}




