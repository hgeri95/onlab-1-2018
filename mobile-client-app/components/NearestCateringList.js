import React from 'react';
import {NearestCateringCard} from "./Card";
import {Text} from "native-base";

export const NearestCateringList = ({caterings, navigation}) => {
    if (caterings !== [] && caterings !== undefined) {
        const cateringList = caterings.map(catering => {
            return <NearestCateringCard cateringItem={catering} navigation={navigation} key={catering.id}/>
        });
        return cateringList;
    } else {
        return <Text>Loading...</Text>
    }
};
