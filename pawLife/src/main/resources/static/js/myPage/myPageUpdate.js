/* 개인 정보 수정 페이지 */

/* 닉네임 전화번호 수정 */

// profileUpdateForm  존재시에만 제출 가능하도록 하기
const profileUpdateForm = document.querySelector("#profileUpdateForm");

if(profileUpdateForm !=null){

  profileUpdateForm.addEventListener("submit",e =>{

    const memberNickname = document.querySelector("#memberNickname");
    const memberTel = document.querySelector("#memberTel");

    // 닉네임 유효성 검사
    if(memberNickname.value.trim().length ==0){
      alert("닉네임을 입력해 주세요.");
  
      e.preventDefault(); // 제출 막기
      return;
    }
  
  
    let regExp = /^[가-힣\w\d]{2,10}$/;
    if( !regExp.test(memberNickname.value)){
      alert("닉네임이 유효하지 않습니다");
      e.preventDefault(); // 제출 막기
      return;
  
    }

    // 전화 번호 유효성 검사
    if(memberTel.value.trim().length ==0){
      alert("전화번호를  입력해 주세요.");

      e.preventDefault(); // 제출 막기
      return;
    }

    // 정규식이 맞지 않으면
    regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
    if(!regExp.test(memberTel.value)){
      alert("전화번호가 유효하지 않습니다");
      e.preventDefault(); // 제출 막기
      return;

    }


  });
}

/* 비밀번호 수정 */
// 비밀번호 변경 form 태그 
const changePwForm = document.querySelector("#changePwForm");

if(changePwForm !=null){

  changePwForm.addEventListener("submit", e=>{

   const currentPw = document.querySelector("#currentPw");
   const newPw = document.querySelector("#newPw");
   const newPwConfirm = document.querySelector("#newPwConfirm");


    
    //- 값을 모두 입력 했는가 

     // 안했으면 return

     //-> 유지 보수하기 힘들어서 별로 안씀
     let str; // undefined : 처음에는 값이 대입이 되지 않은 상태
     if( currentPw.value.trim().length == 0 )  str = "현재 비밀번호를 입력해 주세요";
     else if( newPw.value.trim().length == 0 ) str = "새 비밀번호를 입력 해주세요";
     else if( newPwConfirm.value.trim().length == 0 ) str = "새 비밀번호 확인을 입력 해주세요";
     
    if(str!=undefined){ // str에 값이 대입됨 == if문 중 하나 실행됨

      alert(str);
      e.preventDefault();
      return;
    }
    //- 새 비밀번호 정규식
    //비밀번호 정규식
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    if( !regExp.test(newPw.value)){
      alert("새 비밀번호가 유효하지 않습니다.");
      e.preventDefault();
      return;
    }

    //- 새 비밀번호 == 새 비밀번호 확인
    if(newPw.value != newPwConfirm.value){
      alert("새 비밀번호가 일치하지 않습니다.");
      e.preventDefault();
      return;
    }

  });
}


//--------------------------------------------------------------
// 개인 정보 수정 페이지에서 마이 페이지로 보내는 동작
const profileCompeleteBtn= document.querySelector("#profileCompeleteBtn");

profileCompeleteBtn.addEventListener("click", () => {
  location.href = "myPage-first"; 
  
});
