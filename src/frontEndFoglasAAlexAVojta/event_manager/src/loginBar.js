
import React, {useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';

export default function LoginBar() {
    const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
    const [email, setEmail]=React.useState('')
    const [password, setPassword]= useState('')
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
                response.json();
                console.log('LOG IN SUCCESSFULL')
              }
                else throw response})
                console.log(email, password)
    }


  return (
    <Container>
<Paper elevation={3} style={paperStyle}>
            <h1 style={{color:"blue"}}><u>Login</u></h1>
    <form noValidate autoComplete="off">

    <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Email" variant="outlined" fullWidth 
      value={email}
      onChange={(e)=>setEmail(e.target.value)} />
      <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Password" variant="outlined" fullWidth 
     value={password}
     onChange={(e)=>setPassword(e.target.value)} />
    
     <Button variant="contained" onClick={handleClick}> Submit </Button>
    </form>
    </Paper>
    </Container>
  );
}
