import {cateringConstants, imageConstants} from "../constants/actionTypes";


const initialState = {
    cateringUnits: [],
    cateringUnit: '',
    isImageUploaded: false,
    imageIds: []
};

export default function (state = initialState, action) {
    switch (action.type) {
        case cateringConstants.GET_ALL_CATERINGS:
            return {
                ...state,
                cateringUnits: action.payload
            };
        case cateringConstants.CREATED_CATERING:
            return {
                ...state,
                cateringUnit: action.payload
            };
        case cateringConstants.GET_CATERING:
            return {
                ...state,
                cateringUnit: action.payload
            };
        case cateringConstants.UPDATED_CATERING:
            return {
              ...state,
              cateringUnit: action.payload
            };
        case imageConstants.UPLOADED_IMAGE:
            return {
                isImageUploaded: true
            };
        case imageConstants.IMAGE_IDS:
            return {
                ...state,
                imageIds: action.payload
            };
        default:
            return state;
    }
}