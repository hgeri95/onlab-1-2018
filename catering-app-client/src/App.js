import React, {Component} from 'react';

import {Provider} from 'react-redux';
import store from "./store/store";
import CustomNavbar from "./components/CustomNavbar";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Register from "./components/Register";
import Login from "./components/Login";
import ListAllCatering from "./components/ListAllCatering";
import CreateCatering from "./components/CreateCatering";
import UserDetails from "./components/UserDetails";
import Home from "./components/Home";

class App extends Component {
    render() {
        return (
            <Provider store={store}>
                <BrowserRouter>
                    <div className="app">
                        <CustomNavbar/>
                        <Switch>
                            <Route exact path="/" component={Home}/>
                            <Route exact path="/register" component={Register}/>
                            <Route exact path="/login" component={Login}/>
                            <Route exact path="/list-all-catering" component={ListAllCatering}/>
                            <Route exact path="/create-catering" component={CreateCatering}/>
                            <Route exact path="/user-details" component={UserDetails}/>
                            <Route exact path="/catering-details/:id" component={CreateCatering}/>
                        </Switch>
                    </div>
                </BrowserRouter>
            </Provider>
        )
    }
}

export default App;
