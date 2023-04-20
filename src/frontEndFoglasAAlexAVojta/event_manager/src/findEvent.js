import React,{useEffect, useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';
import { eventWrapper } from '@testing-library/user-event/dist/utils';

function FindEvent(){
    const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
    const [city, setCity] = useState('');
    const [region, setRegion] = useState('');
    const [destrict, setDestrict] = useState('');
    const [time, setTime] = useState('');
    const [categories, setCategories] = useState([]);
    const [categoriesUl, setCategoriesCheck] = useState('');
    const [eventCategory, setEventCategory] = useState('');
    

    function setCategoryString(props){
        setEventCategory(eventCategory + ','+ props.id);
    }

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

    useEffect(()=>{console.log('category id '+ eventCategory)},[eventCategory])
        


    useEffect(()=> {
    const categoriesCheck = categories.map((category) => <label>{category.name} <input type="checkbox" value={category.id} onChange={(e) => setCategoryString(e.target.value)} /></label>)
    setCategoriesCheck(categoriesCheck);
    },[])

  
    return(
        <Container>
        <Paper  elevation={3} style={paperStyle}>
        <h1>Najdi event</h1>
        <form>
        <label>Region   <TextField variant='outlined' label = "region" value={region} onChange={(e) => setRegion(e.target.value)}/></label>
          
            <label>Destrict     <TextField variant='outlined' label = "destrict" value={destrict} onChange={(e) => setDestrict(e.target.value)}/> </label>
          <label>City   <TextField variant='outlined' label = "city" value={city} onChange={(e) => setCity(e.target.value)}/></label>
          <label>Time <input style={{margin:"10px auto"}} type="date" onChange={e=>setTime(e.target.value)}/></label> 
          {categoriesUl}
          <button variant="contained" >find</button>
          
        </form>
       </Paper>
       </Container>
    )

}

export default FindEvent;