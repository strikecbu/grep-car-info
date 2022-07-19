import ProductItem from './ProductItem';
import classes from './Products.module.css';
import { useDispatch } from 'react-redux';
import { cartActions } from '../../store/cart-slice';

const DUMMY_PRODUCTS = [
  {
    id: 1,
    title: 'Test',
    price: 6,
    description: 'This is a first product - amazing!'
  },
  {
    id: 2,
    title: 'Test2',
    price: 16,
    description: 'This is a second product - amazing!'
  },
  {
    id: 3,
    title: 'Test3',
    price: 25,
    description: 'This is a good~~ product - amazing!'
  },
  {
    id: 4,
    title: 'Test4',
    price: 99.9,
    description: 'This is wow second product - amazing!'
  },
];

const Products = (props) => {

  const dispatch = useDispatch();

  const addCartHandler = (id) => {
    const item = DUMMY_PRODUCTS.find(item => item.id === id);
    dispatch(cartActions.addItemToCart(item));
  }

  console.log('products init')
  return (
    <section className={classes.products}>
      <h2>Buy your favorite products</h2>
      <ul>
        {
          DUMMY_PRODUCTS.map(item => {
            return (
              <ProductItem
                key={item.id}
                id={item.id}
                title={item.title}
                price={item.price}
                description={item.description}
                addCart={addCartHandler}
              />
            );
          })
        }
      </ul>
    </section>
  );
};

export default Products;
