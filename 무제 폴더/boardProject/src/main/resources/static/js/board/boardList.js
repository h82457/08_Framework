
/* 글쓰기 버튼 클릭시 */
const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼이 존재할때 (로그인상태)
if(insertBtn != null){

    insertBtn.addEventListener("click", () => {
        
        // alert(boardCode);

        // * boardCode 얻어오는 방법 *
        // 1) @PathVariable("boardCode") 얻어와 전역 변수 저장
        // 2) location.pathname.split("/")[2]

        location.href = `/editBoard/${boardCode}/insert`; // ${}는 el이 아닌 `` 안에서 변수명으로 사용
    });
}