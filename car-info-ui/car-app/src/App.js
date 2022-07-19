import Cart from './components/Cart/Cart';
import Layout from './components/Layout/Layout';
import Products from './components/Shop/Products';
import {useDispatch, useSelector} from 'react-redux';
import {Fragment, useEffect} from 'react';
import {fetchCartData, sendCartData} from './store/cart-slice'
import Notification from './components/UI/Notification';
import Example from "./components/UI/HomePage";
import HomePage from "./components/page/HomePage";
import FloatCard from "./components/UI/FloatCard";
import FeatureBox from "./components/UI/FeatureBox";
import {Footer} from "./components/UI/Footer";
import Alert from "./components/UI/Alert";
import ClosingAlert from "./components/UI/Alert";
import BannerNews from "./components/UI/BannerNews";
import Stats from "./components/UI/Stats";

let isInitial = true;

function App() {

    const dispatch = useDispatch();
    const isShowCart = useSelector(state => state.ui.isShowCart);
    const cart = useSelector(state => state.cart);
    const notification = useSelector(state => state.ui.notification);

    useEffect(() => {
        dispatch(fetchCartData());
    }, [dispatch]);

    useEffect(() => {
        if (isInitial) {
            isInitial = false;
            return;
        }
        if (cart.change) {
            dispatch(sendCartData(cart));
        }

    }, [cart, dispatch])

    console.log('App init')
    return (
        <Fragment>
            <BannerNews />
            {/*{notification &&*/}
            {/*    <Notification status={notification.status} title={notification.title} message={notification.message}/>}*/}
            <Layout>
                {isShowCart && <Cart/>}
                <Example/>
                {/*<FloatCard/>*/}
                <Stats />
                {/*<FeatureBox/>*/}
                {/*<Products/>*/}

                {/*<ClosingAlert />*/}
                <Footer />
            </Layout>
        </Fragment>
    );
}

export default App;
