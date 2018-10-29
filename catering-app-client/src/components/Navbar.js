import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {logoutAction} from '../actions/authentication';
import {withRouter} from 'react-router-dom';

class Navbar extends Component {

    onLogout = event => {
        event.preventDefault();
        this.props.logoutAction(this.props.history);
    };

    render() {
        const {authenticated, roles} = this.props.authentication;
        const authLinks = (
            <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                    <a className="nav-link" href="/user-details">User details</a>
                </li>
                <li className="nav-item" onClick={this.onLogout.bind(this)}>
                    <a className="nav-link">Logout</a>
                </li>
            </ul>
        );

        const guestLinks = (
            <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                    <Link className="nav-link" to="/register">Register</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/login">Login</Link>
                </li>
            </ul>
        );

        const menuLinks = (
            <ul className="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href={"/list-all-catering"}>List all</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href={"/create-catering"}>Create</a>
                </li>
            </ul>
        );


        return (
            <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <div className="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
                    {menuLinks}
                </div>
                <div className="mx-auto order-0">
                    <a className="navbar-brand mx-auto" href="/">Catering Unit Monitor</a>
                    <button className="navbar-toggler" type="button" data-toggle="collapse"
                            data-target=".dual-collapse2">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div className="navbar-collapse collapse w-100 order-3 dual-collapse2">
                    {authenticated ? authLinks : guestLinks}
                </div>
            </nav>
        )
    }
}

Navbar.propTypes = {
    logoutAction: PropTypes.func.isRequired,
    authentication: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    authentication: state.authentication
})

export default connect(mapStateToProps, {logoutAction})(withRouter(Navbar));