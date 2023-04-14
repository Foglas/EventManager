import {Route, Routes} from "react-router-dom"
import LoginPage from "./loginPage"
import Menu from "./Menu"
import Navbar from "./Navbar"
import Footer from "./Footer"


export default function App(){



    return (
        <>
         <Navbar/>
                <div>
                    <Routes>
                        <Route path="/" element={<Menu/>}/>
                        <Route path="/LoginPage" element={<LoginPage/>}/>
                        <Route path="/Menu" element={<Menu/>}/>
                    </Routes>
                </div>
    
            <Footer/>
        </>
       
    )
}