import { useEffect } from "react";
import { NavLink } from "react-router-dom";
import { useState } from 'react/cjs/react.development';
import axios from "axios";
import moment from 'moment';

function CustomerOrders() {
    let [orders, setOrders] = useState([]);

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.get('https://online-shop-vegetables.herokuapp.com/purchase/orders',
            {
                headers: { "Authorization": `Bearer ${token}` }
            })
            .then((response) => {
                setOrders(response.data);
            }, (error) => {
                console.log(error);
            });
    }, [])

    return (
        <div className="container mt-3">
            <div className="row">
                <div className="col-10">
                    <h1>Your Current Orders</h1>
                </div>
                <div className="col-2">
                    <NavLink to='/' className='btn btn-light'>
                        <i className="fa fa-arrow-circle-left mr-2" aria-hidden="true"></i><b>Go back</b>
                    </NavLink>
                </div>
            </div>

            <div className="row mt-1">
                <div className="col">
                    <table className="table table-hover table-bordered table-striped table-sm">
                        <thead>
                            <tr>
                                <th>Vegetable</th>
                                <th>Delivery Date</th>
                                <th>Price</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {orders.map((elem, idx) => {
                                return <tr key={idx}>
                                    <td>{elem.requestedProducts.map(k => k.product.name).join(", ")}</td>
                                    <td>{moment(elem.scheduling.dateAndTime).format("DD-MMM-YYYY HH:mm A")}</td>
                                    <td>€{parseFloat(elem.totalPrice).toFixed(2)}</td>
                                    <td>{elem.status}</td>
                                </tr>
                            })
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default CustomerOrders;