import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";

function Navbar(props) {
    let [badgeValue, setBadgeValue] = useState(0);
    const [bol, setBol] = useState();
    const [isAdmin, setIsAdmin] = useState(false);
    const [user, setUser] = useState(null);
    let [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        let t = JSON.parse(localStorage.getItem('user'));
        if (t) {
            setUser(t);
            setIsAdmin(t.admin);
            setIsLoggedIn(true);
        }
    }, []);

    const getBadgeValue = () => {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        let val = 0;
        cart.forEach(elem => {
            val += elem.count;
        });
        setBadgeValue(val);
    }

    useEffect(() => {
        getBadgeValue();
    }, [bol])

    const logout = () => {
        localStorage.clear();
        window.location.href = '/';
    }

    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-light" style={{ backgroundColor: '#02754E' }}>
                <NavLink className="navbar-brand" to="/" style={{ color: '#fff', textDecoration: 'none', fontSize: '22px' }}>
                    <i className="fa fa-shopping-cart mr-2" aria-hidden="true"></i>
                    Vegetables Online Shopping
                </NavLink>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav mt-1">
                        <li className="nav-item">
                            <NavLink className="nav-link" to='/' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Home</NavLink>
                        </li>
                        {!isLoggedIn && <>
                            <li className="nav-item">
                                <NavLink className="nav-link" to='/login' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Login</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to='/signup' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Signup</NavLink>
                            </li>
                            </>}
                        {isLoggedIn && !isAdmin && <li className="nav-item">
                            <NavLink className="nav-link" to='/customerOrders' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Orders</NavLink>
                        </li>}
                        {isLoggedIn && isAdmin && <li className="nav-item">
                            <NavLink className="nav-link" to='/addVeg' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Add Vegetable</NavLink>
                        </li>}
                        {isLoggedIn && isAdmin && <li className="nav-item">
                            <NavLink className="nav-link" to='/customers' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Customers</NavLink>
                        </li>}
                        {isLoggedIn && isAdmin && <li className="nav-item">
                            <NavLink className="nav-link" to='/orders' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }}>Orders</NavLink>
                        </li>}
                        {isLoggedIn && <li className="nav-item">
                            <NavLink className="nav-link" to='#' style={{ color: '#fff', textDecoration: 'none', fontSize: '18px' }} onClick={logout}>Logout</NavLink>
                        </li>}
                    </ul>
                    <ul className="navbar-nav ml-auto">
                        {isLoggedIn &&
                            <><li className="nav-item">
                                <h4 className="mt-2 mr-3 text-white" style={{ fontFamily: 'monospace' }}>
                                    Welcome {isAdmin ? "Admin" : "Customer"}
                                </h4>
                            </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to='/cart' style={{ fontSize: '22px', color: '#fff' }}>
                                        <i className="fa fa-cart-plus" aria-hidden="true"><span className='badge badge-danger' id='lblCartCount'>{badgeValue}</span></i>
                                    </NavLink>
                                </li></>
                        }
                    </ul>
                </div>
            </nav>
        </div>
    );
}

export default Navbar;