const express = require("express");
const path = require("path");
const methodOverride = require("method-override");
const setAttendedEventsStyle = require("./Controller/AttendedEvents.js");
const getEvent = require("./Model/EventModel.js")().then();
const app = express();

app.set("view engine", "ejs");
app.set("views", path.join(__dirname,"/Views"));

app.use(express.static(__dirname+"/Public"));
app.use(methodOverride("_method"));
app.use(express.urlencoded({extended: true}));
app.use(express());


app.listen(8081, ()=>{
    console.log("Server is started and listening");
});

app.get("/events", (req, res) => {
    
   getEvent().then((events) => {
        const styles = setAttendedEventsStyle(events, );
        res.render("Events", {events});
    });

});
  