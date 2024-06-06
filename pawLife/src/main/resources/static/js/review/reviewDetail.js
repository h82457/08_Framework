// const updateBtn = document.querySelector(".update-btn")

// updateBtn.addEventListener("click",()=>{
//     location.href="reviewUpdate"
// })


/* 댓글 목록 조회 */
const selectCommentList = () => {


    fetch("/comment?reviewNo=" + reviewNo)
    .then(resp => resp.json())
    .then(commentList => {

        const ul = document.querySelector("#commentList");
        ul.innerHTML = "";

        for(let comment of commentList) {

            const li = document.createElement("li");
            li.classList.add("comment-row");

            // 작성자 (p태그)
            const commentWriter = document.createElement("p");
            commentWriter.classList.add("comment-writer");

            // p태그 하위 요소
            const profileImg = document.createElement("img");
            // 이미지 추가할것
            // if(comment.profileImg == null)  profileImg.src = userDefaultImage;
            // else    profileImg.src = comment.profileImg;

            const nickname = document.createElement("span");
            nickname.innerText = comment.memberNickname;

            const commentDate = document.createElement("span");
            commentDate.classList.add("comment-date");
            commentDate.innerText = comment.commentWriteDate;

            commentWriter.append(profileImg, nickname, commentDate);
            //li.append(commentWriter);

            // 덧글 내용
            const content = document.createElement("p");
            content.classList.add("comment-content");
            content.innerText = comment.commentContent;
            li.append(commentWriter, content);

            // 버튼
            const commentBtnArea = document.createElement("div");
            commentBtnArea.classList.add("comment-btn-area");

            if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                const updateBtn = document.createElement("button");
                updateBtn.innerText = "수정";
                updateBtn.setAttribute("onclick", `UpdateComment(${comment.commentNo}, this)`);

                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "삭제";
                deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo})`);

                // 버튼 영역에 수정/삭제 버튼 추가
                commentBtnArea.append(updateBtn, deleteBtn);
                li.append(commentBtnArea);
            }
            ul.append(li);
        }
    })
}

/* 댓글 등록 */
const commentContent = document.querySelector("#commentContent");
const addComment = document.querySelector("#addComment");

addComment.addEventListener("click", e => {

    if(loginMemberNo == null) {
        alert("로그인후 이용해주세요.");
        return;
    }

    if(commentContent.value.trim().length == 0){
        alert(" 내용 작성후 등록 버튼을 클릭해주세요.");
        commentContent.focus();
        return;
    }

    const data = {
        "commentContent" : commentContent.value,
        "reviewNo" : reviewNo,
        "memberNo" : loginMemberNo
    };

    fetch("/comment", {
        method : "POST",
        headers : {"content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("댓글이 등록되었습니다.");
            commentContent.value = "";
            selectCommentList();
        }
        else alert("댓글 등록 오류");
    })
    .catch( err => console.log(err));
});


/* 댓글 삭제 */
const deleteComment = commentNo => {

    console.log(commentNo);

    if( !confirm("삭제 하시겠습니까?")) {
        return;
    }

    fetch("/comment", {
        method : "DELETE",
        headers : {"content-Type" : "application/json"},
        body : commentNo
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("댓글이 삭제되었습니다.");
            selectCommentList();
        }
        else    alert("댓글 삭제 실패");
    })
    .catch( err => console.log(err));

};





selectCommentList();