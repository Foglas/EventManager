let buttonsAttend = null;

function addEventListnerToAll(buttons){
    console.log("addEvenetListener " +buttons);
   buttons.forEach((button)=>{
        button.addEventListener("click", attendToEventHandler);
    });
}


function attendToEventHandler(e){
    const button = e.target;
    const attend = button.dataset.attend;
    if(attend == "false"){
        console.log("data attend " + attend);
        attendToEvent(e);
        button.setAttribute("data-attend", true);
    } else {
        console.log("data attend " + attend);
        cancelAttendToEvent(e);
        button.setAttribute("data-attend", false);
    }
    console.log("attend id " + button.dataset.id);

}

function setSuccessButtonAttendStyle(button){
    console.log("success style change");
    button.textContent = "Přihlášeno";
    button.classList.remove("buttonAttend");
    button.classList.add("buttonAttendSuccess");
}

function setUnsuccessfulButtonAttendStyle(button){
    button.setAttribute("data-attend", false);
    console.log("unsuccessful style change");
    button.textContent = "Přihlásit";
    button.classList.remove("buttonAttendSuccess");
    button.classList.add("buttonAttend");
}

function setAttendedButton(){
    if(isUserLogged()){
    buttonsAttend = document.querySelectorAll(".buttonAttend");
    addEventListnerToAll(buttonsAttend);
    setAttendedButtonStyle();
}
}

function setAttendedButtonStyle(){
    getAttendedEvents().then((events) => {
        events.forEach((event) => {
            buttonsAttend.forEach((button)=>{
                const idEvent = button.dataset.id;
                console.log("comparison " + idEvent + "  " + event.id);
                if(idEvent == event.id){
                    button.setAttribute("data-attend", true);
                    setSuccessButtonAttendStyle(button);
                }
            });

        })
    })
}


function addEventsToHtml(data){
    data.forEach(element => {
        const article = document.createElement("article");
        article.classList.add("eventDescription");
        

        //Desctiption section
        const clickablePart = document.createElement("a");
        clickablePart.setAttribute("data-id",element.id);
        clickablePart.classList.add("clickEvent");
        clickablePart.setAttribute("href","Event.html");
        clickablePart.addEventListener("click", handleRedirectToEvent)


        const sectionOfDescriptionAndPlace = document.createElement("section");
        sectionOfDescriptionAndPlace.setAttribute("data-id",element.id);
        sectionOfDescriptionAndPlace.classList.add("descriptionAndPlace_section");

        const eventHeading = document.createElement("h1");
        eventHeading.setAttribute("data-id",element.id);
        eventHeading.textContent = element.name;

        const partOfDescription = document.createElement("p");
        partOfDescription.setAttribute("data-id",element.id);
        partOfDescription.classList.add("description");
        partOfDescription.textContent = element.description;

        const partOfCity = document.createElement("p");
        partOfCity.setAttribute("data-id",element.id);
        partOfCity.classList.add("specification");
        partOfCity.textContent = element.city;
        
        const partOfAttendedPeoples = document.createElement("p");
        partOfAttendedPeoples.setAttribute("data-id",element.id);
        partOfAttendedPeoples.classList.add("specification");
        partOfAttendedPeoples.textContent = "533";
    
        //Time and attendent section
        const sectionOfTimeAndAttendend = document.createElement("section");
        sectionOfTimeAndAttendend.classList.add("timeAndAttend_section");
        
        const parOfStartTime = document.createElement("p");
        parOfStartTime.classList.add("time");
        parOfStartTime.textContent = "Čas začátku: " + element.dateAndTime;

        const parOfEndTime = document.createElement("p");
        parOfEndTime.classList.add("time");
        parOfEndTime.textContent = "Čas konce: " + element.endDateAndTime;

        let button;
        if(isUserLogged()){
        button = document.createElement("button");
        button.setAttribute("data-id",element.id);
        button.setAttribute("data-attend", false);
        button.textContent = "Přihlásit se";
        button.classList.add("buttonAttend");
        }


        //Complete html
        sectionOfDescriptionAndPlace.appendChild(eventHeading);
        sectionOfDescriptionAndPlace.appendChild(partOfDescription);
        sectionOfDescriptionAndPlace.appendChild(partOfCity);
        sectionOfDescriptionAndPlace.appendChild(partOfAttendedPeoples);
        clickablePart.appendChild(sectionOfDescriptionAndPlace);

        sectionOfTimeAndAttendend.appendChild(parOfStartTime);
        sectionOfTimeAndAttendend.appendChild(parOfEndTime);
        
        if(isUserLogged()){
        sectionOfTimeAndAttendend.appendChild(button);
        }
        article.appendChild(clickablePart);
        article.appendChild(sectionOfTimeAndAttendend);

        console.log("elementId" + element.id);
        eventContainer.appendChild(article);
    });

    setAttendedButton();
}


function handleRedirectToEvent(e){
    e.preventDefault();
    console.log( e.target);
    localStorage.setItem("currentEvent", e.target.dataset.id);
    window.location.assign("Event.html");
}