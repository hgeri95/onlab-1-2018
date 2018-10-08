import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';
import { registerUserAction } from '../actions/authentication';
import classnames from 'classnames';

class Register extends Component {
    constructor() {
        super();
        
        this.state = {
            name: '',
            password: '',
            password_confirm: '',
            errors: {}
        }
        //TODO add role state and input

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateForm() {
        return this.state.password === this.state.password_confirm;
    }

    handleInputChange = event => {
        this.setState({[event.target.name]: event.target.value})
    }

    handleSubmit = event => {
        event.preventDefault();
        const user = {
            name: this.state.name,
            password: this.state.password
        }
        this.props.registerUserAction(user, this.props.history);
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            });
        }
    }

    render() {
        const { errors } = this.state;
        return(
            <div className="container">
                <h2>Registration</h2>
                <from onSubmit={ this.handleSubmit}>
                    <div className="form-group">
                        <input type="text" placeholder="Username" 
                        className={classnames('form-control form-control-lg',{ 'is-valid': errors.name})}
                        name="name" value={ this.state.name } onChange={ this.handleInputChange } />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Password" 
                        className={classnames('form-control form-control-lg',{ 'is-valid': errors.password})}
                        name="password" onChange={ this.handleInputChange } />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Password confirmation" 
                        className="form-control"
                        name="password_confirm" onChange={ this.handleInputChange } />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="btn btn-primary" 
                        disabled={!this.validateForm()}>
                            Register
                        </button>
                    </div>
                </from>
            </div>
        )
    }
}

Register.PropTypes = {
    registerUserAction: PropTypes.func.isRequired,
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(mapStateToProps, { registerUserAction })(withRouter(Register))