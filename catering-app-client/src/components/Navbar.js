import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { logoutAction } from '../actions/authentication';
import { withRouter } from 'react-router-dom'; 

class Navbar extends Component {

    onLogout = event => {
        event.preventDefault();
        this.props.logoutAction(this.props.history);
    }

    render() {
        const { authenticated } = this.props.authentication;
        const authLinks = (
            <ul className="navbar-nav ml-auto">
                <a className="nav-link" onClick={this.onLogout.bind(this)}>
                    Logout
                </a>
            </ul>
        )

        const guestLinks = (
            <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                    <Link className="nav-link" to="/register">Register</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/login">Login</Link>
                </li>
            </ul>
        )
        return(
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <Link className="navbar-brand" to="/">Catering Unit Monitor</Link>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
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

export default connect(mapStateToProps, { logoutAction })(withRouter(Navbar));