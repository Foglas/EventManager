//controller, který se stará o správné vykreslení stránky konkrétního eventu

const startTime = document.querySelector("#startTime");
const endTime = document.querySelector("#endTime");
const destrict = document.querySelector("#destrict");
const city = document.querySelector("#city");
const street = document.querySelector("#street");
const bin = document.querySelector("#bin");
const categoriesDiv = document.querySelector("#categories");
const eventName = document.querySelector("#name");
const description = document.querySelector("#description");

window.addEventListener("DOMContentLoaded", initialize);

//nastaví uživatelský pohled a poté volá nastavení obsahu stránky konkrétního eventu
function initialize(){
    setBasicUserView();
    setEventInfo();
}


//podle id v localstorage se získá info z be a poté nastaví obsah stránky na event
function setEventInfo(){
    const id = localStorage.getItem("currentEvent");
    getEvent(id).then((event) => {
        console.log(event)
        startTime.textContent = parseDate(event.dateAndTime);
        endTime.textContent = parseDate(event.endDateAndTime);
        destrict.textContent = event.place.destrict;
        city.textContent = event.place.city;
        street.textContent = event.place.street;
        bin.textContent = event.place.bin;
        eventName.textContent = event.name;
        description.textContent = event.description;

        const categories = event.categories;
        console.log(categories)
        categories.forEach((category) => {
            const categoryH3 = document.createElement("h3");
            categoryH3.textContent = category.name;
            categoriesDiv.appendChild(categoryH3);
        });
    });
}