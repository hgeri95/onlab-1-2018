import {createStackNavigator} from 'react-navigation';
import LoginScreen from "../screens/LoginScreen";
import {Platform} from "react-native";

const config = Platform.select({
    web: {headerMode: 'screen'},
    default: {},
});

const AuthNavigator = createStackNavigator(
    {
        Login: {screen: LoginScreen}
    },
    config
);

export default AuthNavigator;
