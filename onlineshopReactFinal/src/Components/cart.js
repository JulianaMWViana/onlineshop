import { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import Swal from "sweetalert2";

function Cart() {
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')) || []);
    const [count, setCount] = useState(1);
    const [total, setTotal] = useState(0);
    const [bol, setBol] = useState(false);
    const [showBtns, setShowBtns] = useState(true);

    const setVal = () => {
        cart.length === 0 ? setShowBtns(true) : setShowBtns(false);
        let k = JSON.parse(localStorage.getItem('cart')) || [];
        setCart(k);
    }

    useEffect(() => {
        countTotal();
        setVal();

        let val = 0;
        cart.forEach(elem => {
            val += elem.count;
        });
        document.getElementById('lblCartCount').innerText = val;
    }, [bol]);

    const countTotal = () => {
        let k = 0;
        let a = JSON.parse(localStorage.getItem('cart')) || [];
        a.forEach(elem => {
            k += (elem.price * elem.count);
        });
        k = k.toFixed(2);
        setTotal(k);
    }

    const subtract = (id) => {
        bol === false ? setBol(true) : setBol(false);
        let elem = cart.find(x => x.id === id);
        if (elem.count == 1) {
            remove(id);
        } else {
            elem.count -= 1;
            localStorage.setItem('cart', JSON.stringify(cart));
        }
    }

    const add = (id) => {
        bol === false ? setBol(true) : setBol(false);
        let elem = cart.find(x => x.id === id);

        if(elem.count < elem.quantity) {
            elem.count += 1;
            localStorage.setItem('cart', JSON.stringify(cart));
        }
        else {
            Swal.fire(`Maximum available quantity for ${elem.name} is ${elem.quantity}.`);
        }
    }

    const remove = (id) => {
        bol === false ? setBol(true) : setBol(false);
        let k = cart.filter(x => x.id !== id);
        setCart(k);
        countTotal();
        localStorage.setItem('cart', JSON.stringify(k));
    }

    const emptyCart = () => {
        localStorage.removeItem('cart');
        bol === false ? setBol(true) : setBol(false);
        setShowBtns(false);
    }

    return (
        <div className='container'>
            <h1 className='m-2'>Your Shopping Cart</h1>
            {showBtns && <p className='m-3'><b style={{ color: 'grey' }}>Cart is currently empty</b></p>}
            <div className='row'>
                {cart.map(elem => {
                    return <div className="card ml-2 mt-2" style={{ width: '17rem', float: 'left' }} key={elem.id}>
                        <img className="card-img-top" src={`${elem.url}`} style={{ objectFit: 'cover', height: '250px', width: '270px' }} />
                        <div className="card-body mb-0">
                            <span className="card-title mb-2"><b>{elem.name}</b></span>
                            <span className="card-text mb-2" style={{ float: 'right' }}><b>€{elem.price}</b></span>
                            <div className='mt-0'>
                                {elem.count >= 1 && <button className='btn btn-light' onClick={() => subtract(elem.id)}>-</button>}
                                <span><b>{elem.count}</b></span>
                                <button className='btn btn-light' onClick={() => add(elem.id)}>+</button>
                                <button className='btn btn-danger btn-sm' onClick={() => remove(elem.id)} style={{ float: 'right' }}>Remove</button>
                            </div>
                        </div>
                    </div>
                })
                }
            </div>
            <div className='row m-3' style={{ backgroundColor: '#bbb', padding: '1%' }}>
                <h2 className='mt-1'>Total Price: €{total}</h2>
                <div style={{ marginLeft: '55%' }}>
                    <button className='btn btn-danger mt-2' onClick={emptyCart} disabled={showBtns}>Emprty Cart</button>
                    <NavLink to='/calendar'><button className='btn btn-success ml-2 mt-2' disabled={showBtns}>Checkout</button></NavLink>
                </div>
            </div>
        </div>
    );
}

export default Cart;