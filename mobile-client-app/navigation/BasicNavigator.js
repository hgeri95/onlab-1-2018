import React from 'react';
import {createStackNavigator, HeaderBackButton} from "react-navigation";
import CateringDetailsScreen from "../screens/CateringDetailsScreen";

const BasicNavigator = createStackNavigator(
    {
        Details: {
            screen: CateringDetailsScreen,
            navigationOptions: ({navigation}) => ({
                title: 'Details',
                headerLeft: <HeaderBackButton onPress={() => navigation.navigate('Home')}/>
            })
        }
    }
);

export default BasicNavigator;
