import React, { useEffect, useState } from 'react'

import { Price } from '../Shop/PriceLine'
import { useSelector } from 'react-redux'
import { Car } from '../Shop/CarItem'
import Environment from '../../env/Environment'
import { RootState } from '../../store'
import { useAnnounce } from '../../hooks/announceHook'
import { CarAniContent } from '../Shop/CarAniContent'
import { useCarPage } from '../../hooks/carPageUIhook'

type CarPage = {
    cars: Car[]
}
const CarsPage: React.FC = () => {
    const [cars, setCars] = useState<Car[]>([])
    const [pages, setPages] = useState<CarPage[]>([{ cars: [] }])
    const [nowPage, setNowPage] = useState(0)

    const [showPrice, setShowPrice] = useState(false)
    const [detailUrl, setDetailUrl] = useState('')
    const [prices, setPrices] = useState<Price[]>([])
    const [state, setMessage] = useAnnounce()
    const [scraping, setScrapeStat] = useState(false)
    const pageSettings = useCarPage()
    const token = useSelector((state: RootState) => state.account.token)

    useEffect(() => {
        fetchData()
    }, [])

    useEffect(() => {
        if (state.message === '拉取新資料中，請等候通知...') {
            setScrapeStat(true)
        } else {
            setScrapeStat(false)
            fetchData()
        }
    }, [state])

    const fetchData = async () => {
        const response: Response = await fetch(Environment.fetchCarUrl, {
            headers: new Headers({
                Authorization: `Bearer ${token}`,
            }),
        })
        const cars: Car[] = (await response.json()) as Car[]
        const sortCars = cars.sort((a: Car, b: Car) => {
            if (a.brand === b.brand) {
                return 0
            }
            return a.brand > b.brand ? 1 : -1
        })
        setCars(sortCars)
        setPages(splitPage(sortCars))
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

    function splitPage(cars: Car[]): CarPage[] {
        const result: CarPage[] = []
        const copyCars = [...cars]
        const eachPageCount = pageSettings.cardCount
        const pageNum = Math.ceil(cars.length / eachPageCount)
        for (let i = 0; i < pageNum; i++) {
            result.push({ cars: copyCars.splice(0, eachPageCount) })
        }
        return result
    }

    async function refreshListHandler() {
        if (scraping) {
            return
        }
        try {
            const data = {
                vendor: 'SHOU_SHI',
            }
            const response: Response = await fetch(Environment.reScrapeUrl, {
                headers: new Headers({
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }),
                body: JSON.stringify(data),
                method: 'POST',
            })
            if (response.status === 200) {
                setMessage('資料已為最新資料！')
            } else if (response.status !== 202) {
                setMessage('呼叫錯誤，請稍後再試')
            } else {
                setMessage('拉取新資料中，請等候通知...')
            }
        } catch (e: any) {
            setMessage('呼叫錯誤，請稍後再試')
        }
    }

    function pageSwitchHandler(type: 1 | -1) {
        switch (type) {
            case 1:
                setNowPage((prevState) => {
                    if (prevState === pages.length - 1) return prevState
                    return prevState + 1
                })
                break
            case -1:
                setNowPage((prevState) => {
                    if (prevState === 0) return prevState
                    return prevState - 1
                })
                break
        }
    }

    return (
        <CarAniContent
            scraping={scraping}
            refreshListHandler={refreshListHandler}
            cars={pages[nowPage].cars}
            showPrice={showPrice}
            detailUrl={detailUrl}
            prices={prices}
            closePriceLine={closePriceLine}
            openPriceLine={openPriceLine}
            pageSwitchHandler={pageSwitchHandler}
        />
    )
}

export default CarsPage
