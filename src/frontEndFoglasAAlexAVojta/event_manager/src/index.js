import React from "react";
import ReactDOM from 'react-dom/client';
import App from "./App"
import { BrowserRouter } from "react-router-dom";

/*
Defaultní dokument, který odkazuje na Funkci App, která se klíčová pro zobrazení stránky
*/

 const page = ReactDOM.createRoot(document.getElementById('root'));

 page.render(
 <BrowserRouter>
  <App/>
 </BrowserRouter>
 

 );
