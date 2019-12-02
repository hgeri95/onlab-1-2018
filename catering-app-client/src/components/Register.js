import React, {Component} from 'react';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom';
import {registerUserAction} from '../action_creators/authentication';
import Dropdown from 'react-dropdown'
import 'react-dropdown/style.css'
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {renderError} from "./ErrorAlert";

class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            password: '',
            password_confirm: '',
            role: '',
            errorMessage: ''
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    validateForm() {
        return this.state.password === this.state.password_confirm && this.state.password.length > 4 && this.state.role.length > 0;
    }

    handleInputChange = event => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleSelect = event => {
        this.setState({role: event.value})
    };

    handleSubmit = event => {
        event.preventDefault();
        const user = {
            name: this.state.name,
            password: this.state.password,
            role: this.state.role
        };
        this.props.registerUserAction(user, this.props.history);
        this.setState({name: '', password: '', password_confirm: '', role: '', errorMessage: ''});
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.authentication.authenticated) {
            this.props.history.push('/')
        }
        if (nextProps.errors) {
            console.log(nextProps.errors);
            this.setState({errorMessage: nextProps.errors.errors.message});
        }
    }

    render() {
        const roles = [{value: 'ROLE_OWNER', label: 'OWNER'}, {value: 'ROLE_USER', label: 'USER'}];
        const {role} = this.state;
        return (
            <Container>
                <h2>Registration</h2>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Username</Label>
                        <Input type="text" placeholder="Username" id="name" name="name" value={this.state.name}
                               onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="password">Password</Label>
                        <Input type="password" placeholder="Password" id="password" name="password"
                               value={this.state.password}
                               onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="password_confirm">Password again</Label>
                        <Input type="password" placeholder="Password confirmation" id="password_confirm"
                               name="password_confirm" value={this.state.password_confirm}
                               onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="role">Role</Label>
                        <Dropdown options={roles} onChange={this.handleSelect} value={role}
                                  placeholder="Role"></Dropdown>
                    </FormGroup>
                    {renderError(this.state.errorMessage)}
                    <Button disabled={!this.validateForm()} type="submit">Register</Button>
                </Form>
            </Container>
        );
    }
}

const mapStateToProps = state => ({
    errors: state.errors,
    authentication: state.authentication
});

export default connect(mapStateToProps, {registerUserAction})(withRouter(Register))