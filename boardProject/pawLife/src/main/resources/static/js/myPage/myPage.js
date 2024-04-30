// 마이 페이지 -> 개인 정보 수정 페이지로 보내는 동작
const profileUpdateBtn= document.querySelector("#profileUpdateBtn");

profileUpdateBtn.addEventListener("click", () => {
  location.href = "/myPage/myPage-profileupdate"; 
  
});
