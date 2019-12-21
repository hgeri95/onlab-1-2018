import React, {useState, useEffect} from 'react';
import {View, Label, List, ListItem, Form, Item as FormItem, Input, Picker, Button, Text, Item} from 'native-base';
import API from '../utils/Api';
import {authenticate} from "../actions/Authentication";
import {renderError} from "./ErrorAlert";

export const Ratings = ({cateringUnitName}) => {
    const [open, setOpen] = useState(false);
    const [ratings, setRatings] = useState([]);
    const [ownRating, setOwnRating] = useState(0);
    const [ownComment, setOwnComment] = useState("");
    const [error, setError] = useState('');

    useEffect(() => {
        API.get('/rating/' + cateringUnitName)
            .then(res => {
                console.log(res.data);
                setRatings(res.data);
            })
            .catch(err => {
                console.log(err.response.data);
            });
        API.get('/rating/specific/?cateringUnitName=' + cateringUnitName)
            .then(res => {
                console.log(res.data);
                setOwnRating(res.data.rate);
                setOwnComment(res.data.comment);
            })
            .catch(err => {
                console.log(err.response.data);
            })
    }, [cateringUnitName]);

    const handleSubmit = event => {
        console.log("Handle submit");
        API.put('/rating/rate', {cateringUnitName: cateringUnitName, rate: ownRating, comment: ownComment})
            .then(res => {
                console.log(res.data);
                setOwnComment(res.data.comment);
                setOwnRating(res.data.rate);
            })
            .catch(err => {
                console.log(err);
                setError(err.response.data.message);
            });
        event.preventDefault();
    };

    return (
        <View style={{paddingTop: 6, paddingEnd: 2}}>
            <View>
                <Button onPress={() => setOpen(!open)} transparent>
                    <Label style={{fontSize: 20, fontWeight: 'bold'}}>Ratings:</Label>
                </Button>
            </View>
            {open &&
            <View>
                <List>
                    <RatingsList ratings={ratings}/>
                </List>
                <Form>
                    <FormItem stackedLabel>
                        <Label>Your rate: </Label>
                        <Item picker>
                            <Picker mode="dropdown" selectedValue={ownRating}
                                    onValueChange={val => {
                                        setOwnRating(val)
                                    }} style={{width: '80%'}}>
                                <Picker.Item label="1" value="1"/>
                                <Picker.Item label="2" value="2"/>
                                <Picker.Item label="3" value="3"/>
                                <Picker.Item label="4" value="4"/>
                                <Picker.Item label="5" value="5"/>
                            </Picker>
                        </Item>
                    </FormItem>
                    <FormItem stackedLabel>
                        <Label>Your comment:</Label>
                        <Input type="text" value={ownComment}
                               onChangeText={value => setOwnComment(value)}
                               name="comment" id="comment"/>
                    </FormItem>
                </Form>
                {renderError(error)}
                <View style={{paddingTop: 20}}>
                    <Button block rounded iconLeft
                            style={{width: '80%', height: 50, marginBottom: 10, alignSelf: 'center'}}
                            onPress={handleSubmit}>
                        <Text>Send</Text>
                    </Button>
                </View>
            </View>}
        </View>
    );
};

const RatingItem = ({rating}) => {
    return (
        <ListItem>
            <Label style={{fontWeight: 'bold'}}>{rating.username}: </Label>
            <Label>{rating.rate} - </Label>
            <Label>{rating.comment}</Label>
        </ListItem>
    );
};

const RatingsList = ({ratings}) => {
    const ratingsList = ratings.map(rating => {
        return <RatingItem rating={rating} key={rating.username}/>
    });
    return ratingsList;
};
