import { NavLink } from "react-router-dom";
import { useState, useEffect } from "react/cjs/react.development";
import axios from "axios";
import moment from "moment";

function Orders() {
    let [orders, setOrders] = useState([]);

    const getOrders = () => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.get('https://online-shop-vegetables.herokuapp.com/purchase',
            {
                headers: { "Authorization": `Bearer ${token}` }
            })
            .then((response) => {
                setOrders(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    useEffect(() => {
        getOrders();
    }, []);

    const handleChangeStatus = (id, status) => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.put(`https://online-shop-vegetables.herokuapp.com/purchase/${id}`,
            {
                "status": `${status}`
            },
            {
                headers: { "Authorization": `Bearer ${token}` }
            })
            .then((response) => {
                getOrders();
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return (
        <div className="container mt-3">
            <div className="row">
                <div className="col-10">
                    <h1>Customer Orders</h1>
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
                                <th>Total Quantity</th>
                                <th>Delivery Date</th>
                                <th>Customer</th>
                                <th>Address</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {orders.map((elem, idx) => {
                                let sum = elem.requestedProducts.map(k => k.quantity).reduce((a,b) => a + b, 0);
                                return <tr key={idx}>
                                    <td>{elem.requestedProducts.map(k => k.product.name + " (" + k.quantity + ")" ).join(", ")}</td>
                                    <td>{sum}</td>
                                    <td>{moment(elem.scheduling.dateAndTime).format("DD-MMM-YYYY HH:mm A")}</td>
                                    <td>{elem.endUser.name}</td>
                                    <td>{elem.endUser.address}</td>
                                    <td>€{parseFloat(elem.totalPrice).toFixed(2)}</td>
                                    <td>{elem.status}</td>
                                    <td>
                                        {elem.status === "DELIVERED" && <span className="badge badge-success">DELIVERED</span>}
                                        {elem.status === "PROCESSING" &&
                                            <button className="btn btn-sm btn-info" onClick={e => handleChangeStatus(elem.id, "IN_TRANSIT")}>IN TRANSIT</button>}
                                        {elem.status === "IN_TRANSIT" &&
                                            <button className="btn btn-sm btn-info" onClick={e => handleChangeStatus(elem.id, "DELIVERED")}>DELIVERED</button>}
                                    </td>
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

export default Orders;