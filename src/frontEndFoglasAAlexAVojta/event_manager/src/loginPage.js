import { Button, Paper } from "@mui/material";
import LoginBar from "./loginBar";
import RegistrationBar from "./RegistrationBar";
import { useEffect, useState} from "react";


function LoginPage() {
    const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' }
    const [registration, setRegistration] = useState(false);
    const [buttonName, setButtonName] = useState('Registration');
    const [login, setLogin] = useState('');
    const [view, setView] = useState('');
   
    const logout = (e) => {
        e.preventDefault();
        console.log("logout")
        localStorage.setItem('login', false);
        window.dispatchEvent(new Event('storage'));
        setLogin(false);
        console.log(login)
        window.location.reload();
        const user = {
            method: "POST",
            headers: {
              'Authorization': 'Bearer ' + localStorage.getItem('token'),
              'Content-Type': 'application/json'
            }
          }

        fetch('http://localhost:8080/api/auth/user/logout', user)
        .then((response) => {
          if (response.status == 200) {
                console.log("logout success");
                return;
                }
                throw response;
        }).catch((response)=>{
            console.log(response);
        })
        localStorage.setItem('token', "");
       
    }
/*
    useState(()=>{
        setView(() => {
            if (registration == true) {
                   return(
                   <div>
                        <RegistrationBar />
                        <Button variant="contained" onClick={HandleRegistration}>{buttonName}</Button>
                    </div>
                    )
                
            } else {
                return(
                    <div>
                        <LoginBar />
                        <Button variant="contained" onClick={HandleRegistration}>{buttonName}</Button>
                    </div>
         ) }
        
        });
    },[]);
    
*/

    useEffect(() => {
        if(localStorage.getItem('token') != ""){
            setLogin(true);
            console.log('jednaPage')
        } else{
            setLogin(false);
            console.log("dvaPage")
        }
        window.addEventListener("storage", () => {
            setLogin(window.localStorage.getItem('login'));
            console.log('change ' + login);
        })
    }, []);

    console.log("loginDefault " + login);

    const HandleRegistration = () => {
        if (registration == true) {
            console.log("regis")
            setButtonName('Registration');
            setRegistration(false);
        } else {
            setRegistration(true);
            console.log('TRUE');
            setButtonName('Login');
        }
    }

  

    useEffect(() => {
    
        if (login == false) {
            console.log("heeey " + login)
            setView(() => {
                if (registration == true) {
               return(
                 <div>
                        <RegistrationBar />
                        <Button variant="contained" onClick={HandleRegistration}>{buttonName}</Button>
                    </div>
                    )
                } else {
                    return(
                    <div>
                        <LoginBar />
                        <Button variant="contained" onClick={HandleRegistration}>{buttonName}</Button>
                    </div>
                    )
                }

            });
        } else {
               console.log("heeeeey222")
            setView(() => {
                
                return(<div>
                        <h1>You are in</h1>
                        <Button variant="contained" onClick={(e) => logout(e)}>Logout</Button>
                    </div>
                )
            });
        }
    }, [login,registration]);



    /*
     {
                        login === false ? (
                            registration === true ? (
                                <RegistrationBar />
                            ) : (
                                <div>
                                    <LoginBar />
                                    <Button variant="contained" onClick={HandleRegistration}>{buttonName}</Button>
                                </div>
                            )
                        ) : (
                            <div>
                                <h1>You are in</h1>
                                <Button variant="contained" onClick={(e) => logout(e)}>Logout</Button>
                            </div>
                        )
                    }
    */

    return (
        <div>
            <Paper elevation={3} style={paperStyle}>
                {view}

            </Paper>

        </div>
    )

    }





export default LoginPage;
