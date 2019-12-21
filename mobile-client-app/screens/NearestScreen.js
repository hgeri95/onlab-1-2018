import React, {useEffect, useState} from 'react';
import {Content, Header, Text, Title} from "native-base";
import {StatusBar} from "react-native-web";
import API from "../utils/Api";
import {NearestCateringList} from "../components/NearestCateringList";

export default function NearestScreen({navigation}) {
    const [caterings, setCaterings] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [location, setLocation] = useState({});

    const findCurrentLocation = () => {
      navigator.geolocation.getCurrentPosition(
          position => {
                console.log(position);
                setLocation(position);
          }, err => {
              console.log(err);
          },
          {enableHighAccuracy: true, timeout: 20000, maximumAge: 100}
      );
    };

    /*const getLocation = async () => {
        let {status} = await Permissions.askAsync(Permissions.LOCATION);
        if (status !== 'granted') {
            console.log('No Location permission!');
            setErrorMessage("Permission to access location was denied");
        } else {
            Location.getCurrentPositionAsync({})
                .then(location => {
                    console.log('Location: ' + JSON.stringify(location));
                    setLocation(JSON.stringify(location));
                }).catch(err => {
                console.log(err);
            })
        }
    };*/

    const loadNearest = () => {
        if (location.coords !== undefined) {
            console.log("Get nearest places");
            API.get('/cateringunit/nearest?distance=5.0&longitude=' + location.coords.longitude + '&latitude=' + location.coords.latitude)
                .then(res => {
                    console.log(res.data);
                    setCaterings(res.data);
                }).catch(err => {
                console.log(err);
                setErrorMessage(err.response.data.message);
            });
        }
    };

    useEffect(() => {
        console.log("Location changed");
        if (location !== {}) {
            loadNearest();
        }
    }, [location]);

    useEffect(() => {
        //getLocation();
        console.log("Nearest screen loaded");
        findCurrentLocation();
    }, []);

    return (
        <Content>
            <Header><Title>Nearest</Title></Header>
            <Content>
                {errorMessage === "" ? <NearestCateringList caterings={caterings} navigation={navigation}/> : <Text>{errorMessage}</Text>}
            </Content>
        </Content>
    );
}

NearestScreen.navigationOptions = {
    headerStyle: {
        height: StatusBar.currentHeight
    }
}
