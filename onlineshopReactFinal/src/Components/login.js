import { useState } from "react/cjs/react.development";
import axios from "axios";
import React from 'react';
import { Navigate } from "react-router-dom";

function Login(props) {
    let [email, setEmail] = useState('');
    let [password, setPassword] = useState('');
    let [redirectTo, setRedirectTo] = useState(null);
    let [errorMessage, setErrorMessage] = useState(null);

    const login = () => {
        axios.post('https://online-shop-vegetables.herokuapp.com/auth', {
            password: password,
            email: email
        })
            .then((response) => {
                localStorage.setItem('user', JSON.stringify(response.data));
                window.location.href = "/";
            })            
            .catch(error => {
                setErrorMessage(`Invalid credentials.`);
            });
    }

    return (
        <div className="container mt-2">
            {redirectTo && <Navigate to={redirectTo} />}
            <h2>Login here</h2>
            <form onSubmit={login}>
                <div className="form-group">
                    <label>Email address</label>
                    <input type="email" className="form-control" aria-describedby="emailHelp" placeholder="Enter email"
                        value={email} onChange={e => setEmail(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="Enter Password"
                        value={password} onChange={e => setPassword(e.target.value)} />
                </div>
                {
                    errorMessage &&
                    <p className="alert alert-danger">{errorMessage}</p>
                }
            </form>
            <button className="btn btn-success btn-sm btn-block" onClick={login}>Login</button>
        </div>
    )
}

export default Login;