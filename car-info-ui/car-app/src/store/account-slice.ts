import { createSlice } from '@reduxjs/toolkit'

type accountType = {
    token: string
}

const initialState: accountType = {
    token: '',
}

const accountSlice = createSlice({
    name: 'account',
    initialState,
    reducers: {
        addToken(status, action) {
            status.token = action.payload
        },
        removeToken(status) {
            status.token = ''
        },
    },
})

export const AccountActions = accountSlice.actions
export const accountReducer = accountSlice.reducer
