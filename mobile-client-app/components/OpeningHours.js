import React, {useState} from 'react';
import {Button, Label, List, ListItem, Text, View} from 'native-base';

export const OpeningHours = ({openingHours}) => {
    const [open, setOpen] = useState(false);

    return (
        <View style={{paddingTop: 6, paddingEnd: 2}}>
            <View style={{flexDirection: 'row'}}>
                <Button onPress={() => setOpen(!open)} transparent>
                    <Label style={{fontSize: 20, fontWeight: 'bold'}}>Opening hours: </Label>
                </Button>
            </View>
            {open && <List>
                <OpeningHoursList openingHours={openingHours}/>
            </List>
            }
        </View>
    );
};

function OpeningHour({openingHour}) {
    return (
        <ListItem>
            <Label style={{fontWeight: 'bold'}}>{openingHour.weekDay} </Label>
            <Label>{openingHour.open} - </Label>
            <Label>{openingHour.close}</Label>
        </ListItem>);
}

const OpeningHoursList = ({openingHours}) => {
    if (openingHours !== undefined) {
        const openingHoursList = openingHours.map(openingHour => {
            return <OpeningHour openingHour={openingHour} key={openingHour.weekDay}/>
        });
        return openingHoursList;
    } else {
        return <Text>No opening hours.</Text>
    }
};
