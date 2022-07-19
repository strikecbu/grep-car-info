import React from 'react'
import classes from './CarItem.module.css'

export type Props = {
    keyId: string
    img: string
    title: string
    description: string
    brand: string
    updateTime: string
    price: string
}

const CarItem: React.FC<Props> = ({
    keyId,
    img,
    title,
    description,
    brand,
    updateTime,
    price,
}) => {
    const sss = 'adf'
    return (
        <article
            className="max-w-md mx-auto mt-4 shadow-lg border rounded-md duration-300 hover:shadow-sm bg-white"
            key={keyId}
        >
            <a href="#">
                <img
                    src={img}
                    loading="lazy"
                    alt={title}
                    className="w-full h-48 rounded-t-md object-fill"
                />
                <div className="flex justify-between items-center mt-2 mr-4">
                    <div className="flex items-center ml-4 pt-2 mr-2">
                        <div className="flex-none w-10 h-10 rounded-full">
                            <img
                                src={img}
                                className="w-full h-full rounded-full"
                                alt={brand}
                            />
                        </div>
                        <div className="ml-3">
                            <span className="block text-gray-900">{brand}</span>
                            <span className="block text-gray-400 text-sm">
                                {updateTime}
                            </span>
                        </div>
                    </div>
                    <div className={classes.price}>${price}</div>
                </div>
                <div className="pt-3 ml-4 mr-2 mb-3">
                    <h3 className="text-xl text-gray-900">{title}</h3>
                    <p className="text-gray-400 text-sm mt-1">{description}</p>
                </div>
            </a>
        </article>
    )
}

export default CarItem
