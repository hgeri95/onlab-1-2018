import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./SignUp.css";
import API from '../utils/api';

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            password2: "",
            role: ""
        };
    }

    validateForm() {
        return this.state.username.length > 0 && this.state.password.length > 0  && 
        this.state.role.length > 0 && 
        this.state.password === this.state.password2;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault();

        API.post('/users/sign-up', {"username": this.state.username,
                "password": this.state.password, "role": [this.state.role]})
            .then(res => {
                
            }).catch(error => {
                alert(error.response.data.message);
                console.log(error);
            });
    }

    handleSelectChange(e) {
        console.log('Role selected: ', this.inputEl.value);
        this.state.role = this.inputEl.value;
        console.log('Role length: ', this.state.role.length);
    }

    render() {
        return (
            <div className="SignUp">
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
                    <FormGroup controlId="password2" bsSize="large">
                        <ControlLabel>Password again</ControlLabel>
                        <FormControl 
                            type="password"
                            value={this.state.password2}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="role" bsSize="large">
                        <ControlLabel>Role</ControlLabel>
                        <FormControl componentClass="select" placeholder="Choose role" 
                            onChange = {this.handleSelectChange.bind(this)}
                            inputRef={ el => this.inputEl=el }
                            placeholder="select"
                            onChange={this.handleChange}>
                            <option value="">Select your role...</option>
                            <option value="ROLE_OWNER">Owner</option>
                            <option value="ROLE_USER">User</option>
                        </FormControl>
                    </FormGroup>
                    <Button
                        block
                        bsSize="large"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        SignUp
                    </Button>
                </form>
            </div>
        );
    }
}