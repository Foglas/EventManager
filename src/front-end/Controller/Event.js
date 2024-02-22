const eventContainer = document.querySelector(".eventContainer");
const searchBar = document.querySelector("#searchBar");
const buttonFind = document.querySelector(".submit_findEvent");
const myPlanAttendNavEventButton = document.querySelector("#planAttendNavButton");
const myEventNavButton = document.querySelector("#myEventNavButton");
const findEventNavButton = document.querySelector("#findEventNavButton");

let buttonAttend = null;

console.log(eventContainer);
window.addEventListener("DOMContentLoaded", initialize)

searchBar.addEventListener("submit", getFilterValuesFromForm);


function initialize(){

//uložen token a id usera, než bude vytvořeno přihlašování
localStorage.setItem("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJldmUzM25ALmN6IiwiZXhwIjoxNzA5MzE0MDAzfQ.qhAVm3BaJY4isvWTDus6lHHxStLLdcm1t3afotquQvJRxZV03MpDiGL8zVn_4dSjRWcCAxjmtOnbseVFNlGhcg");
localStorage.setItem("userId",9);

if(localStorage.getItem("category") === null){
    console.log("category loading");
    getCategory();
} else{
    console.log("category is loaded");
}

if(localStorage.getItem("place") === null){
    console.log("place loading");
    getPlace();
} else{
    console.log("place is loaded");
}
getEvent();
fillSearchBarHtml();
}





function addEventListnerToAll(buttons){
    console.log("addEvenetListener " +buttons.length);
   buttons.forEach((button)=>{
        button.addEventListener("click", attendToEvent);
    });
}


function attendToEvent(e){
    e.preventDefault();
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        method: "POST",
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "state":"koupeno"
        })
      }

    console.log("event id in attendEvent " +e.target.dataset.id);
    fetch("http://localhost:8080/api/auth/event/"+e.target.dataset.id+"/attend",headers)
    .then((response) => response.json())
    .then((data) => 
    {
        const button =  e.target;
        setSuccessButtonAttendStyle(button);
        console.log(data);
    })
    .catch((error) => console.log("There is problem", error));
    

}

function setSuccessButtonAttendStyle(button){
    console.log("success style change");
    button.textContent = "Přihlášeno";
    button.classList.remove("buttonAttend");
    button.classList.add("buttonAttendSuccess");
}

function findEventHandle(e){
    e.preventDefault();
    console.log("Filtering..")
    getEventByFilterQuery();
}

/*
function getEventByFilterQuery(query){
console.log(query);

fetch("http://localhost:8080/api/events/search?"+query)
.then((response) => response.json())
.then((data) => 
{
    console.log(data);
    removeAllEvents();
    setEvents(data);
})
.catch((error) => console.log("There is problem", error));
}
*/

function getFilterValuesFromForm(e){
    e.preventDefault();
    console.log("getFormValues")
    let formValues = new FormData(searchBar);
    let query = madeEventQuery(formValues);
    const events = getEventByFilterQuery(query);
    console.log("Events " + events);
    removeAllEvents();
}

function madeEventQuery(formValues){
  let query = "";
  let categoryNotice = false;
  for (const [key, value] of formValues) {
    if(!categoryNotice){
        if(key === "category"){
            categoryNotice = true;
            query += key+"="+value;
            
        } else if(value !== "" && value !== "disabled"){
        query += key+"="+value+"&";
    }
    } else {
        query +=","+value;
    }
  }
  console.log("query " + query);
  return query;
  }



/*
function getPlace(){
    fetch("http://localhost:8080/api/places")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        let index = 0;
        data.forEach(element => {
            localStorage.setItem("place"+index, JSON.stringify(element));  
            index +=1;
        });
        localStorage.setItem("PlaceCount", index);
    })
    .catch((error) => console.log("There is problem", error));
    
}
*/

/*
function getCategory(){
    fetch("http://localhost:8080/api/category")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        let index = 0;
        data.forEach(element => {
            localStorage.setItem("category"+index, JSON.stringify(element));  
            index +=1;    
        });
        localStorage.setItem("CategoryCount", index);

    })
    .catch((error) => console.log("There is problem", error));
}
*/


function fillSearchBarHtml(){
console.log(localStorage.getItem("category"));

const selectCity = document.createElement("select")
selectCity.setAttribute("name","city");
const selectRegion = document.createElement("select")
selectRegion.setAttribute("name","region");
const selectDestrict = document.createElement("select")
selectDestrict.setAttribute("name","destrict");

const disabledRegionOption = document.createElement("option");
disabledRegionOption.setAttribute("value","disabled");
disabledRegionOption.textContent = "Okres";
selectRegion.appendChild(disabledRegionOption);

const disabledCityOption = document.createElement("option");
disabledCityOption.setAttribute("value","disabled");
disabledCityOption.textContent = "Město";
selectCity.appendChild(disabledCityOption);

const disabledDestrictOption = document.createElement("option");
disabledDestrictOption.setAttribute("value","disabled");
disabledDestrictOption.textContent = "Kraj";
selectDestrict.appendChild(disabledDestrictOption);

for(let i = 0; i<localStorage.getItem("PlaceCount"); i++){
  
    let onePlace = localStorage.getItem("place"+i);
    onePlace = JSON.parse(onePlace);
    console.log(onePlace);

    const regionOption = document.createElement("option");
    regionOption.setAttribute("value", onePlace.region);
    regionOption.textContent = onePlace.region;
    selectRegion.appendChild(regionOption);

    const cityOption = document.createElement("option");
    cityOption.setAttribute("value", onePlace.city);
    cityOption.textContent = onePlace.city;
    selectCity.appendChild(cityOption);

    const destrictOption = document.createElement("option");
    destrictOption.setAttribute("value", onePlace.destrict);
    destrictOption.textContent = onePlace.destrict;
    selectDestrict.appendChild(destrictOption);
}


searchBar.appendChild(selectDestrict);
searchBar.appendChild(selectRegion);
searchBar.appendChild(selectCity);

const timeBar =  document.createElement("div");
timeBar.classList.add("time_bar");

const timeLabel = document.createElement("label");
timeLabel.textContent = "Čas začátku: ";

const inputTime = document.createElement("input");
inputTime.setAttribute("type","datetime-local");
inputTime.setAttribute("name", "time");
timeLabel.appendChild(inputTime);
timeBar.appendChild(timeLabel);

searchBar.appendChild(timeLabel);

const categoryBar = document.createElement("div")


for(let i = 0; i<localStorage.getItem("PlaceCount"); i++){
  
    let category = localStorage.getItem("category"+i);
    category = JSON.parse(category);
    console.log(category);

    const checkboxCategory = document.createElement("input");
    checkboxCategory.setAttribute("value", category.id);
    checkboxCategory.setAttribute("type", "checkbox");
    checkboxCategory.setAttribute("id", category.name);
    checkboxCategory.setAttribute("name", "categories");

    const labelCheckBox = document.createElement("label");
    labelCheckBox.setAttribute("for", category.name);
    labelCheckBox.textContent = category.name;
    console.log(category.name);
    labelCheckBox.appendChild(checkboxCategory);

    categoryBar.appendChild(labelCheckBox);

}

searchBar.appendChild(categoryBar);
}

/*
function getEvent(){
    fetch("http://localhost:8080/api/events")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        setEvents(data);
    })
    .catch((error) => console.log("There is problem", error));

}
*/

function setEvents(data){
    if(data.length === 0){
        console.log("Eventy nebyli nalezeny.")
        displayZeroEvents();
    } else {
        fillDivWithEvents(data);
        console.log(data.length);
    }   
}

/*
function getAttendedEvents(){
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        }
      }
    return fetch("http://localhost:8080/api/auth/user/"+localStorage.getItem("userId") +"/getAttendedEvents",headers)
    .then((response) => {return response.json()})
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}
*/

function setAttendedEventsStyle(){
    getAttendedEvents().then((events) => {
        events.forEach((event) => {
            buttonAttend.forEach((button)=>{
                const idEvent = button.dataset.id;
                console.log("comparison " + idEvent + "  " + event.id);
                if(idEvent == event.id){
                    setSuccessButtonAttendStyle(button);
                }
            });

        })
    })
}

function displayZeroEvents(){
        const newElement = document.createElement("div");
        newElement.innerHTML = `<h1 id="exception">Nebyly nalezeny žádné eventy</h1>`
        console.log(newElement.innerHTML)
        eventContainer.appendChild(newElement);

}

function removeAllEvents(){
    eventContainer.innerHTML ="";
}

function fillDivWithEvents(data){
    data.forEach(element => {
        const article = document.createElement("article");
        article.classList.add("eventDescription");
        

        //Desctiption section
        const clickablePart = document.createElement("a");
        clickablePart.classList.add("clickEvent");
        clickablePart.setAttribute("href","Event.html");

        const sectionOfDescriptionAndPlace = document.createElement("section");
        sectionOfDescriptionAndPlace.classList.add("descriptionAndPlace_section");
        const eventHeading = document.createElement("h1");
        eventHeading.textContent = element.name;

        const parOfDescription = document.createElement("p");
        parOfDescription.classList.add("description");
        parOfDescription.textContent = element.description;

        const parOfCity = document.createElement("p");
        parOfCity.classList.add("specification");
        parOfCity.textContent = element.city;
        
        const parOfAttendedPeoples = document.createElement("p");
        parOfAttendedPeoples.classList.add("specification");
        parOfAttendedPeoples.textContent = "533";
        

        //Time and attendent section
        const sectionOfTimeAndAttendend = document.createElement("section");
        sectionOfTimeAndAttendend.classList.add("timeAndAttend_section");
        
        const parOfStartTime = document.createElement("p");
        parOfStartTime.classList.add("time");
        parOfStartTime.textContent = "Čas začátku: " + element.dateAndTime;

        const parOfEndTime = document.createElement("p");
        parOfEndTime.classList.add("time");
        parOfEndTime.textContent = "Čas konce: " + element.endDateAndTime;

        const button = document.createElement("button");
        button.setAttribute("data-id",element.id);
        button.textContent = "Přihlásit se";
        button.classList.add("buttonAttend");
        

        //Complete html
        sectionOfDescriptionAndPlace.appendChild(eventHeading);
        sectionOfDescriptionAndPlace.appendChild(parOfDescription);
        sectionOfDescriptionAndPlace.appendChild(parOfCity);
        sectionOfDescriptionAndPlace.appendChild(parOfAttendedPeoples);
        clickablePart.appendChild(sectionOfDescriptionAndPlace);

        sectionOfTimeAndAttendend.appendChild(parOfStartTime);
        sectionOfTimeAndAttendend.appendChild(parOfEndTime);
        sectionOfTimeAndAttendend.appendChild(button);

        article.appendChild(clickablePart);
        article.appendChild(sectionOfTimeAndAttendend);

        console.log("elementId" + element.id);
        eventContainer.appendChild(article);
    });
    buttonAttend = document.querySelectorAll(".buttonAttend");
    addEventListnerToAll(buttonAttend);
    setAttendedEventsStyle();
    console.log(buttonAttend);
}

