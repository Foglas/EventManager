function addCategoriesToElement(categoryBar){
    for(let i = 0; i<localStorage.getItem("CategoryCount"); i++){
  
    let category = localStorage.getItem("category"+i);
    category = JSON.parse(category);
    console.log(category);

    const checkboxCategory = document.createElement("input");
    checkboxCategory.setAttribute("value", category.id);
    checkboxCategory.setAttribute("type", "checkbox");
    checkboxCategory.setAttribute("id", category.name);
    checkboxCategory.setAttribute("name", "categories");

    const labelCheckBox = document.createElement("label");
    labelCheckBox.setAttribute("for", category.name);
    labelCheckBox.textContent = category.name;
    console.log(category.name);
    labelCheckBox.appendChild(checkboxCategory);

    categoryBar.appendChild(labelCheckBox);
}
}