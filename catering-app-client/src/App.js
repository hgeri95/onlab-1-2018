import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from "./store/store";

import Navbar from './components/Navbar';
import Register from './components/Register';
import Login from './components/Login';
import Home from './components/Home';
import ListAllCatering from './components/ListAllCatering';
import CreateCatering from './components/CreateCatering';
import UserDetails from './components/UserDetails';

class App extends Component {
  render() {
    return (
      <Provider store = { store }>
        <Router>
          <div>
            <Navbar />
              <Route exact path="/" component={ Home } />
              <div className="container">
                <Route exact path="/register" component={ Register } />
                <Route exact path="/login" component={ Login } />
                  <Route exact path="/list-all-catering" component={ ListAllCatering } />
                  <Route exact path="/create-catering" component={ CreateCatering } />
                  <Route exact path="/user-details" component={ UserDetails } />
              </div>
          </div>
        </Router>
      </Provider>
    )
  }
}

export default App;
