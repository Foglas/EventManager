function setEvents(data){
    if(data.length === 0){
        console.log("Eventy nebyli nalezeny.")
        displayZeroEvents();
    } else {
    addEventsToHtml(data);
    }
}

function displayZeroEvents(){
    const newElement = document.createElement("div");
    newElement.innerHTML = `<h1 id="exception">Nebyly nalezeny žádné eventy</h1>`
    console.log(newElement.innerHTML)
    eventContainer.appendChild(newElement);
}

function removeAllEvents(){
    while(eventContainer.firstChild){
        eventContainer.removeChild(eventContainer.lastChild);
    }
}





