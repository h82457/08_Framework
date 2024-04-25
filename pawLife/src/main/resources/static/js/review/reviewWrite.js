submitPost = function() {
    oEditors.getById["reviewContent"].exec("UPDATE_CONTENTS_FIELD", []);
   
    const title = document.querySelector("#reviewTitle").value;
    const content = document.querySelector("#reviewContent").value;

    console.log(title);
    console.log(content);


    // js에서 form 태그(formData) 생성 -> append 로 데이터 추가 


    fetch("/review/reviewWrite2", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then( resp => resp.text() )
    .then( result => {
        console.log(result);
    })


// https://heannim-world.tistory.com/125




    const obj = {
        "reviewTitle" : title,
        "reviewContent" : content
    };

    // fetch("/review/reviewWrite", {
    //     method : "POST",
    //     headers : {"Content-Type" : "application/json"},
    //     body : JSON.stringify(obj)
    // })
    // .then( resp => resp.text() )
    // .then( result => {
    //     console.log(result);
    // })



    
    }
    