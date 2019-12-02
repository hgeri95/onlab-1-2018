import React from 'react';
import {CateringCard} from "./Card";
import {Text} from "native-base";

export const CateringList = ({caterings, navigation}) => {
    if (caterings !== undefined) {
        const cateringList = caterings.map(catering => {
            return <CateringCard cateringItem={catering} navigation={navigation} key={catering.id}/>
        });
        return cateringList;
    } else {
        return <Text>Loading...</Text>
    }
};
