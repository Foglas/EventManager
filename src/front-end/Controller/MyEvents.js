/*Controller, který se stará o správné vykreslení stránky vlastních eventů uživatele a její obsluhu.
*/
const eventContainer = document.querySelector(".eventContainer");

window.addEventListener("DOMContentLoaded", initialize);


//nastaví uživatelské rozhraní a načtě všechny eventy, které pořádá přihlášený user
function initialize(){
    setBasicUserView();
    getOrganizations().then((organizations) => {
        if(organizations){
        findAllEvents(organizations);
    }})
}


//obsluha, která vymaže zvolený event podle id
function deleteEventHandler(e){
    const button = e.target;
    deleteEvent(button.dataset.id).then(()=>{
        getOrganizations().then((organizations) => {
            if(organizations){
            findAllEvents(organizations);
        }
        })
    });
}
