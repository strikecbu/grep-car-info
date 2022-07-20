import { Fragment } from 'react'
import Header from './Header'
import BannerNews from '../UI/BannerNews'

const Layout = (props) => {
    return (
        <Fragment>
            <BannerNews announceWords={'Welcome to brand new website!'} />
            <Header />
            {/*<MainHeader />*/}
            <main>{props.children}</main>
        </Fragment>
    )
}

export default Layout
