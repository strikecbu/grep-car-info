import { useState } from 'react'

export default () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false)

    return (
        <div className="bg-gray-900">
            <div className="px-4 py-5 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8">
                <div className="relative flex items-center justify-between">
                    <div className="flex items-center">
                        <a
                            href="/"
                            aria-label="AndySrv"
                            title="AndySrv"
                            className="inline-flex items-center mr-8"
                        >
                            <img
                                className="w-7"
                                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABmJLR0QA/wD/AP+gvaeTAAAB80lEQVQ4jWNkIAD698/N5mRlk7/36dmkLq/yJ+jyLPg0d+6ZFqAhLzeJmYmJie0VizQDA0M0uhomQi74z/AfxsAKmPFp3rNo6w1NRyv2H79/nHr19lXzvuU7P2NVmLu2UyZ3bacMNrn06j4TfOpZevbMDFCWll74n+E/g/yemfElLukbkBX//cuM4nh09Uw8XFwO3JycfDycXHxsjOxhSWWdvAwMDAwFMy4HVS9/eFlMx2dF3qzLgQwMDAxJZZ28rIxs4TD1PDyc9swWoY7vmJmYjb98//78xacPNd+fMbIZWHkqCMjqzBQUFNTg5OIS+v/3n4mGmuJxNlYWNnaZ3+dh6p++ed/OiCsAq5c/vCwgwK/DwMDA8PH9x8stUfJ62NThTAcf3r5sZvj3t4WBkenv+zdPpuJShxOkVk+0YmD4zwhjh4auwhrlWBNSVsNUHqZ/jB8YGBj/MzAwMEizvj8hrPbEGJtalDAo29Ypo8Qnlffm9bf/NYHp5chymZ2d6ipGIhlsHEwf3t552NmQ2PCDgQEtDNREZLvlxMQj5KT+/uvcOe14uXsWPE1oWYlFqMvKFTAwMDDc+P//PwMDQxNOL2AFTP+wCqO44Nabx6W/f/979OP39wfItjMwMDC8vfW488Zfxv+MDIz/39191A0TBwB+SLeKE/NZ3QAAAABJRU5ErkJggg=="
                                alt="AndySRV"
                            />
                            <span className="ml-2 text-xl font-bold tracking-wide text-gray-100 uppercase">
                                AndySRV
                            </span>
                        </a>
                        <ul className="flex items-center hidden space-x-8 lg:flex">
                            <li>
                                <a
                                    href="/"
                                    aria-label="Our product"
                                    title="Our product"
                                    className="font-medium tracking-wide text-gray-100 transition-colors duration-200 hover:text-teal-accent-400"
                                >
                                    Product
                                </a>
                            </li>
                            <li>
                                <a
                                    href="/"
                                    aria-label="Our product"
                                    title="Our product"
                                    className="font-medium tracking-wide text-gray-100 transition-colors duration-200 hover:text-teal-accent-400"
                                >
                                    Features
                                </a>
                            </li>
                            <li>
                                <a
                                    href="/"
                                    aria-label="Product pricing"
                                    title="Product pricing"
                                    className="font-medium tracking-wide text-gray-100 transition-colors duration-200 hover:text-teal-accent-400"
                                >
                                    Pricing
                                </a>
                            </li>
                            <li>
                                <a
                                    href="/"
                                    aria-label="About us"
                                    title="About us"
                                    className="font-medium tracking-wide text-gray-100 transition-colors duration-200 hover:text-teal-accent-400"
                                >
                                    About us
                                </a>
                            </li>
                        </ul>
                    </div>
                    <ul className="flex items-center hidden space-x-8 lg:flex">
                        <li>
                            <a
                                href="/"
                                aria-label="Sign in"
                                title="Sign in"
                                className="font-medium tracking-wide text-gray-100 transition-colors duration-200 hover:text-teal-accent-400"
                            >
                                Sign in
                            </a>
                        </li>
                        <li>
                            <a
                                href="/"
                                className="inline-flex items-center justify-center h-12 px-6 font-medium tracking-wide text-white transition duration-200 rounded shadow-md bg-purple-800 hover:bg-purple-400 focus:shadow-outline focus:outline-none"
                                aria-label="Sign up"
                                title="Sign up"
                            >
                                Sign up
                            </a>
                        </li>
                    </ul>
                    <div className="lg:hidden">
                        <button
                            aria-label="Open Menu"
                            title="Open Menu"
                            className="p-2 -mr-1 transition duration-200 rounded focus:outline-none focus:shadow-outline"
                            onClick={() => setIsMenuOpen(true)}
                        >
                            <svg
                                className="w-5 text-gray-600"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    fill="currentColor"
                                    d="M23,13H1c-0.6,0-1-0.4-1-1s0.4-1,1-1h22c0.6,0,1,0.4,1,1S23.6,13,23,13z"
                                />
                                <path
                                    fill="currentColor"
                                    d="M23,6H1C0.4,6,0,5.6,0,5s0.4-1,1-1h22c0.6,0,1,0.4,1,1S23.6,6,23,6z"
                                />
                                <path
                                    fill="currentColor"
                                    d="M23,20H1c-0.6,0-1-0.4-1-1s0.4-1,1-1h22c0.6,0,1,0.4,1,1S23.6,20,23,20z"
                                />
                            </svg>
                        </button>
                        {isMenuOpen && (
                            <div className="absolute top-0 left-0 w-full">
                                <div className="p-5 bg-white border rounded shadow-sm">
                                    <div className="flex items-center justify-between mb-4">
                                        <div>
                                            <a
                                                href="/"
                                                aria-label="AndySrv"
                                                title="AndySrv"
                                                className="inline-flex items-center"
                                            >
                                                <img
                                                    className="w-7"
                                                    src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABmJLR0QA/wD/AP+gvaeTAAAB80lEQVQ4jWNkIAD698/N5mRlk7/36dmkLq/yJ+jyLPg0d+6ZFqAhLzeJmYmJie0VizQDA0M0uhomQi74z/AfxsAKmPFp3rNo6w1NRyv2H79/nHr19lXzvuU7P2NVmLu2UyZ3bacMNrn06j4TfOpZevbMDFCWll74n+E/g/yemfElLukbkBX//cuM4nh09Uw8XFwO3JycfDycXHxsjOxhSWWdvAwMDAwFMy4HVS9/eFlMx2dF3qzLgQwMDAxJZZ28rIxs4TD1PDyc9swWoY7vmJmYjb98//78xacPNd+fMbIZWHkqCMjqzBQUFNTg5OIS+v/3n4mGmuJxNlYWNnaZ3+dh6p++ed/OiCsAq5c/vCwgwK/DwMDA8PH9x8stUfJ62NThTAcf3r5sZvj3t4WBkenv+zdPpuJShxOkVk+0YmD4zwhjh4auwhrlWBNSVsNUHqZ/jB8YGBj/MzAwMEizvj8hrPbEGJtalDAo29Ypo8Qnlffm9bf/NYHp5chymZ2d6ipGIhlsHEwf3t552NmQ2PCDgQEtDNREZLvlxMQj5KT+/uvcOe14uXsWPE1oWYlFqMvKFTAwMDDc+P//PwMDQxNOL2AFTP+wCqO44Nabx6W/f/979OP39wfItjMwMDC8vfW488Zfxv+MDIz/39191A0TBwB+SLeKE/NZ3QAAAABJRU5ErkJggg=="
                                                    alt="AndySRV"
                                                />
                                                <span className="ml-2 text-xl font-bold tracking-wide text-gray-800 uppercase">
                                                    ANDYSRV
                                                </span>
                                            </a>
                                        </div>
                                        <div>
                                            <button
                                                aria-label="Close Menu"
                                                title="Close Menu"
                                                className="p-2 -mt-2 -mr-2 transition duration-200 rounded hover:bg-gray-200 focus:bg-gray-200 focus:outline-none focus:shadow-outline"
                                                onClick={() =>
                                                    setIsMenuOpen(false)
                                                }
                                            >
                                                <svg
                                                    className="w-5 text-gray-600"
                                                    viewBox="0 0 24 24"
                                                >
                                                    <path
                                                        fill="currentColor"
                                                        d="M19.7,4.3c-0.4-0.4-1-0.4-1.4,0L12,10.6L5.7,4.3c-0.4-0.4-1-0.4-1.4,0s-0.4,1,0,1.4l6.3,6.3l-6.3,6.3 c-0.4,0.4-0.4,1,0,1.4C4.5,19.9,4.7,20,5,20s0.5-0.1,0.7-0.3l6.3-6.3l6.3,6.3c0.2,0.2,0.5,0.3,0.7,0.3s0.5-0.1,0.7-0.3 c0.4-0.4,0.4-1,0-1.4L13.4,12l6.3-6.3C20.1,5.3,20.1,4.7,19.7,4.3z"
                                                    />
                                                </svg>
                                            </button>
                                        </div>
                                    </div>
                                    <nav>
                                        <ul className="space-y-4">
                                            <li>
                                                <a
                                                    href="/"
                                                    aria-label="Our product"
                                                    title="Our product"
                                                    className="font-medium tracking-wide text-gray-700 transition-colors duration-200 hover:text-deep-purple-accent-400"
                                                >
                                                    Product
                                                </a>
                                            </li>
                                            <li>
                                                <a
                                                    href="/"
                                                    aria-label="Our product"
                                                    title="Our product"
                                                    className="font-medium tracking-wide text-gray-700 transition-colors duration-200 hover:text-deep-purple-accent-400"
                                                >
                                                    Features
                                                </a>
                                            </li>
                                            <li>
                                                <a
                                                    href="/"
                                                    aria-label="Product pricing"
                                                    title="Product pricing"
                                                    className="font-medium tracking-wide text-gray-700 transition-colors duration-200 hover:text-deep-purple-accent-400"
                                                >
                                                    Pricing
                                                </a>
                                            </li>
                                            <li>
                                                <a
                                                    href="/"
                                                    aria-label="About us"
                                                    title="About us"
                                                    className="font-medium tracking-wide text-gray-700 transition-colors duration-200 hover:text-deep-purple-accent-400"
                                                >
                                                    About us
                                                </a>
                                            </li>
                                            <li>
                                                <a
                                                    href="/"
                                                    aria-label="Sign in"
                                                    title="Sign in"
                                                    className="font-medium tracking-wide text-gray-700 transition-colors duration-200 hover:text-deep-purple-accent-400"
                                                >
                                                    Sign in
                                                </a>
                                            </li>
                                            <li>
                                                <a
                                                    href="/"
                                                    className="inline-flex items-center justify-center w-full h-12 px-6 font-medium tracking-wide text-white transition duration-200 rounded shadow-md bg-deep-purple-accent-400 hover:bg-deep-purple-accent-700 focus:shadow-outline focus:outline-none"
                                                    aria-label="Sign up"
                                                    title="Sign up"
                                                >
                                                    Sign up
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}
