
//přidá místa do poskytnutého elementu v argumentu. Konkrétně select pro města, kraje a okresy.
async function addPlacesToElement(searchBar){

    const cities = await getCities();
    const regions = await getRegion();
    const destricts = await getDestricts();


    const selectCity = document.createElement("select")
    selectCity.setAttribute("name","city");
    const selectRegion = document.createElement("select")
    selectRegion.setAttribute("name","region");
    const selectDestrict = document.createElement("select")
    selectDestrict.setAttribute("name","destrict");
    
    const disabledRegionOption = document.createElement("option");
    disabledRegionOption.setAttribute("value","disabled");
    disabledRegionOption.textContent = "Okres";
    selectRegion.appendChild(disabledRegionOption);
    
    const disabledCityOption = document.createElement("option");
    disabledCityOption.setAttribute("value","disabled");
    disabledCityOption.textContent = "Město";
    selectCity.appendChild(disabledCityOption);
    
    const disabledDestrictOption = document.createElement("option");
    disabledDestrictOption.setAttribute("value","disabled");
    disabledDestrictOption.textContent = "Kraj";
    selectDestrict.appendChild(disabledDestrictOption);

   cities.forEach((city) => {
        const cityOption = document.createElement("option");
        cityOption.setAttribute("value", city);
        cityOption.textContent = city;
        selectCity.appendChild(cityOption);
   });

   regions.forEach((region) =>{
        const regionOption = document.createElement("option");
        regionOption.setAttribute("value", region);
        regionOption.textContent = region;
        selectRegion.appendChild(regionOption);
   });

   destricts.forEach((destrict)=>{
        const destrictOption = document.createElement("option");
        destrictOption.setAttribute("value", destrict);
        destrictOption.textContent = destrict;
        selectDestrict.appendChild(destrictOption);
   })
    

    searchBar.appendChild(selectDestrict);
    searchBar.appendChild(selectRegion);
    searchBar.appendChild(selectCity);

}


//přidá místa do elementu. V jednom selectu vše. Město, ulice, číslo domu
function addPlacesSelectToElement(address){
    const selectAddress = document.createElement("select")
    selectAddress.setAttribute("name", "place")


    const disabledSelectAddress = document.createElement("option");
    disabledSelectAddress.setAttribute("value","");
    disabledSelectAddress.textContent = "Adresa";
    selectAddress.appendChild(disabledSelectAddress);
    
    for(let i = 0; i<localStorage.getItem("PlaceCount"); i++){
      
        let onePlace = localStorage.getItem("place"+i);
        onePlace = JSON.parse(onePlace);
        console.log(onePlace);
    
        const option = document.createElement("option");
        option.setAttribute("value", onePlace.id);
        option.textContent = "Město: " + onePlace.city + ", ulice: " + onePlace.street + ", Číslo domu: " +onePlace.bin;
        selectAddress.appendChild(option);
    }
    
    
    address.appendChild(selectAddress);
    }
