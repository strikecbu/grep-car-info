import CarItem, { Car } from './CarItem'
import React from 'react'

export type Props = {
    cars: Car[]
    openPriceLine: Function
}

const Cars: React.FC<Props> = ({ cars, openPriceLine }) => {
    return (
        <section className="mt-12 mx-auto px-4 max-w-screen-xl lg:px-8">
            <div className="text-center">
                <h1 className="text-3xl text-gray-800 font-semibold">Blog</h1>
                <p className="mt-3 text-gray-500">
                    Blogs that are loved by the community. Updated every hour.
                </p>
            </div>
            <div className="mt-12 grid gap-2 sm:grid-cols-2 lg:grid-cols-3">
                {cars.map((items, key) => (
                    <CarItem
                        key={items.id}
                        keyId={items.id}
                        img={items.imageUrl}
                        title={items.title}
                        description={items.description}
                        brand={items.brand}
                        updateTime={items.updateTime}
                        price={items.latestPrice.price.toString()}
                        openPriceLine={openPriceLine}
                    />
                ))}
            </div>
        </section>
    )
}

export default Cars
