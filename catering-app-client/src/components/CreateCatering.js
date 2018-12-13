import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

class CreateCatering extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cateringUnit: {
              name: "Dream restaurant",
              description: "The best restaurant",
              openingHours: [],
              address: {
                  country: "Budapest",//Country
                  street: "Nagy",
                  number: "6",
                  otherInformation: ""
              },
              categoryParameters: []
            },
            errors: {}
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleAddressChange = this.handleAddressChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit[name] = value;
        this.setState({cateringUnit});
    }

    handleAddressChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit.address[name] = value;
        this.setState({cateringUnit});
    }

    handleSubmit(event) {

    }

    render() {
        const {cateringUnit} = this.state;

        return(
            <div>
                <Container>
                    <h2>Create Catering Unit</h2>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="name">Name</Label>
                            <Input type="text" name="name" id="name" value = {cateringUnit.name || ''}
                                onChange={this.handleChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="description">Description</Label>
                            <Input type="text" name="description" id="description" value = {cateringUnit.description || ''}
                                   onChange={this.handleChange}/>
                        </FormGroup>

                        <FormGroup>
                            <Label for="country">Country</Label>
                            <Input type="text" name="country" id="country" value = {cateringUnit.address.country || ''}
                                   onChange={this.handleAddressChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="street">Street</Label>
                            <Input type="text" name="street" id="street" value = {cateringUnit.address.street || ''}
                                   onChange={this.handleAddressChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="number">House Number</Label>
                            <Input type="text" name="number" id="number" value = {cateringUnit.address.number || ''}
                                   onChange={this.handleAddressChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="otherInformation">Other Address Informations</Label>
                            <Input type="text" name="otherInformation" id="otherInformation" value = {cateringUnit.address.otherInformation || ''}
                                   onChange={this.handleAddressChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

export default (CreateCatering)