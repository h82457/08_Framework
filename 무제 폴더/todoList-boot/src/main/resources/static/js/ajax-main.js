/* 요소 얻어와서 변수에 저장 */
const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");

// 할 일 추가 관련 요소
const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");

// 할 일 목록 조회 관련 요소
const tbody = document.querySelector("#tbody");

// 할 일 상세 조회 관련 요소
const popupLayer = document.querySelector("#popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoTitle");
const popupComplete = document.querySelector("#popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");
const popupClose = document.querySelector("#popupClose");

// 상세 조회 버튼
const deleteBtn = document.querySelector("#deleteBtn");
const completeBtn = document.querySelector("#completeBtn");
const updateView = document.querySelector("#updateView");

// 수정 화면 관련 요소
const updateLayer = document.querySelector("#updateLayer");
const updateTitle = document.querySelector("#updateTitle");
const updateContent = document.querySelector("#updateContent");
const updateBtn = document.querySelector("#updateBtn");
const updateCancel = document.querySelector("#updateCancel");

// function getTotalCount(){ <- 함수 정의
// }

// //getTotalCount() <- 함수 호출

// 전체 Todo 개수 조회 + 출력 함수
function getTotalCount(){

    // 비동기로 서버(DB)에서 전체 Todo 개수 조회하는 fetch() API 코드 작성 (fetch() : 가지고오다)
    fetch("/ajax/totalCount") // 비동기 요청 수행 -> Promise 객체 반환

    .then( Response => {

        // response : 비동기 요청에 대한 응답이 담긴 객체
        // response.text() : 응답 데이터를 문자열/숫자 형태로 변환한 결과를 가지는 Promise 객체 반환
        // console.log(Response);
        // console.log(Response.text());

        return Response.text();
    })
    // 두번째 then의 매개 변수(result) == 첫번째 then에서 반환된 promise 객체의 promiseResult 값
    .then(result => {

        // result 매개변수 == controller 메서드에서 반환된 값
        // console.log("result", result);

    // totalCount 요소의 내용을 result값으로 변경
    totalCount.innerText = result;        
    })
 }

// completeCount 값 비동기 통신으로 얻어와서 화면 출력
function getCompleteCount(){

    // fetch() : 비동기로 요청해서 결과 데이터 가져오기
    fetch("/ajax/completeCount")

    // ~~ controller에서 값 전달 받아옴

    // 첫번째 then의 response : 응답 결과, 요청 주소, 응답 데이터 등이 담겨있음
    // .then( Response => { return Response.text() } ) <-  { return ~ } 리턴값만 남기고 괄호 생략 가능  
    .then( Response => Response.text() )

    // 두번째 .then의 result <- 첫번째 then에서 text로 변환된 응답 데이터(completeCount값)
    .then( result => {

        // #completeCount 요소에 내용으로 result값 출력
        completeCount.innerText = result;
    })
}
// 새로고침 버튼이 클릭되었을떄
reloadBtn.addEventListener("click", () => {

    getTotalCount() // 비동기로 전체 할 일 개수 조회
    getCompleteCount() // 비동기로 완료된 할 일 개수 조회
});




/* 할 일 추가 버튼 클릭시 동작 */
addBtn.addEventListener("click", () => {

    // 비동기로 할 일 추가하는 fetch() API 코드 작성
    //  ㄴ 조건1) 요청주소: "/ajax/add" ㄴ 조건2) 데이터 전달 방식(method) : "POST" 방식
  
    // 파라미터를 저장한 JS 객체 {K:V, K:V}
    const param = { "todoTitle" : todoTitle.value, "todoContent" : todoContent.value }

    fetch("/ajax/add", {
        // key : value
        method : "POST", // POST 방식 요청
        headers : { "Content-Type" : "application/json" }, // 요청 데이터의 형식을 json으로 지정
        body : JSON.stringify(param) // param 객체를 JSON(String)으로 변환
        })

    .then( resp => resp.text()) // 반환된 값을 text로 변혼

    .then( temp => { // 첫번째 then에서 반환된 값중 변환된 text를 temp에 저장

        if(temp > 0) { // 성공
            alert("추가 성공@@@");

            // 추가 성공한 제목, 내용 지우기
            todoTitle.value="";
            todoContent.value="";
            
            // 할 일이 추가되었기 때문에 전체 todo개수 다시 조회
            getTotalCount();

            // 할 일 목록 다시 조회
            selectTodoList();

        } else alert("추가 실패");
    })
});
// ------------------------------------

// 비동기(ajax)로 할 일 상세 조회하는 함수
const selectTodo = (url) => {
    // 매개변수 url == "/ajax/detail?todoNo=10" 형태의 문자열

    fetch(url)

    .then(response => response.json())
    // ㄴ response.json() : 응답 데이터가 JSON 인 경우 자동으로 Object 형태로 변환하는 메서드 == JSON.parse(JSON 데이터)
    .then(todo => {
        // 매개변수 todo : 서버 응답(JSON)이 Objext로 변환된 값 (첫번쨰 then의 반환 결과)

        console.log(todo);

        /* popup layer에  조회된 값을 출력 */
        popupTodoNo.innerText = todo.todoNo;
        popupTodoTitle.innerText = todo.todoTitle;
        popupComplete.innerText = todo.complete;
        popupRegDate.innerText = todo.regDate;
        popupTodoContent.innerText = todo.todoContent;

        // popup layer 보이게 하기
        popupLayer.classList.remove("popup-hidden");
        // ㄴ 요소..classList.toggle("클래스명") : 요소에 해당 클래스가 있으면 제거/없으면 추가
        // ㄴ 요소.ClassList.add("클래스명") : 요소에 해당 클래스가 없으면 추가
        // ㄴ 요소.ClassList.remove("클래스명") : 요소에 해당 클래스가 있으면 제거

        // update Layer가 혹시라도 열려있으면 숨기기
        updateLayer.classList.add("popup_hidden");
    });
};

//-------------------------------------
// 비동기(ajax)로 할 일 목록 조회하는 함수

const selectTodoList = () => { // <- 변수에 함수를 선언하는 형식 (해석전까지 실행X)

    fetch("/ajax/selectList")

    .then(response => response.text()) // 응답 결과를 text로 변환
  
    .then(result => { // result == 첫 번째 then에서 반환된 결과값
  
      console.log(result)
      
      console.log(typeof result); // 타입 검사 ~> JSON <- 객체 (X), 문자열(O)
        
        // JSON.parse(JSON데이터) : string 형태의 JSON 데이터를 JS objext 타입으로 변환
        // JSON.stringify(JS Object) : JS Object 타입을 string 형태의 JSON 데이터로 변환

        const todoList = JSON.parse(result);
        
        console.log(todoList);// 객체 배열 형태 확인

        // ------------------------
        
        /* 기존에 출력되어있던 할 일 목록 모두 삭제 */
        tbody.innerHTML = "";

        // #tbody에 tr/td 요소 생성해서 내용 추가
        for(let todo of todoList){ // 향상된 for문

            // tr태그 생성
            const tr = document.createElement("tr");

            const arr = ['todoNo', 'todoTitle', 'complete', 'regDate'];
            
            for(let key of arr){
                const td = document.createElement("td");

                // 제목인 경우
                if(key === 'todoTitle'){
                    const a = document.createElement("a"); // a태그 생성
                    a.innerText = todo[key]; // 제목을 a태그 내용으로 대입
                    a.href = "/ajax/detail?todoNo=" + todo.todoNo;
                    td.append(a);
                    tr.append(td);

                // a태그 클릭시 기본 이벤트(페이지 이동) 막기
                a.addEventListener("click", e => { 
                    
                    e.preventDefault();
                
                    // 할 일 상세 조회 비동기 요청
                    selectTodo(e.target.href);
                    // ㄴ e.tartet.href : 클릭된 a태그의 href 속성 값
                });
                continue;
                }
                td.innerText = todo[key];
                tr.append(td);
            }
            // tbody의 자식으로 tr (한 줄) 추가
            tbody.append(tr);
    }
    })
  };

// -----------------------

// popup layer의 X버튼(#popupClose) 클릭 시 닫기
popupClose.addEventListener("click", () => {

    // 숨기는 class를 추가
    popupLayer.classList.add("popup-hidden"); 
});

/* 삭제 버튼 클릭 시 */
deleteBtn.addEventListener("click", () => {

    // 취소 클릭 시 아무것도 안함
    if(!confirm("정말 삭제 하시겠습니까?")) { return;  }
  
    // 삭제할 할 일 번호(PK) 얻어오기
    const todoNo = popupTodoNo.innerText; // #popupTodoNo 내용 얻어오기
  
    // 비동기 DELETE 방식 요청
    fetch("/ajax/delete", {
      method : "DELETE", // DELETE 방식 요청 -> @DeleteMapping 처리 <- 비동기에서만 사용 가능
        // 데이터 하나를 전달해도 application/json 작성
        headers : {"Content-type" : "application/json"},
        body : todoNo // todoNo 값을 body에 담아서 전달
                      // -> @RequestBody로 꺼냄
      })
    
      .then( response => response.text()) // 요청 결과를 text형태로 변환
      .then( result => {
    
        if(result > 0){ // 삭제 성공
          alert("삭제 되었습니다");
    
          // 상세 조회 창 닫기
          popupLayer.classList.add("popup-hidden");
    
          // 전체, 완료된 할 일 개수 다시 조회
          // + 할 일 목록 다시 조회
          getTotalCount();
          getCompleteCount();
          selectTodoList();
        } else{
          alert("삭제 실패...");
        }
    })
});

/* 완료 여부 수정 버튼 클릭시 */
completeBtn.addEventListener("click", () => {

    // 완료 여부 수정할 todoNo 얻어오기
    const todoNo = popupTodoNo.innerText;
    let complete = popupComplete.innerText === 'Y' ? 'N' : 'Y';

    // sql 수행에 필요한 값을 객체로 묶음
    const obj = {"todoNo" : todoNo, "complete" : complete};

    //비동기 PUT 방식 요청 (수정)
    fetch("/ajax/complete", {
        method : "PUT", // @PutMapping
        headers : {"Content-Type" : "application/json"}, 
        body : JSON.stringify(obj) // obj를 JSON으로 변경
      })
    
      .then( response => response.text() )
      .then( result => {
    
        if(result > 0){
          
            // update된 DB 데이터를 다시 조회해서 화면에 출력시 서버 부하가 큼
            // ㄴ 서버 부하를 줄이기 위해 상세 조회에서 Y/N만 바꾸기
            popupComplete.innerText = complete; 

            // 서버 부하를 줄이기 위해 완료된 Todo 개수 +-1
            const count = Number(completeCount.innerText);

            if(complete === 'Y') completeCount.innerText = count + 1;
            else                 completeCount.innerText = count - 1;

            selectTodoList();

            }else{
                alert("완료 여부 변경 실패!!");
            }
      })
    } );
    
// --------------
// 상세조회에서 수정 버튼(#updateView) 클릭 시
updateView. addEventListener("click", () => {

    // 기존 팝업 레이어는 숨기고
    popupLayer.classList.add("popup-hidden");
  
    // 수정 레이어 보이게
    updateLayer.classList.remove("popup-hidden");
  
    // 수정 레이어 보일 때
    // 팝업 레이어에 작성된 제목, 내용 얻어와 세팅
    updateTitle.value = popupTodoTitle.innerText;
    updateContent.value = popupTodoContent.innerHTML.replaceAll("<br>", "\n");
    // ㄴHTML 화면에서 줄 바꿈이 <br>로 인식되고 있는데 textarea에서는 \n으로 바꿔야 줄 바꿈으로 인식된다!
  
    // 수정 레이어 수정 버튼에 data-todo-no 속성 추가
    updateBtn.setAttribute("data-todo-no", popupTodoNo.innerText);
});
  
//--------------------------
/* 수정 레이어에서 취소버튼(#updateCancel) */
updateCancel.addEventListener('click', () => {

    // 수정 레이어 숨기기
    updateLayer.classList.add("popup-hidden");
    
    // 팝업 레이어 보이기
    popupLayer.classList.remove("popup-hidden");
  });
  
/* 수정 레이어 - 수정버튼(#updateBtn) 클릭시 */
updateBtn.addEventListener("click", e => {
    // 서버로 전달해야하는 값을 객체로 묶어둠
    const obj = {
        "todoNo" : e.target.dataset.todoNo,
        "todoTitle" : updateTitle.value, // input: value / 그 외 태그 : innerText
        "todoContent" : updateContent.value
    }

    // 비동기 요청
    fetch("/ajax/update", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })

    .then( response => response.text() ) // 문자열화 시켜서 반환

    .then( result => {

        if(result > 0) {
            alert("수정 성공!!!");

            updateLayer.classList.add("popup-hidden");

            // 성능 개선
            popupTodoTitle.innerText = updateTitle.value;
            popupTodoContent.innerHTML = updateContent.value.replaceAll("\n", "<br>");

            popupLayer.classList.remove("popup-hidden");

            selectTodoList();

            updateTitle.value = ""; // 남은 흔적 제거
            updateContent.value = "";
            updateBtn.removeAttribute("data-todo-no");    

        } else alert("수정 실패...");
    })
});




// ---------------------- 로딩시 실행되는 부분
// Js파일에 함수 호출 코드 작성 -> 페이지 로딩시 바로 실행
getTotalCount()
getCompleteCount()
selectTodoList()
