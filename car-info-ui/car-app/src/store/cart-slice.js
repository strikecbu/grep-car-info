import { createSlice } from '@reduxjs/toolkit';
import { uiActions } from './ui-slice';


/**
 *
 * 
 * [
 *  {
 *    id
 *    title
 *    price
 *    totalPrice
 *    quantity
 *  }
 * ]
 * 
 */

const cartSlice = createSlice({
    name: 'cart',
    initialState: {
        items: [],
        totalQuantity: 0,
        change: false
    },
    reducers: {
        replaceCart(state, action) {
            state.items = action.payload.items;
            state.totalQuantity = action.payload.totalQuantity
        },
        addItemToCart(state, action) {
            const newItem = action.payload;
            const existItem = state.items.find(item => item.id === newItem.id);
            state.totalQuantity++;
            state.change = true;
            if (!existItem) {
                state.items.push({
                    ...newItem,
                    totalPrice: newItem.price,
                    quantity: 1
                });
            } else {
                existItem.totalPrice += existItem.price;
                existItem.quantity++;
            }
        },
        removeItemFromCart(state, action) {
            const productId = action.payload;
            const existItem = state.items.find(item => item.id === productId);
            state.totalQuantity--;
            state.change = true;
            if (existItem.quantity === 1) {
                state.items = state.items.filter(item => item.id !== productId);
            } else {
                existItem.quantity--;
                existItem.totalPrice -= existItem.price;
            }
        },
        // addCart(state, action) {
        //     const product = action.payload;
        //     const items = state.items;
        //     const itemIds = state.itemIds;
        //     if (!itemIds.includes(product.id)) {
        //         itemIds.push(product.id);
        //         items.push({ count: 1, product: product })
        //     } else {
        //         for (let item of items) {
        //             if (product.id === item.product.id) {
        //                 item.count++;
        //                 break;
        //             }
        //         }
        //     }
        // },
        // increaseQuality(state, action) {
        //     const items = state.items;
        //     const item = items.find(item => item.product.id === action.payload);
        //     item.count++;
        // },
        // decreaseQuality(state, action) {
        //     const items = state.items;
        //     const id = action.payload;

        //     for (let i = 0; i < items.length; i++) {
        //         const item = items[i];
        //         if (item.product.id !== id)
        //             continue;
        //         item.count--;
        //         if (item.count === 0) {
        //             items.splice(i, 1);
        //             const itemIds = state.itemIds;
        //             const index = itemIds.findIndex(productId => id === productId);
        //             itemIds.splice(index, 1);
        //         }
        //     }
        // }

    }
});

export const sendCartData = (cartData) => {
    return async (dispatch) => {
        const updateCart = async () => {
            dispatch(uiActions.notification({
                status: 'pending',
                title: 'Pending',
                message: 'Sending cart data...'
            }));

            let transCartData = { ...cartData }
            delete transCartData.change;
            const response = await fetch('https://react-http-68cbe-default-rtdb.asia-southeast1.firebasedatabase.app/cart.json',
                {
                    method: 'PUT',
                    body: JSON.stringify(transCartData),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

            if (!response.ok) {
                throw new Error('Send cart data failed.');
            }
        };

        try {
            await updateCart();
            dispatch(uiActions.notification({
                status: 'success',
                title: 'Finished',
                message: 'Send cart data successed.'
            }));
        } catch (error) {
            dispatch(uiActions.notification({
                status: 'error',
                title: 'Error',
                message: error.message
            }));
        }
    }
}

export const fetchCartData = () => {
    return async (dispatch) => {
        dispatch(uiActions.notification({
            status: 'pending',
            title: 'Pending',
            message: 'Getting cart data...'
        }));
        const loadCartData = async () => {
            const response = await fetch('https://react-http-68cbe-default-rtdb.asia-southeast1.firebasedatabase.app/cart.json');
            if (!response.ok) {
                throw new Error('Fetch data failed.');
            }
            const cartData = await response.json();
            if (cartData) {
                return cartData;
            } else {
                return {
                    items: [],
                    totalQuantity: 0
                }
            }
        }
        try {
            const payload = await loadCartData();
            console.log(payload)
            dispatch(cartActions.replaceCart(payload));
            dispatch(uiActions.notification({
                status: 'success',
                title: 'Success',
                message: 'Got cart data successed.'
            }));
        } catch (error) {
            dispatch(uiActions.notification({
                status: 'error',
                title: 'Error',
                message: error.message
            }));
        }
    }
}

export const cartActions = cartSlice.actions;

export const cartReducer = cartSlice.reducer;