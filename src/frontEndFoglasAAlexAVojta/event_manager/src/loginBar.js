import React, {useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Button}  from '@mui/material';
/*
    Funkce LoginBar je přihlašovací formulář, který je volán z LoginPage
*/

export default function LoginBar() {
    const [email, setEmail]=React.useState('')
    const [password, setPassword]= useState('')
    const [error, setError] = useState('');

    /*
        handleClick spustí pokus o login zasláním požadavku na Spring Boot, údaje se načtou z formuláře
    */
  
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
              localStorage.setItem('login', true);
              window.dispatchEvent(new Event('storage'));
             localStorage.setItem('token', response.message);
              setError("");
            }).catch((err)=>{
                setError(err.message);
                console.log("error " + err.message)
            })
                console.log(email, password)
      }
      /*
          Return vrátí přihlašovací formulář
      */
          
  return (
    <Container>
            <h1 style={{color:"blue"}}><u>Login</u></h1>
    <form noValidate autoComplete="off">

    <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Email" variant="outlined" fullWidth 
      value={email}
      onChange={(e)=>setEmail(e.target.value)} />
      <TextField style={{margin:"10px auto"}} type='password' id="outlined-basic" label="Password" variant="outlined" fullWidth 
     value={password}
     onChange={(e)=>setPassword(e.target.value)} />
    <h3>{error}</h3>
     <Button  style={{margin:"10px auto"}} variant="contained" onClick={handleClick}> Submit </Button>
    </form>
    </Container>
  );
  }
