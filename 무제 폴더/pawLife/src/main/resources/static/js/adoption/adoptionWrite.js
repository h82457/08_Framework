// submitPost = function() {
//    oEditors.getById["adoptContent"].exec("UPDATE_CONTENTS_FIELD", []);
  
//    const title = document.querySelector("#adoptTitle").value;
//    const content = document.querySelector("#adoptContent").value;
//    const infoName = document.querySelector("#infoName").value;
//    const infoAge = document.querySelector("#infoAge").value;
//    const infoType = document.querySelector("#infoType").value;
//    const infoAddress = document.querySelector("#infoAddress").value;
 


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

    // 유효성 검사 넣어라

    oEditors.getById["adoptContent"].exec("UPDATE_CONTENTS_FIELD", []);

})