
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



