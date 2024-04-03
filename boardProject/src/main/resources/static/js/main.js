/* 쿠키에서 key가 일치하는 value 얻어오기 함수 */

// 쿠키는 "K=V; K=V; K=V;" 형식 <- 사용시 객체 형태로 쪼개야함
const getCookie = key => {

    const cookies = document.cookie; // <- 변환전 형태  - "K=V; K=V"

    // cookies 문자열을 배열 형태로 변환
//     배열.map(함수) : 배열의 각 요소를 이용해 함수 수행수 결과 값으로 새로운 배열을 만들어 반환 (향상된 for문 형식)
    const cookieList = cookies.split("; ") // <- ["K=V", "K=V"]  
                                     .map( el => el.split("=") ); // <- ["K", "V"]
    // console.log(cookieList);                                 
       
    // 배열 -> 객체로 변환 (다루기 용이)
    const obj = {}; // 빈 객체 선언
    
    for(let i=0; i<cookieList.length; i++){
        
        const k = cookieList[i][0]; // key
        const v = cookieList[i][1]; // value
        obj[k] = v; // 객체에 추가
    }
    // console.log("obj", obj);

    return obj[key]; // 매개변수로 전달받은 key와 obj 객체에 저장된 키가 일치하는 요소의 값 반환
} 

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

if(loginEmail != null){ // 로그인창의 이메일 입력 부분이 있을떄 == 로그인 안된 상태

    // 쿠키중 key값이 "saveId"인 요소의 value 얻어오기
    const saveId = getCookie("saveId"); // undefined 또는 email값

    // saveId 값이 있을 경우
    if(saveId != undefined){

        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input에 value로 세팅
      
        document.querySelector("input[name='saveId']").checked = true; // 아이디 저장 체크박스에 체크 해두기
    }
}


/* 이메일/비밀번호 미작성시 로그인 막기 */

// 로그인 시도를 중단 == form 태그 제출 X 
//  ㄴ> form요소.addEventListener("submit", e =>{ e.preventDeault(); })
//                                                ㄴ> e.preventDeault(); : 기본 이벤트 막기                    
const loginForm = document.querySelector("#loginForm");
// loginEmail : 이메일 input요소
const loginPw = document.querySelector("#loginForm input[name='memberPw']");


// #loginForm이 화면에 존재 할 때 (== 비 로그인상태일때)
if(loginForm != null){

    loginForm.addEventListener("submit", e => { // 제출 이벤트 발생시

        if(loginEmail.value.trim().length === 0) { // 이메일 미작성

            alert("이메일을 작성해주세요!");
            e.preventDefault(); // 기본 이벤트 막기 (제출 막기)
            loginEmail.focus(); // 초점 이동
            return;
        }  
        if(loginPw.value.trim().length === 0) { // 비밀번호 미작성

            alert("비밀번호를 작성해주세요!");
            e.preventDefault();
            loginPw.focus();
            return;
        }  
    });
}

/* 빠른 로그인 */
const quickLoginBtns = document.querySelectorAll(".quick-login");

quickLoginBtns.forEach( (item, index) => {

    //item, : 현재 반복시 꺼내온 객체, index : 현재 반복중인 인덱스

    // quickLoginBtns 요소를 하나씩 깨너새 이벤트 리스너 추가
    item.addEventListener("click", e => {

        const email = item.innerText; // 버튼에 작성된 이메일 얻어오기
        
        location.href = "/member/quickLogin?memberEmail=" + email; 
    });
})

// ---------------------

/* 회원 목록 조회 (비동기) */
const selectMemberList = document.querySelector("#selectMemberList");
const memberList = document.querySelector("#memberList");

// td 요소를 만들고 text 추가후 반환
const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td;
}





// 조회 버튼 클릭시
selectMemberList.addEventListener("click", () => {

    // 1) 비동기로 회원 목록 조회 (포함될 회원 정보 : 회원 번호, 이메일, 닉네임, 탈퇴 여부)
    fetch("/member/selectMemberList")

    // 첫번째 then(resp => resp.json()}) -> [{}, {}, {}],<- JSON Array, JS 객채 배열로 변환 필요
    .then(resp => resp.json())

    // 2) 두번쨰 then : tbody에 이미 작성된 내용 삭제
    // 3) 두번쨰 then : 조회된 JS 객체 배열을 이용, tbody에 들어갈 요소를 만들고 값 세팅+추가
    .then( list => {
        console.log(list);

        memberList.innerHTML = "";

        list.forEach( (member, index) => {

            // index : 현재 접근 중인 index

            // tr 만들어서 그 안에 td 만들어서 append 후
            // tr을 tbody에 append

            const keyList = ['memberNo', 'memberEmail', 'memberNickname', 'memberDelFl'];
            const tr = document.createElement("tr");
            
            // KeyList에서 key를 하나씩 얻어온 후
            // 해당 key에 맞는 member 객체 값을 얻어와 생성되는 td요소로 innerText 추가후 tr 요소의 자식으로 추가
            keyList.forEach( key =>  tr.append( createTd(member[key])) );

            // memberList(body) 자식으로 tr 추가
            memberList.append(tr);
        })
    })
    


});
