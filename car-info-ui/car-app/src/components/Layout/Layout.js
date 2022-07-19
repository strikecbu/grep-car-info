import { Fragment } from 'react';
import MainHeader from './MainHeader';
import Header from "./Header";

const Layout = (props) => {
  return (
    <Fragment>
      {/*<Header />*/}
      {/*<MainHeader />*/}
      <main>{props.children}</main>
    </Fragment>
  );
};

export default Layout;
