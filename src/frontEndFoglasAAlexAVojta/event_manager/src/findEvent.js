import React,{useEffect, useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';
import { eventWrapper } from '@testing-library/user-event/dist/utils';
import userEvent from '@testing-library/user-event';

function FindEvent(){

    const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
    const [city, setCity] = useState('');
    const [region, setRegion] = useState('');
    const [destrict, setDestrict] = useState('');
    const [time, setTime] = useState('');
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(['']);
    const [checked, setChecked] = useState([]);
    const [events, setEvents] = useState([]);
    
    const handleInput = (event) => {
        var array = [...checked];
    if (event.target.checked) {
        console.log("checked " + event.target.checked);
      array = [...checked, event.target.value];
    } else {
      array.splice(checked.indexOf(event.target.value), 1);
    }
    setChecked(array);
      };

      function HandleSearch(e){
        e.preventDefault();
    
        var count = 0;
        var query = '';

        if(time != ''){
            count++;
            query = query + "time="+ time;
           }

        if(city != ''){
           if(count === 0){
            count++;
            query = query + "city="+ city;
           }else{
            count++;
            query = query + "&city="+ city;
           }
           
        }

        if(region != ''){
            if(count === 0){
             count++;
             query = query + "region="+ region;
            }else{
             count++;
             query = query + "&region="+ region;
            }
         }


         if(destrict != ''){
            if(count === 0){
             count++;
             query = query + "destrict="+ destrict;
            }else{
             count++;
             query = query + "&destrict="+ destrict;
            }
         }

         if(checkedCategoriesString != '')
         if(count === 0){
            count++;
            query = query + "categories="+ checkedCategoriesString;
           }else{
            count++;
            query = query + "&categories="+ checkedCategoriesString;
           }
        
        console.log('query ' + query);

        const  header = {
            method: "GET",
            headers : {
                'Authorization': '',
                'Content-Type' : 'application/json'
            },
        }

      fetch('http://localhost:8080/api/events/search?' + query, header)
      .then((response)=>{
        if(response.status === 200){
        return response.json();
    }})
    .then((data) => {
        setEvents(data);
        console.log('data ' + data);
     })
    }


      const checkedCategoriesString = checked.length ? checked.reduce((total, category) => {return total + ','+ category;}) : '';

    useEffect(() => {
        const eventHeader = {
            method: "GET",
            headers : {
                'Authorization': '',
                'Content-Type' : 'application/json'
            }
        }
        
         fetch('http://localhost:8080/api/category', eventHeader)
        .then((response) => {
            if(response.status == 200){
            console.log('categories  ok');
            }
           return response.json()})
        .then((category) => {
            setCategories(category);
    })},[]);

    useEffect(() => console.log("array " + selectedCategory), [selectedCategory]);
    console.log(checked);

    return(
        <Container>
        <Paper  elevation={3} style={paperStyle}>
        <h1>Najdi event</h1>
        <form>
        <label>Region   <TextField variant='outlined' label = "region" value={region} onChange={(e) => setRegion(e.target.value)}/></label>
          
        <label>Destrict     <TextField variant='outlined' label = "destrict" value={destrict} onChange={(e) => setDestrict(e.target.value)}/> </label>
          <label>City   <TextField variant='outlined' label = "city" value={city} onChange={(e) => setCity(e.target.value)}/></label>
          <label>Time <input style={{margin:"10px auto"}} type="datetime-local" onChange={e=>setTime(e.target.value)}/></label> 
          { categories.map((category) => <label>{category.name} <input type="checkbox"  value={category.id} onChange={(e)=> {handleInput(e)}} /></label>)}
          <button variant="contained" onClick={HandleSearch}>find</button>
        </form>
       
        <ul>
            <h1>events</h1>
            { events.map((event) => <li>{event.city}</li>)}
        </ul>

       </Paper>
       </Container>
    )

}

export default FindEvent;