import { NavLink } from "react-router-dom";
import { useState } from "react/cjs/react.development";
import { Navigate } from "react-router-dom";
import axios from 'axios';

function Checkout() {
    let [name, setName] = useState('');
    let [email, setEmail] = useState('');
    let [address, setAddress] = useState('');
    let [password, setPassword] = useState('');
    let [phoneNo, setPhoneNo] = useState('');
    let [redirectTo, setRedirectTo] = useState(null);

    const submit = (e) => {
        e.preventDefault();

        setRedirectTo('/calendar');
    }

    return (
        <div className="container mt-3">
        {redirectTo && <Navigate to={redirectTo} />}
            <div className="row">
                
                <NavLink to='/cart' className='btn btn-light' style={{ marginLeft: '60%' }}>
                    <i className="fa fa-arrow-circle-left mr-2" aria-hidden="true"></i><b>Go back</b>
                </NavLink>
            </div>
            <p>Please fill all the credentials properly so that you may receive your package without any discomfort</p>
            <form onSubmit={submit}>
                
                <div className="form-row">
                    <div className="form-group col-12">
                        <button type="submit" className="btn btn-success btn-sm btn-block" onClick={submit}>Next</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default Checkout;