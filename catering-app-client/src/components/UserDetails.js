import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import {getUserInfoAction} from "../actions/user";

class UserDetails extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userInfo: {}
        };
    }

    componentWillMount() {
        this.props.getUserInfoAction();
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.user.userInfo) {
            this.setState({
                userInfo: nextProps.user.userInfo
            });
        }
        if(nextProps.errors) {
            alert(JSON.stringify(nextProps.errors, null, 2));
        }
    }

    render() {
        const {userInfo} = this.state;
        return (
            <div><pre>{JSON.stringify({userInfo}, null, 2) }</pre></div>
        );
    }
}

UserDetails.propTypes= {
    errors: PropTypes.object.isRequired,
    user: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    errors: state.errors,
    user: state.user
});

export default connect(mapStateToProps, { getUserInfoAction })(UserDetails)