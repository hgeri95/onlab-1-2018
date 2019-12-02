import React from 'react';
import API, {baseUrl} from "./Api";

export function getImageUrl(cateringUnitName) {
    return API.get('/cateringunit/image/getIds/' + cateringUnitName).then((res) => {
        console.log(res.data);
        if (res.data[0] !== undefined) {
            const url = baseUrl + '/cateringunit/image/download/' + res.data[0];
            console.log(url);
            return url;
        } else {
            return null;
        }
    })
}

export function getImageUrls(cateringUnitName) {
    return API.get('/cateringunit/image/getIds/' + cateringUnitName).then((res) => {
        console.log(res.data);
        if (res.data) {
            let urls = [];
            res.data.map(id => {
                urls.push(baseUrl + '/cateringunit/image/download/' + id);
            });
            return urls;
        } else {
            return [];
        }
    })
}
