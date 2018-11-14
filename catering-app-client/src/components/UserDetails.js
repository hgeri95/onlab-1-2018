import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import {getUserInfoAction, postUserInfoAction } from "../actions/user";
import classnames from "classnames";
import Dropdown from 'react-dropdown';
import Moment from 'moment';

class UserDetails extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userInfo: {
                address: "",
                birthDate: {},
                email: "",
                firstName: "",
                gender: "",
                lastName: ""
            },
            username: "",
            errors: {}
        };
    }

    handleInputChange = event => {
        let localUserInfo = Object.assign({}, this.state.userInfo);
        localUserInfo[event.target.name] = event.target.value;
        this.setState({userInfo: localUserInfo});
    };

    handleSubmit = event => {
        event.preventDefault();
        const modifiedUserInfo = this.state.userInfo;
        this.props.postUserInfoAction(modifiedUserInfo, this.props.history);
    };

    handleSelect = event => {
        let localUserInfo = Object.assign({}, this.state.userInfo);
        localUserInfo.gender = event.value;
        this.setState({userInfo: localUserInfo});
    };

    componentWillMount() {
        this.props.getUserInfoAction();
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.user.userInfo) {
            let copyUserInfo = nextProps.user.userInfo;
            this.setState({username: copyUserInfo.username});
            delete copyUserInfo.username;
            this.setState({
                userInfo: copyUserInfo
            });
        }
        if(nextProps.errors) {
            //alert(JSON.stringify(nextProps.errors, null, 2));
        }
    }

    render() {
        Moment.locale('en');//TODO browser locale
        const { errors } = this.state;
        const { userInfo } = this.state;
        const genders = ['MALE','FEMALE'];
        const formattedDate = Moment(userInfo.birthDate).format('YYYY-MM-DD');
        return (
            //<div><pre>{JSON.stringify({userInfo}, null, 2) }</pre></div>
            <div className="container">
                <h2>Userinfo for: <b>{this.state.username}</b></h2>
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <text>Address</text>
                        <input type="text"
                               className={classnames('form-control form-control-lg')}
                               name="address" value={userInfo.address} onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <text>Birthday</text>
                        <input type="date"
                               className={classnames('form-control form-control-lg')}
                               name="birthDate" value={formattedDate} onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <text>Email</text>
                        <input type="email"
                               className={classnames('form-control form-control-lg')}
                               name="email" value={userInfo.email} onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <text>First name</text>
                        <input type="text"
                               className={classnames('form-control form-control-lg')}
                               name="firstName" value={userInfo.firstName} onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <text>Last name</text>
                        <input type="text"
                               className={classnames('form-control form-control-lg')}
                               name="lastName" value={userInfo.lastName} onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <text>Gender</text>
                        <Dropdown options={genders} onChange={this.handleSelect} value={userInfo.gender}/>
                    </div>
                    <div className="form-group">
                        <button type="submit" className="btn btn-primary">
                            Save
                        </button>
                    </div>
                </form>
            </div>
        );
    }
}

UserDetails.propTypes= {
    errors: PropTypes.object.isRequired,
    user: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    errors: state.errors,
    user: state.user
});

export default connect(mapStateToProps, { getUserInfoAction, postUserInfoAction })(UserDetails)