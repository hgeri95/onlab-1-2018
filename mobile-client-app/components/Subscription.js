import React, {useEffect, useState} from 'react';
import {Button, Label, Text, View} from 'native-base';
import API from '../utils/Api';

export const Subscription = ({cateringUnitName}) => {
    const [subscribed, setSubscribed] = useState(false);

    useEffect(() => {
        isSubscribed();
    }, [cateringUnitName]);

    const isSubscribed = () => {
        API.get('/notification/isSubscribed/' + cateringUnitName)
            .then(res => {
                console.log(res.data);
                if (res.status === 204) {
                    setSubscribed(false);
                } else {
                    setSubscribed(true);
                }
            })
            .catch(err => {
                console.log(err.response.data);
                setSubscribed(false);
            });
    };

    const unsubscribe = () => {
        API.put('/notification/unsubscribe/' + cateringUnitName)
            .then(res => {
                console.log('Unsubscribed: ' + res.data);
                isSubscribed();
            })
            .catch(err => {
                console.log(err.response.data);
                isSubscribed();
            })
    };

    const subscribe = () => {
        API.put('/notification/subscribe/' + cateringUnitName)
            .then(res => {
                console.log('Subscribed:' + res.data);
                isSubscribed();
            })
            .catch(err => {
                console.log(err.response.data);
                isSubscribed();
            })
    };

    const SubscribeButton = ({isSubscribed}) => {
        if (isSubscribed) {
            return (<Button onPress={() => unsubscribe()}><Text>Unsubscribe</Text></Button>);
        } else {
            return (<Button onPress={() => subscribe()}><Text>Subscribe</Text></Button>);
        }
    };

    return (
        <View style={{paddingTop: 6, paddingEnd: 6, flexDirection: 'row'}}>
            <Label style={{fontSize: 20, fontWeight: 'bold'}}>Subscription: </Label>
            <SubscribeButton isSubscribed={subscribed}/>
        </View>
    );
};

