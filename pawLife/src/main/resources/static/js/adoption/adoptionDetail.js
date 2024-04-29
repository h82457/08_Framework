const updateBtn = document.querySelector(".update-btn")

updateBtn.addEventListener("click",()=>{
    location.href="/adoptionUpdate"
})


/* 북마크 체크 시 bookmarkCheck 'N' -> 'Y'로 update */

const bookmarkCheck = document.querySelector("#bookmarkCheck");
bookmarkCheck.addEventListener("click", e=>{


    const obj ={
     "memberNo" : loginMemberNo,
     "boardNo"  : boardNo,
     "bookMark" : bookMark

    };


    // 북마크 insert/ delete 비동기 요청
    fetch("/adopt/bookMark",{
        
        method  : "POST",
        headers : {"Content-Type" : "application/json"},
        body    : JSON.stringify(obj) // 객체를 Json으로 문자화 

    })
       .then(resp =>resp.text()) // 반환 결과 text(글자) 형태로 변환
       .then(count =>{


        if(count == -1){
            console.log("북마크 실패");
            return;
        }

        // bookMark 값 0<->1 변환 ( 클릭될 때 마다 insert/ delete 동작 번갈아 수행)
        bookMark = bookMark == 0? 1: 0;

        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

    });

});