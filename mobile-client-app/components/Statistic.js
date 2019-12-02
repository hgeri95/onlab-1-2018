import React, {useEffect, useState} from 'react';
import {Button, Icon, Label, Left, List, ListItem, Right, View} from 'native-base';
import API from '../utils/Api';

export const Statistic = ({cateringUnitId}) => {
    const [statistics, setStatistics] = useState([]);
    const [statisticMap, setStatisticMap] = useState(new Map());
    const [open, setOpen] = useState(true);

    useEffect(() => {
        if (cateringUnitId !== undefined) {
            API.get('/statistic/' + cateringUnitId)
                .then(res => {
                    console.log(res);
                    res.data.map(statistic => {
                        setStatisticMap(statisticMap.set(statistic.type, statistic));
                    });
                    setStatistics(res.data);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }, [cateringUnitId]);

    return (
        <View style={{paddingTop: 6, paddingEnd: 2}}>
            <Button transparent onPress={() =>setOpen(!open)}>
                <Label style={{fontSize: 20, fontWeight: 'bold'}}>Statistic:</Label>
            </Button>
            {open &&
            <List>
                <StatisticList statisticMap={statisticMap} cateringUnitId={cateringUnitId} statistics={statistics}/>
            </List>}
        </View>
    );
};

const StatisticList = ({statisticMap, cateringUnitId, statistics}) => {
    const types = ['Closed', 'Full', 'Small queue', 'Medium queue', 'Large queue'];
    const statisticList = types.map(type => {
        return (
            <ListItem key={type} style={{width: 300}}>
                <Left>
                    <Label style={{fontWeight: 'bold'}}>{type}: </Label>
                    <Label>{statisticMap.get(type) ? statisticMap.get(type).statistic : 0}</Label>
                </Left>
                <Right>
                    <AddButton type={type} cateringUnitId={cateringUnitId}/>
                </Right>
            </ListItem>
        );
    });
    return statisticList;
};

const AddButton = ({type, cateringUnitId}) => {
    const [pushed, setPushed] = useState(false);
    if (pushed) {
        return (
            <Button onPress={() => {
                addStatistic({type, cateringUnitId});
                setPushed(true);
            }} disabled={true}>
                <Icon name={'md-add'}></Icon>
            </Button>
        );
    } else {
        return (
            <Button onPress={() => {
                addStatistic({type, cateringUnitId});
                setPushed(true);
            }}>
                <Icon name={'md-add'}></Icon>
            </Button>
        );
    }
};

const addStatistic = ({type, cateringUnitId}) => {
    API.post('/statistic/' + type + '/' + cateringUnitId)
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log(err);
        })
};
