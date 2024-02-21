
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

$(document).ready(function() {
    // 이모지 클릭 이벤트 리스너 등록
    $('#likeButton').click(function() {
        // 클릭 시 서버로 요청 보내기
        $.ajax({
            type: 'POST',
            url: '/board/toggle', // 해당 URL은 실제로 컨트롤러에 매핑되는 엔드포인트여야 합니다.
            contentType: 'application/json', // JSON 데이터를 전송한다고 명시
            data: JSON.stringify({
                useremail: '사용자 이메일', // 실제 사용자 이메일로 대체
                boardId: 123 // 좋아요를 누른 게시글 ID로 대체
            }),
            success: function(response) {
                console.log('좋아요가 추가되었습니다.');
            },
            error: function(error) {
                console.error('좋아요 추가 중 오류 발생:', error);
            }
        });
    });
});




