function addPlacesToElement(searchBar){
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
    
    for(let i = 0; i<localStorage.getItem("PlaceCount"); i++){
      
        let onePlace = localStorage.getItem("place"+i);
        onePlace = JSON.parse(onePlace);
        console.log(onePlace);
    
        const regionOption = document.createElement("option");
        regionOption.setAttribute("value", onePlace.region);
        regionOption.setAttribute("data-id", onePlace.id);
        regionOption.textContent = onePlace.region;
        selectRegion.appendChild(regionOption);
    
        const cityOption = document.createElement("option");
        cityOption.setAttribute("value", onePlace.city);
        
        cityOption.textContent = onePlace.city;
        selectCity.appendChild(cityOption);
    
        const destrictOption = document.createElement("option");
        destrictOption.setAttribute("value", onePlace.destrict);
        destrictOption.textContent = onePlace.destrict;
        selectDestrict.appendChild(destrictOption);
    }
    
    
    searchBar.appendChild(selectDestrict);
    searchBar.appendChild(selectRegion);
    searchBar.appendChild(selectCity);
}