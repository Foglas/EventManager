/*Controller jenž se stará o stránku přihlášených eventů.
*/
const eventContainer = document.querySelector(".eventContainer");

window.addEventListener("DOMContentLoaded", initialize);



//Nastaví uživatelská pohled (přihlášený/nepřihlášený) a poté nastaví na stránku přihlášené eventy.
function initialize(){
    setBasicUserView();

    getAttendedEvents().then((events)=>{
        if(events){
        setEvents(events);
    }
});
}