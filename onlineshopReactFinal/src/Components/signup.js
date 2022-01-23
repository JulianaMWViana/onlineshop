import { useState } from "react/cjs/react.development";
import axios from "axios";
import React from 'react';
import { Navigate } from "react-router-dom";

function Signup(props) {
    let [name, setName] = useState('');
    let [email, setEmail] = useState('');
    let [address, setAddress] = useState('');
    let [password, setPassword] = useState('');
    let [phoneNo, setPhoneNo] = useState('');
    let [redirectTo, setRedirectTo] = useState(null);
    let [errorMessage, setErrorMessage] = useState(null);

    const Signup = () => {
        axios.post('https://online-shop-vegetables.herokuapp.com/enduser', {
            name,
            password,
            email,
            phone: phoneNo,
            address
        })
            .then(r => {
                if (r.status === 200 || r.status === 201) {
                    axios.post('https://online-shop-vegetables.herokuapp.com/auth', {
                        password: password,
                        email: email
                    })
                        .then((response) => {
                            localStorage.setItem('user', JSON.stringify(response.data));
                            setRedirectTo("/");
                        });
                }
            })
            .catch(error => {
                console.log(error.response.data);
                setErrorMessage(error.response.data);
            });
    }

    return (
        <div className="container mt-2">
            {redirectTo && <Navigate to={redirectTo} />}
            <h2>Signup here</h2>
            <form onSubmit={Signup}>
            <div className="form-row">
                    <div className="form-group col-md-6">
                        <label>Name</label>
                        <input type="text" className="form-control" placeholder="Name" value={name} onChange={e => setName(e.target.value)} />
                    </div>
                    <div className="form-group col-6">
                        <label>Phone Number</label>
                        <input type="number" className="form-control" placeholder="Alternate cell number" value={phoneNo} onChange={e => setPhoneNo(e.target.value)} />
                    </div>
                </div>
                <div className="form-row">
                    <div className="form-group col-md-6">
                        <label>Email</label>
                        <input type="email" className="form-control" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
                    </div>
                    <div className="form-group col-6">
                        <label>Password</label>
                        <input type="password" className="form-control" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} />
                    </div>
                </div>
                <div className="form-row">
                    <div className="form-group col-12">
                        <label>Address</label>
                        <input type="text" className="form-control" placeholder="Address" value={address} onChange={e => setAddress(e.target.value)} />
                    </div>
                </div>
                {
                    errorMessage &&
                    <div>{errorMessage.map(err => <p className="alert alert-danger">{err.field}: {err.error}</p>)}</div>
                }
            </form>
            <button className="btn btn-success btn-sm btn-block" onClick={Signup}>Signup</button>
        </div>
    )
}

export default Signup;