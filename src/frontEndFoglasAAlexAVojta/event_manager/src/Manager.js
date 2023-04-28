import React,{useEffect, useState} from 'react';

import { Paper, Button} from '@mui/material';
import "./style.css"
/*
    Funkce Manager slouží k zobrazení stránky pro správu eventů - je k dispozici pouze smazání eventů.
    Obsahuje 2 segmenty "Parent Organization" a "Managable Events". V sekci parent organization se zvolí
    organizace a druhý segment zobrazí eventy pod danou organizací s tlačítkem delete, který slouží pro smazání eventu.
*/

export default function Manager() {
    const paperStyle={padding:'50px 20px',width:400,margin:'20px auto'}
    const [user, setUser] = useState('');
    const [parentOrg, setParentOrg] = useState('');
    const [events, setEvents] = useState([]);
    const [orgs, setOrgs] = useState([]); 
    const [clickedCount, setClickedCount] = useState(0);

    /*
      Funkce handleDeleteEvent pošle na Spring Boot DELETE request pro event, jehož ID přijde jako parametr funkce.
    */

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
    /*
        Hook useEffect slouží k načtení aktuálního uživatele, jeho organizací a eventů aktuálně zvolené organizace.
    */
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
      /*
          Return vrátí 2 segmenty - Parent Organization a Managable events, které se namapují podle aktuální parent organization
      */

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

