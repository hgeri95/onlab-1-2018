import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Button, Col, Container, Form, FormGroup, Input, Label, Row, Table} from 'reactstrap';
import Dropdown from "react-dropdown";
import {
    createCateringUnit,
    deleteCateringUnit, deleteImage,
    getCateringUnit,
    getImageIds, sendNotification,
    updateCateringUnit,
    uploadImage
} from "../action_creators/catering";
import {renderError} from "./ErrorAlert";
import {baseUrl} from "../utils/api";

const defaultState = () =>  ({
    cateringUnit: {
        name: "",
        description: "",
        openingHours: [
            {
                weekDay: "",
                open: "",
                close: ""
            }
        ],
        address: {
            address: "",
            coordinate: {
                latitude: 0,
                longitude: 0
            },
            otherInformation: ""
        },
        categoryParameters: [
            {
                key: "",
                value: ""
            }
        ]
    },
    selectedFile: null,
    imageIds: [],
    notification: {
        subject: "",
        message: ""
    },
    errorMessage: ""
});

class CreateCatering extends Component {

    constructor(props) {
        super(props);
        this.state = { ...defaultState()};

        this.handleChange = this.handleChange.bind(this);
        this.handleAddressChange = this.handleAddressChange.bind(this);
        this.handleOpeningHourChange = this.handleOpeningHourChange.bind(this);
        this.handleOpeningHourSelect = this.handleOpeningHourSelect.bind(this);
        this.handleCategorySelect = this.handleCategorySelect.bind(this);
        this.handleCategoryChange = this.handleCategoryChange.bind(this);
        this.addOpeningHour = this.addOpeningHour.bind(this);
        this.addCategoryParameter = this.addCategoryParameter.bind(this);
        this.deleteOpeningHour = this.deleteOpeningHour.bind(this);
        this.deleteCategoryParameter = this.deleteCategoryParameter.bind(this);
        this.handleCoordinateChange = this.handleCoordinateChange.bind(this);
        this.handleNotificationChange = this.handleNotificationChange.bind(this);
        this.refreshImages = this.refreshImages.bind(this);
    }

    handleChange(event) {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit[event.target.name] = event.target.value;
        this.setState({cateringUnit});
    }

    handleNotificationChange(event) {
        let notification = {...this.state.notification};
        notification[event.target.name] = event.target.value;
        this.setState({notification});
    }

    handleAddressChange(event) {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit.address[event.target.name] = event.target.value;
        this.setState({cateringUnit});
    }

    handleCoordinateChange = event => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit.address.coordinate[event.target.name] = event.target.value;
        this.setState({cateringUnit});
    };

    handleOpeningHourChange = idx => event => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit["openingHours"][idx][event.target.name] = event.target.value;
        this.setState({cateringUnit});
    };

    handleOpeningHourSelect = idx => event => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit["openingHours"][idx]["weekDay"] = event.value;
        this.setState({cateringUnit});
    };

    handleCategorySelect = idx => event => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit["categoryParameters"][idx]["key"] = event.value;
        this.setState({cateringUnit});
    };

    handleCategoryChange = idx => event => {
        let caterUnit = {...this.state.cateringUnit};
        caterUnit["categoryParameters"][idx][event.target.name] = event.target.value;
        this.setState({caterUnit});
    };

    addOpeningHour = () => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit["openingHours"] = this.state.cateringUnit.openingHours.concat({weekDay: "", open: "", close: ""});
        this.setState({cateringUnit});
    };

    addCategoryParameter = () => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit["categoryParameters"] = this.state.cateringUnit.categoryParameters.concat({key: "", value: ""});
        this.setState({cateringUnit});
    };

    deleteOpeningHour = idx => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit.openingHours.splice(idx, 1);
        this.setState({cateringUnit});
    };

    deleteCategoryParameter = idx => {
        let cateringUnit = {...this.state.cateringUnit};
        cateringUnit.categoryParameters.splice(idx, 1);
        this.setState({cateringUnit});
    };

    handleSubmit = event => {
        let cateringUnit = this.state.cateringUnit;
        let id = this.props.match.params.id;
        if (typeof id !== "undefined") {
            this.props.updateCateringUnit(cateringUnit, id);
        } else {
            this.props.createCateringUnit(cateringUnit);
        }
        this.props.history.push("/list-all-catering");
        event.preventDefault();
    };

    sendNotification = event => {
      let notification = this.state.notification;
      let cateringName = this.state.cateringUnit.name;

      this.props.sendNotification(cateringName, notification);
      this.setState({notification: {subject: "", message: ""}});
      event.preventDefault();
    };

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            console.log(nextProps.errors);
            this.setState({errorMessage: nextProps.errors.errors.message});
        }
        if (nextProps.cateringState.cateringUnit) {
            this.setState({cateringUnit: nextProps.cateringState.cateringUnit});
        }
        if (nextProps.cateringState.imageIds) {
            this.setState({imageIds: nextProps.cateringState.imageIds});
        }
    }

    getImages(cateringName) {
        this.props.getImageIds(cateringName);
    }

    refreshImages() {
        this.getImages(this.state.cateringUnit.name);
    }

    createUrlFromImgId(id) {
        return baseUrl + '/api/v1/cateringunit/image/download/' + id;
    }

    componentWillUpdate(nextProps, nextState, nextContext) {
        if (nextState.cateringUnit.name !== "" && this.state.cateringUnit.name === "") {
            this.getImages(nextState.cateringUnit.name);
        }
    }

    componentDidMount() {
        let id = this.props.match.params.id;
        if (typeof id !== "undefined") {
            this.props.getCateringUnit(id);
        } else {
            this.setState({...defaultState()});
        }
    }

    fileChangedHandler = event => {
        const file = event.target.files[0];
        this.setState({selectedFile: file});
    };

    uploadHandler = () => {
        this.props.uploadImage(this.state.selectedFile, this.state.cateringUnit.name);
        this.getImages(this.state.cateringUnit.name);
    };

    render() {
        const {cateringUnit} = this.state;
        const weekDays = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
        const categoryParameters = ['Type', 'Capacity', 'Price', 'Cash only'];
        let isDefined = typeof this.props.match.params.id !== 'undefined';
        return (
            <div>
                <Container>
                    <h1>Catering Unit</h1>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="name">Name</Label>
                            <Input type="text" name="name" id="name" value={cateringUnit.name}
                                   placeholder="Dream restaurant" onChange={this.handleChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="description">Description</Label>
                            <Input type="text" name="description" id="description"
                                   value={cateringUnit.description} placeholder="Short description about your place"
                                   onChange={this.handleChange}/>
                        </FormGroup>
                        <br/>
                        <h2>Address</h2>
                        <FormGroup>
                            <Input type="text" name="address" id="address" value={cateringUnit.address.address}
                                   placeholder="Andrássy street 4." onChange={this.handleAddressChange}/>
                        </FormGroup>
                        <FormGroup>
                            <h4>Coordinates</h4>
                            <Row form>
                                <Col>
                                    <FormGroup>
                                        <Label for="latitude">Latitude</Label>
                                        <Input type="number" name="latitude" id="latitude"
                                               value={cateringUnit.address.coordinate.latitude}
                                               placeholder="47.49" onChange={(e) => this.handleCoordinateChange(e)}/>
                                    </FormGroup>
                                </Col>
                                <Col>
                                    <FormGroup>
                                        <Label for="longitude">Longitude</Label>
                                        <Input type="number" name="longitude" id="longitude"
                                               value={cateringUnit.address.coordinate.longitude}
                                               placeholder="19.04" onChange={(e) => this.handleCoordinateChange(e)}/>
                                    </FormGroup>
                                </Col>
                            </Row>
                        </FormGroup>
                        <FormGroup>
                            <Label for="otherInformation">Other address information</Label>
                            <Input type="text" name="otherInformation" id="otherInformation"
                                   value={cateringUnit.address.otherInformation}
                                   placeholder="Address related information"
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
                                {this.state.cateringUnit.openingHours.map((openingHour, idx) => (
                                    <tr>
                                        <td><Dropdown options={weekDays} id="weekDay" name="weekDay"
                                                      onChange={this.handleOpeningHourSelect(idx)}
                                                      value={this.state.cateringUnit.openingHours[idx].weekDay}
                                                      placeholder="Weekday"/></td>
                                        <td><Input type="text" id="open" name="open"
                                                   value={this.state.cateringUnit.openingHours[idx].open}
                                                   onChange={this.handleOpeningHourChange(idx)} placeholder="12:00"/>
                                        </td>
                                        <td><Input type="text" id="close" name="close"
                                                   value={this.state.cateringUnit.openingHours[idx].close}
                                                   onChange={this.handleOpeningHourChange(idx)} placeholder="18:00"/>
                                        </td>
                                        <td>
                                            <Button color="danger"
                                                    onClick={(e) => this.deleteOpeningHour(idx)}>Delete</Button>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </Table>
                            <Button color="secondary" onClick={this.addOpeningHour}>Add new day</Button>
                        </FormGroup>
                        <h2>Category Parameters</h2>
                        <FormGroup>
                            <Table>
                                <tbody>
                                {this.state.cateringUnit.categoryParameters.map((categoryParameter, idx) => (
                                    <tr>
                                        <td><Dropdown options={categoryParameters} id="key" name="key"
                                                      onChange={this.handleCategorySelect(idx)}
                                                      value={this.state.cateringUnit.categoryParameters[idx].key}
                                                      placeholder="Parameter"/></td>
                                        <td>
                                            <Input type="text" placeholder="Value..." id="value" name="value"
                                                   value={this.state.cateringUnit.categoryParameters[idx].value}
                                                   onChange={this.handleCategoryChange(idx)}/>
                                        </td>
                                        <td>
                                            <Button color="danger"
                                                    onClick={(e) => this.deleteCategoryParameter(idx)}>Delete</Button>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </Table>
                            <Button color="secondary" onClick={(e) => this.addCategoryParameter()}>Add new
                                parameter</Button>
                        </FormGroup>
                        <br/>
                        {renderError(this.state.errorMessage)}
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>&nbsp;&nbsp;&nbsp;
                            <Button color="danger"
                                    onClick={() => this.props.deleteCateringUnit(this.state.cateringUnit.id, this.props.history)}>Delete</Button>
                        </FormGroup>
                    </Form>
                </Container>
                {isDefined && <ImageView imageIds={this.state.imageIds} fileChangedHandler={this.fileChangedHandler}
                                         uploadHandler={this.uploadHandler} createUrlFromImgId={this.createUrlFromImgId}
                                         deleteImage={this.props.deleteImage} refreshImages={this.refreshImages}/>}
                {isDefined && <NotificationView sendNotification={this.sendNotification} handleNotificationChange={this.handleNotificationChange} notification={this.state.notification}/>}
            </div>
        );
    }
}

function ImageView(props) {
    const imageIds = props.imageIds;
    return <Container>
        <br/>
        <h2>Images</h2>
        <Table>
            <tbody>
            {imageIds.map((imageId, idx) => (
                <tr>
                    <td>
                        <img src={props.createUrlFromImgId(imageId)} width={500} mode='fit'/>
                    </td>
                    <td>
                        <Button color="danger" onClick={() => props.deleteImage(imageId)}>Delete</Button>
                    </td>
                </tr>
            ))}
            </tbody>
        </Table>
        <Input type="file" onChange={props.fileChangedHandler}/>
        <br/>
        <Button onClick={props.uploadHandler}>Upload</Button>&nbsp;&nbsp;&nbsp;
        <Button onClick={props.refreshImages}>Refresh</Button>
    </Container>;
}

function NotificationView(props) {
    return <Container>
        <br/>
        <Form onSubmit={props.sendNotification}>
            <h1>Notification sending</h1>
            <FormGroup>
                <Label for="subject">Notification subject</Label>
                <Input type="text" name="subject" id="subject" onChange={props.handleNotificationChange} value={props.notification.subject}/>
                <Label for="message">Notification message</Label>
                <Input type="textarea" name="message" id="message" onChange={props.handleNotificationChange} value={props.notification.message}/>
            </FormGroup>
            <FormGroup>
                <Button color="info">Send</Button>
            </FormGroup>
        </Form>
    </Container>;
}

const mapStateToProps = (state) => ({
    errors: state.errors,
    cateringState: state.catering
});

export default connect(mapStateToProps, {
    createCateringUnit,
    getCateringUnit,
    deleteCateringUnit,
    updateCateringUnit,
    uploadImage,
    getImageIds,
    deleteImage,
    sendNotification
})(CreateCatering)
