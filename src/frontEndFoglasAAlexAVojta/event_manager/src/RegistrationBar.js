
import React,{useState} from 'react';
import TextField from '@mui/material/TextField';
import { Container} from '@mui/system';
import { Paper, Button} from '@mui/material';


export default function RegistrationBar() {
    const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
    const [email, setEmail]=React.useState('')
    const [username, setUsername]= useState('')
    const [name, setName]=React.useState('')
    const [surname, setSurname]= useState('')
    const [phone, setPhone]=React.useState('')
    const [password, setPassword]= useState('')
    const [dateofbirth, setDateofbirth]= useState(new Date())
    const handleClick=(e)=>{
        e.preventDefault();
        
        const user = {
          method:"POST",
          headers:{
              'Authorization' : '',
              'Content-Type':'application/json'
            },
              body : JSON.stringify({
                email : email,
                username : username,
                password : password,
                passwordAgain: password,
                userDetails:{
                    name: name,
                    surname: surname,
                    dateOfBirth: dateofbirth,
                    phone : phone
                }
              })
          };
          console.log(user.body);
        fetch('http://localhost:8080/api/user/register',user).then((response)=>{
              if (response.status === 200){
                response.json();
                console.log('REGISTERED')
              }
                else throw response})
    }


  return (
    <Container>
<Paper elevation={3} style={paperStyle}>
            <h1 style={{color:"blue"}}><u>Registration</u></h1>
    <form noValidate autoComplete="off">

    <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Email" variant="outlined" fullWidth 
      value={email}
      onChange={(e)=>setEmail(e.target.value)} />

      <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Username" variant="outlined" fullWidth 
     value={username}
     onChange={(e)=>setUsername(e.target.value)} />

     <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Name" variant="outlined" fullWidth 
      value={name}
      onChange={(e)=>setName(e.target.value)} />

      <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Surname" variant="outlined" fullWidth 
     value={surname}
     onChange={(e)=>setSurname(e.target.value)} />

     <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Phone" variant="outlined" fullWidth 
      value={phone}
      onChange={(e)=>setPhone(e.target.value)} />
      <externalDatePicker/>
    
      <input style={{margin:"10px auto"}} type="date" onChange={e=>setDateofbirth(e.target.value)}/>

      <TextField style={{margin:"10px auto"}} id="outlined-basic" label="Password" variant="outlined" fullWidth 
     value={password}
     onChange={(e)=>setPassword(e.target.value)} />
    
     <Button variant="contained" onClick={handleClick}> Submit </Button>
    </form>
    
    </Paper>
    </Container>
  );
}
