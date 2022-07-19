import React, { Fragment } from 'react'
import Layout from '../Layout/Layout'
import Products from '../Shop/Products'
import Cars from '../Shop/Cars'

const CarsPage: React.FC = () => {
    return (
        <Fragment>
            <Layout>
                <Cars />
                <Products />
            </Layout>
        </Fragment>
    )
}

export default CarsPage
