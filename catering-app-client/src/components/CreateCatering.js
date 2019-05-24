import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {Button, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import Dropdown from "react-dropdown";

class CreateCatering extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cateringUnit: {
                name: "Dream restaurant",
                description: "The best restaurant",
                openingHours: [
                    {
                        weekDay: "MONDAY",
                        open: "12:00",
                        close: "13:00"
                    }
                ],
                address: {
                    address: "Andrássy street 4.",
                    coordinate: {
                        latitude: 40.10,
                        longitude: 50.11
                    },
                    otherInformation: "Entrance in back"
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
        const weekDays = [{value: 'MONDAY', label: 'MONDAY'}, {value: 'THUESRDAY', label: 'THUESRDAY'}, {value: 'WEDNESDAY', label: 'WEDNESDAY'}];
        const categoryParameters = [{value: 'type', label: 'Type'}, {value: 'capacity', label: 'Capacity'}];
        const {weekday} = this.state;
        return (
            <div>
                <Container>
                    <h2>Catering Unit</h2>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="name">Name</Label>
                            <Input type="text" name="name" id="name" value={cateringUnit.name || ''}
                                   onChange={this.handleChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="description">Description</Label>
                            <Input type="text" name="description" id="description"
                                   value={cateringUnit.description || ''}
                                   onChange={this.handleChange}/>
                        </FormGroup>

                        <h2>Address</h2>
                        <FormGroup>
                            <Input type="text" name="address" id="address" value={cateringUnit.address.address || ''}
                                   onChange={this.handleAddressChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="otherInforamtion">Other address information</Label>
                            <Input type="text" name="otherInformation" id="otherInforamtion"
                                   value={cateringUnit.address.otherInformation || ''}
                                   onChange={this.handleAddressChange}/>
                        </FormGroup>

                        <h2>Opening Hours</h2>
                        <FormGroup>
                            <Table>
                                <thead>
                                <tr>
                                    <th>Day of week</th>
                                    <th>Open</th>
                                    <th>Close</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><Dropdown options={weekDays} onChange={this.handleSelect} value={weekday}
                                                  placeholder="Weekday"/></td>
                                    <td><Input type="text" value="12:00"/></td>
                                    <td><Input type="text" value="14:00"/></td>
                                </tr>
                                </tbody>
                            </Table>
                            <Button color="secondary">Add new day</Button>
                        </FormGroup>

                        <h2>Category Parameters</h2>
                        <FormGroup>
                            <Table>
                                <thread>
                                    <tr>
                                        <th>Parameter </th>
                                        <th>Value</th>
                                    </tr>
                                </thread>
                                <tbody>
                                <tr>
                                    <td><Dropdown options={categoryParameters} onChange={this.handleSelect}
                                                  placeholder="Parameter"/></td>
                                    <td>
                                        <Input type="text" placeholder=""/>
                                    </td>
                                </tr>
                                </tbody>
                            </Table>
                            <Button color="secondary">Add new parameter</Button>
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