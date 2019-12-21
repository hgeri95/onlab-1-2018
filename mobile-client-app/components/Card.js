import React, {useEffect, useState} from 'react';
import {Body, Card, CardItem, Left, Right, Text, Thumbnail} from "native-base";
import API, {baseUrl} from '../utils/Api';
import {getImageUrl} from "../utils/ImageUtil";

export const CateringCard = ({cateringItem, navigation}) => {
    const [image, setImage] = useState(require('../assets/images/robot-dev.png'));

    useEffect(() => {
        getImageUrl(cateringItem.name).then((res) => {
            if (res !== null) {
                setImage({uri: res});
            } else {
                setImage(require('../assets/images/robot-dev.png'));
            }
        })
    }, []);

    return (
        <Card key={cateringItem.id}>
            <CardItem button onPress={() => {
                navigation.navigate('Details', {id: cateringItem.id, name: cateringItem.name})
            }}>
                <Left>
                    <Body>
                        <Text>{cateringItem.name}</Text>
                        <Text note>{cateringItem.address.address}</Text>
                    </Body>
                </Left>
                <Right>
                    <Body>
                        <Thumbnail size={100}
                                   source={image}/>
                    </Body>
                </Right>
            </CardItem>
        </Card>
    );
};

export const NearestCateringCard = ({cateringItem, navigation}) => {
    const [image, setImage] = useState(require('../assets/images/robot-dev.png'));

    useEffect(() => {
        getImageUrl(cateringItem.name).then((res) => {
            if (res !== null) {
                setImage({uri: res});
            } else {
                setImage(require('../assets/images/robot-dev.png'));
            }
        })
    }, []);

    return (
        <Card key={cateringItem.id}>
            <CardItem button onPress={() => {
                navigation.navigate('Details', {id: cateringItem.id, name: cateringItem.name})
            }}>
                <Left>
                    <Body>
                        <Text>{cateringItem.name}</Text>
                        <Text note>{cateringItem.address.address}</Text>
                        <Text style={{fontWeight: 'bold'}}>Distance: {Number(cateringItem.distance).toFixed(2)} km</Text>
                    </Body>
                </Left>
                <Right>
                    <Body>
                        <Thumbnail size={100}
                                   source={image}/>
                    </Body>
                </Right>
            </CardItem>
        </Card>
    );
};
