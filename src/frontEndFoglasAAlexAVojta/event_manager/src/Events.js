import React, { useEffect, useState } from 'react';

import { Container } from '@mui/system';
import { Paper, Button } from '@mui/material';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import FindEvent from './findEvent';
import "./style.css"


function Events() {
  const paperStyle = { padding: '50px 20px', width: 550, margin: '20px auto' }
   const paperStyle2={padding:' 20px',width:350,margin:'20px auto'}
  const [success, setSuccess] = useState('');
  const [events, setEvents] = useState([]);
  const [user, setUser] = useState('');
  const [eventName, setEventName] = useState('');
  const [eventDescription, setDescription] = useState('');
  const [eventTime, setEventTime] = useState('');
  const [eventEndTime, setEventEndTime] = useState('');
  const [place, setPlace] = useState('');
  const [places, setPlaces] = useState([]);
  const [attendedEvents, setAttendedEvents] = useState([]);
  const [eventUl, setEventUl] = useState('');
  const [parentOrg, setParentOrg] = useState('');
  const [orgs, setOrgs] = useState([]);
  const [clickedCount, setClickedCount] = useState(0);
  const [categories, setCategories] = useState([]);
  const [checked, setChecked] = useState([]);


  const checkedCategoriesString = checked.length ? checked.reduce((total, category) => { return total + ',' + category; }) : '';

  //chatgpt
  /*
  const [selectedCategories, setSelectedCategories] = useState("");
  
  const handleCheckboxChange = (e) => {
    const category = e.target.value;
    if (e.target.checked) {
      setSelectedCategories((prevSelectedCategories) =>
        prevSelectedCategories ? `${prevSelectedCategories},${category}` : category
      );
    } else {
      setSelectedCategories((prevSelectedCategories) =>
        prevSelectedCategories
          .split(",")
          .filter((c) => c !== category)
          .join(",")
      );
    }
  };
  */

  const handleInput = (event) => {
    var array = [...checked];
    if (event.target.checked) {
      console.log("checked " + event.target.checked);
      array = [...checked, event.target.value];
    } else {
      array.splice(checked.indexOf(event.target.value), 1);
    }
    setChecked(array);
  };
  //chatgpt

  useEffect(() => {
    const userFromRequest = {
      method: "GET",
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      }
    }

    fetch('http://localhost:8080/api/auth/currentUser', userFromRequest)
      .then((response) => response.json())
      .then((user) => {
        console.log('user3 ' + user.id);
        setUser(user);
        return user;
      })
      .then((user) => {
        return fetch('http://localhost:8080/api/user/' + user.id + '/organization')
          .then((response) => response.json())
          .then((data) => setOrgs(data))
          .then(() => user);
      })
      .then((user) => {
        return fetch('http://localhost:8080/api/places')
          .then((response) => response.json())
          .then((data) => setPlaces(data))
          .then(() => user);
      })
      .then((user) => {
        return fetch('http://localhost:8080/api/events/' + user.id + '/getAttendedEvents')
          .then((response) => response.json())
          .then((data) => setAttendedEvents(data));
      }).then(() => {
        const eventHeader = {
          method: "GET",
          headers: {
            'Authorization': '',
            'Content-Type': 'application/json'
          }
        }

        fetch('http://localhost:8080/api/category', eventHeader)
          .then((response) => {
            if (response.status == 200) {
              console.log('categories  ok');
            }
            return response.json()
          })
          .then((category) => {
            setCategories(category);
          })
          .catch((error) => {
            console.log('Error fetching data: ', error);
          });
      })
  }, [clickedCount]);

  /*
      <label>
   <input
            type="checkbox"
            value="6"
            checked={selectedCategories.includes("6")}
            onChange={handleCheckboxChange}
          />
          Zavod
        </label>
        <label>
          <input
            type="checkbox"
            value="7"
            checked={selectedCategories.includes("7")}
            onChange={handleCheckboxChange}
          />
          Workshop
        </label>
        <label>
          <input
            type="checkbox"
            value="8"
            checked={selectedCategories.includes("8")}
            onChange={handleCheckboxChange}
          />
          Koncert
        </label>
        <label>
          <input
            type="checkbox"
            value="9"
            checked={selectedCategories.includes("9")}
            onChange={handleCheckboxChange}
          />
         Koncert
        </label>
  */


  return (

    <Container >
      <div class='div1'>
        <div class="x">
          <Paper elevation={3} style={paperStyle}>
            <h1> Create Event</h1>
            <form noValidate autoComplete="off">
              <TextField style={{ margin: "10px auto" }} fullWidth variant='outlined' label="event name" value={eventName} onChange={(e) => setEventName(e.target.value)}> Event name </TextField>
              <br></br>
              <label> Start time </label>
              <input style={{ margin: "10px auto" }} type="datetime-local" onChange={e => setEventTime(e.target.value)} />
              <label> End time </label>
              <input style={{ margin: "10px auto" }} type="datetime-local" onChange={e => setEventEndTime(e.target.value)} />
              <Box
                component="form"
                sx={{
                  '& .MuiTextField-root': { m: 1, width: 'full' },
                }}
                noValidate
                autoComplete="off"
              >


                <TextField
                  id="outlined-multiline-static"
                  label="Description"
                  multiline
                  fullWidth
                  rows={4}
                  onChange={e => setDescription(e.target.value)}
                />


              </Box>
              <label>Select parent organization</label>

              <select
                id="select-parentOrg"
                value={parentOrg}
                onChange={(e) => {
                  const selectedParentOrg = e.target.value;
                  setParentOrg(selectedParentOrg);
                  console.log(selectedParentOrg);
                }}
              >
                <option value="" disabled>Select parent organization</option>
                {orgs.map((org) => (
                  <option key={org.id} value={org.id}>{org.name}</option>
                ))}
              </select>
              <br /> <br />
              <label>Select Place for event</label>

              <select
                id="select-place"
                value={place}
                onChange={(e) => {
                  setPlace(e.target.value);
                  console.log(e.target.value);
                }}
              >

                <option value="" disabled>Select place for event</option>
                {places.map((place) => (
                  <option key={place.id} value={place.id}>
                    {'Destrict ' + place.destrict + ' Region ' + place.region + ' City ' + place.city + ' Street ' + place.street + ' BIN ' + place.bin}
                  </option>
                ))}
              </select>
              <br /> <br />


              {categories.map((category) => <label>{category.name} <input type="checkbox" value={category.id} onChange={(e) => handleInput(e)} /></label>)}

              <Button variant="contained" onClick={createEvent}> Create Event</Button>
            </form>
          </Paper>
        </div>

        <div class="x"> <Paper elevation={3} style={paperStyle}>
          <h2 class="h2att"> Attended events :</h2>
          <ul>
            {attendedEvents.map((attendedEvents) => <Paper style={paperStyle2}><li><h2>{attendedEvents.name}</h2> <br />
              {attendedEvents.dateAndTime}</li></Paper>)
            }
          </ul>
        </Paper></div>




      </div>
    </Container>
  );

  function createEvent(e) {
    console.log('trying to create event ' + eventTime)
    e.preventDefault();
    var event = null;
    if (eventEndTime == '') {
      event = {
        method: "POST",
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: eventName, description: eventDescription,
          time: eventTime, categoriesid: checkedCategoriesString, placeId: place
        })
      }
    } else {
      event = {
        method: "POST",
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: eventName, description: eventDescription,
          time: eventTime, endtime: eventEndTime, categoriesid: checkedCategoriesString, placeId: place
        })
      }
    }

    console.log(event)
    fetch('http://localhost:8080/api/auth/event/organization/' + parentOrg + '/save', event)
      .then((response) => {
        if (response.status === 200) {
          console.log('event created')
          setClickedCount(clickedCount + 1);
        };
      })
  }
}

export default Events;
