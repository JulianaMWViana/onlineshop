import {useEffect, useState } from 'react';
import axios from 'axios';

function VegDetails() {
    let [veg, setVeg] = useState({});

    useEffect(() => {
        let id = window.location.href.split('id=')[1];
        axios.get(`https://online-shop-vegetables.herokuapp.com/product/${id}`)
            .then((response) => {
                setVeg(response.data);
            }, (error) => {
                console.log(error);
            });
    }, [])

    return (
        <div className="container mt-2">
            <h1>Vegetable Details</h1>
            <p>Following is the details of the vegetable.</p>
            <div className="row">
                <div className="col-6">
                    <img src={`${veg.url}`} height='400px' width='500px' style={{border: '1px solid grey', objectFit: 'cover'}} />
                </div>
                <div className="col-6" style={{border: '1px dotted grey', height: '200px', padding: '16px'}}>
                    <p><u>Name:</u> <b>{veg.name}</b></p>
                    <p><u>Price:</u> <b>${veg.price}</b></p>
                    <p><u>Description:</u> <b>{veg.description}</b></p>
                </div>
            </div>
        </div>
    );
}

export default VegDetails;