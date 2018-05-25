import React from "react";
import { Route, Switch } from "react-router-dom";
import Home from "./containers/Home";
import Login from "./containers/Login";
import SignUp from "./containers/SignUp";
import ListAll from "./containers/ListAll";
import AppliedRoute from "./components/AppliedRoute";
import NotFound from "./containers/NotFound";

export default ({ childProps }) =>
  <Switch>
    <AppliedRoute path="/" exact component={Home} props={childProps} />
    <AppliedRoute path="/login" exact component={Login} props={childProps} />
    <Route path="/sign-up" exact component={SignUp} />
    <Route path="/listall" exact component={ListAll} props={childProps}/>
    <Route component={NotFound} />
  </Switch>;