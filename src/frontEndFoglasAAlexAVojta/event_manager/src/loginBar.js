
import React, {useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';
import RegistrationBar from './RegistrationBar';
import LoginPage from './loginPage';

export default function LoginBar() {
    const [email, setEmail]=React.useState('')
    const [password, setPassword]= useState('')
    const [token, setToken] = useState('');
    const [error, setError] = useState('');

    const handleClick=(e)=>{
        e.preventDefault()
        fetch("http://localhost:8080/api/user/login",{
            method:"POST",
            headers:{
              'Authorization': '',
                "Content-Type":"application/json"},
                body : JSON.stringify({
                    email : email,
                    password : password
                })
            }).then((response)=>{
              if (response.status === 200){
                console.log('LOG IN SUCCESSFULL')
                return response.json();
              } else{
                setError(response.json().message);
                throw response
              }  
            }).then((response) => {
              setToken(response.message);
            })
                console.log(email, password)
            }

            if(token != ''){
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
     <Button variant="contained" onClick={handleClick}> Submit </Button>
    </form>
    </Container>
  );
}
