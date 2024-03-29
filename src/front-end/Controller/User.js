/*Script, který podporuje základní operace s eventy.
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

function initialize(){
    setBasicUserView();
    fillFormWithData();

    submitBtn = document.createElement("button");
    submitBtn.textContent = "uložit";
    submitBtn.addEventListener("click", updateEventHandler);

}


 

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
        username.value = user.username;
        email.value = user.email;
        phone.value = user.phone;
        realName.value = user.name;
        surname.value = user.surname;
        phone.value = user.phone;
        birth.value = user.birthDate;
    })

    console.log(username);
    console.log(email);
    console.log(phone);
    console.log(realName);
    console.log(surname);
    console.log(birth);
}

function editBtnHandler(e){
    e.preventDefault();


    if(isEditable){
    userInfo.forEach((element) => {
        element.disabled = true;
    });

    inputContainers[1].removeChild(submitBtn);
    isEditable = false;
    
    } else {
        userInfo.forEach((element) => {
        element.disabled = false;
    });
    inputContainers[1].appendChild(submitBtn);
    isEditable = true;
    }
}

function updateEventHandler(e){
    e.preventDefault();

    let values = new FormData(form);
    updateUser(values);

    isEditable = true;
    editBtnHandler(e);
    
}