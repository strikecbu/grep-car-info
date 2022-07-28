import CarItem, { Car } from './CarItem'
import React, { Fragment } from 'react'
import { ParallaxLayer } from '@react-spring/parallax'
import { isMobile } from 'react-device-detect'
import { animated, useSpring } from 'react-spring'

export type Props = {
    cars: Car[]
    openPriceLine: Function
}

const Cars: React.FC<Props> = ({ cars, openPriceLine }) => {
    const props = useSpring({
        to: { opacity: 1 },
        from: { opacity: 0 },
        delay: 400,
    })

    if (isMobile) {
        return (
            <Fragment>
                <div className="text-center my-10">
                    <animated.h1
                        style={props}
                        className="text-3xl text-gray-300 font-semibold"
                    >
                        小施汽車
                    </animated.h1>
                    <p className="mt-3 text-gray-500">
                        All data comes from sscar website and all rights
                        belongs.
                    </p>
                </div>
                <div className="grid gap-2 sm:grid-cols-2 lg:grid-cols-3 container mx-auto mb-10">
                    {cars.map((items, key) => {
                        return (
                            <CarItem
                                key={items.id}
                                keyId={items.id}
                                img={items.imageUrl}
                                title={items.title}
                                description={items.description}
                                brand={items.brand}
                                updateTime={items.latestPrice.createTime}
                                price={items.latestPrice.price.toString()}
                                openPriceLine={openPriceLine}
                            />
                        )
                    })}
                </div>
            </Fragment>
        )
    }
    return (
        <Fragment>
            <ParallaxLayer offset={0.2} speed={1}>
                <div className="text-center">
                    <animated.h1
                        style={props}
                        className="text-3xl text-gray-300 font-semibold"
                    >
                        小施汽車
                    </animated.h1>
                    <p className="mt-3 text-gray-500">
                        All data comes from sscar website and all rights
                        belongs.
                    </p>
                </div>
            </ParallaxLayer>

            <ParallaxLayer
                offset={0.3}
                speed={0.6}
                factor={1}
                className={'z-50'}
            >
                <div className="grid gap-2 sm:grid-cols-2 lg:grid-cols-3 container mx-auto">
                    {cars.map((items, key) => {
                        return (
                            <CarItem
                                key={items.id}
                                keyId={items.id}
                                img={items.imageUrl}
                                title={items.title}
                                description={items.description}
                                brand={items.brand}
                                updateTime={items.latestPrice.createTime}
                                price={items.latestPrice.price.toString()}
                                openPriceLine={openPriceLine}
                            />
                        )
                    })}
                </div>
            </ParallaxLayer>
        </Fragment>
    )
}

export default Cars
