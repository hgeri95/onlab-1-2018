import React from 'react';
import {View} from 'native-base';
import {Text} from 'react-native';

export function renderError(errorMessage) {
    if (errorMessage) {
        return (
                <View style={{paddingTop: 20, alignSelf: 'center'}}>
                    <Text style={{color: 'red'}}>Ooops! {errorMessage.toString()}</Text>
                </View>);
    }
}
