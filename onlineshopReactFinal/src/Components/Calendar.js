import axios from "axios";
import React, { useState, Children } from 'react';
import { Navigate } from "react-router-dom";
import moment from "moment";
import { Calendar as BigCalendar, momentLocalizer } from "react-big-calendar";
import 'react-big-calendar/lib/css/react-big-calendar.css';
import Swal from "sweetalert2";

function Calendar(props) {

    const localizer = momentLocalizer(moment);
    let [redirectTo, setRedirectTo] = useState(null);
    let [selectedDateTime, setSelectedDateTime] = useState(null);

    const isEarlierThanEndLimit = (timeValue, endLimit, lastValue) => {
        let timeValueIsEarlier = moment(timeValue, 'hh:mm A').diff(moment(endLimit, 'hh:mm A')) < 0;
        let timeValueIsLaterThanLastValue = lastValue === undefined ? true : moment(lastValue, 'hh:mm A').diff(moment(timeValue, 'hh:mm A')) < 0;
        return timeValueIsEarlier && timeValueIsLaterThanLastValue;
    }

    const getTimeSlots = () => {
        let timeValue = "09:00 AM";
        let lastValue;
        let endLimit = "04:59 PM";
        let step = 30;
        let t = [];
        t.push(timeValue);
        while (isEarlierThanEndLimit(timeValue, endLimit, lastValue)) {
            lastValue = timeValue;
            timeValue = moment(timeValue, 'hh:mm A').add(step, 'minutes').format('hh:mm A');
            t.push(timeValue);
        }

        let f = {};
        t.forEach(k => f[k] = k);
        return f;
    }

    const handleSelect = async ({ start, end }) => {
        let day = moment(start).day();
        if (day === 0) {
            Swal.fire(`Sundays are off!`);
            return;
        }

        const { value: selectedTime } = await Swal.fire({
            title: 'Select a time',
            input: 'select',
            inputOptions: getTimeSlots(),
            inputPlaceholder: 'Select a time',
            showCancelButton: true,
            inputValidator: value => new Promise(resolve => {
                if (value !== '') {
                    resolve();
                } else {
                    resolve('Please choose a time slot.');
                }
            })
        });

        if (!selectedTime) return;

        let d = moment(start).format("YYYY-MM-DD");
        let t = selectedTime.indexOf("PM") !== -1
            ? `${12 + parseInt(selectedTime.split(":")[0])}:${parseInt(selectedTime.split(":")[1])}`
            : `${parseInt(selectedTime.split(":")[0])}:${parseInt(selectedTime.split(":")[1])}`;
        const timeAndDate = moment(d + ' ' + t);


        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.get('https://online-shop-vegetables.herokuapp.com/scheduling', {
            headers: { "Authorization": `Bearer ${token}` }
        })
            .then((response) => {
                let newBookingDate = timeAndDate.format("YYYY-MM-DDTHH:mm:ss");
                let bookings = response.data.filter(k => k.dateAndTime === newBookingDate);
                if (bookings.length >= 2) {
                    Swal.fire(`Already overbooked! Please choose another timeslot.`);
                    return;
                }
                else {
                    setSelectedDateTime(timeAndDate);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    const submit = e => {
        e.preventDefault();

        if (!selectedDateTime) return;

        let purchases = {};
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        cart.forEach(k => purchases[k.id] = k.count);

        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.post('https://online-shop-vegetables.herokuapp.com/purchase',
            {
                scheduling: selectedDateTime,
                productIdAndQuantity: purchases
            },
            {
                headers: { "Authorization": `Bearer ${token}` }
            })
            .then(response1 => {
                localStorage.removeItem('cart');
                document.getElementById('lblCartCount').innerText = "0";
                setRedirectTo("/thankyou");
            })
            .catch(err => {
                console.log(err);
            });
    }

    const ColoredDateCellWrapper = ({ children, value }) => {
        let day = moment(value).day();
        return React.cloneElement(Children.only(children), {
            style: {
                ...children.style,
                backgroundColor: day === 0 ? '#A25751' : children.backgroundColor,
            },
        });
    }

    return (
        <div className="container">
            {redirectTo && <Navigate to={redirectTo} />}
            <div style={{ marginTop: 50, marginBottom: 50, height: '90vh' }}>
                <BigCalendar
                    popup
                    selectable
                    localizer={localizer}
                    events={[]}
                    views={['month']}
                    onSelectSlot={handleSelect}
                    showMultiDayTimes={false}
                    components={{
                        dateCellWrapper: ColoredDateCellWrapper,
                    }}
                />
            </div>
            <div className="form-row">
                <div className="form-group col-12">
                    <button type="submit" className="btn btn-success btn-sm btn-block" onClick={submit}>Next</button>
                </div>
            </div>
        </div>
    )
}

export default Calendar;