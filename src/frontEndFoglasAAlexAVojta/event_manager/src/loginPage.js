import { Button, Paper } from "@mui/material";
import LoginBar from "./loginBar";
import RegistrationBar from "./RegistrationBar";
import { useState } from "react";
import Organization from "./Organization";
import FindEvent from "./findEvent";

 function LoginPage(){
    const paperStyle={padding:'50px 20px',width:600,margin:'20px auto'}
    const [registration, setRegistration] = useState(false);
    const [buttonName, setButtonName] = useState('Registration');

    const HandleRegistration= () =>{
        if(registration == true){
            setButtonName('Registration');
            setRegistration(false);
        } else {
            setRegistration(true);
            console.log('TRUE');
            setButtonName('Login');
        }
    }
    return (
        <div>
   <Paper elevation={3} style={paperStyle}>
    {
     registration === true ?(
     <RegistrationBar/>
     ):(
        <LoginBar/>
     
     )
 }
    <Button variant="contained" onClick={HandleRegistration}>{buttonName}</Button>  
   
    </Paper>
  
    </div>
    )
}





export default LoginPage;
