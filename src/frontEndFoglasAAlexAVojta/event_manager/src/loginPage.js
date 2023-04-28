import { Button, Paper } from "@mui/material";
import LoginBar from "./loginBar";
import RegistrationBar from "./RegistrationBar";
import { useEffect, useState} from "react";
/*
    Funkce LoginPage je stránka, která spravuje login a registraci. Dál se stará o stav přihlášení - nepřihlášenému uživateli zobrazí odlišný omezený obsah.
*/

function LoginPage() {
    const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' }
    const [registration, setRegistration] = useState(false);
    const [buttonName, setButtonName] = useState('Registration');
    const [login, setLogin] = useState('');
    const [view, setView] = useState('');
    /*
    logout je soubor příkazů použitý při stisknutí LOGOUT tlačítka.
    Odhlásí aktuálně přihlášeného uživatele a upraví obsah zobrazený na stránce - zmizí přístup k určitým položkám navigačního panelu.
    */
   
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
        Tento hook useEffect nastavuje hodnotu login
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

    /*
        handleRegistration slouží k přepínání mezi loginem a registrací
    */

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
    /*
            Tento useEffect hook slouží k zobrazení buď registračního nebo přihlašovacího formuláře.
            HandleRegistration nastaví, co tento hook zobrazí
    */

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
            Return zobrazí na stránce aktuálně zvolený formulář
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
