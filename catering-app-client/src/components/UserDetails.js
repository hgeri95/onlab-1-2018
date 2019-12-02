import React, {Component} from 'react';
import {connect} from 'react-redux';
import {deleteUser, getUserInfoAction, putUserInfoAction} from "../action_creators/user";
import Dropdown from 'react-dropdown';
import Moment from 'moment';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {renderError} from "./ErrorAlert";

class UserDetails extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userInfo: {
                city: "",
                birthDate: "",
                email: "",
                fullName: "",
                gender: ""
            },
            errorMessage: ''
        };
    }

    handleInputChange = event => {
        let localUserInfo = Object.assign({}, this.state.userInfo);
        localUserInfo[event.target.name] = event.target.value;
        this.setState({userInfo: localUserInfo});
    };

    handleSubmit = event => {
        event.preventDefault();
        let modifiedUserInfo = this.state.userInfo;
        this.props.putUserInfoAction(modifiedUserInfo, this.props.history);
        this.setState({
            userInfo: {city: "", birthDate: {}, email: "", fullName: "", gender: ""},
            errorMessage: ''
        });
    };

    handleDelete = () => {
        this.props.deleteUser(this.props.history);
        this.setState({
            userInfo: {city: "", birthDate: {}, email: "", fullName: "", gender: ""},
            errorMessage: ''
        });
    }

    handleSelect = event => {
        let localUserInfo = Object.assign({}, this.state.userInfo);
        localUserInfo.gender = event.value;
        this.setState({userInfo: localUserInfo});
    };

    componentWillMount() {
        this.props.getUserInfoAction();
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.user.userInfo) {
            let copyUserInfo = nextProps.user.userInfo;
            this.setState({username: copyUserInfo.username});
            delete copyUserInfo.username;
            this.setState({
                userInfo: copyUserInfo
            });
        }
        if (nextProps.errors) {
            console.log(nextProps.errors);
            this.setState({errorMessage: nextProps.errors.errors.message});
        }
        if (nextProps.authentication.authenticated === false) {
            this.props.history.push("/login");
        }
    }

    render() {
        let userLang = navigator.language || navigator.userLanguage;
        Moment.locale(userLang);
        const {userInfo} = this.state;
        const genders = ['MALE', 'FEMALE'];
        const formattedDate = Moment(userInfo.birthDate).format('YYYY-MM-DD');
        return (
            <Container>
                <h2>User details for: <b>{this.props.authentication.username}</b></h2>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="fullName">Full name</Label>
                        <Input type="text" id="fullName" name="fullName" placeholder="Your full name"
                               value={userInfo.fullName} onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="birthDate">Birth date</Label>
                        <Input type="date" id="birthDate" name="birthDate" value={formattedDate}
                               onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="city">City</Label>
                        <Input type="text" placeholder="City where you live" id="city" name="city"
                               value={userInfo.city} onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="email" id="email" name="email" placeholder="example@domain.com"
                               value={userInfo.email} onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="gender">Gender</Label>
                        <Dropdown options={genders} onChange={this.handleSelect} value={userInfo.gender}
                                  placeholder="Gender"/>
                    </FormGroup>
                    {renderError(this.state.errorMessage)}
                    <FormGroup>
                        <Button type="submit" color="primary">Save</Button>
                    </FormGroup>
                    <FormGroup>
                        <Button color="danger" onClick={this.handleDelete}>Delete user</Button>
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

const mapStateToProps = (state) => ({
    errors: state.errors,
    user: state.user,
    authentication: state.authentication
});

export default connect(mapStateToProps, {getUserInfoAction, putUserInfoAction, deleteUser})(UserDetails)