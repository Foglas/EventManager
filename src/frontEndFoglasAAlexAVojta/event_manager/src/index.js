import React from "react";
import ReactDOM from 'react-dom/client';
import App from "./App"
import { BrowserRouter } from "react-router-dom";

 const page = ReactDOM.createRoot(document.getElementById('root'));

 page.render(
 <BrowserRouter>
  <App/>
 </BrowserRouter>
 

 );
