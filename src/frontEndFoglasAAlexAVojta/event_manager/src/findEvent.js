import React,{useEffect, useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';


function FindEvent(){

    const paperStyle={padding:'50px 20px',width:400,margin:'20px auto'}
    const paperStyle2={padding:'20px 10px', margin:'20px auto', width:200 }
    const [city, setCity] = useState('');
    const [region, setRegion] = useState('');
    const [destrict, setDestrict] = useState('');
    const [time, setTime] = useState('');
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(['']);
    const [checked, setChecked] = useState([]);
    const [events, setEvents] = useState([]);
    //const [currentEventId, setCurrentEventId] = useState('');

    const handleInput = (event) => {
        event.preventDefault();
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
function handleAttend(e,id){
e.preventDefault();
    const  header = {
        method: "POST",
        headers : {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify({'state' : 'koupeno'})
    }
    console.log('id ' + id);

  fetch('http://localhost:8080/api/auth/event/'+id+'/attend', header)
  .then((response)=>{
    if(response.status === 200){
    return response.json();
}else{
    
    throw response;
}})
.then((data) => {
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
         <TextField style={{margin:"10px auto"}} variant='outlined' fullWidth label = "region" value={region} onChange={(e) => setRegion(e.target.value)}/>
          <br/>
       <TextField style={{margin:"10px auto"}} variant='outlined' fullWidth label = "destrict" value={destrict} onChange={(e) => setDestrict(e.target.value)}/>
       <br/>
        <TextField style={{margin:"10px auto"}} variant='outlined' fullWidth label = "city" value={city} onChange={(e) => setCity(e.target.value)}/>
        <br/>
          <label>Time <input style={{margin:"10px auto"}} type="datetime-local" onChange={e=>setTime(e.target.value)}/></label> 
        <br/>
          { categories.map((category) => <label>{category.name} <input type="checkbox"  value={category.id} onChange={(e)=> {handleInput(e)}} /></label> )}
          <br/>
          <Button variant="contained" onClick={HandleSearch}>find</Button>
        </form>
        <h1>Akce</h1>    
        <ul>
            { events.map((event) => <Paper style={paperStyle2}><li>{event.name} <div><form onSubmit={(e) => handleAttend(e,event.id)}>
               
                <Button type='submit' variant='contained'>Attend</Button></form></div></li></Paper>)
           }
        </ul>

       </Paper>
       </Container>
    )

}

export default FindEvent;