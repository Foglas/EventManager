
function getPlaces(){
    fetch("http://localhost:8080/api/places")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        let index = 0;
        data.forEach(element => {
            localStorage.setItem("place"+index, JSON.stringify(element));  
            index +=1;
        });
        localStorage.setItem("PlaceCount", index);
    })
    .catch((error) => console.log("There is problem", error));
    
}
