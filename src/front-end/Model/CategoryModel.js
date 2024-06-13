
//volání api pro získání kategorií eventů
function getCategories(){
    return sendToApi("/api/category",()=>{}, false, "", "GET", false);
}    