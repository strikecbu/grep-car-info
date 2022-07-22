import React, { Fragment, useEffect, useRef, useState } from 'react'
import { Parallax, ParallaxLayer } from '@react-spring/parallax'
import { animated, useSpring } from 'react-spring'
import { isMobile } from 'react-device-detect'

import moon from '../../images/moon.webp'
import land from '../../images/land.webp'

import PriceLine, { Price } from '../Shop/PriceLine'
import { useSelector } from 'react-redux'
import Cars from '../Shop/Cars'
import { Car } from '../Shop/CarItem'
import Environment from '../../env/Environment'
import { Footer } from '../UI/Footer'
import Header from '../Layout/Header'
import { RootState } from '../../store'
import { useAnnounce } from '../../hooks/announceHook'

type parallaxSettings = {
    pages: number
    footerOffset: number
    picOffset: number
}

const CarsPage: React.FC = () => {
    const ref = useRef<any>()
    const [cars, setCars] = useState<Car[]>([])

    const [showPrice, setShowPrice] = useState(false)
    const [detailUrl, setDetailUrl] = useState('')
    const [prices, setPrices] = useState<Price[]>([])
    const [state, setMessage] = useAnnounce()
    const [scraping, setScrapeStat] = useState(false)
    const token = useSelector((state: RootState) => state.account.token)
    const [settings, setSettings] = useState<parallaxSettings>({
        pages: 3,
        picOffset: 1.6,
        footerOffset: 2.5,
    })

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
        fetchData()
        api.pause()
    }, [])

    useEffect(() => {
        if (state.message === '拉取新資料中，請等候通知...') {
            setScrapeStat(true)
        } else {
            api.pause()
            setScrapeStat(false)
            fetchData()
        }
    }, [state])

    const fetchData = async () => {
        const response: Response = await fetch(Environment.fetchCarUrl, {
            headers: new Headers({
                Authorization: `Bearer ${token}`,
            }),
            method: 'GET',
        })
        const cars: Car[] = (await response.json()) as Car[]
        const sortCars = cars.sort((a: Car, b: Car) => {
            if (a.brand === b.brand) {
                return 0
            }
            return a.brand > b.brand ? 1 : -1
        })
        setCars(sortCars)
        const newSettings = calcParallax(sortCars)
        console.log(`new settings: `, newSettings)
        setSettings(newSettings)
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

    async function refreshListHandler() {
        if (scraping) {
            return
        }
        try {
            const data = {
                vendor: 'SHOU_SHI',
            }
            api.resume()
            const response: Response = await fetch(Environment.reScrapeUrl, {
                headers: new Headers({
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }),
                body: JSON.stringify(data),
                method: 'POST',
            })
            if (response.status !== 202) {
                setMessage('呼叫錯誤，請稍後再試')
            } else {
                setMessage('拉取新資料中，請等候通知...')
            }
        } catch (e: any) {
            setMessage('呼叫錯誤，請稍後再試')
            api.pause()
        }
    }

    const calcParallax = (cars: Car[]): parallaxSettings => {
        const pages = (cars.length + 1) / 3 + 2
        const picOffset = pages - 3
        const footerOffset = pages - 0.5

        return {
            pages,
            picOffset,
            footerOffset,
        }
    }

    console.log(`isMobile: ${isMobile}`)
    if (isMobile) {
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

    return (
        <Fragment>
            <div className="bg-black">
                <Parallax className="bg-black" pages={settings.pages} ref={ref}>
                    <ParallaxLayer
                        style={{ height: 0, zIndex: 0 }}
                        sticky={{ start: 0, end: 2 }}
                        factor={0.2}
                    >
                        {/*<ParallaxLayer offset={0} speed={1}>*/}
                        <Header />
                        <div className="hidden md:flex justify-end mr-14 mt-14">
                            <div
                                className={btnStyle}
                                onClick={refreshListHandler}
                            >
                                <animated.div style={styles}>
                                    <img
                                        className="max-w-[3rem]"
                                        alt=""
                                        src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAABmJLR0QA/wD/AP+gvaeTAAAEvElEQVR4nO2b34sWVRjHv8cfrW1Bq1aCkYVZkoHUSpvJikQpgWJpJRTirSVWeBFedJdmRaV0EWF/wEJedFEkRKGVJoW5C11EmC2ubdZqS0G5ybrrp4vzLr6s85x3Zt4zM2vu5/LMnOf7fc7Me+b8eqVJqgXYDLiqfVQGnk+BW6r2Uglc4k9gY9V+SofL2QfMrtpXaSQ0AMBvwJoy9EvpfIC7JXVKWiJpgaTbJc2SdL2k6VY1Se9Jesk5N1SGz6gA7cDbwC/GU07LcWBp1fmkAnDA48ChJpMezwVgJ2C9LbmZFisQ0CFpj6RlsWJeEQAzgNeBkchPfYxeYLmhPbXsfMcbmA98X1DiF4F3gVZDewlwDJhfdt5jBjqBsymTGQW+Bl4F1gH3ADOBacb9/cCqgLYDjtTuHQTK/dkBDwPnUiR+CthOYJibUKcLmNlAf+O4Ov8AK+Nnmiy+HBhqkPgAsIUUvXZdnT+ADSnubwX6EjSHgM44Wdrid9D4te8iw1C2Vmc/MDfl/TsC2oMU1Sfge/tQhzcMbMkRN9N0GNiK71MsuoEZWX2kEd4TED0PPBZd1PaynuSf4Zlag8b9PAIdgVYfBh6NKpjO0xoujT2Ggb3AjUUIOfwnzGJzdNH03rYBnwALixRZG0i+qzDhiQJw2Eh+kAm8cAG0AVubDdIeePrPRvIaFWAKsAn4vebz3maC7TaSPwVcE9F3FIAVQM84r7vzBnO1RJPYHtl70wBPG1578wZcaAQcIeWorUzwo1SLRVa9KYGYK4zyo865083ZjY9z7mdJx4zLD1r1Qg1wn1H+eVpTFfCFUX6/VSHUAHcZ5T2p7ZSP5W2BVSG0JrhX0gcJ5YeyOCqZw5LeTygfKNvIJJNMcmVgrsQAr0iak3Bpl3OurzhL+QFukrQu4dJfzrl9WYMdMEZVz0RxWwDAU4bnz6w6oXHAT0Z5/tlV8bQb5cetCqEGOGqUP5TaTvk8YpR3Z44ELDJep4tUtR0VAJiHvW5pjgTNN8A594Okk0mXJD0QwXNsNik5n5POuRO5InL5UngPYM0SKwNoAX41nv5rzQReXAsyCLxI1dvRBsDzRvIAi2MEb4vkNTrAbPy+YhIHqvZXOMCHgae/ukjhO4GPgW2FiTT28EIg+S+LEm3DH4k5XxMaBdYXIhb2sRb7WM4IYK1o5Racit94PJMg+G+ZjQCsrmla7CpC9Fr81rPFKBl2YvDL7pn3FfFfowsBH90UtWeBPxQ1GBDfkTLOXPyhCDJo30y4wwO/G3Rb/gzTGekA/k4Q7weuS1F/A3WfrRT3t+B3gUMND/6cUDknSoFVNcF6gkfd8afCusa7Dtw/D3gZON0g8bHkyx2hAsvqnsoRAsdcag3Wb5ifDszCjzqfAN4AvsVPutIwQNnH5OoSuxX4Bn9MNul6K/6wY9pkstJN0b/5RmDMD/B9xY8FJT4KvAO0lJ1vQ/Cv9E7Cn6pmOAhYKz+ZiXZavAQOSnrLObe/aiOpAJbi/+zQDL34TrG5KW2AQv8ygz/p/aak5wJaw5LOSTorqU/SCUnfSfoq90rORANYifHXmaq9lQZwA/4w49XZAGMAT5JhKPy/BJgDfHTVNoCUfzo8SWT+A1hJFpSyuEEDAAAAAElFTkSuQmCC"
                                    />
                                </animated.div>
                            </div>
                        </div>
                    </ParallaxLayer>
                    <ParallaxLayer
                        offset={0.1}
                        speed={1.2}
                        factor={2.5}
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
                        offset={settings.picOffset}
                        speed={1.2}
                        factor={3}
                        style={{
                            backgroundImage: `url(${land})`,
                            backgroundSize: 'cover',
                        }}
                    ></ParallaxLayer>
                    <Cars cars={cars} openPriceLine={openPriceLine} />
                    {/*<ParallaxLayer sticky={{ start: 4.5, end: 5 }}>*/}
                    <ParallaxLayer
                        offset={settings.footerOffset}
                        speed={1}
                        factor={2}
                    >
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
