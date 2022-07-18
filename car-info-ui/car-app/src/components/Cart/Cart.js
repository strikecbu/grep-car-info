import Card from '../UI/Card';
import classes from './Cart.module.css';
import CartItem from './CartItem';
import { useSelector, useDispatch } from 'react-redux';
import { cartActions } from '../../store/cart-slice';

const Cart = (props) => {

  const dispatch = useDispatch();
  const items = useSelector(state => state.cart.items);

  const decreaseQualityHandler = (id) => {
    dispatch(cartActions.removeItemFromCart(id));
  }

  const increaseQualityHandler = (id) => {
    const item = items.find(item => item.id === id)
    dispatch(cartActions.addItemToCart(item));
  }

  return (
    <Card className={classes.cart}>
      <h2>Your Shopping Cart</h2>
      <ul>
        {
          items.map(item => {

            return (<CartItem
              key={item.id}
              item={{ id: item.id, title: item.title, quantity: item.quantity, total: item.totalPrice, price: item.price }}
              increaseItem={increaseQualityHandler}
              decreaseItem={decreaseQualityHandler}
            />);
          })
        }
        
      </ul>
    </Card>
  );
};

export default Cart;
