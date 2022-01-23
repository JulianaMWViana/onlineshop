import axios from "axios";
import { useEffect, useState } from "react/cjs/react.development";

function Customers() {
    let [customers, setCustomers] = useState([]);

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.get('https://online-shop-vegetables.herokuapp.com/enduser',
            {
                headers: { "Authorization": `Bearer ${token}` }
            })
            .then((response) => {
                setCustomers(response.data);
            }, (error) => {
                console.log(error);
            });
    }, [])

    return (
        <div className="container">
            <h1>Customers</h1>
            <div className="row">
                <div className="col">
                    <table className="table table-hover table-striped table-bordered table-sm">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                        <tbody>
                            {customers.map((elem, idx) => {
                                return <tr key={idx}>
                                    <td>{elem.name}</td>
                                    <td>{elem.email}</td>
                                    <td>{elem.phone}</td>
                                    <td>{elem.address}</td>
                                </tr>
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default Customers;