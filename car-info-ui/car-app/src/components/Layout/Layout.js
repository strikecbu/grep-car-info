import { Fragment } from 'react'
import Header from './Header'
import BannerNews from '../UI/BannerNews'
import { Footer } from '../UI/Footer'

const Layout = (props) => {
    return (
        <Fragment>
            <BannerNews announceWords={'Welcome to brand new website!'} />
            <Header />
            {/*<MainHeader />*/}
            <main>{props.children}</main>
            <Footer />
        </Fragment>
    )
}

export default Layout
