import React, {Component} from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import {withRouter} from 'react-router-dom';
import {registerUserAction} from '../actions/authentication';
import classnames from 'classnames';
import Dropdown from 'react-dropdown'
import 'react-dropdown/style.css'

class Register extends Component {
    constructor() {
        super();

        this.state = {
            name: '',
            password: '',
            password_confirm: '',
            role: '',
            errors: {}
        }

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    validateForm() {
        return this.state.password === this.state.password_confirm;
    }

    handleInputChange = event => {
        this.setState({[event.target.name]: event.target.value})
    }

    handleSelect = event => {
        this.setState({role: event.value})
    }

    handleSubmit = event => {
        event.preventDefault();
        const user = {
            name: this.state.name,
            password: this.state.password,
            role: this.state.role
        }
        this.props.registerUserAction(user, this.props.history);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.authentication.authenticated) {
            this.props.history.push('/')
        }
        if (nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            });
        }
    }

    componentDidMount() {
        if (this.props.authentication.authenticated) {
            this.props.history.push('/');
        }
    }

    render() {
        const {errors} = this.state;
        const roles = [{value: 'ROLE_ADMIN', label: 'ADMIN'}, {
            value: 'ROLE_OWNER',
            label: 'OWNER'
        }, {value: 'ROLE_USER', label: 'USER'}];
        const {role} = this.state;
        return (
            <div className="container">
                <h2>Registration</h2>
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <input type="text" placeholder="Username"
                               className={classnames('form-control form-control-lg', {'is-valid': errors.name})}
                               name="name" value={this.state.name} onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <input type="password" placeholder="Password"
                               className={classnames('form-control form-control-lg', {'is-valid': errors.password})}
                               name="password" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <input type="password" placeholder="Password confirmation"
                               className={classnames('form-control form-control-lg')}
                               name="password_confirm" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <Dropdown options={roles} onChange={this.handleSelect} value={role} placeholder="Roles"/>
                    </div>
                    <div className="form-group">
                        <button type="submit" className="btn btn-primary"
                                disabled={!this.validateForm()}>
                            Register
                        </button>
                    </div>
                </form>
            </div>
        );
    }
}

Register.propTypes = {
    registerUserAction: PropTypes.func.isRequired,
    authentication: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    authentication: state.authentication
});

export default connect(mapStateToProps, {registerUserAction})(withRouter(Register))