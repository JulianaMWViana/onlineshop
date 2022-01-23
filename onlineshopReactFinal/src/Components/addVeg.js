import { NavLink } from "react-router-dom";
import { useState } from "react/cjs/react.development";
import axios from "axios";

function AddVeg() {
    let [name, setName] = useState('');
    let [description, setDescription] = useState('');
    let [price, setPrice] = useState('');
    let [quantity, setQuantity] = useState('');
    let [errorMessage, setErrorMessage] = useState(null);

    const submit = e => {
        e.preventDefault();
        const token = JSON.parse(localStorage.getItem('user')).token;

        let image = document.getElementById('img-file').files[0];
        if(image == null){
            setErrorMessage([{'field': 'image', 'error': 'Select one image'}]);
        } else {
        axios.post('https://online-shop-vegetables.herokuapp.com/product',
            {
                name: name,
                description: description,
                price: price,
                quantity: quantity
            },
            {
                headers: { "Authorization": `Bearer ${token}` }
            })
            .then(response => {
                var formData = new FormData();
                formData.append('image', image);

                axios.post(`https://online-shop-vegetables.herokuapp.com/image/${response.data.id}`, formData,
                    {
                        headers: { "Authorization": `Bearer ${token}` }
                    }
                )
                    .then(response => {
                        window.location.href = "/";
                    })
                    .catch((error) => {
                        console.log(error)
                    });
            }).catch((error) => {
                setErrorMessage(error.response.data);
            });
        }
    }

    return (
        <div className="container mt-3">
            <div className="row">
                <h2>Add New Vegetables</h2>
                <NavLink to='/' className='btn btn-light' style={{ marginLeft: '60%' }}>
                    <i className="fa fa-arrow-circle-left mr-2" aria-hidden="true"></i><b>Go back</b>
                </NavLink>
            </div>
            <p>Please fill all the credentials to upload an ad of your vegetable</p>
            <form onSubmit={submit}>
                <div className="form-row">
                    <div className="form-group col-7">
                        <label>Name</label>
                        <input type="text" className="form-control" placeholder="Name" value={name} onChange={e => setName(e.target.value)} />
                    </div>
                    <div className="form-group col-5">
                        <label>Choose image</label><br />
                        <input type="file" id="img-file" accept="images/*" />
                    </div>
                    <div className="form-group col-12">
                        <label>Description</label>
                        <textarea type="text" className="form-control" placeholder="Description" value={description} onChange={e => setDescription(e.target.value)}></textarea>
                    </div>
                    <div className="form-group col-6">
                        <label>Price</label>
                        <input type="number" className="form-control" placeholder="Price" value={price} onChange={e => setPrice(e.target.value)} />
                    </div>
                    <div className="form-group col-6">
                        <label>Quantity</label>
                        <input type="number" className="form-control" placeholder="Quantity" value={quantity} onChange={e => setQuantity(e.target.value)} />
                    </div>
                </div>
                {
                    errorMessage &&
                    <div>{errorMessage.map(err => <p className="alert alert-danger">{err.field}: {err.error}</p>)}</div>
                }
            </form>
            <NavLink to='/' className="btn btn-success btn-sm btn-block" onClick={submit}>Register</NavLink>
        </div>
    )
}

export default AddVeg;