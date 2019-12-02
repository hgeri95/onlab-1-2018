import React, {useState} from 'react';
import {Button, Label, View, Text, ListItem, List} from "native-base";

export const CategoryParameters = ({categoryParameters}) => {
    const [open, setOpen] = useState(false);

    return (
        <View style={{paddingTop: 6, paddingEnd: 2}}>
            <View style={{flexDirection: 'row'}}>
                <Button onPress={() => setOpen(!open)} transparent>
                    <Label style={{fontSize: 20, fontWeight: 'bold'}}>Category parameters: </Label>
                </Button>
            </View>
            {open && <List>
                <CategoryParameterList categoryParameters={categoryParameters}/>
            </List>}
        </View>
    );
};

const CategoryParameterList = ({categoryParameters}) => {
    if (categoryParameters !== undefined) {
        const categoryParameterList = categoryParameters.map(categoryParameter => {
            return (
                <ListItem key={categoryParameter.key}>
                    <Label>{categoryParameter.key}: </Label>
                    <Label>{categoryParameter.value}</Label>
                </ListItem>);
        })
        return categoryParameterList;
    } else {
        return <Text>No category parameter.</Text>
    }
};
