/* 조회버튼 클릭시 비동기로 전체 도서 조회 */

const selectBtn = document.querySelector("#selectBtn");
const bookList = document.querySelector("#bookList");

// td 요소를 만들고 text 추가후 반환
const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td;
}

// 조회 버튼 클릭시
selectBtn.addEventListener("click", () => {

    fetch("selectBookList")

    .then(resp => resp.json() )

    .then( list => {
        console.log(list);

        bookList.innerHTML = "";

        list.forEach( (book, index) => {

            const keyList = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];
            const tr = document.createElement("tr");

            keyList.forEach( key => tr.append( createTd(book[key])) );

            bookList.append(tr);
        });

    })
});