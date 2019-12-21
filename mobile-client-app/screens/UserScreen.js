import React, {useContext} from 'react';
import {Body, Button, Container, Form, Header, Item as FormItem, Text, Title, View} from 'native-base';
import {StatusBar} from "react-native-web";
import {logout} from "../actions/Authentication";
import authContext from "../context/Context";

export const UserScreen = ({navigation}) => {
    const {state, dispatch} = useContext(authContext);

    return (
        <Container>
            <Header>
                <Body><Title>User</Title></Body>
            </Header>
            <Form>
                <View style={{paddingTop: 20}}>
                    <FormItem stackedLabel>
                        <Button onPress={logout(navigation, dispatch)}><Text>Logout</Text></Button>
                    </FormItem>
                </View>
            </Form>
        </Container>
    );
};

export default UserScreen;

UserScreen.navigationOptions = {
    headerStyle: {
        height: StatusBar.currentHeight
    }
};
