// 회원 가입 유효성 검사

// 필수 입력 항목 유효성 검사 여부를 위한 객체 
// true면 유효하고 false면 유효하지 않은 항목

const checkSign= {

    "memberEmail"     : false,
    "memberPw"        : false,
    "memberPwConfirm" : false,
    "memberNickname"  : false,
    "memberTel"       : false,
    "authKey"         : false

};

// 1. 관련 요소 얻어옴
// 2. input에 작성된 값 얻어오기
// 3. 입력되지 않은 경우
// 4. 입력 받은 내용 정규식 검사
// 5. 중복 검사 ->  유효하지 않은 경우 / 유효한 경우
// 6. 예외 처리

/* 이메일 유효성 검사 */


/* 비밀번호 + 비밀번호 유효성 검사 */
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

