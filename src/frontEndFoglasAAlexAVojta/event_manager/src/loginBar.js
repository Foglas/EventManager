
import React, {useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Button}  from '@mui/material';
import { pickersArrowSwitcherClasses } from '@mui/x-date-pickers/internals';


export default function LoginBar() {
    const [email, setEmail]=React.useState('')
    const [password, setPassword]= useState('')
    const [token, setToken] = useState('');
    const [error, setError] = useState('');

     const handleClick =(e)=>{
        e.preventDefault();

       fetch("http://localhost:8080/api/user/login",{
            method:"POST",
            headers:{
              'Authorization': '',
                "Content-Type":"application/json"},
                body : JSON.stringify({
                    email : email,
                    password : password
                })
            }).then(async(response)=>{
              if (response.status === 200){
                console.log('LOG IN SUCCESSFULL')
                return await response.json();
              } else{
                throw await response.json();
              }  
            }).then((response) => {
              setToken(response.message);
              setError("");
            }).catch((err)=>{
                setError(err.message);
                console.log("error " + err.message)
            })
                console.log(email, password)
            }
            if(token !== ''){
              localStorage.setItem('token', token);
            }
            console.log('token ' + token);
            

  return (
    <Container>
            <h1 style={{color:"blue"}}><u>Login</u></h1>
    <form noValidate autoComplete="off">

    <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Email" variant="outlined" fullWidth 
      value={email}
      onChange={(e)=>setEmail(e.target.value)} />
      <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Password" variant="outlined" fullWidth 
     value={password}
     onChange={(e)=>setPassword(e.target.value)} />
    <h3>{error}</h3>
     <Button  style={{margin:"10px auto"}} variant="contained" onClick={handleClick}> Submit </Button>
    </form>
    </Container>
  );
    }
