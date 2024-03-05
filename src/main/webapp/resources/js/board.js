// 게시글 삭제
function axboardDelete(board_id) {
    let url = "/board/deleteBoard/" + board_id;

    if (confirm("삭제하시겠습니까?")) {
        axios.post(url)
            .then(response => {
                window.location.href = "/board/boardPage";
                alert("삭제되었습니다.");
            })
            .catch(err => {
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
    }, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
            let likeCountElement = document.getElementById('likeCount');
            let currentLikeCount = parseInt(likeCountElement.textContent);

            if (response.status === 200) {
                likeCountElement.textContent = currentLikeCount + 1; // 좋아요 추가
            } else if (response.status === 204) {
                likeCountElement.textContent = currentLikeCount - 1; // 좋아요 삭제
            }
        });
}


// 댓글 수정
document.addEventListener('DOMContentLoaded', function () {
    var editButtons = document.querySelectorAll('.edit-btn');

    editButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            var listItem = button.closest('li');  // 변경된 부분
            var commentContentSpan = listItem.querySelector('.comment-content');  // 변경된 부분
            var editCommentInput = listItem.querySelector('.edit-comment');  // 변경된 부분

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
                button.textContent = '완료';
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


// 댓글 삭제
document.addEventListener('DOMContentLoaded', function () {
    var deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            handleDeleteButtonClick(button);
        });
    });

    function handleDeleteButtonClick(button) {
        var listItem = button.closest('li');  
        var commentId = button.getAttribute('data-idx');
        deleteCommentOnServer(commentId);

        // 서버 응답 확인
        // 삭제 후 서버로부터 응답을 받지 않고 화면 갱신을 수행할 경우
        listItem.style.display = 'none'; 
    }
});

function deleteCommentOnServer(commentId) {
    var xhr = new XMLHttpRequest();
    xhr.open('DELETE', '/board/deleteComments?comment_id=' + commentId, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // 삭제할 댓글의 ID를 서버로 전송
    var formData = 'comment_id=' + commentId;
    xhr.send(formData);

    // 서버 응답 확인
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log('댓글이 성공적으로 삭제되었습니다.');
                // 삭제 성공 시 적절한 화면 갱신 로직 추가
                    window.location.reload();
            } else {
                console.error('댓글 삭제에 실패했습니다.');
            }
        }
    };
}

// 답글달기 및 답글 입력 폼 토글
function toggleReply(comment_id) {
    const replyContainer = document.getElementById(`reply-${comment_id}`);
    replyContainer.style.display = (replyContainer.style.display === 'none') ? 'block' : 'none';
}
