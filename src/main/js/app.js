'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.goToNextWeek = this.goToNextWeek.bind(this)
		this.goToPreviousWeek = this.goToPreviousWeek.bind(this)
		this.createReservation = this.createReservation.bind(this)
		this.state = {
		roomId: 1,
		week: 0,
		reservationWeek: {
		    days: []
		}
		};
	}

	goToNextWeek() {
	    this.state.week += 1
        this.querySchedule()
	}

	goToPreviousWeek() {
    	this.state.week -= 1
        this.querySchedule()
    }

	querySchedule() {
	    $.get("/reservation", {roomId: this.state.roomId, week: this.state.week})
     	.done(function(data) {
             this.setState({reservationWeek: data.value});
           }.bind(this))
           .fail(function() {
             console.log( "error" );
           });
	}

	createReservation(dateFrom, dateTo) {
	    var data = {
	        dateFrom: dateFrom,
	        dateTo: dateTo,
	        roomId: this.state.roomId,
	        users: []
	    }

        $.ajax({
                  type: "POST",
                  url: "/reservation",
                  data: JSON.stringify(data),
                  contentType: "application/json; charset=utf-8",
                  dataType: "json",
                  success: function(data){
                      this.querySchedule()
                 }.bind(this),
                  error: function(err) {
                      alert(err.responseText);
                  }
              });
        }

	componentDidMount() {
	    this.querySchedule()
	}

	render() {
		return (
		    <div className="container schedule">
		        <Header handlePrevious={this.goToPreviousWeek} handleNext={this.goToNextWeek}/>
			    <DayList reservationWeek={this.state.reservationWeek}/>
			    <ReservationForm createReservation = {this.createReservation} />
			</div>
		)
	}
}

class Header extends React.Component{
    render() { return (
        <div className="row" style={{display: "flex"}}>
            <div className="col-md-8">
        	    <h2 className="col-md-12">MeetRoom</h2>
        	</div>
        	<div className="col-md-4" style={{paddingTop: '20px'}}>
        		<button onClick={this.props.handlePrevious} >Previous</button>
        		<button onClick={this.props.handleNext} >Next</button>
        	</div>
        </div>
        )
    }
}

class DayList extends React.Component{
	render() {
		const daysHeader = this.props.reservationWeek.days.map(day =>
			<HeaderDay key={'header' + day.dayOfWeek} day={day}/>
		);
		const days = this.props.reservationWeek.days.map(day =>
        			<Day key={day.dayOfWeek} day={day}/>
        		);
		return (
			<div className="row flex-nowrap">
			    <div className="col-md-12">
			        <div className="col-md-1"></div>
			        <div className="col-md-11">
					    {daysHeader}
					</div>
				</div>
				<div className="col-md-12">
                	<div className="col-md-1">
                	    <TimeLine />
                	</div>
                	<div className="col-md-11">
                	    {days}
                	</div>
                </div>
			</div>
		)
	}
}

class HeaderDay extends React.Component{
	render() {
	    var dateString = new Date(this.props.day.date).toLocaleDateString()
		return (
			<div className="col-md-2 text-center">
				<div className="col-md-12">{this.props.day.dayOfWeek}</div>
				<div className="col-md-12">{dateString}</div>
			</div>
		)
	}
}

class Day extends React.Component{
	render() {
	    const reservations = this.props.day.reservations.map(reservation =>
    			<Reservation key={reservation.id} reservation={reservation}/>
    		);
		return (
			<div className="col-md-2 day" style={{height:600}}>
            	{reservations}
            </div>
		)
	}
}

class Reservation extends React.Component{
	render() {
	    var dateFrom = new Date(this.props.reservation.dateFrom);
	    var dateTo = new Date(this.props.reservation.dateTo );
	    var msInDay = 24*60*60*1000;
	    var truncDate = new Date(dateFrom.getFullYear(), dateFrom.getMonth(), dateFrom.getDate(), 0, 0, 0);
	    var msBegin = dateFrom.getTime() - truncDate.getTime();
	    var meetingDuration = dateTo.getTime() - dateFrom.getTime();
	    var reservHeight = 100.0 * meetingDuration / msInDay;
	    var offset = 100.0 * msBegin / msInDay;

	    const users = this.props.reservation.users.map(user =>
            			<User key={user.id} user={user}/>
            		);

		return (
		    <div className="reservation" style={{height: reservHeight + '%', top: offset + '%', color: "#000"}}>
				<span className="text-center">{this.props.reservation.room.name}</span>
				<br/>
				{users}
			</div>
		)
	}
}

class User extends React.Component {
    render() {
        return (
            <span> {this.props.user.firstName} {this.props.user.lastName} </span>
        )
    }
}

class ReservationForm extends React.Component {

    constructor(props) {
    	super(props);
        this.handleInputChange = this.handleInputChange.bind(this)
        this.createReservation = this.createReservation.bind(this)
        this.state = {
        }
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
          [name]: value
        });
      }

    createReservation() {
        var date = new Date(this.state.date);
        var time = this.parseTime(this.state.timeStart);
        var dateFrom = new Date(date.getFullYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(), 0)
        var dateTo = new Date(dateFrom.getTime() + this.state.duration * 60 * 1000)
        this.props.createReservation(dateFrom, dateTo)
      }

    parseTime( t ) {
         var d = new Date();
         var time = t.match( /(\d+)(?::(\d\d))?\s*(p?)/ );
         d.setHours( parseInt( time[1]) + (time[3] ? 12 : 0) );
         d.setMinutes( parseInt( time[2]) || 0 );
         return d;
      }

    render() {
        return (
        <div className="col-md-6">
        <form>
          <div className="form-group">
            <label for="date">Meeting date</label>
            <input name="date" value={this.state.date} onChange={this.handleInputChange} type="date" className="form-control" id="date" placeholder="Meeting date" />
          </div>
          <div className="form-group">
            <label for="timeStart">Meeting starts at</label>
            <input name="timeStart" value={this.state.timeStart} onChange={this.handleInputChange} type="time" className="form-control" id="timeStart" placeholder="Password"/>
          </div>
          <div className="form-group form-check">
            <label for="duration">Duration, minutes</label>
            <input name="duration" value={this.state.duration} onChange={this.handleInputChange} type="number" className="form-control" id="duration"/>
          </div>
          <button type="button" onClick={this.createReservation} className="btn btn-primary">Create reservation</button>
        </form>
        </div>
        )
    }
}

class TimeLine extends React.Component {
    render() {
        var indents = [];
        for (var i = 0; i < 24; i++) {
          indents.push(
            <div className='time-mark' key={i} style={{height: 100/24 + '%',
                                                          top: 100 * i / 24 + '%'}}>
                {i}:00
            </div>
          );
        }
        return indents;
    }
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
