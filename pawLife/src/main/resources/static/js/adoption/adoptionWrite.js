// submitPost = function() {
//    oEditors.getById["adoptContent"].exec("UPDATE_CONTENTS_FIELD", []);
  

 


//    console.log(title);
//    console.log(content);

//    const obj = {
//        "adoptTitle" : title,
//        "adoptContent" : content,
//        "infoName":infoName,
//        "infoAge":infoAge,
//        "infoType":infoType,
//        "infoAddress":infoAddress
//    };

//    fetch("/adoption/adoptionWrite", {
//        method : "POST",
//        headers : {"Content-Type" : "application/json"},
//        body : JSON.stringify(obj)
//    })
//    .then( resp => resp.text() )
//    .then( result => {
//        console.log(result);
//    })

// }

   

const submitPost = document.querySelector("#submitPost");

submitPost.addEventListener("submit", e => {

   const title = document.querySelector("#adoptTitle");
   const content = document.querySelector("#adoptContent");
   const infoName = document.querySelector("#infoName");
   const infoAge = document.querySelector("#infoAge");
   const infoType = document.querySelector("#infoType");
   const infoAddress = document.querySelector("#infoAddress");


   oEditors.getById["adoptContent"].exec("UPDATE_CONTENTS_FIELD", []);


//     // 유효성 검사 넣어라

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