import { useDispatch, useSelector } from 'react-redux'
import { Fragment, useEffect } from 'react'
import { fetchCartData, sendCartData } from './store/cart-slice'
import { Outlet } from 'react-router-dom'

let isInitial = true

function App() {
    const dispatch = useDispatch()
    const isShowCart = useSelector((state) => state.ui.isShowCart)
    const cart = useSelector((state) => state.cart)
    const notification = useSelector((state) => state.ui.notification)

    useEffect(() => {
        dispatch(fetchCartData())
    }, [dispatch])

    useEffect(() => {
        if (isInitial) {
            isInitial = false
            return
        }
        if (cart.change) {
            dispatch(sendCartData(cart))
        }
    }, [cart, dispatch])

    console.log('App init')
    return (
        <Fragment>
            <Outlet />
        </Fragment>
    )
}

export default App
