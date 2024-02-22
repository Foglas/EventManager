    
function getCategory(){
    fetch("http://localhost:8080/api/category")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        let index = 0;
        data.forEach(element => {
            localStorage.setItem("category"+index, JSON.stringify(element));  
            index +=1;    
        });
        localStorage.setItem("CategoryCount", index);

    })
    .catch((error) => console.log("There is problem", error));
}