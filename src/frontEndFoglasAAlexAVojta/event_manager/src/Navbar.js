import "./style.css"
import {Link, useMatch, useResolvedPath} from "react-router-dom"

export default function Navbar() {
    return(
        <nav className="nav">
            <Link to="/" className="title"> EventManager</Link>
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

        </nav>
    )
}

function FLink({to, children, ...props}){
   const resolvePath = useResolvedPath(to)
   const isActive = useMatch({path: resolvePath.pathname, end : true})
        return (
        <li className={isActive ? "active" : ""}>
            <Link to ={to}  {...props} >
                {children}
            </Link>

        </li>
    )

}