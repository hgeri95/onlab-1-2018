import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

class CreateCatering extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="container">
                <h2>Register Catering Unit</h2>
            </div>
        );
    }
}

export default (CreateCatering)