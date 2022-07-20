export default function () {
    return (
        <div className="px-4 py-16 md:px-24 lg:px-8 lg:py-20 bg-gray-50 w-[100vw] h-[65vh]">
            <div className="flex flex-col lg:items-center lg:flex-row justify-center gap-5">
                <div className="flex items-center mb-6 lg:w-1/2 lg:mb-0 justify-center lg:justify-end">
                    <div className="flex items-center justify-center w-16 h-16 mr-5 rounded-full bg-indigo-50 sm:w-24 sm:h-24 xl:mr-10 xl:w-28 xl:h-28">
                        <svg
                            className="w-12 h-12 text-deep-purple-accent-400 sm:w-16 sm:h-16 xl:w-20 xl:h-20"
                            stroke="currentColor"
                            viewBox="0 0 52 52"
                        >
                            <polygon
                                strokeWidth="3"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                fill="none"
                                points="29 13 14 29 25 29 23 39 38 23 27 23"
                            />
                        </svg>
                    </div>
                    <h3 className="text-4xl font-extrabold sm:text-5xl xl:text-6xl lg:border-r-4 lg:border-amber-700 pr-5">
                        404
                    </h3>
                </div>
                <div className="lg:w-1/2 text-center lg:text-left">
                    <p className="text-3xl text-gray-800 font-black">
                        Page not Found
                    </p>
                </div>
            </div>
        </div>
    )
}
