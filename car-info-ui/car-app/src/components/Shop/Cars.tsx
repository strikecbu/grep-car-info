import CarItem, { Car } from './CarItem'
import React, { Fragment, useRef } from 'react'
import { Parallax, ParallaxLayer } from '@react-spring/parallax'
import Environment from '../../env/Environment'

export type Props = {
    cars: Car[]
    openPriceLine: Function
}

const Cars: React.FC<Props> = ({ cars, openPriceLine }) => {
    const ref = useRef<any>()

    return (
        <Fragment>
            <ParallaxLayer offset={0.2} speed={1}>
                <div className="text-center">
                    <h1 className="text-3xl text-gray-300 font-semibold">
                        小施汽車
                    </h1>
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
                        if (key < 30 || !Environment.showCarPic) {
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
                        }
                    })}
                </div>
            </ParallaxLayer>
        </Fragment>
    )
}

export default Cars
