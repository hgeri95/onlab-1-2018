import React, { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import { LinkContainer } from "react-router-bootstrap";
import { Navbar, Nav, NavItem } from "react-bootstrap";
import './App.css';
import Routes from "./Routes";
import { withRouter } from "react-router-dom";
import setAuthToken from './utils/SetAuthToken';
import API from './utils/api';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isAuthenticated: false,
      isAuthenticating: true,
      role: ""
    };
  }


  userHasAuthenticated = authenticated => {
    this.setState({ isAuthenticated: authenticated });
  }

  userChangeRole = role => {
    this.setState({ role: role});
  }

  handleLogout = event => {
    API.post('/authenticate/logout');
    this.userHasAuthenticated(false);
    setAuthToken(false);
    this.props.history.push("/login");
  }

  render() {
    const childProps = {
      isAuthenticated: this.state.isAuthenticated,
      userHasAuthenticated: this.userHasAuthenticated,
      userChangeRole: this.userChangeRole
    };

    return (
      <div className="App container">
        <Navbar fluid collapseOnSelect>
          <Navbar.Header>
            <Navbar.Brand>
              <Link to="/">Catering Unit Monitor</Link>
            </Navbar.Brand>
            <Navbar.Toggle/>
          </Navbar.Header>
          <Nav>
            {this.state.role === "ROLE_OWNER" ? 
              <NavItem eventKey={1}>List all</NavItem> :
              <NavItem eventKey={1}>List all2</NavItem>
            }
          </Nav>
          <Navbar.Collapse>
            <Nav pullRight>
            {this.state.isAuthenticated
              ? <NavItem onClick={this.handleLogout}>Logout</NavItem>
              : <Fragment>
                  <LinkContainer to="sign-up">
                    <NavItem>Signup</NavItem>
                  </LinkContainer>
                  <LinkContainer to="/login">
                    <NavItem>Login</NavItem>
                  </LinkContainer>
                </Fragment>
            }
            </Nav>
          </Navbar.Collapse>
        </Navbar>
        <Routes childProps={childProps} />
      </div>
    );
  }
}

export default withRouter(App);
