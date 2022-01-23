// import './App.css';
import React, {useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './Components/home';
import Login from './Components/login';
import Signup from './Components/signup';
import Navbar from './Components/navbar';
import Cart from './Components/cart';
import Checkout from './Components/checkout';
import ThankYou from './Components/thankyou';
import AddVeg from './Components/addVeg';
import Orders from './Components/orders';
import Customers from './Components/customers';
import VegDetails from './Components/vegDetails';
import CustomerOrders from './Components/customerOrders';
import Calendar from "./Components/Calendar";

function App() {

	return (
		<div className="App">
			<BrowserRouter>
				<Navbar />
				<Routes>
					<Route path='/' element={<Home/>} />
					<Route path='/login' element={<Login />} />
					<Route path='/signup' element={<Signup />} />
					<Route path='/cart' element={<Cart/>} />
					<Route path='/checkout' element={<Checkout/>} />
					<Route path='/calendar' element={<Calendar/>} />
					<Route path='/thankyou' element={<ThankYou/>} />
					<Route path='/addVeg' element={<AddVeg/>} />
					<Route path='/orders' element={<Orders/>} />
					<Route path='/customers' element={<Customers/>} />
					<Route path='/vegDetails' element={<VegDetails/>} />
					<Route path='/customerOrders' element={<CustomerOrders/>} />
				</Routes>
			</BrowserRouter>
		</div>
	);
}

export default App;
