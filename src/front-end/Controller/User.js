/*Controller, který se stará o vykreslení stránky uživatele a její obsluhu
*/
const inputContainers = document.querySelectorAll(".inputContainer");
const editBtn = document.querySelector("#editBtn");
const form = document.querySelector("#registrationForm");

let isEditable = false;

let userInfo;
let form1Children;
let form2Children;

let submitBtn;

window.addEventListener("DOMContentLoaded", initialize);
editBtn.addEventListener("click", editBtnHandler);


//nastavuje uživatelský pohled (přihlášený/nepřihlášení) a vytváří tlačítko pro uložení aktualizovaných dat uživatele
function initialize(){
    setBasicUserView();
    fillFormWithData();

    submitBtn = document.createElement("button");
    submitBtn.classList.add("normalBtn");
    submitBtn.textContent = "uložit";
    submitBtn.addEventListener("click", updateEventHandler);

}


 
//vyplní formulář daty z db
function fillFormWithData(){
    form1Children = inputContainers[0].children;
    form2Children = inputContainers[1].children;

    
    const username =  form1Children.namedItem("username");
    const email =  form1Children.namedItem("email");
    const phone =  form1Children.namedItem("phone");
    const realName =  form2Children.namedItem("name");
    const surname =  form2Children.namedItem("surname");
    const birth =  form2Children.namedItem("birth");

    userInfo = [username,email, phone,realName,surname,birth];

    getCurrentUser(localStorage.getItem("token")).then((user)=>{
        if(user){
        username.value = user.username;
        email.value = user.email;
        phone.value = user.phone;
        realName.value = user.name;
        surname.value = user.surname;
        phone.value = user.phone; 
        birth.value = user.birthDate;
    }})

    console.log(username);
    console.log(email);
    console.log(phone);
    console.log(realName);
    console.log(surname);
    console.log(birth);
}


//handler stisknutí tlačítka edit. Přidá tlačítko uložení a změní popis. Také odstraňuje případné errory.
function editBtnHandler(e){
    e.preventDefault();


    if(isEditable){
    userInfo.forEach((element) => {
        element.disabled = true;
    });
    editBtn.textContent = "Editovat";
    fillFormWithData();
    removeErrror();
    inputContainers[1].removeChild(submitBtn);
    isEditable = false;
    
    } else {
        userInfo.forEach((element) => {
        element.disabled = false;
    });
    editBtn.textContent = "Zrušit editaci";
    inputContainers[1].appendChild(submitBtn);
    isEditable = true;
    }
}


//obsluha stisknutí tlačítka updatu.
function updateEventHandler(e){
    e.preventDefault();

    let values = new FormData(form);
    updateUser(values).then((data)=>{
    if(data){
        localStorage.setItem("token", data.message);
        isEditable = true;
        editBtnHandler(e);
        addSuccessMessage("User information updated", 2, ()=>{});
    }
    });
    
}