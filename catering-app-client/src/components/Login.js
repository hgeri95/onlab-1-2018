import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { loginAction } from '../actions/authentication';
import classnames from 'classnames';

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            errors: {}
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateForm() {
        return this.state.username.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({ [event.target.name]: event.target.value})
    }

    handleSubmit = event => {
        event.preventDefault();
        const {username, password} = this.state;
        this.props.loginAction({username, password});
        this.setState({username: "", password: "", errors: {}});
    }

    componentDidMount() {
        if(this.props.authentication.authenticated) {
            this.props.history.push('/');
        }
    }

    componentWillReveiceProps(nextProps) {
        if(nextProps.authentication.authenticated) {
            this.props.history.push('/');
        }
        if(nextProps.erros) {
            this.setState({
                errors: nextProps.errors
            });
        }
    }

    render() {
        const { errors } = this.state;
        return (
            <div className="container">
                <h2>Login</h2>
                <form onSubmit={ this.handleSubmit }>
                    <div className="form-group">
                        <input type="text" placeholder="Username"
                         className={classnames('form-control form-control-lg', {'is-valid': errors.username})} 
                        name="username" onChange={ this.handleChange } value={ this.state.username } />
                    </div>
                    <div className="form-group">
                        <input type="password" placeholder="Password"
                        className={classnames('form-control form-control-lg', {'is-valid': errors.password})}
                        name="password" onChange={ this.handleChange } value={ this.state.password } />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="btn btn-primary" 
                        disabled={!this.validateForm()}>
                            Login
                        </button>
                    </div>
                </form>
            </div>
        );
    }
}

Login.propTypes = {
    errors: PropTypes.object.isRequired,
    authentication: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    errors: state.errors,
    authentication: state.authentication
})

export default connect(mapStateToProps, { loginAction })(Login)