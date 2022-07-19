import React, { Fragment, useEffect, useState } from 'react'
import Layout from '../Layout/Layout'
import Products from '../Shop/Products'
import Cars from '../Shop/Cars'
import PriceLine, { Price } from '../Shop/PriceLine'
import { Car } from '../Shop/CarItem'
import Environment from '../../env/Environment'

const CarsPage: React.FC = () => {
    const [cars, setCars] = useState<Car[]>([])

    const [showPrice, setShowPrice] = useState(false)
    const [detailUrl, setDetailUrl] = useState('')
    const [prices, setPrices] = useState<Price[]>([])

    useEffect(() => {
        fetchData()
    }, [])

    const fetchData = async () => {
        const response: Response = await fetch(Environment.fetchCarUrl, {
            headers: {},
        })
        const cars: Car[] = (await response.json()) as Car[]
        setCars(cars)
    }

    const closePriceLine = () => {
        setShowPrice(false)
    }

    const openPriceLine = (id: string) => {
        const index = cars.findIndex((car) => car.id === id)
        if (index !== -1) {
            const foundCar: Car = cars[index]
            setDetailUrl(foundCar.detailUrl)
            const prices = foundCar.prices.map((p) => {
                const price: Price = {
                    price: p.price,
                    date: p.createTime,
                }
                return price
            })
            setPrices(prices)
            setShowPrice(true)
        }
    }

    return (
        <Fragment>
            <Layout>
                <Cars cars={cars} openPriceLine={openPriceLine} />
                <Products />
                {showPrice && (
                    <PriceLine
                        detailUrl={detailUrl}
                        prices={prices}
                        closeFn={closePriceLine}
                    />
                )}
            </Layout>
        </Fragment>
    )
}

export default CarsPage
