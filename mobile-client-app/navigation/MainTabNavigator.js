import React from 'react';
import {Platform} from 'react-native';
import {createBottomTabNavigator, createStackNavigator} from 'react-navigation';

import TabBarIcon from '../components/TabBarIcon';
import HomeScreen from '../screens/HomeScreen';
import RecommendationScreen from "../screens/RecommendationScreen";
import NearestScreen from "../screens/NearestScreen";

const config = Platform.select({
    web: {headerMode: 'screen'},
    default: {},
});

const HomeStack = createStackNavigator(
    {
        Home: HomeScreen,
    },
    config
);

HomeStack.navigationOptions = {
    tabBarLabel: 'Home',
    tabBarIcon: ({focused}) => (
        <TabBarIcon
            focused={focused}
            name={'md-home'}

        />
    ),
};

HomeStack.path = '';

const RecommendationStack = createStackNavigator(
    {
        Recommendation: RecommendationScreen
    },
    config
);

RecommendationStack.navigationOptions = {
    tabBarLabel: 'Recommended',
    tabBarIcon: ({focused}) => (
        <TabBarIcon
            focused={focused}
            name={'md-bulb'}
        />
    )
};

RecommendationStack.path = '';

const NearestStack = createStackNavigator(
    {
        Nearest: NearestScreen
    },
    config
);

NearestStack.navigationOptions = {
    tabBarLabel: 'Nearest places',
    tabBarIcon: ({focused}) => (
        <TabBarIcon
            focused={focused}
            name={'md-map'}
        />
    )
};

NearestStack.path = '';

/*
const LoginStack = createStackNavigator(
    {
        Login: LoginScreen,
    },
    config
);

LoginStack.navigationOptions = {
    tabBarLabel: 'Login',
    tabBarIcon: ({focused}) => (
        <TabBarIcon
            focused={focused}
            name={Platform.OS === 'ios' ? 'ios-link' : 'md-link'}
        />
    ),
};

LoginStack.path = '';

const SettingsStack = createStackNavigator(
    {
        Settings: SettingsScreen,
    },
    config
);

SettingsStack.navigationOptions = {
    tabBarLabel: 'Settings',
    tabBarIcon: ({focused}) => (
        <TabBarIcon focused={focused} name={Platform.OS === 'ios' ? 'ios-options' : 'md-options'}/>
    ),
};

SettingsStack.path = '';
*/
const tabNavigator = createBottomTabNavigator({
    HomeStack,
    RecommendationStack,
    NearestStack,
});

tabNavigator.path = '';

export default tabNavigator;
