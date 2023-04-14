
import React, {useEffect, useState } from "react";



function GetEvent ()  {
    const [data, setData] = useState('');


    useEffect(() => {
        fetch('http://localhost:8080/api/events/22')
        .then((response) => {
            if(response.ok){
                return response.json()
            }
            throw response;
        }).then(data => {
            setData(data);
        }).catch((error) => console.log(error))
    }, [])


    return (
    <div className="hadding"> 
    <p>{data.name}</p>
    </div>
    )
}


const Login = () => {
    const [message, setMessage] = useState('');

    useEffect(() => {
        const user = {
            method: "POST",
            headers : {
                'Authorization': '',
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify({
                email : 'react34@dns.cz',
                username : 'react34',
                password : 'password1',
                passwordAgain: 'password1',
                userDetails:{
                    name: 'reaktak',
                    surname: 'obivan',
                    dateOfBirth: '2004-03-05',
                    phone : '930 384 843'
                }
            })
        };
        console.log(user.body);
        fetch('http://localhost:8080/api/user/register', user)
       .then((response) => {
       if (response.status === 200){
        console.log('3sdhsdh');
        return response.json();
       } else{
       throw response
       }
    })
       .then((response) => {   
        console.log(response.message);
        return response})
       .then((response) => {setMessage(response.message)})
       .catch((error) => console.log(error))
}, [])

    return (
    <div className="hadding"> 
    <h1>message</h1>
    <p>{message}</p>
    <p>sdj</p>
    <GetEvent/>
    </div>
    )
}

export default Login;