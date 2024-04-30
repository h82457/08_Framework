const updateBtn = document.querySelector(".update-btn")

/* 수정 버튼 클릭 시 */
if(updateBtn!=null){
    updateBtn.addEventListener("click",()=>{
    
        location.href =  location.pathname.replace('adoptionList', 'editAdoption')
                     + "/update"
                     + location.search;
        
        
    })
}
const deleteBtn = document.querySelector(".delete-btn");

/* 삭제 버튼 클릭 시 */
if(deleteBtn!=null){

    deleteBtn.addEventListener("click",()=>{
        if(!confirm("삭제하시겠습니까?")){
            alert("취소됨")
            return;
        }

        const url = location.pathname.replace("adoptionList","editAdoption") +"/delete";

        const form = document.createElement("form")
        form.action = url;
        form.method = "POST";

        // cp값을 저장할 input 생성
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "cp";

        // 쿼리스트링에서 원하는 파라미터 얻어오기
        const params = new URLSearchParams(location.search)
        const cp = params.get("cp");
        input.value = cp;

        form.append(input);

        // 화면에 form태그를 추가한 후 제출하기
        document.querySelector("body").append(form);
        form.submit();
    })

}
    

/* 북마크 체크 시 bookmarkCheck 'N' -> 'Y'로 update */

const bookmarkCheck = document.querySelector("#bookmarkCheck");
bookmarkCheck.addEventListener("click", e=>{


    const obj ={
     "memberNo" : loginMemberNo,
     "adoptNo"  : adoptNo,
     "bookmark" : bookmark

    };


    // 북마크 insert/ delete 비동기 요청
    fetch("/adoption/bookmark",{
        
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
        bookmark = bookmark == 0? 1: 0;

        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");
        

    });

});


