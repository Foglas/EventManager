/*Script, který podporuje základní operace s eventy.
*/
const catForm = document.querySelector("#catDiv");
const placeForm = document.querySelector("#placeDiv");
const eventForm = document.querySelector("#eventForm");

window.addEventListener("DOMContentLoaded", initialize);

eventForm.addEventListener("submit", saveEventHandler);

function initialize(){
    setBasicUserView();
    addServerSideForm();

}


function saveEventHandler(e){
    e.preventDefault();
    
    let values = new FormData(eventForm);
    let categories = "";
    let categoriesNotice = false;

    console.log(values);
    for (const [key, value] of values) {
        if(key === "categories" && categoriesNotice == false){
            categoriesNotice = true;
            categories += value;
        } else if (key === "categories" && categoriesNotice == true){
            categories +=","+value;
        }
    }
    
    const event = {
            "description": values.get("description"),
            "name": values.get("name"),
            "time": values.get("startTime"),
            "endtime": values.get("endTime"),
            "placeId": values.get("place"),
            "categoriesid": categories
    }

    console.log(event);

    saveEvent(event).then((data)=>{
            console.log("data " + data);
          //  window.location.assign("MyEvents.html");
    }).catch((error)=>{
        console.log("error" +error)
        throwError(error);
    });
}

function addServerSideForm(){
    addAddressSelectToElement(placeForm);
    addCategoriesToElement(catForm);
}