import API from '../utils/api';
import {cateringConstants, imageConstants, userConstants} from "../constants/actionTypes";

//Actions
export const createdCateringUnitAction = (cateringUnit) => ({
    type: cateringConstants.CREATED_CATERING,
    payload: cateringUnit
});

export const updatedCateringUnitAction = (cateringUnit) => ({
    type: cateringConstants.UPDATED_CATERING,
    payload: cateringUnit
});

export const getAllAction = (data) => ({type: cateringConstants.GET_ALL_CATERINGS, payload: data});

export const noErrorAction = () => ({type: userConstants.NO_ERRORS, payload: {}});

export const getCateringAction = (data) => ({type: cateringConstants.GET_CATERING, payload: data});

export const errorAction = (message) => ({type: userConstants.GET_ERRORS, payload: {message: message}});

export const imageUploadedAction = () => ({type: imageConstants.UPLOADED_IMAGE, payload: {}});

export const imageIdsAction = (ids) => ({type: imageConstants.IMAGE_IDS, payload: ids});


//Action creators
export const getAllCateringsAction = () => dispatch => {
    API.get('/cateringunit/owned')
        .then(res => {
            console.log(res);
            dispatch(getAllAction(res.data));
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to get all catering units! Message: " + err.response.data.message));
        });
};

export const createCateringUnit = (cateringUnit) => dispatch => {
    API.post('/cateringunit', cateringUnit)
        .then(res => {
            console.log(res);
            dispatch(createdCateringUnitAction(res.data));
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to create catering unit! Message: " + err.response.data.message));
        })
};

export const updateCateringUnit = (cateringUnit, id) => dispatch => {
    API.put('/cateringunit/' + id, cateringUnit)
        .then(res => {
            console.log(res);
            dispatch(updatedCateringUnitAction(res.data));
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to update catering unit! Message: " + err.response.data.message));
        })
};

export const getCateringUnit = (id) => dispatch => {
    API.get('/cateringunit/' + id)
        .then(res => {
            console.log(res);
            dispatch(getCateringAction(res.data));
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to get catering unit! Message: " + err.response.data.message));
        })
};

export const deleteCateringUnit = (id, history) => dispatch => {
    API.delete('/cateringunit/' + id)
        .then(res => {
            console.log(res);
            history.push('/list-all-catering');
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to delete catering unit! Message: " + err.response.data.message));
        })
};

export const uploadImage = (selectedFile, cateringName) => dispatch => {
    const formData = new FormData();
    formData.append(
        "file",
        selectedFile,
        selectedFile.name
    );
    API.post('/cateringunit/image/upload/' + cateringName, formData)
        .then(res => {
            dispatch(imageUploadedAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to upload image! Message: " + err.response.data.message));
        });
};

export const getImageIds = (cateringName) => dispatch => {
  API.get('/cateringunit/image/getIds/' + cateringName)
      .then(res => {
          dispatch(imageIdsAction(res.data));
      })
      .catch(err => {
          console.log(err);
          dispatch(errorAction("Failed to get image ids! Message: " + err.response.data.message));
      })
};

export const deleteImage = (imageId) => dispatch => {
    API.delete('/cateringunit/image/' + imageId)
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log(err);
        })
};

export const sendNotification = (cateringName, notification) => dispatch => {
    API.put('/notification/email/subscribed/' + cateringName, notification)
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log(err);
        })
};
