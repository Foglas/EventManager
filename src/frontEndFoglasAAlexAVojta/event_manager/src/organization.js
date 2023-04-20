import React,{useEffect, useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';


function Organization(){
    const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
    const [organizationName, setOrganizationName] = useState('');
    const [organizations, setOrganizations] = useState([]);
    const [user, setUser] = useState('');
    const [organizationUl, setOrganizationUl] = useState('');
    const [clickedCount, setClickedCount] = useState(0);

    function createOrganization(e){
    e.preventDefault();
      const  organization = {
            method: "POST",
            headers : {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify({name : organizationName})
        }
      fetch('http://localhost:8080/api/auth/organization/save', organization)
      .then((response)=>{
        if(response.status === 200){
        console.log('created')
        setClickedCount(clickedCount + 1);
    };
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
    .then((response) => {
       return response.json()})
    .then((user) => {
        console.log('user3 ' + user.id);
        setUser(user);
        return user;
    }).then((user) => fetch('http://localhost:8080/api/user/'+user.id+'/organization')
    .then((response) => {
   
       return response.json();
    })
    .then((data) => {
      setOrganizations(data)
      console.log(organizations)
   }))},[clickedCount]);
  
    
   useEffect(()=> {
    console.log('use ' + organizations);
  const organizationUl = organizations.map((organization) => <li>{organization.name}</li>);
setOrganizationUl(organizationUl);
   },[organizations]);



    return(
        <Container>
     <Paper  elevation={3} style={paperStyle}>
        <h1> Organizace </h1>
        <form noValidate autoComplete="off"> 
            
            <TextField style={{margin:"10px auto"}} variant='outlined' label = "organization name" value={organizationName} fullWidth onChange={(e) => setOrganizationName(e.target.value)}> organizationName</TextField>
            <Button variant="contained" onClick={createOrganization}>Create organization</Button>
        </form>
        
        <h2>Organizace uživatele: </h2>
            <ul>
                {organizationUl}
            </ul>
        

    </Paper>
    </Container>
    );
}

export default Organization;