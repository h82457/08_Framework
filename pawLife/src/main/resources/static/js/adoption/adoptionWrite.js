submitPost = function() {
   oEditors.getById["adoptContent"].exec("UPDATE_CONTENTS_FIELD", []);
  
   const title = document.querySelector("#adoptTitle").value;
   const content = document.querySelector("#adoptContent").value;

   console.log(title);
   console.log(content);

   const obj = {
       "adoptTitle" : title,
       "adoptContent" : content
   };

   fetch("/adoption/adoptionWrite", {
       method : "POST",
       headers : {"Content-Type" : "application/json"},
       body : JSON.stringify(obj)
   })
   .then( resp => resp.text() )
   .then( result => {
       console.log(result);
   })

}