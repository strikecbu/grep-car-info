/* This example requires Tailwind CSS v2.0+ */
import { SpeakerphoneIcon, XIcon } from '@heroicons/react/outline'
import React, { Fragment, useEffect, useState } from 'react'
import { useAnnounce, State } from '../../hooks/announceHook'

export type Props = {
    announceWords: string
}

const BannerNews: React.FC<Props> = ({ announceWords }) => {
    const [show, setShow] = useState(true)
    const [state, setMessage] = useAnnounce()

    function handleClose() {
        setShow(!show)
    }

    useEffect(() => {
        setMessage(announceWords)
    }, [])

    useEffect(() => {
        setShow(true)
    }, [state])

    return (
        <Fragment>
            {show && (
                <div className="bg-indigo-600">
                    <div className="max-w-7xl mx-auto py-3 px-3 sm:px-6 lg:px-8">
                        <div className="flex items-center justify-between flex-wrap">
                            <div className="w-0 flex-1 flex items-center">
                                <span className="flex p-2 rounded-lg bg-indigo-800">
                                    <SpeakerphoneIcon
                                        className="h-6 w-6 text-white"
                                        aria-hidden="true"
                                    />
                                </span>
                                <p className="ml-3 font-medium text-white truncate">
                                    {/*<span className="md:hidden">We announced a new product!</span>*/}
                                    <span className="inline">
                                        {state.message}
                                    </span>
                                </p>
                            </div>
                            {/*<div className="order-3 mt-2 flex-shrink-0 w-full sm:order-2 sm:mt-0 sm:w-auto">*/}
                            {/*    <a*/}
                            {/*        href="#"*/}
                            {/*        className="flex items-center justify-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-indigo-600 bg-white hover:bg-indigo-50"*/}
                            {/*    >*/}
                            {/*        Learn more*/}
                            {/*    </a>*/}
                            {/*</div>*/}
                            <div className="order-2 flex-shrink-0 sm:order-3 sm:ml-3">
                                <button
                                    type="button"
                                    className="-mr-1 flex p-2 rounded-md hover:bg-indigo-500 focus:outline-none focus:ring-2 focus:ring-white sm:-mr-2"
                                    onClick={handleClose}
                                >
                                    <span className="sr-only">Dismiss</span>
                                    <XIcon
                                        className="h-6 w-6 text-white"
                                        aria-hidden="true"
                                    />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </Fragment>
    )
}

export default BannerNews
