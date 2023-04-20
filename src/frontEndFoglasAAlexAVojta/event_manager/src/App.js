import {Route, Routes} from "react-router-dom"
import LoginPage from "./loginPage"
import Navbar from "./Navbar"
import Footer from "./Footer"
import Organization from "./Organization"
import Events from "./Events"
import "./style.css"


export default function App(){



    return (
        <>
         <Navbar/>
                <div>
                    <Routes>
                        <Route path="/" element={<LoginPage/>}/>
                        <Route path="/LoginPage" element={<LoginPage/>}/>
                        <Route path="/organization" element={<Organization/>}/>
                        <Route path="/events" element={<Events/>}/>
                    </Routes>
                </div>
    
            <Footer/>
        </>
       
    )
}