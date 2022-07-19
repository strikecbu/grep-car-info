import React from 'react'
import classes from './CarItem.module.css'
import Environment from '../../env/Environment'

export type Props = {
    keyId: string
    img: string
    title: string
    description: string
    brand: string
    updateTime: string
    price: string
    openPriceLine: Function
}

export type Price = {
    createTime: string
    id: string
    price: number
}

export type Car = {
    id: string
    title: string
    latestPrice: Price
    prices: Price[]
    description: string
    detailUrl: string
    imageUrl: string
    year: number
    brand: string
    updateTime: string
}

const CarItem: React.FC<Props> = ({
    keyId,
    img,
    title,
    description,
    brand,
    updateTime,
    price,
    openPriceLine,
}) => {
    function openPriceHandle() {
        openPriceLine(keyId)
    }

    return (
        <article
            className="max-w-md mx-auto mt-4 shadow-lg border rounded-md duration-300 hover:shadow-sm bg-white"
            key={keyId}
        >
            <div>
                {Environment.showCarPic && (
                    <img
                        src={img}
                        loading="lazy"
                        alt={title}
                        className="w-full h-48 rounded-t-md object-fill"
                    />
                )}
                <div className="flex justify-between items-center mt-2 mr-4">
                    <div className="flex items-center ml-4 pt-2 mr-2">
                        <div className="flex-none w-10 h-10 rounded-full">
                            {Environment.showCarPic && (
                                <img
                                    src={img}
                                    className="w-full h-full rounded-full"
                                    alt={brand}
                                />
                            )}
                            {!Environment.showCarPic && (
                                <img
                                    className="w-full h-full rounded-full"
                                    alt={brand}
                                    src="data:image/png;base64,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"
                                />
                            )}
                        </div>
                        <div className="ml-3">
                            <span className="block text-gray-900">{brand}</span>
                            <span className="block text-gray-400 text-sm">
                                {updateTime}
                            </span>
                        </div>
                    </div>
                    <div
                        className={classes.price + ' cursor-pointer'}
                        onClick={openPriceHandle}
                    >
                        ${price}
                    </div>
                </div>
                <div className="pt-3 ml-4 mr-2 mb-3">
                    <h3 className="text-xl text-gray-900">{title}</h3>
                    <p className="text-gray-400 text-sm mt-1">{description}</p>
                </div>
            </div>
        </article>
    )
}

export default CarItem
