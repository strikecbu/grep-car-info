import { Fragment } from 'react'
import HomePageHeader from '../UI/HomePageHeader'
import Stats from '../UI/Stats'
import { Footer } from '../UI/Footer'
import BannerNews from '../UI/BannerNews'
import Header from '../Layout/Header'

function HomePage() {
    return (
        <Fragment>
            <BannerNews announceWords={'Welcome to brand new website!'} />
            <Header />
            <HomePageHeader />
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
