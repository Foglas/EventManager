
//rozhoduje zda zobrazí hlášku že eventy nebyli nalezeny, nebo volá přidání eventů do stránky
function setEvents(data){
    if(data.length === 0){
        console.log("Eventy nebyli nalezeny.")
        displayZeroEvents();
    } else {
    addEventsToHtml(data);
    }
}


//Zobrazí hlášku kdy nebyli nalezeny žádné eventy
function displayZeroEvents(){
    const zeroEvents = document.createElement("h1");
    zeroEvents.textContent = "Nebyli nalezeny žádné eventy";
    eventContainer.appendChild(zeroEvents);
}

//vymaže všechny eventy
function removeAllEvents(){
    while(eventContainer.firstChild){
        eventContainer.removeChild(eventContainer.lastChild);
    }
}

//parsuje datetime z be na adekvátní tvar pro zobrazení na fe
function parseDate(inputDate){
let year;
let day;
let month;
let time;

let splitDate = inputDate.split("-");

year = splitDate[0];
month = splitDate[1];

splitDate = splitDate[2].split("T");

day = splitDate[0];
time = splitDate[1];

return  day + ". " + month +". " + time + " " + year; 
}



