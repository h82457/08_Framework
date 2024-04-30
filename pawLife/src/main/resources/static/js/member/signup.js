// 회원 가입 유효성 검사

// 필수 입력 항목 유효성 검사 여부를 위한 객체, true면 유효하고 false면 유효하지 않은 항목
const checkSign= {

    "memberEmail"     : false,
    "memberPw"        : false,
    "memberPwConfirm" : false,
    "memberNickname"  : false,
    "memberTel"       : false,
    "authKey"         : false

};

/* 이메일 유효성 검사 */
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

memberEmail.addEventListener("input", e => {

    const inputEmail = e.target.value;

    // 이메일 입력 X
    if(inputEmail.trim().length == 0) { 
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";
        emailMessage.classList.remove("confirm", "error");
        checkSign.memberEmail = false;
        memberEmail.value = "";
        return;
    }

    // 이메일 형식 X
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if( !regExp.test(inputEmail)) { 
        emailMessage.innerText = "알맞은 이메일 형식으로 작성해주세요.";
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");
        checkSign.memberEmail = false;
        return;
    }

    // 중복 검사
    fetch("/member/checkEmail?memberEmail" + inputEmail)
    .then(resp => resp.text())
    .then(result => {

        // 중복 이메일
        if(result == 1) {
            emailMessage.innerText = "사용중인 이메일 입니다.";
            emailMessage.classList.add("error");
            emailMessage.classList.remove("confirm");
            checkSign.memberEmail = false;
            return;
        }

        // 유효 이메일
        emailMessage.innerText = "사용 가능한 이메일 입니다.";
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");
        checkSign.memberEmail = true;
    })
    .catch( e => { console.log(e); })
});

/* 인증키 유효성 검사 */

const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const authKeyMessage = document.querySelector("#authKeyMessage");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

let authTimer = 0;
const initMin = 4;
const initSec = 59;
const initTime = "05:00";
let min = initMin;
let sec = initSec;

// 이메일 발송
sendAuthKeyBtn.addEventListener("click", () => {
    if( !checkSign.memberEmail) alert("유효한 이메일 작성후 클릭해 주세요.");

    min = initMin;
    sec = initSec;
    clearInterval(authTimer);
    checkSign.authKey = false;

    fetch("/email/sendEmail", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 1) {
            console.log(result);
        }
        console.log(result);
    })
    
    // 타이머 시작
    authKeyMessage.innerText = initTime;
    authKeyMessage.classList.remove("confirm", "error");

    authTimer = setInterval(() => {

        authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

        if(min === 0 && sec === 0) {
            checkSign.authKey = false;
            clearInterval(authTimer);
            authKeyMessage.classList.add("error");
            authKeyMessage.classList.remove("confirm");
            return;
        }
        if(sec == 0) {
            sec = 60;
            min--;
        }
        sec--;
    }, 1000);
});

function addZero(number){
    if(number<10) return "0" + number;
    else return number;
}

// 인증번호 확인
checkAuthKeyBtn.addEventListener("click", () => {

    // 제한시간 초과
    if(min === 0 && sec === 0) {
        alert("인증번호 입력 제한시간을 초과하였습니다.");
        return;
    }
    // 인증번호 미입력
    if(authKey.value.length < 6) {
        alert("인증번호를 정확히 입력해주세요.");
        return;
    }

    const obj = {
        "email" : memberEmail.value,
        "authKey" : authKey.value
    };

    fetch("/email/checkAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp)
    .then(result => {
        if(result == 0) {
            alert("인증번호가 일치하지 않습니다.");
            checkSign.authKey = false;
            return;
        }
        clearInterval(authTimer);
        authKeyMessage.innerText = "인증 되었습니다.";
        authKeyMessage.classList.add("confirm");
        authKeyMessage.classList.remove("error");
        checkSign.authKey = true;
    })
});

/* 비밀번호 + 비밀번호 유효성 검사 */
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

// 비밀번호 / 비밀번호 확인 비교
const checkPw = () => {
    if(memberPw.value === memberPwConfirm.value) {
        pwMessage.innerText = "비밀번호가 일치합니다.";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkSign.memberPwConfirm = true;  
        return; 
    }
}

memberPw.addEventListener("input", e => {

    const inputPw = e.target.value;
    // 비밀번호 입력 X
    if(inputPw.trim().length == 0) { 
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwMessage.classList.remove("confirm", "error");
        checkSign.memberPw = false;
        memberPw.value = "";
        return;
    }

    // 비밀번호 형식 X
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
    if( !regExp.test(inputPw)) { 
        pwMessage.innerText = "비밀번호가 유효하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkSign.memberPw = false;
        return;
    }

    // 유효 비밀번호
    pwMessage.innerText = "비밀번호가 유효한 형식입니다.";
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkSign.memberPw = true;    

    if(memberPwConfirm.value.length > 0) checkPw();
});

memberPwConfirm.addEventListener("input", e => {
    if(checkSign.memberPw) {
        checkPw();
        return;
    }
    checkSign.memberPwConfirm = false;
});

/* 닉네임 유효성 검사 */
const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");

memberNickname.addEventListener("input", e => {

    const inputNickname = e.target.value;

    // 닉네임 입력 X
    if(inputNickname.trim().length == 0) { 
        nickMessage.innerText = "한글,영어,숫자로만 2~10글자로 입력해주세요.";
        nickMessage.classList.remove("confirm", "error");
        checkSign.memberNickname = false;
        memberNickname.value = "";
        return;
    }

    // 닉네임 형식 X
    const regExp = /^[\w\d가-힣]{2,10}$/;

    if( !regExp.test(inputNickname)) { 
        nickMessage.innerText = "닉네임이 유효하지 않습니다."
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkSign.memberNickname = false;
        return;
    }

    // 중복 검사
    fetch("/member/checkNickname?memberNickname" + inputNickname)
    .then(resp => resp.text())
    .then(result => {
        // 중복 닉네임
        if(result == 1) {
            nickMessage.innerText = "사용중인 닉네임 입니다.";
            nickMessage.classList.add("error");
            nickMessage.classList.remove("confirm");
            checkSign.memberNickname = false;
            return;
        }
        // 유효 닉네임
        nickMessage.innerText = "사용 가능한 닉네임 입니다.";
        nickMessage.classList.add("confirm");
        nickMessage.classList.remove("error");
        checkSign.memberNickname = true;
    })
    .catch( e => { console.log(e); })
});

/* 전화번호 유효성 검사 */
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

memberTel.addEventListener("input", e => {
    
    const inputTel = e.target.value;

    // 전화번호 입력 X
    if(inputTel.trim().length == 0) { 
        telMessage.innerText = "전화번호를 입력해주세요.(-제외)";
        telMessage.classList.remove("confirm", "error");
        checkSign.memberTel = false;
        memberTel.value = "";
        return;
    }

    // 전화번호 형식 X
    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

    if( !regExp.test(inputTel)) { 
        telMessage.innerText = "유효하지 않은 전화번호 형식입니다";
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
        checkSign.memberTel = false;
        return;
    }

    // 중복 검사
    fetch("/member/checkTel?memberTel" + inputTel)
    .then(resp => resp.text())
    .then(result => {

        // 중복 전화번호
        if(result == 1) {
            telMessage.innerText = "유효하지 않은 전화번호 형식입니다.";
            telMessage.classList.add("error");
            telMessage.classList.remove("confirm");
            checkSign.memberTel = false;
            return;
        }

        // 유효 전화번호
        telMessage.innerText = "사용 가능한 전화번호 입니다.";
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error");
        checkSign.memberTel = true;
    })
    .catch( e => { console.log(e); })
});

/* 회원 가입 버튼 클릭시 전체 유효성 여부 확인 */
const signUpBtn = document.querySelector("#signUpBtn");

signUpBtn.addEventListener("click", e => {

    for (const key in checkSign) {
        
        if( !checkSign[key]) {
            let str = "";

            switch(key){
                case "memberEmail": str = "이메일이 유효하지 않습니다."; break;
                case "authKey" : str = "이메일이 인증되지 않았습니다."; break;
                case "memberPw": str = "비밀번호가 유효하지 않습니다."; break;
                case "memberPwConfirm": str = "비밀번호가 일치하지 않습니다."; break;
                case "memberNickname": str = "닉넨임이 유효하지 않습니다."; break;
                case "memberTel": str = "전화번호가 유효하지 않습니다."; break;
            }
            alert(str);
            document.getElemsentById(key).focus(); // 초점 이동
            e.preventDefault(); // 폼태그의 기본이벤트 (제출)막기
            return;
        }
    }
});
