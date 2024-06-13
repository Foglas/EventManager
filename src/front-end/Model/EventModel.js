
//volání api pro získání eventů podle filter query - argument
function getEventsByFilterQuery(query){
    return sendToApi("/api/events/search?"+query,()=>{}, false, "", "GET", false);
}    

//volání api pro získání všech eventů
function getEvents(){
    return sendToApi("/api/events",()=>{}, false, "", "GET", false);
}

//volání api pro získání konkrétního eventu. id je id eventu.
function getEvent(id){
    return sendToApi("/api/events/"+id,()=>{}, false, "", "GET", false);
}

//volání api pro získání všech eventů. na kterých je přihlášen
function getAttendedEvents(){
    return sendToApi("/api/auth/user/"+localStorage.getItem("userId") +"/getAttendedEvents",()=>{}, true, "", "GET", false);
}

//volání api pro zrušení přihlášení na event. id je id eventu, fun je funkce která se má vykonat po skončení
function cancelAttendToEvent(id, fun){
    return sendToApi("/api/auth/event/"+ id +"/cancelAttend",fun, true, "", "DELETE", false);
}

//volání api pro přihlášení na event. id je id eventu, fun je funkce která se má vykonat po skončení
function attendToEvent(id, fun){
    console.log("id "+ id);
    return sendToApi("/api/auth/event/"+ id +"/attend",fun, true, { "state":"přihlášen"}, "POST", false);
}

//volání api pro získání eventů, jež uživatel vytvořil. id je id organizace, ve které je uživatel členem
function getMyEvents(id){
    return sendToApi("/api/organization/"+id+"/events",()=>{}, true, "", "GET", false);
}

//volání api pro vymazání eventu. id je id eventu
function deleteEvent(id){
    console.log(id);
   return sendToApi("/api/auth/event/"+id+"/delete",()=>{}, true, "", "DELETE", false);
}

//volání api pro uložení eventu
function saveEvent(event){
return sendToApi("/api/auth/event/organization/-1/save", ()=>{},  true, event, "POST");
}


