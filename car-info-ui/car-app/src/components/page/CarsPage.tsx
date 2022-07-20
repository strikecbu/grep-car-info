import React, { Fragment, useEffect, useRef, useState } from 'react'
import Cars from '../Shop/Cars'
import PriceLine, { Price } from '../Shop/PriceLine'
import { Car } from '../Shop/CarItem'
import Environment from '../../env/Environment'
import { Parallax, ParallaxLayer } from '@react-spring/parallax'
import { Footer } from '../UI/Footer'
import BannerNews from '../UI/BannerNews'
import Header from '../Layout/Header'
import moon from '../../images/moon.webp'
import land from '../../images/land.webp'
import { useSelector } from 'react-redux'
import { RootState } from '../../store'
import { useLocation } from 'react-router-dom'

const CarsPage: React.FC = () => {
    const ref = useRef<any>()
    const [cars, setCars] = useState<Car[]>([])

    const [showPrice, setShowPrice] = useState(false)
    const [detailUrl, setDetailUrl] = useState('')
    const [prices, setPrices] = useState<Price[]>([])

    const token = useSelector((state: RootState) => state.account.token)

    useEffect(() => {
        fetchData()
    }, [])

    const fetchData = async () => {
        const response: Response = await fetch(Environment.fetchCarUrl, {
            headers: new Headers({
                Authorization: `Bearer ${token}`,
            }),
            method: 'GET',
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
            <div className="bg-black">
                <Parallax className="bg-black" pages={3} ref={ref}>
                    <ParallaxLayer
                        style={{ height: 0, zIndex: 0 }}
                        sticky={{ start: 0, end: 4 }}
                        factor={0.2}
                    >
                        {/*<ParallaxLayer offset={0} speed={1}>*/}
                        <BannerNews
                            announceWords={'Welcome to brand new website!'}
                        />
                        <Header />
                    </ParallaxLayer>
                    <ParallaxLayer
                        offset={0.2}
                        speed={1.2}
                        factor={2}
                        style={{
                            backgroundImage: `url(${moon})`,
                            backgroundSize: 'cover',
                        }}
                    />
                    {/*<ParallaxLayer*/}
                    {/*    offset={1.2}*/}
                    {/*    speed={1.2}*/}
                    {/*    factor={3}*/}
                    {/*    style={{*/}
                    {/*        backgroundImage: `url(${middle})`,*/}
                    {/*        backgroundSize: 'cover',*/}
                    {/*    }}*/}
                    {/*></ParallaxLayer>*/}
                    <ParallaxLayer
                        offset={1.6}
                        speed={1.2}
                        factor={3}
                        style={{
                            backgroundImage: `url(${land})`,
                            backgroundSize: 'cover',
                        }}
                    ></ParallaxLayer>
                    <Cars cars={cars} openPriceLine={openPriceLine} />
                    {/*<ParallaxLayer sticky={{ start: 4.5, end: 5 }}>*/}
                    <ParallaxLayer offset={2.5} speed={1} factor={2}>
                        <Footer />
                    </ParallaxLayer>
                </Parallax>
            </div>
            {showPrice && (
                <PriceLine
                    detailUrl={detailUrl}
                    prices={prices}
                    closeFn={closePriceLine}
                />
            )}
        </Fragment>
    )
}

export default CarsPage
