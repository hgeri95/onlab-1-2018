import React, {useEffect, useState} from 'react';
import {Button, Container, Content, Form, Item, Label, View} from 'native-base';
import API from '../utils/Api';
import {getImageUrls} from "../utils/ImageUtil";
import {CustomSliderBox} from "../components/CustomSliderBox";
import {Address} from "../components/Address";
import {OpeningHours} from "../components/OpeningHours";
import {CategoryParameters} from "../components/CategoryParameters";
import {Statistic} from "../components/Statistic";
import {Ratings} from "../components/Ratings";
import {Subscription} from "../components/Subscription";

export const CateringDetailsScreen = ({navigation}) => {
    const [catering, setCatering] = useState({});
    const [imageUrls, setImageUrls] = useState([]);
    const [error, setError] = useState("");
    const [open, setOpen] = useState(true);

    useEffect(() => {
        const id = navigation.getParam('id', "");
        const name = navigation.getParam('name', "");
        if (id !== "")
            API.get('/cateringunit/' + id)
                .then(res => {
                    console.log(res.data);
                    setCatering(res.data);
                })
                .catch(err => {
                    console.log(err.response);
                    setError(err.response.data);
                });

        getImageUrls(name).then(res => {
            setImageUrls(res);
        });
    }, []);

    return (
        <Container>
            <Content>
                <CustomSliderBox images={imageUrls} sliderBoxHeight={200} resizeMode='contain'/>
                <Form style={{paddingLeft: 8, paddingRight: 8}}>
                    <Item underline>
                        <Button onPress={() => setOpen(!open)} transparent>
                            <Label style={{fontSize: 28, fontWeight: 'bold'}}>{catering.name}</Label>
                        </Button>
                    </Item>
                    {open &&
                    <View>
                        <Item underline>
                            <View style={{paddingTop: 6, paddingEnd: 2}}>
                                <Label style={{fontSize: 20, fontWeight: 'bold'}}>Description:</Label>
                                <Label>{catering.description}</Label>

                            </View>
                        </Item>
                        <Item underline>
                            {catering.address && <Address address={catering.address}/>}
                        </Item>
                        <Item underline>
                            {catering.openingHours && <OpeningHours openingHours={catering.openingHours}/>}
                        </Item>
                        <Item underline>
                            {catering.categoryParameters &&
                            <CategoryParameters categoryParameters={catering.categoryParameters}/>}
                        </Item>
                    </View>}
                    <Item underline>
                        <Statistic cateringUnitId={catering.id}/>
                    </Item>
                    <Item underline>
                        <Ratings cateringUnitName={catering.name}/>
                    </Item>
                    <Item underline>
                        <Subscription cateringUnitName={catering.name}/>
                    </Item>
                </Form>
            </Content>
        </Container>
    );
};

export default CateringDetailsScreen;

