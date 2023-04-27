import "./style.css"
import { Link, useMatch, useResolvedPath } from "react-router-dom"
import { useEffect, useState} from "react";





export default function Navbar() {
    const [login, setLogin] = useState('');
    const [view, setView] = useState('');

    useEffect(() => {
        if(localStorage.getItem('token') != ""){
            setLogin(true);
            console.log("jedna")
        } else{
            setLogin(false);
            console.log('dva')
        }
        window.addEventListener("storage", () => {
            let login = localStorage.getItem('login'); 
            setLogin(login);
            
        })
    }, []);

    useEffect(()=>{console.log('change in navbar ' + login);},[login]);

    useEffect(() => {
        console.log("navbar " +login)
        if (login == false) {
            console.log("navbar3 "+ login);
            setView(() => {
                return (<ul>
                    <li>
                        <FLink to="/loginPage"> Login</FLink>
                    </li>
                    <li>
                        <FLink to="/search"> Search </FLink>
                    </li>
                </ul>)
            })
        } else {
            console.log("navbar2"+login)
            setView(()=>{
                return(
                    <ul>
                    <li>
                        <FLink to="/loginPage"> Login</FLink>
                    </li>
                    <li>
                        <FLink to="/organization"> My Organizations </FLink>
                    </li>
                    <li>
                        <FLink to="/events"> My Events </FLink>
                    </li>
                    <li>
                        <FLink to="/search"> Search </FLink>
                    </li>
                    <li>
                        <FLink to="/manage">Manager</FLink>
                    </li>
                </ul>

                )
            })
          
        }
    }, [login]);



    return (
        <nav className="nav">
            <Link to="/" className="title"> EventManager</Link>
            {view}

        </nav>
    )
}

function FLink({ to, children, ...props }) {
    const resolvePath = useResolvedPath(to)
    const isActive = useMatch({ path: resolvePath.pathname, end: true })
    return (
        <li className={isActive ? "active" : ""}>
            <Link to={to}  {...props} >
                {children}
            </Link>

        </li>
    )

}