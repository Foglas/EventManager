//controller, který se stará o správné vykreslení stránky všech eventů a vyhledávajícího formuláře
const eventContainer = document.querySelector(".eventContainer");
const eventNav = document.querySelector("#event_nav");
const searchBar = document.querySelector("#searchBar");

console.log(eventContainer);

window.addEventListener("DOMContentLoaded", initialize)

searchBar.addEventListener("submit", getFilterValuesFromForm);


//nejdříve vymaže všechny eventy, nastaví uživatelský pohled, přídá searhBar a přidá všechny eventy na stránku 
function initialize(){
  removeAllEvents();
  setBasicUserView();

  localStorage.removeItem("category0")
  localStorage.removeItem("category1")
  localStorage.removeItem("category2")
  localStorage.removeItem("category3")
  localStorage.removeItem("categoryCount")

  localStorage.removeItem("place0")
  localStorage.removeItem("place1")
  localStorage.removeItem("place2")
  localStorage.removeItem("place3")
  localStorage.removeItem("PlaceCount")


  if(!isUserLogged()){
    eventNav.classList.add("hidden");
    
  }

   initPlaceAndCategory(()=>{
    fillSearchBarHtml();
   });
        
  
getEvents().then((events)=>{
    if(events){
    setEvents(events);
}
});

}


//získá filtrovací parametry z formuláře searchbaru
function getFilterValuesFromForm(e){
    e.preventDefault();
    console.log("getFormValues")
    let formValues = new FormData(searchBar);
    let query = madeEventQuery(formValues);
    getEventsByFilterQuery(query).then((events) => {
        setEvents(events)
    });
    removeAllEvents();
}


//vytvoří query, podle kterého se poté na be vyhledávají konkrétní eventy
function madeEventQuery(formValues){
  let query = "";
  let categoryNotice = false;
  for (const [key, value] of formValues) {
    if(!categoryNotice){
        if(key === "categories"){
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

//Vytvoří a přidá searchBar na stránku.
async function fillSearchBarHtml(){
    console.log("filling");
const container = document.createElement("div");
console.log(localStorage.getItem("category"));

await addPlacesToElement(container);

const categoryBar = document.createElement("div")
categoryBar.classList.add("catBar");

addCategoriesToElement(categoryBar);
container.appendChild(categoryBar);

const timeBar =  document.createElement("div");
timeBar.classList.add("timeBar");

const timeLabel = document.createElement("label");
timeLabel.setAttribute("for","time");
timeLabel.textContent = "Čas začátku: ";

const inputTime = document.createElement("input");
inputTime.setAttribute("type","datetime-local");
inputTime.setAttribute("name", "time");
inputTime.setAttribute("id","time");

timeBar.appendChild(timeLabel);
timeBar.appendChild(inputTime);

container.appendChild(timeBar);

searchBar.insertBefore(container, searchBar.firstChild);
}


