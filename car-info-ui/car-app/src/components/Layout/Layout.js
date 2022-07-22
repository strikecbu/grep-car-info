import { Fragment } from 'react'
import Header from './Header'
import { Footer } from '../UI/Footer'

const Layout = (props) => {
    return (
        <Fragment>
            <Header />
            {/*<MainHeader />*/}
            <main>{props.children}</main>
            <Footer />
        </Fragment>
    )
}

export default Layout
