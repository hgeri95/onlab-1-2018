import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./Login.css";
//import { logInUser } from "../actions/AuthActions";
import API from '../utils/api';
import setAuthToken from '../utils/SetAuthToken';

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: ""
        };
    }

    validateForm() {
        return this.state.username.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = async event => {
        event.preventDefault();
        
        try {
            const res = await API.post('/authenticate/login',
                {"username": this.state.username, "password": this.state.password});
            
                const accessToken = res.data.accessToken;
            if (res.status === 200) {
                this.props.userHasAuthenticated(true);
                const role = res.data.user.roles[0];
                this.props.userChangeRole(role);
            } else {
                this.props.userHasAuthenticated(true);
            }
            localStorage.setItem('jwtToken', accessToken);
            //localStorage.setItem('userRole', res.data.user.roles[0]);
            setAuthToken(accessToken);
            
            this.props.userHasAuthenticated(this.props.userHasAuthenticated);
            this.props.history.push("/");
        } catch (e) {
            console.log(e);
            alert("Wrong username or password");
        }
    }

    render() {
        return (
            <div className="Login">
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="username" bsSize="large">
                        <ControlLabel>Username</ControlLabel>
                        <FormControl 
                            autoFocus
                            type="username"
                            value={this.state.username}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="password" bsSize="large">
                        <ControlLabel>Password</ControlLabel>
                        <FormControl 
                            type="password"
                            value={this.state.password}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <Button
                        block
                        bsSize="large"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Login
                    </Button>
                </form>
            </div>
        );
    }
}