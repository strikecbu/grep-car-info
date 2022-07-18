import Card from '../UI/Card';
import classes from './ProductItem.module.css';


const ProductItem = (props) => {
  const { title, price, description } = props;
  
  const addCartHandler = () => {
    props.addCart(props.id)
  }

  return (
    <li className={classes.item}>
      <Card>
        <header>
          <h3>{title}</h3>
          <div className={classes.price}>${price.toFixed(2)}</div>
        </header>
        <p>{description}</p>
        <div className={classes.actions}>
          <button onClick={addCartHandler}>Add to Cart</button>
        </div>
      </Card>
    </li>
  );
};

export default ProductItem;
