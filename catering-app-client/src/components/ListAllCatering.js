import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getAllCateringsAction } from "../action_creators/catering";
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

class ListAllCatering extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cateringUnits: []
        };
    }

    componentWillMount() {
        this.props.getAllCateringsAction();
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.catering.cateringUnits) {
            let copyCaterings = nextProps.catering.cateringUnits;
            this.setState({cateringUnits: copyCaterings.cateringUnits});
        }
    }

    render() {
        const {cateringUnits} = this.state;
        const cateringList = cateringUnits.map(catering => {
            return <tr>
                <td style={{whiteSpace: 'nowrap'}}><b>{catering.name}</b></td>
                <td>{catering.description}</td>
                <td>{catering.address.address}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/catering-details/" + catering.id}>Details</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div className="container">
                <Container fluid>
                    <h3>Catering Units</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Description</th>
                            <th width="20%">City</th>
                        </tr>
                        </thead>
                        <tbody>
                            {cateringList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

ListAllCatering.propTypes = {
    catering: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    catering: state.catering
});

export default connect(mapStateToProps, {getAllCateringsAction})(ListAllCatering)