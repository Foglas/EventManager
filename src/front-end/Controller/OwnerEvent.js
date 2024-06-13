/*Controller, který se stará o správné vykreslení stránky pro tvorbu nového eventu.
*/
const catForm = document.querySelector("#catDiv");
const placeForm = document.querySelector("#placeDiv");
const eventForm = document.querySelector("#eventForm");

window.addEventListener("DOMContentLoaded", initialize);

eventForm.addEventListener("submit", saveEventHandler);


//nastavuje uživvatelský pohled a volá metody pro vytvoření formuláře a vložení do stránky.
function initialize(){
    setBasicUserView();
    initPlaceAndCategory(()=>{});
    addServerSideForm();

}

//obsluha pro uložení eventu
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

    saveEvent(event)
    .then((response)=>{
        if(response){
            console.log("end");
            addSuccessMessage(response.message, 1, ()=> window.location.assign("MyEvents.html"));  
        }
    });
}


//volá metody pro vytvoření formuláře na tvorbu eventu
function addServerSideForm(){
    addPlacesSelectToElement(placeForm);
    addCategoriesToElement(catForm);
}
