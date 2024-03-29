/*Script, který podporuje základní operace s eventy.
*/
const eventContainer = document.querySelector(".eventContainer");

window.addEventListener("DOMContentLoaded", initialize);


function initialize(){
    setBasicUserView();
    getOrganizations().then((organizations) => {
        findAllEvents(organizations);
    })
}

function deleteEventHandler(e){
    const button = e.target;
    deleteEvent(button.dataset.id).then(()=>{
        getOrganizations().then((organizations) => {
            findAllEvents(organizations);
        })
    });
}
