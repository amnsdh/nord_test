'use strict';

// tag::vars[]
const React = require('react'); // <1>
const ReactDOM = require('react-dom'); // <2>
// end::vars[]

// tag::app[]
class App extends React.Component { // <1>

	constructor(props) {
		super(props);
		this.state = {
		roomId: 1,
		week: 0,
		reservationWeek: {
		    days: []
		}
		};
	}

	componentDidMount() { // <2>
	$.get("/reservation", {roomId: state.roomId, week: state.week})
	.done(function() {
        this.setState({reservationWeek: data.value});
        alert( data )
      })
      .fail(function() {
        alert( "error" );
      })
      .always(function() {
        alert( "finished" );
      });
	}

	render() { // <3>
		return (
			<DayList reservationWeek={this.state.reservationWeek}/>
		)
	}
}
// end::app[]

// tag::employee-list[]
class DayList extends React.Component{
	render() {
		const days = this.props.reservationWeek.days.map(day =>
			<Day day={day}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
					</tr>
					{days}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::employee[]
class Day extends React.Component{
	render() {
	    const reservations = this.props.day.reservations(reservation =>
    			<Reservation reservation={reservation}/>
    		);
		return (
			<tr>
				<td>{this.props.day.dayOfWeek}</td>
				<td>{this.props.day.date}</td>
			</tr>
		)
	}
}

class Reservation extends React.Component{
	render() {
	    const reservations = this.props.day.reservations(reservation =>
    			<Reservation reservation={reservation}/>
    		);
		return (
		    <div>
				<span>{this.props.reservation.dateFrom}</span>
				<span>{this.props.reservation.dateTo}</span>
			</div>
		)
	}
}

// end::employee[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]