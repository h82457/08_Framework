

/* 썸네일 이미지 추가 변경 삭제 */
// -1 : 초기 상태(변화 없음)
// 0  : 프로필 이미지 삭제
// 1  : 새 이미지 선택
let statusCheck = -1;

// 요소.cloneNode(true/false) : 요소 복제(true 작성 시 하위 요소도 복제)
let backupInput;


// img 태그 (프로필 이미지가 보여지는 요소)
const preview = document.querySelector(".preview")

// input type="file" 태그 (실제 업로드할 프로필 이미지를 선택하는 요소)
let thumnail = document.querySelector("#thumnail")

// x버튼 (프로필 이미지를 제거하고 기본 이미지로 변경하는 요소)
const deleteImage = document.querySelector(".delete-image")

const changeImageFn = e=>{  

    // 업로드 가능한 파일 최대 크기 지정하려 필터링
    const maxSize = 1024*1024*5; // 5MB == 1024KB * 5 == 1024B * 1024 *5


    const file = e.target.files[0];


    // ---------------업로드된 파일이 없다면(취소한 경우)--------------------------
    if(file == undefined){
        console.log("파일 선택 후 취소됨");

        
        // 파일 선택 후 취소 -> value == ' '
        // -> 선택한 파일 없음으로 기록됨
        // -> backupInput 으로 교체 시켜서
        //    이전 이미지가 남아있는 것처럼 보이게 함

        // 백업의 백업본
        const temp = backupInput.cloneNode(true);
        
        // input 요소 다음에 백업 요소 추가
        thumnail.after(backupInput);
        
        // 화면에 존재하는 기존 input 제거
        thumnail.remove();

        // thumnail 변수에 백업을 대입해서 대신하도록 함
        thumnail=backupInput;

        // 화면에 추가된 백업본에는 이벤트 리스너가 존재하지 않기 때문에 추가
        thumnail.addEventListener("change",changeImageFn);

        // 한 번 화면에 추가된 요소는 재사용 불가능
        // backupInput의 백업본이 temp를 backupInput으로 변경
        backupInput = temp;
        
        return;
    }

    // ---------- 선택된 파일이 최대 크기를 초과한 경우 ---------------------------
    if(file.size>maxSize){
        alert("최대 5MB 이하의 이미지 파일만 등록 가능합니다.")

        // 선택한 이미지가 없는데 5MB 초과하는 이미지를 선택한 경우
        if(statusCheck == -1){
            thumnail.value= '';
        }else{// 기존 선택한 이미지가 있는데 
            // 다음 선택한 이미지가 최대 크기를 초과한 경우
            // 백업의 백업본
            const temp = backupInput.cloneNode(true);
            
            // input 요소 다음에 백업 요소 추가
            thumnail.after(backupInput);
            
            // 화면에 존재하는 기존 input 제거
            thumnail.remove();

            // thumnail 변수에 백업을 대입해서 대신하도록 함
            thumnail=backupInput;

            // 화면에 추가된 백업본에는 이벤트 리스너가 존재하지 않기 때문에 추가
            thumnail.addEventListener("change",changeImageFn);

            // 한 번 화면에 추가된 요소는 재사용 불가능
            // backupInput의 백업본이 temp를 backupInput으로 변경
            backupInput = temp;

        }

        return;
    }

    // ----------- 선택된 이미지 미리보기 -----------------------------------------

    // Js에서 파일을 읽을때 사용하는 객체
    // - 파일을 읽고 클라이언트 컴퓨터에 저장할 수 있음
    const reader = new FileReader();

    // 선택한 파일(file)을 읽어와
    // BASE64 인코딩 형태로 읽어와 result 변수에 저장
    reader.readAsDataURL(file); // 읽어오기 이벤트(load)

    // 읽어오기 끝났을 때
    reader.addEventListener("load",e=>{

        // e.target == reader

        // 읽어온 이미지 파일이 BASE64 형태로 반환됨
        const url = e.target.result; // reader.result

        // 프로필 이미지 (img)에 src 속성으로 url값 세팅
        preview.setAttribute("src",url);

        // 새 이미지 선택 상태를 기록
        statusCheck = 1;

        // 파일이 선택된 input을 복제해서 백업
        backupInput = thumnail.cloneNode(true);
    })

}

// change 이벤트 : 새로운 값이 기존 값과 다를 경우 발생
// 
thumnail.addEventListener("change",changeImageFn);


// --------------x 버튼 클릭시 기본 이미지 변경 -------------------------------
deleteImage.addEventListener("click",()=>{
    
    // 프로필 이미지 (img)를 기본 이미지로 변경
    preview.src = "";

    // input에 저장된 값(value)를 ''(빈칸)으로 변경
    // -> input에 저장된 파일 정보가 모두 사라짐 == 데이터 삭제
    thumnail.value='';

    backupInput=undefined; // 백업본도 삭제

    // 삭제 상태임을 기록
    statusCheck = 0;
})



const adoptUpdateForm = document.querySelector("#adoptUpdateForm")

/* 폼 제출 클릭 시 */
adoptUpdateForm.addEventListener("submit",e=>{


    let flag = true;

        // 기존 프로필 이미지가 있고 변경하지 않은 경우
        if(thumnailImage != null && statusCheck==-1) flag = false;
        if(thumnailImage != null && statusCheck== 1) flag = false;


        if(flag){// flag 값이 true인 경우
            
            e.preventDefault();
            alert("이미지 변경 후 클릭하세요.")
        }

    /* 유효성 검사 */
    
    oEditors.getById["adoptContent"].exec("UPDATE_CONTENTS_FIELD", []);

    const title = document.querySelector("#adoptTitle");
   const content = document.querySelector("#adoptContent");
   const infoName = document.querySelector("#adoptName");
   const infoAge = document.querySelector("#adoptAge");
   const infoType = document.querySelector("#adoptType");
   const infoAddress = document.querySelector("#adoptAddress");



    let str = document.querySelector("#adoptContent").value;
    
    str = str.replace(/(<p>|<\/p>|\&nbsp\;)/g,"").trim()
    
        /* 제목 공란 여부 */
        if(title.value.trim().length==0){
            alert("제목을 작성해주세요.")
            title.focus();
            e.preventDefault();
            return;
        }
        /* 내용 공란 여부 */
        if(str.length==0){
            alert("내용을 작성해주세요.")
            content.focus();
            e.preventDefault();
            return;
        }
        /* 이름 공란 여부 */
        if(infoName.value.trim().length==0){
            alert("이름을 작성해주세요.")
            infoName.focus();
            e.preventDefault();
            return;
        }
        /* 나이 공란 여부 */
        if(infoAge.value.trim().length==0){
            alert("나이를 작성해주세요.")
            infoAge.focus();
            e.preventDefault();
            return;
        }
        /* 나이 숫자 여부 */
        if(isNaN(Number(infoAge.value))==true){
            alert("나이를 숫자 형식으로 작성해주세요")
            infoAge.innerText="";
            infoAge.focus();
            e.preventDefault();
            return;
        }
    
        /* 종 공란 여부 */
        if(infoType.value.trim().length==0){
            alert("종을 작성해주세요.")
            infoType.focus();
            e.preventDefault();
            return;
        }
        /* 주소 공란 여부 */
        if(infoAddress.value.trim().length==0){
            alert("발견 장소를 작성해주세요.")
            infoAddress.focus();
            e.preventDefault();
        }

       

})


