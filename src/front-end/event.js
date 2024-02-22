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

function getFilterValuesFromForm(e){
    e.preventDefault();
    console.log("getFormValues")
    let formValues = new FormData(searchBar);
    let query = madeEventQuery(formValues);
    getEventByFilterQuery(query);
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



function fillSearchBarHtml(){
console.log(localStorage.getItem("category"));

const newCityElement = document.createElement("select")
newCityElement.setAttribute("name","city");
const newRegionElement = document.createElement("select")
newRegionElement.setAttribute("name","region");
const newDestrictElement = document.createElement("select")
newDestrictElement.setAttribute("name","destrict");

let innerRegionHTML = "<option value= disabled selected>Okres</option>";
let innerCityHTML = "<option value= disabled selected>Město</option>";
let innerDestrictHTML = "<option value= disabled selected>Kraj</option>";

for(let i = 0; i<localStorage.getItem("PlaceCount"); i++){
  
    let onePlace = localStorage.getItem("place"+i);
    onePlace = JSON.parse(onePlace);
    console.log(onePlace);

innerCityHTML +=  "<option value=" + "\"" + onePlace.city + "\"" + ">"+onePlace.city+"</option>"
innerRegionHTML +=   "<option value=" + "\"" + onePlace.region + "\"" + ">"+onePlace.region+"</option>"
innerDestrictHTML +=   "<option value=" + "\"" + onePlace.destrict + "\"" + ">"+onePlace.destrict+"</option>"
}

const newElementTime =  document.createElement("div");
newElementTime.classList.add("time_div");
newElementTime.innerHTML = "<br> <label>Čas začátku: <input name=time type=datetime-local></Label>";

console.log(innerCityHTML);
console.log(innerRegionHTML);
console.log(innerDestrictHTML);

newCityElement.innerHTML = innerCityHTML;
newRegionElement.innerHTML = innerRegionHTML;
newDestrictElement.innerHTML = innerDestrictHTML;

searchBar.appendChild(newDestrictElement);
searchBar.appendChild(newRegionElement);
searchBar.appendChild(newCityElement);
searchBar.appendChild(newElementTime);

const newCategoryElement = document.createElement("div")
let innerCategoryHTML = "";

for(let i = 0; i<localStorage.getItem("PlaceCount"); i++){
  
    let category = localStorage.getItem("category"+i);
    category = JSON.parse(category);
    console.log(category);

innerCategoryHTML +=   "<input value="+category.id+" type=checkbox id="+category.name+" name=categories" +">"+ "<label for=" + category.name +">" + category.name+"</label>";
}

console.log(innerCategoryHTML);


newCategoryElement.innerHTML = innerCategoryHTML;

searchBar.appendChild(newCategoryElement);
}


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

function setEvents(data){
    if(data.length === 0){
        console.log("Eventy nebyli nalezeny.")
        displayZeroEvents();
    } else {
        fillDivWithEvents(data);
        console.log(data.length);
    }   
}

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
        const newElement = document.createElement("article");
        newElement.classList.add("eventDescription");
        newElement.innerHTML = `  <a class="clickEvent" href="Event.html">
        <section class="description_heading">
        <h1>${element.name}</h1>
        <p class="description">${element.description}</p>
        <p class="specification">${element.city}</p>
        <p class="specification"> 532</p>
        </a>
      </section>
        <section class = "timeAndAttend_div">
          <p class="time">Kdy začíná? ${element.dateAndTime}0</p>
          <p class="time">Kdy končí? ${element.endDateAndTime}</p>
          <button data-id=${element.id} class="buttonAttend">Přihlásit se</button>
        </section>`
        console.log("elementId" + element.id);
        console.log(newElement.innerHTML)
        eventContainer.appendChild(newElement);
    });
    buttonAttend = document.querySelectorAll(".buttonAttend");
    addEventListnerToAll(buttonAttend);
    setAttendedEventsStyle();
    console.log(buttonAttend);
}

