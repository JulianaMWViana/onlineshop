import { NavLink } from "react-router-dom";

function ThankYou() {
    return (
        <div className="container">
            <h1 className="m-4" style={{ textAlign: 'center' }}>Thanks for Shoping here!</h1>
            <img style={{ objectFit: 'cover', marginLeft: '25%' }}
                src='http://dl.glitter-graphics.net/pub/3677/3677301rmf3077rzf.gif' /> <br/>
            <NavLink to='/' className='btn btn-light btn-sm col-12'>
                <i className="fa fa-arrow-circle-left mr-2" aria-hidden="true"></i><b>Go back</b>
            </NavLink>
        </div>
    );
}

export default ThankYou;