import React from 'react';
import {Platform} from 'react-native';
import {createBottomTabNavigator, createStackNavigator} from 'react-navigation';

import TabBarIcon from '../components/TabBarIcon';
import HomeScreen from '../screens/HomeScreen';
import RecommendationScreen from "../screens/RecommendationScreen";
import NearestScreen from "../screens/NearestScreen";
import {UserScreen} from "../screens/UserScreen";

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


const UserStack = createStackNavigator(
    {
        User: UserScreen
    },
    config
);

UserStack.navigationOptions = {
    tabBarLabel: 'Logout',
    tabBarIcon: ({focused}) => (
        <TabBarIcon
            focused={focused}
            name={'md-person'}
        />
    )
};

UserStack.path = '';

const tabNavigator = createBottomTabNavigator({
    HomeStack,
    RecommendationStack,
    NearestStack,
    UserStack
});

tabNavigator.path = '';

export default tabNavigator;
