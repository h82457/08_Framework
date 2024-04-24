
/** 댓글 목록 조회(ajax)
 * 
 */
const selectCommentList = () => {

    fetch("/comment?boardNo=" + boardNo) // GET방식 요청, boardNo <- boardList.html에서 전역변수로 선언
    .then( resp => resp.json())
    .then( commentList => {

        // ul 태그( 댓글 목록 감싸는 요소)
        const ul = document.querySelector("#commentList");

        ul.innerHTML = ""; // 기존 댓글 목록 삭제

        /* ************* 조회된 commentList를 이용해 댓글 출력 ************* */ 
        
        for(let comment of commentList){

            // 행(li) 생성 + 클래스 추가 (ul > li)
            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // > 대댓글(자식 댓글)인 경우 "child-comment" 클래스 추가
            if(comment.parentCommentNo != 0) commentRow.classList.add("child-comment");

            // > 만약 삭제된 (부모)댓글이지만 자식 댓글이 존재하는 경우
            if(comment.commentDelFl == 'Y') commentRow.innerText = "삭제된 댓글 입니다.";

            // > 삭제되지 않은 댓글
            else { 
                
                // 작성자 정보를 감싸는 p태그 요소 생성 (li > p)
                const commentWriter = document.createElement("p");
                commentWriter.classList.add("comment-writer");

                // p태그 하위로 들어가는 목록 요소 생성 (프로필 이미지, 날짜(작성일))
                const profileImg = document.createElement("img");

                if(comment.profileImg == null)  profileImg.src = userDefaultImage;
                //              기본 프로필 이미지 boardDetail.html에서 전역변수로 선언후 사용
                else    profileImg.src = comment.profileImg;

                const nickname = document.createElement("span"); // 닉네임
                nickname.innerText = comment.memberNickname;

                const commentDate = document.createElement("span"); // 작성날짜
                commentDate.classList.add("comment-date");
                commentDate.innerText = comment.commentWriteDate;

                // commentWriter(작성자정보 영역)에 하위 태그 추가 + commentRow(댓글행)에 추가
                commentWriter.append(profileImg, nickname, commentDate);
                commentRow.append(commentWriter);

                // 댓글 내용 영역 생성,내용 추가 + commentRow(댓글행)에 추가
                const content = document.createElement("p");
                content.classList.add("comment-content");
                content.innerText = comment.commentContent;
                commentRow.append(content);

                // 버튼 영역 생성
                const commentBtnArea = document.createElement("div");
                commentBtnArea.classList.add("comment-btn-area");

                // 답글 버튼 추가 + onclick 이벤트 리스너 속성 추가, this : 클릭한 버튼 객체
                const childCommentBtn = document.createElement("button");// 답글 버튼
                childCommentBtn.innerText = "답글";
                childCommentBtn.setAttribute("onclick", `showInsertComment(${comment.commntNo}, this)`);

                commentBtnArea.append(childCommentBtn); // 버튼 영역에 답글 추가
                commentRow.append(commentBtnArea); // commentRow(댓글행)에 버튼 영역 추가

                // 로그인한 회원 번호, 댓글 작성자 번호가 같을때(작성자본인일때) - 댓글 수정/삭제 버튼 출력
                if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                    // 수정 버튼
                    const updateBtn = document.createElement("button");
                    updateBtn.innerText = "수정";
                    updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commntNo}, this)`);

                    // 삭제 버튼
                    const deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "삭제";
                    deleteBtn.setAttribute("onclick", `deleteComment(${comment.commntNo})`);

                    // 버튼 영역에 수정/삭제 버튼 추가
                    commentBtnArea.append(updateBtn, deleteBtn);
                }
                ul.append(commentRow); // 댓글 목록(ul)에 행(li) 추가
            }
        }
    });
}

// ------------------------------------------------------------------------------

/* ***** 댓글 등록(ajax) ***** */

const addContent = document.querySelector("#addComment"); // 댓글 등록 버튼
const commentContent = document.querySelector("#commentContent"); // 댓글 입력 textarea

// 댓글 등록 버튼 클릭시
addContent.addEventListener("click", e => {

    // > 미로그인시
    if(loginMemberNo == null) {
        alert("로그인후 이용해주세요.");
        return; // <- early return; 중간에 리턴 넣어서 함수 종료
    }

    // > 댓글 내용이 작성되지 않은 경우
    if(commentContent.value.trim().length == 0){
        alert(" 내용 작성후 등록 버튼을 클릭해주세요.");
        commentContent.focus();
        return;
    }

    // > 댓글 등록 요청 (ajax 이용)
    const data = {
        "commentContent" : commentContent.value,
        "boardNo" : boardNo,
        "memberNo" : loginMemberNo // 또는 컨트롤러에서 Session에 담긴 회원 번호 활용도 가능
    };

    fetch("/comment", {
        method : "POST",
        headers : {"content-Type" : "application/json"},
        body : JSON.stringify(data)// data 객체를 JSON 문자열로 변환
    })

    .then( resp => resp.text() )
    .then( result => {
        
        if(result > 0) {
            alert("댓글이 등록되었습니다.");
            commentContent.value = ""; // textarea에서 작성된 내용 지우기
            selectCommentList(); // 댓글 목록을 다시 조회, 화면 출력
        }
        else alert("댓글 등록 실패");
    })
    .catch( err => console.log(err));
});

/** 답글(자식댓글) 작성 화면 추가
 * 
 * @param {*} parentCommentNo 
 * @param {*} btn 
 */
const showInsertComment = (parentCommentNo, btn) => {

    // 답글 작성 textarea가 한 개만 열릴 수 있도록 만들기 ()
    const temp = document.getElementsByClassName("commentInsertContent");
  
    if(temp.length > 0){ // 답글 작성 textarea가 이미 화면에 존재하는 경우
  
      if(confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")){
        temp[0].nextElementSibling.remove(); // 버튼 영역부터 삭제
        temp[0].remove(); // textara 삭제 (기준점은 마지막에 삭제해야 된다!)
      
      } else{
        return; // 함수를 종료시켜 답글이 생성되지 않게함.
      }
    }
    
    // 답글을 작성할 textarea 요소 생성
    const textarea = document.createElement("textarea");
    textarea.classList.add("commentInsertContent");
    
    // 답글 버튼의 부모의 뒤쪽에 textarea 추가, after(요소) : 뒤쪽에 추가
    btn.parentElement.after(textarea);
  
    // 답글 버튼 영역 + 등록/취소 버튼 생성 및 추가
    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");
  
    const insertBtn = document.createElement("button");
    insertBtn.innerText = "등록";
    insertBtn.setAttribute("onclick", "insertChildComment("+parentCommentNo+", this)");
  
    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "insertCancel(this)");
  
    // 답글 버튼 영역의 자식으로 등록/취소 버튼 추가
    commentBtnArea.append(insertBtn, cancelBtn);
  
    // 답글 버튼 영역을 화면에 추가된 textarea 뒤쪽에 추가
    textarea.after(commentBtnArea);
}
// -------------

/** ***** 답글(자식 댓글) 작성 취소 ****** 
 * 취소 버튼을 감싸고 있는 요소의 이전 요소 삭제한 다음 다시 버튼을 감싸고 있는 요소 삭제
     ㄴ 버튼을 감싸고 있는 기준 요소를 먼저 지우면 나중에 지정이 불가능, 기준은 가장 나중에 삭제!
 * @param {*} cancelBtn 
 */
const insertCancel = (cancelBtn) => {

    cancelBtn.parentElement.previousElementSibling.remove(); // 취소 버튼 부모의 이전 요소 (textarea) 삭제
    cancelBtn.parentElement.remove(); //  취소 버튼이 존재하는 버튼 영역 삭제
}

/** ***** 답글(자식 댓글) 작성 등록 ***** 
 * 
 * @param {*} parentCommentNo : 부모 댓글 번호
 * @param {*} btn : 클릭된 등록 버튼
 */
const insertChildComment = (parentCommentNo, btn) => {

    const textarea = btn.parentElement.previousElementSibling; // 답글 내용이 작성된 textarea

    // 유효성 검사
    if(textarea.value.trim().length == 0){

        alert("내용 작성후 등록 버튼을 클릭해주세요");
        textarea.focus();
        return;
    }
    
    // 답글(자식댓글) 등록 요청 (ajax 이용)
    const data = {
        "commentContent" : textarea.value,
        "boardNo" : boardNo,
        "memberNo" : loginMemberNo,
        "parentCommentNo" : parentCommentNo // 부모 댓글 번호
    }

    fetch("/comment",{
        method : "POST",
        headers : {"content-Type" : "application/json"},
        body : JSON.stringify(data)// data 객체를 JSON 문자열로 변환
    })

    .then( resp => resp.text() )
    .then( result => {
        
        if(result > 0) {
            alert("답글이 등록되었습니다.");
            selectCommentList(); // 댓글 목록을 다시 조회, 화면 출력
        }
        else alert("답글이 등록 실패");
    })
    .catch( err => console.log(err));
}

// -----------------------------
/** 댓글 삭제
 * @param {*} commentNo 
 */
const deleteComment = commentNo => {
    if( !confirm("삭제 하시겠습니까?")) return;

    fetch("/comment", {
        method : "DELETE",
        headers : {"content-Type" : "application/json"},
        body : commentNo
    })
    .then( resp => resp.text() )
    .then( result => {

        if(result > 0) {
            alert("삭제되었습니다.");
            selectCommentList();
        }
        else alert("삭제 실패");
    })
    .catch( err => console.log(err));
}

// -------------------------------------

// 수정 취소시 이전 댓글 형태로 돌아가기 위한 백업 변수 선언
let beforeCommentRow; 


/**댓글 수정 화면 전환
 * 
 * @param {*} commentNo 
 * @param {*} btn 
 */
const showUpdateComment = (commentNo, btn) => {

    /* 댓글 수정 화면이 1개만 열릴 수 있게 하기 */ 
    const temp = document.querySelector(".update-textarea"); // 5번에서 수정창 생성

    if(temp != null){ // .update-textarea 존재 == 열려있는 댓글 수정창이 존재

        if(confirm("수정중인 댓글이 있습니다. 현재 댓글을 수정 하시겠습니까?")){

            const commentRow = temp.parentElement; // 기존 댓글 행
            commentRow.after(beforeCommentRow); // 기존 댓글 다음에 백업 추가
            commentRow.remove(); // 기존 삭제 -> 백업이 기존행 위치로 이동
        }
        else return; // 취소
    }
    // ----

    // 1.  댓글 수정이 클릭된 행(.comment-row) 선택
    const commentRow = btn.closest("li");
    // console.log(commentRow);

    // 2. 행 전체를 백업 ( 요소.cloneNode(true): 요소 복제, 매개변수 true == 하위요소도 복제)
    beforeCommentRow = commentRow.cloneNode(true);
    // console.log(beforeCommentRow);

    // 3. 기존 댓글에 작성되어있던 내용만 얻어오기
    let beforeComment = commentRow.children[1].innerText;

    // 4. 댓글 행 내부를 모두 삭제
    commentRow.innerHTML = "";

    // 5. textarea 생성 + 클래스 추가 + 내용 추가
    const textarea = document.createElement("textarea");
    textarea.classList.add("update-textarea");
    textarea.value = beforeComment;

    // 6. 댓글행에 textarea 추가
    commentRow.append(textarea);

    // 7. 버튼 영역 생성
    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    // 8. 수정 버튼 생성
    const updateBtn = document.createElement("button");
    updateBtn.innerText = "수정";
    updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);

    // 9. 취소 버튼 생성
    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "updateCancle(this)");

    // 10. 버튼 영역 수정/ 취소 버튼 추가 후 댓글행에 버튼 영역 추가
    commentBtnArea.append(updateBtn, cancelBtn);
    commentRow.append(commentBtnArea);

}

/** 댓글 수정 취소
 * 
 * @param {*} btn 
 */
const updateCancle = (btn) => {

    if(confirm("취소 하시겠습니까?")){

        const commentRow = btn.closest("li"); // 버튼에서 가장 가까운 li태그, 기존 댓글행
        commentRow.after(beforeCommentRow); // 기존 댓글 다음에 백업 추가
        commentRow.remove(); // 기존 삭제 -> 백업이 기존행 위치로 이동
    }
}

/** 댓글 수정
 * 
 * @param {*} commentNo : 수정할 댓글 번호
 * @param {*} btn : 클릭된 수정 버튼
 */
const updateComment = (commentNo, btn) => {

    // 수정된 내용이 작성된 textarea 얻어오기
    const textarea = btn.parentElement.previousElementSibling;

    // 유효성 검사
    if(textarea.value.trim().length == 0){
        alert("댓글 작성 후 수정버튼을 클릭해주세요.");
        textarea.focus();
        return;
    }

    // 댓글 수정 (ajax)
    const data = {
        "commentNo" : commentNo,
        "commentContent" : textarea.value
    }

    fetch("/comment", {
        method : "PUT",
        headers : {"content-Type" : "application/json"},
        body : JSON.stringify(data)
    })

    .then( resp => resp.text() )
    .then( result => {

        if(result > 0){
            alert("댓글이 수정되었습니다.");
            selectCommentList();
            
        } else alert("댓글 수정 실패");
    })
    .catch( err => console.log(err));
}