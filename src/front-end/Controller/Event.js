
window.addEventListener("DOMContentLoaded", initialize);

const startTime = document.querySelector("#startTime");
const endTime = document.querySelector("#endTime");
const destrict = document.querySelector("#destrict");
const city = document.querySelector("#city");
const street = document.querySelector("#street");
const bin = document.querySelector("#bin");
const categoriesDiv = document.querySelector("#categories");
const eventName = document.querySelector("#name");
const description = document.querySelector("#description");
function initialize(){
    setBasicUserView();
    setEventInfo();
}


function setEventInfo(){
    const id = localStorage.getItem("currentEvent");
    getEvent(id).then((event) => {
        console.log(event)
        startTime.textContent = event.dateAndTime;
        endTime.textContent = event.endDateAndTime;
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