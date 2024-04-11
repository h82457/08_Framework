

/* 조회버튼 클릭시 비동기로 전체 도서 조회 */

const selectBtn = document.querySelector("#selectBtn");
const bookList = document.querySelector("#bookList");

// td 요소를 만들고 text 추가후 반환
const createTd = (text) => {

    const td = document.createElement("td");

    if(text == null){
        const button = document.createElement("button");
        button.innerText = "수정";
        td.append(button);
    }
    else td.innerText = text;
    return td;
}

// 조회 버튼 클릭시
// selectBtn.addEventListener("click", () => {

//     bookList.innerHTML = "";

//     fetch("selectBookList")

//     .then(resp => resp.json() )

//     .then( list => {
//         // console.log(list);

//         list.forEach( (book, index) => {

//             const keyList = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];
//             const tr = document.createElement("tr");

//             keyList.forEach( key => tr.append( createTd(book[key])) );

//             bookList.append(tr);
//         });

//     })
// });


/* 검색버튼 클릭시 비동기로 해당하는 도서 조회 */
const searchBtn = document.querySelector("#searchBtn"); // 검색버튼
const searchList = document.querySelector("#searchList"); //tbody

/* 검색 버튼 클릭시 */
searchBtn.addEventListener("click", () => {

    const keyword = document.querySelector("#keyword").value;

    console.log(keyword);

    fetch("searchBook?keyword=" + keyword)
    .then( resp => resp.json() )
    .then( list => {
        
        console.log(list);
        searchList.innerHTML = "";

        list.forEach( (book, index) => {

            const keyList = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate' , '수정', '삭제'];
            const tr = document.createElement("tr");

            keyList.forEach( key => tr.append( createTd(book[key])) );


            searchList.append(tr);
        });        
    })
} );