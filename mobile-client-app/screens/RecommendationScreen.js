import React, {useEffect, useState} from 'react';
import {Content, Header, Title} from "native-base";
import {CateringList} from "../components/CateringList";
import {StatusBar} from "react-native-web";
import API from "../utils/Api";

export default function RecommendationScreen({navigation}) {
    const [caterings, setCaterings] = useState([]);

    const loadRecommendations = () => {
        API.get('/cateringunit/getall')
            .then(res => {
                console.log(res.data);
                setCaterings(res.data.cateringUnits);
            }).catch(err => {
            console.log(err);
            setError(err.response.data.message);
        });//TODO Change to reccommendation call
    };

    useEffect(() => {
        loadRecommendations();
    }, []);

    return (
        <Content>
            <Header><Title>Recommendations</Title></Header>
            <Content>
                <CateringList caterings={caterings} navigation={navigation}/>
            </Content>
        </Content>

    );
}

RecommendationScreen.navigationOptions = {
    headerStyle: {
        height: StatusBar.currentHeight
    }
}
