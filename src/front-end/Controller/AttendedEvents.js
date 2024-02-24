let buttonAttend = null;

function addEventListnerToAll(buttons){
    console.log("addEvenetListener " +buttons.length);
   buttons.forEach((button)=>{
        button.addEventListener("click", attendToEvent);
    });
}

function setSuccessButtonAttendStyle(button){
    console.log("success style change");
    button.textContent = "Přihlášeno";
    button.classList.remove("buttonAttend");
    button.classList.add("buttonAttendSuccess");
}

function setAttendedButton(){
    buttonAttend = document.querySelectorAll(".buttonAttend");
    addEventListnerToAll(buttonAttend);
    setAttendedEventsStyle();
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
