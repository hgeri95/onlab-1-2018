import React from 'react';
import {createAppContainer, createSwitchNavigator} from 'react-navigation';

import MainTabNavigator from './MainTabNavigator';
import AuthNavigator from './AuthNavigator';
import BasicNavigator from "./BasicNavigator";

export default createAppContainer(
    createSwitchNavigator({
            Login: AuthNavigator,
            Basic: BasicNavigator,
            // You could add another route here for authentication.
            // Read more at https://reactnavigation.org/docs/en/auth-flow.html
            Main: MainTabNavigator,
        },
        {
            initialRouteName: 'Login'
        })
);
