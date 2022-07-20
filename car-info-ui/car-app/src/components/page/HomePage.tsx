import { Fragment } from 'react'
import Example from '../UI/HomePage'
import Stats from '../UI/Stats'
import { Footer } from '../UI/Footer'
import BannerNews from '../UI/BannerNews'

function HomePage() {
    return (
        <Fragment>
            <BannerNews announceWords={'Welcome to brand new website!'} />
            <Example />
            {/*<Cars/>*/}
            <Stats />
            {/*<FeatureBox/>*/}
            {/*<Products/>*/}

            {/*<ClosingAlert />*/}
            <Footer />
        </Fragment>
    )
}

export default HomePage
