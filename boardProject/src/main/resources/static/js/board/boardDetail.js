/* 좋아요 버튼 클릭시 비동기로 좋아요 INSERT / DELETE */

// 1) 로그인한 회원 번호 준비 --> session에서 얻어오기
// (session은 서버에서 관리 -> JS로 불가, html 하단 스크립트 태그 내부에 작성)
// 2) 현재 게시글 번호 준비
// 3) 좋아요 여부 준비

// 1. #boardLike가 클릭 되었을때
const boardLike = document.querySelector("#boardLike");
boardLike.addEventListener("click", e => {

    // 2. 로그인 상태가 아닌 경우 동작 X
    if(loginMemberNo == null){
        alert("로그인 후 이용해 주세요");
        return;
      }

    // 3. 준비된 3개의 변수를 객체로 저장 (JSON 변환 예정)
    const obj = {
        "memberNo"  : loginMemberNo,
        "boardNo"   : boardNo,
        "likeCheck" : likeCheck
      };

    // 4. 좋아요 INSERT / DELETE 비동기 요청
    fetch("/board/like", {
        method  : "POST",
        headers : {"Content-Type" : "application/json"},
        body    : JSON.stringify(obj) // <- 객체를 Json 형식으로 문자열화해서 body에 담아 전달 
    })
    .then(resp => resp.text()) // 반화 결과 text(글자) 형태로 변환
    .then(count => { // count == 첫번째 then로 파싱되어 반환된 값 (-1 또는 게시글 좋아요 수)

        console.log("count : ", count);

        if(count == -1){
            console.log("좋아요 처리 실패");
            return;
          }

        // 5. likeCheck 값 0 <-> 1 (클릭시마다 INSERT/DELETE 동작을 번갈아 수행 위함)
        likeCheck = likeCheck == 0 ? 1 : 0;

        // 6. 하트를 채웠다/비웠다 바꾸기
        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        // 7. 게시글 좋아요 수 수정
        e.target.nextElementSibling.innerText = count;
    });
});


/* 게시글 삭제 */
const deleteBtn = document.querySelector("#deleteBtn");

deleteBtn.addEventListener("click", () => {

  if ( !window.confirm("삭제 하시겠습니까?")) {
    return;
  }
      location.href = `/editBoard/${boardCode}/${boardNo}/delete`;


});

/* -------------------------------------------------------- */
/* 게시글 수정 버튼 */
const updateBtn = document.querySelector("#updateBtn");

if(updateBtn != null){ // 수정버튼 존재시

  updateBtn.addEventListener("click", e => {

    // 현재 주소 : /board/1/2005?cp=1
    // 목표 주소 : /editBoard/1/2005/update?cp=1 (GET 방식)
    location.href = location.pathname.replace('board', 'editBoard') + "/update" + location.search;
  });

}

/* -------------------------------------------------------- */
/* 목록으로 버튼 */
const goToListBtn = document.querySelector("#goToListBtn");

goToListBtn.addEventListener("click", () => {

  // 상세조회 : /board/1/2011?cp=1
  // 목록     : /board/1?cp=1
  let url = location.pathname;
  url = url.substring(0, url.lastIndexOf("/"));
  location.href = url + location.search;
})
