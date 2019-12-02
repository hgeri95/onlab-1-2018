import React from 'react';
import {Label, Text, View} from "native-base";


export const Address = ({address}) => {
    return (
        <View style={{paddingTop: 6, paddingEnd: 2}}>
            <Label style={{fontSize: 20, fontWeight: 'bold'}}>Address:</Label>
            <Text>{address.address}</Text>
            <OtherInformation address={address}/>
        </View>
    )
};

function OtherInformation({address}) {
    if (address.otherInformation) {
        return (
            <View>
                <Label style={{fontWeight: 'bold'}}>Address other information:</Label>
                <Label>{address.otherInformation}</Label>
            </View>
        )
    } else {
        return (<View/>)
    }
}
