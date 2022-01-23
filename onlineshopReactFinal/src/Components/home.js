import axios from "axios";
import { useEffect, useState } from "react";
import Navbar from "./navbar";
import swal from 'sweetalert';
import { NavLink } from "react-router-dom";

function HomeScreen() {
    const [arr, setArr] = useState([]);
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')) || []);
    const [user, setUser] = useState(JSON.parse(localStorage.getItem('user')) || {});
    const [bol, setBol] = useState(false);

    const renderAds = () => {
        axios.get('https://online-shop-vegetables.herokuapp.com/product')
            .then((response) => {
                setArr(response.data);
            }, (error) => {
                console.log(error);
            });
    }

    useEffect(() => {
        renderAds();
    }, [])

    const addToCart = id => {
        if (!user.token) {
            alert('Please login first to buy any vegetable.');
            return;
        };
        let idx = cart.findIndex(x => x.id === id);
        if (idx !== -1) {
            let elem = cart.find(x => x.id === id);
            elem.count += 1;
        } else {
            let elem = arr.find(x => x.id === id);
            elem.count = 1;
            cart.push(elem);
        }
        localStorage.setItem('cart', JSON.stringify(cart));

        let val = 0;
        cart.forEach(elem => {
            val += elem.count;
        });
        document.getElementById('lblCartCount').innerText = val;
    }

    return (
        <div className="container-fluid mt-3">
                <h1 className="m-3">Available Vegetables</h1>
                <p className="ml-4">Fresh vegetables are available in fair price.</p>
                {arr.map(elem => {
                    return <div className="card ml-2 mt-2" style={{ width: '20rem', height: '23rem', float: 'left', border: '1px solid grey' }} key={elem.id}>
                        <NavLink to={`/vegDetails?id=${elem.id}`} style={{color: 'black'}} title='Click here to see vegetable details'>
                            <img className="card-img-top" src={`${elem.url}`} style={{ objectFit: 'cover', height: '250px', width: '318px' }} />
                        </NavLink>
                        <div className="card-body">
                            <h5 className="card-title mb-2">{elem.name}</h5>
                            <p className="card-text cutText mb-2" title={elem.description}>{elem.description}</p>
                            <p className="card-text mb-2"><b>€{elem.price}</b>
                                <span style={{ float: 'right' }}>
                                    <i className="fa fa-cart-arrow-down" style={{ cursor: 'pointer' }} aria-hidden="true" onClick={() => addToCart(elem.id)}></i>
                                </span>
                            </p>
                        </div>
                    </div>
                })
            }
        </div>
    )
}

export default HomeScreen;