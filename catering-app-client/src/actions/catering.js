import API from '../utils/api';
import {cateringConstants} from "../constants/catering.constants";

export const getAllCateringsAction = () => dispatch => {
  API.get('/cateringunit/getall')
    .then(res => {
        dispatch({
            type: cateringConstants.GET_ALL_CATERINGS,
            payload: res.data
        });
    })
        .catch(err => {
            /*dispatch({
                type: cateringConstants.GET_ERRORS,
                payload: err.response.data
            });*/
        });
};