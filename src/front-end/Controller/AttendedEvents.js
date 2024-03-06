/*Script, který podporuje základní operace s eventy.
*/
const eventContainer = document.querySelector(".eventContainer");

window.addEventListener("DOMContentLoaded", initialize);

function initialize(){
    //uložen token a id usera, než bude vytvořeno přihlašování
    //localStorage.setItem("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJldmUzM25ALmN6IiwiZXhwIjoxNzEwMjUyOTM5fQ.zTiJsLuxOrUyQPu9Tp9Zx_AcWCVVwC4j0hJIz1siN_O6JTTlq-ft5A4HruS4RagnyXdOartKrpMS1tetSnzHKg");
    //localStorage.setItem("userId",9);
     
    getAttendedEvents().then((events)=>{
        setEvents(events);
    });
}