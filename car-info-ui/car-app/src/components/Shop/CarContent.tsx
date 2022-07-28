import React, { Fragment, useEffect } from 'react'
import Header from '../Layout/Header'
import { animated, useSpring } from 'react-spring'
import Cars from './Cars'
import { Footer } from '../UI/Footer'
import PriceLine, { Price } from './PriceLine'
import { Car } from './CarItem'

type inputType = {
    scraping: boolean
    refreshListHandler: any
    cars: Car[]
    showPrice: boolean
    detailUrl: string
    prices: Price[]
    closePriceLine: Function
    openPriceLine: Function
}

export const CarContent: React.FC<inputType> = ({
    scraping,
    refreshListHandler,
    cars,
    showPrice,
    detailUrl,
    prices,
    closePriceLine,
    openPriceLine,
}) => {
    const btnStyle = scraping
        ? 'flex justify-center items-center w-16 h-16 bg-gray-400 rounded-lg opacity-70 cursor-progress'
        : 'flex justify-center items-center w-16 h-16 bg-gray-400 rounded-lg opacity-70 hover:scale-110 duration-300 cursor-pointer'

    const [styles, api] = useSpring(() => ({
        loop: true,
        from: { rotateZ: 0 },
        to: { rotateZ: 180 },
        config: { duration: 200 },
    }))

    useEffect(() => {
        if (scraping) {
            api.resume()
        } else {
            api.pause()
        }
    }, [scraping])

    return (
        <Fragment>
            <Header />
            <div className="flex justify-end fixed top-1/4 right-5">
                <div className={btnStyle} onClick={refreshListHandler}>
                    <animated.div style={styles}>
                        <img
                            className="max-w-[3rem]"
                            alt=""
                            src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAABmJLR0QA/wD/AP+gvaeTAAAEvElEQVR4nO2b34sWVRjHv8cfrW1Bq1aCkYVZkoHUSpvJikQpgWJpJRTirSVWeBFedJdmRaV0EWF/wEJedFEkRKGVJoW5C11EmC2ubdZqS0G5ybrrp4vzLr6s85x3Zt4zM2vu5/LMnOf7fc7Me+b8eqVJqgXYDLiqfVQGnk+BW6r2Uglc4k9gY9V+SofL2QfMrtpXaSQ0AMBvwJoy9EvpfIC7JXVKWiJpgaTbJc2SdL2k6VY1Se9Jesk5N1SGz6gA7cDbwC/GU07LcWBp1fmkAnDA48ChJpMezwVgJ2C9LbmZFisQ0CFpj6RlsWJeEQAzgNeBkchPfYxeYLmhPbXsfMcbmA98X1DiF4F3gVZDewlwDJhfdt5jBjqBsymTGQW+Bl4F1gH3ADOBacb9/cCqgLYDjtTuHQTK/dkBDwPnUiR+CthOYJibUKcLmNlAf+O4Ov8AK+Nnmiy+HBhqkPgAsIUUvXZdnT+ADSnubwX6EjSHgM44Wdrid9D4te8iw1C2Vmc/MDfl/TsC2oMU1Sfge/tQhzcMbMkRN9N0GNiK71MsuoEZWX2kEd4TED0PPBZd1PaynuSf4Zlag8b9PAIdgVYfBh6NKpjO0xoujT2Ggb3AjUUIOfwnzGJzdNH03rYBnwALixRZG0i+qzDhiQJw2Eh+kAm8cAG0AVubDdIeePrPRvIaFWAKsAn4vebz3maC7TaSPwVcE9F3FIAVQM84r7vzBnO1RJPYHtl70wBPG1578wZcaAQcIeWorUzwo1SLRVa9KYGYK4zyo865083ZjY9z7mdJx4zLD1r1Qg1wn1H+eVpTFfCFUX6/VSHUAHcZ5T2p7ZSP5W2BVSG0JrhX0gcJ5YeyOCqZw5LeTygfKNvIJJNMcmVgrsQAr0iak3Bpl3OurzhL+QFukrQu4dJfzrl9WYMdMEZVz0RxWwDAU4bnz6w6oXHAT0Z5/tlV8bQb5cetCqEGOGqUP5TaTvk8YpR3Z44ELDJep4tUtR0VAJiHvW5pjgTNN8A594Okk0mXJD0QwXNsNik5n5POuRO5InL5UngPYM0SKwNoAX41nv5rzQReXAsyCLxI1dvRBsDzRvIAi2MEb4vkNTrAbPy+YhIHqvZXOMCHgae/ukjhO4GPgW2FiTT28EIg+S+LEm3DH4k5XxMaBdYXIhb2sRb7WM4IYK1o5Racit94PJMg+G+ZjQCsrmla7CpC9Fr81rPFKBl2YvDL7pn3FfFfowsBH90UtWeBPxQ1GBDfkTLOXPyhCDJo30y4wwO/G3Rb/gzTGekA/k4Q7weuS1F/A3WfrRT3t+B3gUMND/6cUDknSoFVNcF6gkfd8afCusa7Dtw/D3gZON0g8bHkyx2hAsvqnsoRAsdcag3Wb5ifDszCjzqfAN4AvsVPutIwQNnH5OoSuxX4Bn9MNul6K/6wY9pkstJN0b/5RmDMD/B9xY8FJT4KvAO0lJ1vQ/Cv9E7Cn6pmOAhYKz+ZiXZavAQOSnrLObe/aiOpAJbi/+zQDL34TrG5KW2AQv8ygz/p/aak5wJaw5LOSTorqU/SCUnfSfoq90rORANYifHXmaq9lQZwA/4w49XZAGMAT5JhKPy/BJgDfHTVNoCUfzo8SWT+A1hJFpSyuEEDAAAAAElFTkSuQmCC"
                        />
                    </animated.div>
                </div>
            </div>
            <Cars cars={cars} openPriceLine={openPriceLine} />
            <Footer />
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
