/* This example requires Tailwind CSS v2.0+ */
import React, { Fragment } from 'react'
import { CartesianGrid, Line, LineChart, Tooltip, XAxis, YAxis } from 'recharts'

export type Price = {
    date: string
    price: number
}

export type Props = {
    prices: Price[]
    detailUrl: string
    closeFn: Function
}

const PriceLine: React.FC<Props> = ({ prices, detailUrl, closeFn }) => {
    function closeWindow() {
        closeFn()
    }

    return (
        <Fragment>
            <div className="fixed w-full inset-0 w-full">
                <div className="fixed w-full flex justify-center items-center inset-0 w-full ">
                    <div
                        className="absolute w-full h-full bg-gray-500 opacity-90 z-20"
                        onClick={closeWindow}
                    ></div>
                    <div className="bg-white shadow overflow-hidden sm:rounded-lg container relative z-50">
                        <div
                            className="absolute top-3.5 right-5 cursor-pointer"
                            onClick={closeWindow}
                        >
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                x="0px"
                                y="0px"
                                width="30"
                                height="30"
                                viewBox="0 0 30 30"
                            >
                                <path d="M 7 4 C 6.744125 4 6.4879687 4.0974687 6.2929688 4.2929688 L 4.2929688 6.2929688 C 3.9019687 6.6839688 3.9019687 7.3170313 4.2929688 7.7070312 L 11.585938 15 L 4.2929688 22.292969 C 3.9019687 22.683969 3.9019687 23.317031 4.2929688 23.707031 L 6.2929688 25.707031 C 6.6839688 26.098031 7.3170313 26.098031 7.7070312 25.707031 L 15 18.414062 L 22.292969 25.707031 C 22.682969 26.098031 23.317031 26.098031 23.707031 25.707031 L 25.707031 23.707031 C 26.098031 23.316031 26.098031 22.682969 25.707031 22.292969 L 18.414062 15 L 25.707031 7.7070312 C 26.098031 7.3170312 26.098031 6.6829688 25.707031 6.2929688 L 23.707031 4.2929688 C 23.316031 3.9019687 22.682969 3.9019687 22.292969 4.2929688 L 15 11.585938 L 7.7070312 4.2929688 C 7.5115312 4.0974687 7.255875 4 7 4 z"></path>
                            </svg>
                        </div>
                        <div className="px-4 py-5 sm:px-6">
                            <h3 className="text-lg leading-6 font-medium text-gray-900">
                                價格趨勢
                            </h3>
                            <p className="mt-1 max-w-2xl text-sm text-gray-500">
                                根據歷次更新價格
                            </p>
                        </div>
                        <div className="flex justify-center">
                            <LineChart
                                className={'hidden lg:block'}
                                width={800}
                                height={600}
                                data={prices}
                            >
                                <YAxis
                                    type="number"
                                    yAxisId={0}
                                    domain={[0, 1020]}
                                />
                                <XAxis dataKey="date" />
                                <Tooltip position={{ y: 200 }} />
                                <CartesianGrid stroke="#f5f5f5" />
                                <Line
                                    type="monotone"
                                    dataKey="price"
                                    stroke="#ff7300"
                                    strokeWidth={2}
                                    yAxisId={0}
                                />
                            </LineChart>
                            <LineChart
                                className={'block lg:hidden'}
                                width={400}
                                height={300}
                                data={prices}
                            >
                                <YAxis
                                    type="number"
                                    yAxisId={0}
                                    domain={[0, 1020]}
                                />
                                <XAxis dataKey="date" />
                                <Tooltip position={{ y: 200 }} />
                                <CartesianGrid stroke="#f5f5f5" />
                                <Line
                                    type="monotone"
                                    dataKey="price"
                                    stroke="#ff7300"
                                    strokeWidth={2}
                                    yAxisId={0}
                                />
                            </LineChart>
                        </div>
                        <div className="border-t border-gray-200">
                            <dl>
                                <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                                    <dt className="text-sm font-medium text-gray-500">
                                        資料來源
                                    </dt>
                                    <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                                        <a
                                            target="_blank"
                                            rel="noreferrer"
                                            href={detailUrl}
                                        >
                                            {detailUrl}
                                        </a>
                                    </dd>
                                </div>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>
        </Fragment>
    )
}

export default PriceLine
