
//najde všechny eventy organizace jejíž je user členem, součástí toho odstraní předešlé a zobrazí nově získané
function findAllEvents(organizations){
    console.log(organizations);
    organizations.forEach(org => {
       getMyEvents(org.id).then((events) =>{
        console.log(events);
        removeAllEvents();
        addMyEventsToHtml(events);
     });
    });
}

//Přidá do stránky eventy
function addMyEventsToHtml(events) {
    events.forEach((event) => {
        const article = document.createElement("article");
        article.classList.add("infoEvent");
        console.log("event " + event);
    
        const name = document.createElement("h3");
        name.textContent = event.name;
    
        const city = document.createElement("h3");
        city.textContent = event.place.city;


        const startDate = document.createElement("h3");
        startDate.textContent = parseDate(event.dateAndTime);

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Vymazat";
        deleteBtn.setAttribute("data-id", event.id);
        deleteBtn.classList.add("deleteBtn");
        deleteBtn.addEventListener("click", deleteEventHandler);

        article.appendChild(name);
        article.appendChild(city);
        article.appendChild(startDate);
        article.appendChild(deleteBtn);
        
        eventContainer.appendChild(article);
    });
}