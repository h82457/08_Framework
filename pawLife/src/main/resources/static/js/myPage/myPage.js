// 마이 페이지 -> 개인 정보 수정 페이지로 보내는 동작
const profileUpdateBtn= document.querySelector("#profileUpdateBtn");

profileUpdateBtn.addEventListener("click", () => {
  location.href = "myPage-profileupdate"; 
  
});

//------------------------------------------------------------------

/* 화면 비동기로 바꾸는 버튼 얻어오기 */

/* 프로필 버튼 */
// -- 클릭하면 이전 내용을 지우고 수정/저장된 자료를 보여줌

// 수정/저장된 회원 정보
const profileListBtn = document.querySelector("#profileListBtn");

/* session에 loginMember로 있는 memberNo의 입양 리스트 최신 순서로 조회함 */
const adoptionListBtn = document.querySelector("#adoptionListBtn");

/* session에 loginMember로 있는 memberNo의 게시글 전부 조회 */
const boardListBtn = document.querySelector("#boardListBtn");

/* -- memberNo의 댓글 모음  */
const commentListBtn = document.querySelector("#commentListBtn");

/* memberno가 좋아요 누른 게시글 조회 */
const likeListBtn = document.querySelector("#likeListBtn");


/* ----------------------------------------------------------- */

// 비동기로 내용 불러올 공간
const profilebox = document.querySelector("#profileContainer");

// 내가 값을 출력할id를 가져와서 

//  이전 내용 삭제하고 조회 및 출력하는  코드

//--------------------------------------------------------------------
/* 내가 쓴 게시글 비동기로 조회하기 */

// 함수 만들기
const selectMyReviewBoard = () =>{

 
  fetch("/myPage/selectReview")

  .then(response => response.json())

  .then(reviewList=>{
     // list를 담아오자
    
     // list 확인
     console.log(reviewList);

   
     profilebox.innerHTML =""; // 기존 내용 지우기

     const table = document.createElement("table");
     table.id='reviewbox'
     const tbody = document.createElement("tbody");
     tbody.id='reviewList'
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

      
      /* 테이블 헤더 만들기 */
      const headers=["게시글 제목", "조회수","게시글 작성일"];
      for(let head of headers){
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let review of reviewList){
   

      const arr = ['reviewTitle', 'readCount', 'reviewWriteDate'];
      const tr= document.createElement("tr");

      for(let key of arr){
        const td = document.createElement("td");

        // 제목인 경우
         if(key== 'reviewTitle'){
          const a= document.createElement("a");
          a.innerText = review[key]; // 제목을 a 태그 내용으로 대입
          a.href="/review/reviewDetail?reviewNo=" + review.reviewNo;
          td.append(a);
        
         }else{
  
          td.innerText = review[key];
         }

        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     

     
   
  });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
boardListBtn.addEventListener('click',()=>{

  // 화면 비워주고
  // myPage의 section 내부 -> 여기 내부를 비우고 다른 화면을 부르는 코드를 쓴다

  // 다른 화면이 보이는 코드
    // profilecontainer가 reviewList로 바꿔어야 함
   /*  getInnerHTML(); */
   selectMyReviewBoard();



});








/* ---- 내가 쓴 댓글 비동기로 조회하기 ---- */

// 함수 만들기
const selectMyCommentBoard = () =>{

 
  fetch("/myPage/selectComment")

  .then(resp => resp.json())

  .then(commentList=>{
     // list를 담아오자
    console.log(commentList);

     profilebox.innerHTML =""; // 기존 내용 지우기


     if (commentList == null || commentList.length === 0) {
      // commentList가 null이거나 빈 배열인 경우
      // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

     // 기존 요소 지우기
     const message = document.getElementById("noCommentMessage");
     if (message) {
         message.innerHTML = ""; // 내용 지우기
     } else {
         // 내용 설정
     
     message.innerHTML = "아직 작성된 댓글이 없습니다";
     }
 
   


  } else {
      // commentList에 값이 있는 경우
      // 댓글을 화면에 표시하는 코드 추가

      const table = document.createElement("table");
      table.id="commentBox"
      const tbody = document.createElement("tbody");
      tbody.id="commentList"
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

            
      /* 테이블 헤더 만들기 */
      const headers=["게시글 제목","댓글","댓글 작성일"];
      for(let head of headers){
        
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
       
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let com of commentList){
   

      const arr = ['reviewTitle', 'commentContent', 'commentWriteDate'];
      const tr = document.createElement("tr"); 

      for(let key of arr){
        const td = document.createElement("td");
        
        // 제목인 경우
         if(key== 'reviewTitle'){
          const a= document.createElement("a");
          a.innerText = com[key]; // 제목을 a 태그 내용으로 대입
          a.href="/review/reviewDetail?reviewNo=" + com.commentNo;
          td.append(a);
        
         }else{
  
          td.innerText = com[key];
         }

        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     



  }


  });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
commentListBtn.addEventListener('click',()=>{

  

  // 다른 화면이 보이는 코드
    // profilecontainer가 reviewList로 바꿔어야 함
   /*  getInnerHTML(); */
   selectMyCommentBoard();



});


/* 내가 북마크한 게시물 비동기로 조회하기 */

const selectMyBookMark=()=>{

  fetch("/myPage/selectBookMark")

  .then(resp => resp.json())

  .then(bookmarkList =>{

    console.log(bookmarkList);

    profilebox.innerHTML="";


    
    if (bookmarkList == null || bookmarkList.length === 0) {
      // bookmarkList null이거나 빈 배열인 경우
      // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

     // 기존 요소 지우기
     const message = document.getElementById("noCommentMessage");
     if (message) {
         message.innerHTML = ""; // 내용 지우기
     } else {
         // 내용 설정
     
     message.innerHTML = "아직 북마크한 게시글이 없습니다";
     }
 
   


  } else {
      // bookmarkList 값이 있는 경우
      // 댓글을 화면에 표시하는 코드 추가

      const table = document.createElement("table");
      table.id="bookMarkBox"
      const tbody = document.createElement("tbody");
      tbody.id="bookMarkList"
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

            
      /* 테이블 헤더 만들기 */
      const headers=["게시글 제목","입양 상태"];
      for(let head of headers){
        
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
       
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let mark of bookmarkList){
   

      const arr = ['reviewTitle', 'bookMarkCheck'];
      const tr = document.createElement("tr"); 

      for(let key of arr){
        const td = document.createElement("td");
        
        // 제목인 경우
         if(key== 'reviewTitle'){
          const a= document.createElement("a");
          a.innerText = mark[key]; // 제목을 a 태그 내용으로 대입
          a.href="/adoption/adoptionDetail?adoptNo=" + mark.adoptNo;
          td.append(a);
        
         }else{
  
          td.innerText = mark[key];
         }

        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     



  }


  });


}

profileListBtn.addEventListener('click',()=>{
 
   selectMyBookMark();


});




// ----------------------------------- ----------



//-----------------------------------------------------------------------

/* 첫 화면에서 프로필 페이지 바로 ajax로 연결 + 프로필 수정 */

/* 프로필 첫 화면 만들기 */

// 함수 만들기
const selectMyProfile = () =>{

 
  fetch("/myPage/selectProfile")

  .then(response => response.json())

  .then(profileList=>{
     // list를 담아오자
    
     // list 확인
     console.log(profileList);

   
     profilebox.innerHTML =""; // 기존 내용 지우기

     const table = document.createElement("table");
    
     const tbody = document.createElement("tbody");
    
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

      
      /* 테이블 헤더 만들기 */
     
     
     profilebox.append(table);
  });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
profileListBtn.addEventListener('click',()=>{

  // 화면 비워주고
  // myPage의 section 내부 -> 여기 내부를 비우고 다른 화면을 부르는 코드를 쓴다

  // 다른 화면이 보이는 코드
    
   /*  getInnerHTML(); */
   selectMyProfile();



});


//---------------------------------------------------------------------------------
/* 마이페이지 프로필  */ 


/* 회원 탈퇴 */

const memberDel = document.querySelector("#memberDel");

if(memberDel !=null){

  memberDel.addEventListener("submit", e=>{

    if( !confirm("정말 탈퇴하시겠습니까?")){ 

      alert("취소되었습니다");
      e.preventDefault();
      return;
     }


  })
}


// ------------------------------------------------------------------------------

/* 프로필 이미지 추가/ 변경 / 삭제 */

// 프로필 이미지 페이지 form 태그
const profileForm = document.querySelector("#profileForm");

/* 프로필 이미지 변경 버튼 */
const pImageUpdate = document.querySelector("#pImageUpdate");

// 프로필 이미지가 새로 업로드 되거나 삭제되었음을 기록하는 상태 변수
let statusCheck = -1; // -1 초기상태(변화 없음) / 0 프로필 이미지 삭제 / 1 새 이미지 선택

// input type="file" 태그의 값이 변경되었을 때 변경된 상태를 백업하여 저장할 변수
// -> 파일이 선택 / 취소된 input을 복제해서 저장

// 요소.cloneNode(true|false) : 요소 복제(true 작성 시 하위 요소도 복제)
let backupInput; 


if(profileForm !=null){

// 프로필 이미지
const profileImg = document.querySelector("#profileImg");

//input type="file" 실제 업로드할 프로필 이미지를 선택하는 요소
let imageInput = document.querySelector("#imageInput");

// x 버튼 (프로필 이미지 제거 + 기본 이미지로 변경하는 요소)
const deleteImage = document.querySelector("#deleteImage");



/* input type="file"의 값이 변했을 때 동작할 함수 (이벤트 핸들러) */
const changeImageFn = e =>{

// 업로드 가능한 파일 최대 크기를 지정해서 필터링
const maxSize = 1024*1024*5; // 5MB

console.log("e.target", e.target); // input
console.log("e.target.value", e.target.value); // 변경된 값(파일명)
console.log("e.target.files", e.target.files); // 선택된 파일에 대한 정보가 담긴 배열 반환
// 왜 배열-> multiple 옵셥에 대한 대비 여러개 선택 가능해서

// 업로드된 파일이 1개 있으면 files[0]에 저장됨
// 파일이 없는 경우 files[0] == undefined
console.log("e.target.files[0]", e.target.files[0]);

const file = e.target.files[0];

//--------------------업로드된 파일이 없다면(취소한 경우)------------------------------

if(file==undefined){
  console.log("파일 선택 후 취소됨");


// 파일 선택 후 취소 -> value ==' '
//-> 선택한 파일 없음으로 기록됨
// -> backupInput으로 교체 시켜서 이전 이미지가 남아 있는 것 처럼 보이게 함

//백업의 백업
const temp = backupInput.cloneNode(true);

// input 요소 다음에 백업 요소 추가
imageInput.after(backupInput);


// 화면에 존재하는 기존 input 제거
imageInput.remove();

// imageInput 변수에 백업을 대입해서 대신하도록 함
imageInput = backupInput;

// 화면에 추가된 백업본에 이벤트 리스너가 존재하지 않아서 추가해줌
imageInput.addEventListener("change", changeImageFn);

// 한 번 화면에 추가된 요소(backupInput)는 재사용 불가
// backupInput의 백업본 temp를 backupInput으로 변경
backupInput = temp;


  return;
}

// --------------- 선택된 파일이 최대 크기를 초과한 경우------
if(file.size > maxSize){
  alert("5MB 이하의 이미지 파일을 선택해 주세요");

  // 선택한 이미지가 없는데 5MB 초과하는 이미지를 선택한 경우
  if(statusCheck ==-1){
    imageInput.value='';
  } else{ // 기존 선택한 이미지가 있는데
          // 다음 선택한 이미지가 최대 크기를 초과한 경우


     // 백업의 백업본
  const temp = backupInput.cloneNode(true);

  // input 요소 다음에 백업 요소 추가
  imageInput.after(backupInput);

  // 화면에 존재하는 기존 input 제거
  imageInput.remove();

  // imageInput 변수에 백업을 대입해서 대신하도록 함
  imageInput = backupInput;

  // 화면에 추가된  백업본에는 
  // 이벤트 리스너가 존재하지 않기 때문에 추가
  imageInput.addEventListener("change", changeImageFn);


  // 한번 화면에 추가된 요소(backupInput)는 재사용 불가능
  // backupInput의 백업본이 temp를 backupInput으로 변경
  backupInput = temp;

  
  


  }


  return;
 }


//------------------ 선택된 이미지 미리보기 ----------------------------------------

// JS에서 파일을 읽을 때 사용하는 객체
// - 파일을 읽고 클라이언트 컴퓨터에 저장할 수 있음
const reader = new FileReader();

// 지정된 File을 읽어오는 역할 
// -> base64 인코딩된 스트링 데이터가 result 속성(attribute)에 담아지게 됨
// 이미지 -> 011010 -> ABCD (더 압축됨) -> result 속성에 담아옴

// 선택한 파일을 읽어옴
reader.readAsDataURL(file); //-> 읽어오기 이벤트(load)

// 읽어오기 끝났을 때
reader.addEventListener("load", e=>{

  // e.target == reader

  // 읽어온 이미지 파일이 BASE64 형태로 반환됨 -> 이 값을 img 태그의 src 속성에 집어넣으면 그대로 보임
  const url = e.target.result; // reader.result

  //프로필 이미지 (img)에 src 속성으로 url 값 세팅
  profileImg.setAttribute("src",url);

  // 새 이미지 선택 상태를 기록
  statusCheck =1;

  // 파일이 선택된 input을 복제해서 백업 -> 
  backupInput= imageInput.cloneNode(true);


});

}


// change 이벤트 : 새로운 값과 기존 값이 다를 경우 발생
imageInput.addEventListener("change", changeImageFn);


//---------------- x 버튼 클릭 시 기본 이미지로 변경 ---------------------


deleteImage.addEventListener("click", ()=>{


  //프로필 이미지 (img)를 기본 이미지로 변경
  profileImg.src ="/images/user.png";

  // input에 저장된 값(value)를 ''(빈칸)으로 변경
    // -> input에 저장된 파일 정보가 모두 사라짐 == 데이터 삭제
    imageInput.value = '';

    backupInput.value = undefined; // 백업본도 삭제


    // 삭제 상태임을 기록
    statusCheck = 0;



});

// ------------#profileForm 제출 시--------------------------

pImageUpdate.addEventListener("submit", e =>{

  let flag = true; 


  // 기존 프로필 이미지가 없다가 새 이미지가 선택된 경우
  if(loginMemberProfileImg == null && statusCheck ==1) flag = false;

  // 기존 프로필 이미지가 있다가 삭제한 경우
  if(loginMemberProfileImg !=null && statusCheck ==0) flag = false;
  
  // 기존 프로필 이미지가 있다가 새 이미지가 선택된 경우
  if(loginMemberProfileImg !=null && statusCheck ==1) flag = false;

  if(flag){ // flag 값이 true인 경우 

    e.preventDefault();
    alert("이미지 변경 후 클릭하세요");

  }

 });

}


