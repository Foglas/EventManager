
//volání api pro získání všech míst
function getPlaces(){
    return sendToApi("/api/places",()=>{}, false, "", "GET", false);
}

//volání api pro získání všech měst.
function getCities(){
    return sendToApi("/api/cities", ()=>{}, false, "", "GET", false);
}

//volání api pro získání všech krajů.
function getDestricts(){
    return sendToApi("/api/destricts", ()=>{}, false, "", "GET", false);
}

//volání api pro získání všech okresů.
function getRegion(){
    return sendToApi("/api/regions", ()=>{}, false, "", "GET", false);
}
