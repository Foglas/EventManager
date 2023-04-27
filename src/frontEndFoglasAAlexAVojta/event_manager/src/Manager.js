import React,{useEffect, useState} from 'react';

import { Paper, Button} from '@mui/material';
import "./style.css"

export default function Manager() {
    const paperStyle={padding:'50px 20px',width:400,margin:'20px auto'}
    const paperStyle2={padding:' 20px',width:200,margin:'20px auto'}
    const [user, setUser] = useState('');
    const [parentOrg, setParentOrg] = useState('');
    const [events, setEvents] = useState([]);
    const [orgs, setOrgs] = useState([]); 
    const [clickedCount, setClickedCount] = useState(0);

    function handleDeleteEvent(e, id){
      e.preventDefault();
      setClickedCount(clickedCount+1);
      
      const  header = {
          method: "DELETE",
          headers : {
              'Authorization': 'Bearer ' + localStorage.getItem('token'),
              'Content-Type' : 'application/json'
          },
        }
      console.log('id ' + id);
      
      fetch('http://localhost:8080/api/auth/event/'+id+'/delete', header)
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
            console.log('user ' + user.id);
            setUser(user);
            return user;
          })
          .then((user) => {
            return fetch('http://localhost:8080/api/user/'+user.id+'/organization')
              .then((response) => response.json())
              .then((data) => setOrgs(data))
              .then(() => user);
          })
          .then((user) => {
            return fetch('http://localhost:8080/api/organization/'+parentOrg+'/events')
              .then((response) => response.json())
              .then((data) => setEvents(data))
              .then(console.log(events))
              .then(() => user);
          })
          
          .catch((error) => {
            console.log('Error fetching data: ', error);
          });
      }, [clickedCount]);


    return (
        <div >
        <div class="x"> <Paper elevation={3} style={paperStyle}>
        <h2 class="h2att"> Parent Organization:</h2>
        <select
  id="select-parentOrg"
  value={parentOrg}
  onChange={(e) => {
    const selectedParentOrg = e.target.value;
    setParentOrg(selectedParentOrg);
    console.log(selectedParentOrg);
    setClickedCount(clickedCount+1)
  }}
>
  <option value="" disabled>Select parent organization</option>
  {orgs.map((org) => (
    <option key={org.id} value={org.id}>{org.name}</option>
  ))}
</select>
    </Paper></div>
        
        
        <div class="x"> <Paper elevation={3} style={paperStyle}>
        <h2 class="h2att"> Managable Events:</h2>
        <ul>
        { events.map((event) => <li> <Paper><h2>{event.name}</h2>
        <h3>{event.description}</h3>
        <form on onSubmit={(e) => handleDeleteEvent(e,event.id)}>
          <Button type='submit' variant='contained'>DELETE</Button></form></Paper></li>)
           }
      </ul>
    </Paper></div>
    </div>
    )
}

