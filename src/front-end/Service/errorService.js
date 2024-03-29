const container = document.querySelector("#errorDiv");
const messageElement = document.createElement("h3");
function throwError(message){
 
    if(!container.classList.contains("error")){
        messageElement.classList.add("errorH3");
        messageElement.textContent = message;
        container.classList.add("error");
        container.appendChild(messageElement);
    } else if (container.classList.contains("error")){
        console.log("error");
        messageElement.textContent = message;
    }
}