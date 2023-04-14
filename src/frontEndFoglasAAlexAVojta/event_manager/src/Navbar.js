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
                    <FLink to="/Menu"> Menu </FLink>
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