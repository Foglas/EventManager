import React,{useEffect, useState} from 'react';

import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';


function Events() {
        const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
        const [success, setSuccess] = useState('');
        const [events, setEvents] = useState([]);
        const [user, setUser] = useState('');
        const [eventName, setEventName] = useState('');
        const [eventDescription, setDescription] = useState('');
        const [eventTime, setEventTime] = useState(new Date());
        const [eventEndTime, setEventEndTime] = useState(new Date());
        const [place, setPlace] = useState('');
        const [places, setPlaces] = useState([]);
        const [eventUl, setEventUl] = useState('');
        const [parentOrg, setParentOrg] = useState('');
        const [orgs, setOrgs] = useState([]);
        const [clickedCount, setClickedCount] = useState(0);


        useEffect(() => {
          const userFromRequest = {
            method: "GET",
            headers : {
              'Authorization': 'Bearer ' + localStorage.getItem('token'),
              'Content-Type' : 'application/json'
            }
          }
        
          fetch('http://localhost:8080/api/auth/currentUser', userFromRequest)
            .then((response) => response.json())
            .then((user) => {
              console.log('user3 ' + user.id);
              setUser(user);
              return user;
            })
            .then((user) => {
              return fetch('http://localhost:8080/api/user/'+user.id+'/organization')
                .then((response) => response.json())
                .then((data) => setOrgs(data))
            })
            .then(() => {
              return fetch('http://localhost:8080/api/places')
                .then((response) => response.json())
                .then((data) => setPlaces(data))
            })
            .catch((error) => {
              console.log('Error fetching data: ', error);
            });
        }, [clickedCount]);

       

    return(
        <Container>
              



  <Container>
     <Paper  elevation={3} style={paperStyle}>
        <h1> Events </h1>
        <form noValidate autoComplete="off"> 
            <TextField style={{margin:"10px auto"}}  fullWidth variant='outlined' label = "event name" value={eventName} onChange={(e) => setEventName(e.target.value)}> Event name </TextField>
            <br></br>
            <label> Start time </label>
            <input style={{margin:"10px auto"}} type="datetime-local" onChange={e=>setEventTime(e.target.value)}/> 
            <label> End time </label>
            <input style={{margin:"10px auto"}} type="datetime-local" onChange={e=>setEventEndTime(e.target.value)}/>
            <Box
      component="form"
      sx={{
        '& .MuiTextField-root': { m: 1, width: 'full' },
      }}
      noValidate
      autoComplete="off"
    >
      <div>
       
        <TextField
          id="outlined-multiline-static"
          label="Description"
          multiline
          fullWidth
          rows={4}
          onChange={e=>setDescription(e.target.value)}
        />
      </div>
     
    </Box>
            <label>Select parent organization</label>
          
  <select
    id="select-parentOrg"
    value={parentOrg}
    onChange={e=>{setParentOrg(e.target.value);console.log(parentOrg)}} >
          {orgs.map((orgs) => (
              <option value={orgs.id}>{orgs.name}</option>
            ))}
            </select>
            <br/> <br/>
            <label>Select Place for event</label>
          
          <select
            id="select-place"
            onChange={e=>{setPlace(e.target.value);console.log(place)}} >
                  {places.map((places) => (
                      <option value={places.id}>{places.city}</option>
                    ))}
                    </select>
                    <br/> <br/>
            <Button variant="contained" onClick={createEvent}> Create Event</Button>
            <input type = "checkbox" value={place} onChange={e=>testFunc(e.target.value)}/>
            
        </form>
        <h3>{success}</h3>
        </Paper>
        
            <Paper elevation={3} style={paperStyle}>
            <h2> Attended events :</h2>
            <ul>
                {eventUl}
            </ul>
            </Paper>
       

   
    </Container>

   
    </Container>
    );
function testFunc(e){
  console.log(e)
}
        
function createEvent(e){
    console.log('trying to create event')
    e.preventDefault();
    const  event = {
          method: "POST",
          headers : {
              'Authorization': 'Bearer ' + localStorage.getItem('token'),
              'Content-Type' : 'application/json'
          },
          body : JSON.stringify({name : eventName, description : eventDescription,
          time : eventTime, endtime : eventEndTime, categoriesid : "6,7", placeId : place})
      }
      console.log(event)
    fetch('http://localhost:8080/api/auth/event/organization/'+parentOrg+'/save', event)
    .then((response)=>{
      if(response.status === 200){
      console.log('event created')
      setClickedCount(clickedCount + 1);
  };
   })
}

}

export default Events;