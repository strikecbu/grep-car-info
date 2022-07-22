import { Fragment } from 'react'
import HomePageBanner from '../UI/HomePageBanner'
import Stats from '../UI/Stats'
import { Footer } from '../UI/Footer'
import Header from '../Layout/Header'

function HomePage() {
    return (
        <Fragment>
            <Header />
            <HomePageBanner />
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
