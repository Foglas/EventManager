function addAddressSelectToElement(address){
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
