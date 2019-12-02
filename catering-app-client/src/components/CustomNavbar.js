import React, {Component} from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem} from "reactstrap";
import {Link, withRouter} from 'react-router-dom';
import {logoutAction} from "../action_creators/authentication";
import {connect} from "react-redux";

class CustomNavbar extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isOpen: false
        };

        this.toggle = this.toggle.bind(this);
    }

    onLogout = event => {
        event.preventDefault();
        this.props.logoutAction(this.props.history);
    };

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        const {authenticated} = this.props.authentication;
        const isOwner = this.props.authentication.authenticated && this.props.authentication.role === "ROLE_OWNER";

        const menuLinks = (
            <Nav className="ml-auto" navbar>
                <NavItem>
                    <Link className="nav-link" to="/list-all-catering">List all</Link>
                </NavItem>
                <NavItem>
                    <Link className="nav-link" to="/create-catering">Create</Link>
                </NavItem>
            </Nav>
        );

        const authLinks = (
            <Nav className="ml-auto" navbar>
                {isOwner && menuLinks}
                <NavItem>
                    <Link className="nav-link" to="/user-details">User details</Link>
                </NavItem>
                <NavItem>
                    <Link className="nav-link" to="/" onClick={this.onLogout.bind(this)}>Logout</Link>
                </NavItem>
            </Nav>
        );

        const guestLinks = (
            <Nav className="ml-auto" navbar>
                <NavItem>
                    <Link className="nav-link" to="/register">Register</Link>
                </NavItem>
                <NavItem>
                    <Link className="nav-link" to="/login">Login</Link>
                </NavItem>
            </Nav>
        );

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">CateringUnitMonitor</NavbarBrand>
                <NavbarToggler onClick={this.toggle}/>
                <Collapse isOpen={this.state.isOpen} navbar>
                    {authenticated ? authLinks : guestLinks}
                </Collapse>
            </Navbar>
        )
    }
}

const mapStateToProps = (state) => ({
    authentication: state.authentication
});

export default connect(mapStateToProps, {logoutAction})(withRouter(CustomNavbar));