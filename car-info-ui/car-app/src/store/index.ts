import { configureStore } from '@reduxjs/toolkit'
import { cartReducer } from './cart-slice'
import uiSlice from './ui-slice'
import { accountReducer } from './account-slice'

const store = configureStore({
    reducer: {
        cart: cartReducer,
        ui: uiSlice.reducer,
        account: accountReducer,
    },
})

export default store

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch
