import { createSlice } from "@reduxjs/toolkit";


const authSlice = createSlice({
    name: "auth", 
    initialState: {
        user: JSON.parse(localStorage.getItem("userAuth")) || null,
        token: localStorage.getItem("token") || null,
    },
    reducers: {
        authentication: (state, action) => {
            state.token = action.payload.token
            localStorage.setItem('token', action.payload.token)
        },
        login: (state, action) => {
            state.user = action.payload.user;
            localStorage.setItem('userAuth', JSON.stringify(action.payload.user))
            
        }, 
        logout: (state) => {
            state.user = null
            state.token = null
            localStorage.removeItem('userAuth')
            localStorage.removeItem('token')
        },
    },
});

export const {login, logout, authentication} = authSlice.actions;
export default authSlice.reducer;

