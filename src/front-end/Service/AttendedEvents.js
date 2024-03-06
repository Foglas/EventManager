let buttonsAttend = null;

function addEventListnerToAll(buttons){
    console.log("addEvenetListener " +buttons.length);
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
        sectionOfDescriptionAndPlace.appendChild(parOfDescription);
        sectionOfDescriptionAndPlace.appendChild(parOfCity);
        sectionOfDescriptionAndPlace.appendChild(parOfAttendedPeoples);
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
