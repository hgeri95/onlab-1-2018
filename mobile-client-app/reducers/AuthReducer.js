import Constants from "../constants/Constants";

export const initialState = {
    isAuthenticated: false,
    username: null,
    refreshToken: null,
    role: null,
    errorMessage: null
};

export const AuthReducer = (state, action) => {
  switch (action.type) {
      case Constants.LOGIN:
          return {
            ...state,
            isAuthenticated: true,
            username: action.payload.username,
            refreshToken: action.payload.refreshToken,
            role: action.payload.role
          };
      case Constants.LOGOUT:
          return {
              ...state,
              isAuthenticated: false,
              username: null,
              refreshToken: null,
              role: null
          };
      case Constants.ERROR:
          return {
              ...state,
              errorMessage: action.payload
          };
      default:
          return state;
  }
};
