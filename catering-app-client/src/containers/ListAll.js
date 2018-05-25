import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel, ListGroup, ListGroupItem } from "react-bootstrap";
import API from '../utils/api';
import "./ListAll.css";

export default class ListAll extends Component {
    constructor(props) {
        super(props);

        this.state = {
            input: []
        };
    }

    handleSubmit = async event => {
        event.preventDefault();
    }

      async componentDidMount() {
        try {
            const res = await API.get('cateringunit/getall');
            console.log(res.data.cateringUnits);
            this.setState({ input: res.data.cateringUnits });
        } catch(error) {
            console.log(error);
        }
      }


    render() {
        const data = [];
        return(
            <div className="ListAll">
                <ListGroup>
                    {
                        this.state.input.map((data, idx) => {
                            return <ListGroupItem header={data.name}>Description: {data.description} Address: {data.address.street} {data.address.number}</ListGroupItem>
                        })
                    }
                </ListGroup>
            </div>
        );
    }
}