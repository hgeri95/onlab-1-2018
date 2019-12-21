import React, {useEffect, useState} from 'react';
import {Button, Content, Header, Icon, Input, Item, Right, Text} from 'native-base';
import API from '../utils/Api';
import {StatusBar} from "react-native-web";
import {CateringList} from "../components/CateringList";


export default function HomeScreen({navigation}) {
    const [caterings, setCaterings] = useState([]);
    const [query, setQuery] = useState("");
    const [error, setError] = useState("");

    const loadCaterings = () => {
        API.get('/cateringunit/all')
            .then(res => {
                console.log(res.data);
                setCaterings(res.data.cateringUnits);
            }).catch(err => {
            console.log(err);
            setError(err.response.data.message);
        });
    };

    const search = event => {
        event.preventDefault();
        if (query !== "") {
            API.get('cateringunit/search/?term=' + query)
                .then(res => {
                    console.log(res);
                    setCaterings(res.data);
                })
                .catch(err => {
                    console.log(err);
                    setError(err.response.data.message);
                });
        } else {
            loadCaterings();
        }
    };

    useEffect(() => {
        loadCaterings();
    }, []);

    return (
        <Content>
            <Header searchBar rounded>
                <Item style={{flex: 0.65}}>
                    <Icon name="ios-search"
                          android={'md-search'}
                    />
                    <Input placeholder="Search" autoCorrect={false} maxLength={120}
                           onChangeText={(keyword) => {
                               setQuery(keyword)
                           }}
                    />
                </Item>
                <Right style={{flex: 0.35}}>
                    <Button transparent
                            onPress={search}>
                        <Text>Search</Text>
                    </Button>
                </Right>
            </Header>
            <Content>
                <CateringList caterings={caterings} navigation={navigation}/>
            </Content>
        </Content>
    );
}

HomeScreen.navigationOptions = {
    headerStyle: {
        height: StatusBar.currentHeight
    }
};
