import React, {Component} from 'react';
import {connect} from 'react-redux';
import {loginAction} from '../action_creators/authentication';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {renderError} from "./ErrorAlert";

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            errorMessage: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateForm() {
        return this.state.username.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleSubmit = event => {
        event.preventDefault();
        const {username, password} = this.state;
        this.props.loginAction({username, password});
        this.setState({username: "", password: "", errorMessage: ""});
    };

    componentDidMount() {
        if (this.props.authentication.authenticated) {
            this.props.history.push('/');
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.authentication.authenticated) {
            this.props.history.push('/');
        }
        if (nextProps.errors) {
            this.setState({errorMessage: nextProps.errors.errors.message});
        }
    }

    render() {
        return (
            <div>
                <Container>
                    <h2>Login</h2>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="username">Username</Label>
                            <Input type="text" name="username" id="username" placeholder="Username"
                                   value={this.state.username} onChange={this.handleChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="password">Password</Label>
                            <Input type="password" name="password" id="password" placeholder="Password"
                                   value={this.state.password}
                                   onChange={this.handleChange}/>
                        </FormGroup>
                        {renderError(this.state.errorMessage)}
                        <FormGroup>
                            <Button disabled={!this.validateForm()} type="submit">Login</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    errors: state.errors,
    authentication: state.authentication
});

export default connect(mapStateToProps, {loginAction})(Login)
