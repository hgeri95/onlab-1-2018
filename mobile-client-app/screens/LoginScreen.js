import React, {useContext, useEffect, useState} from 'react';
import {Body, Button, Container, Form, Header, Input, Item as FormItem, Label, Text, Title, View} from 'native-base';
import authContext from "../context/Context";
import {authenticate} from "../actions/Authentication";
import {renderError} from "../components/ErrorAlert";
import {StatusBar} from "react-native-web";

export const LoginScreen = ({navigation}) => {
    const [data, setData] = useState({username: '', password: ''});
    const [error, setError] = useState('');
    const {state, dispatch} = useContext(authContext);

    const handleSubmit = event => {
        console.log("Handle submit");
        authenticate(data, dispatch);
        setData({username: "", password: "", errorMessage: ""});
        event.preventDefault();
    };

    useEffect(() => {
        if (state.isAuthenticated) {
            if (state.role === "ROLE_OWNER") {
                console.log("Owner authenticated");
                setError("You are an Owner. Please use the administration webpage!");
            } else {
                console.log("authenticated");
                navigation.navigate('Main');
            }
        }
        if (state.errorMessage) {
            console.log(state.errorMessage);
            setError(state.errorMessage);
        }
    }, [state]);

    return (
        <Container>
            <Header>
                <Body><Title>Catering Unit Monitor</Title></Body>
            </Header>
            <Form>
                <FormItem stackedLabel>
                    <Label>Username</Label>
                    <Input type="text" value={data.username}
                           onChangeText={value => setData({...data, "username": value})}
                           name="username" id="username"/>
                </FormItem>
                <FormItem stackedLabel>
                    <Label>Password</Label>
                    <Input type="text" value={data.password} secureTextEntry={true}
                           onChangeText={value => setData({...data, "password": value})}
                           name="password" id="password"/>
                </FormItem>
                {renderError(error)}
                <View style={{paddingTop: 20}}>
                    <Button block rounded iconLeft
                            style={{width: '80%', height: 50, marginBottom: 10, alignSelf: 'center'}}
                            onPress={handleSubmit}>
                        <Text>Login</Text>
                    </Button>
                </View>
            </Form>
        </Container>
    );
};

export default LoginScreen;

LoginScreen.navigationOptions = {
    headerStyle: {
        height: StatusBar.currentHeight
    }
};
