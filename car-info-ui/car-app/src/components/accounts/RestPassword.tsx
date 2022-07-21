import { sendPasswordResetEmail } from 'firebase/auth'
import { LockClosedIcon } from '@heroicons/react/solid'
import { useNavigate } from 'react-router-dom'
import React, { useRef, useState } from 'react'
import { ClipLoader } from 'react-spinners'
import { useFirebase } from '../../hooks/firebaseHook'
import { Auth } from '@firebase/auth'

export default function RestPassword() {
    const navigate = useNavigate()
    const emailRef = useRef<HTMLInputElement | null>(null)
    const [message, setMessage] = useState<string>('')
    const [isLoading, setLoading] = useState(false)
    const [isGoingHome, setGoingHome] = useState(false)
    const auth = useFirebase()[1] as Auth

    async function handleSubmit(
        e: React.MouseEvent<HTMLButtonElement, MouseEvent>
    ) {
        e.preventDefault()
        const email = emailRef.current!.value
        try {
            setLoading(true)
            await sendPasswordResetEmail(auth, email)
            emailRef.current!.value = ''
            setMessage('')
            setLoading(false)
            returnHome()
        } catch (e: any) {
            setMessage(e.message)
            setLoading(false)
        }
    }

    const returnHome = () => {
        setGoingHome(true)
        setTimeout(() => {
            setGoingHome(false)
            navigate('/home', { replace: true })
        }, 3000)
    }

    return (
        <div className="h-[65vh] bg-gray-50">
            <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
                <div className="max-w-md w-full space-y-8">
                    <div>
                        <h2 className="mt-6 text-center tracking-widest text-3xl font-extrabold text-gray-900">
                            {isGoingHome
                                ? '重設成功，正在返回首頁 '
                                : '重設您的密碼'}
                            {isGoingHome && <ClipLoader size={20} />}
                        </h2>
                    </div>
                    <form className="mt-8 space-y-6" action="#" method="POST">
                        <input
                            type="hidden"
                            name="remember"
                            defaultValue="true"
                        />
                        {!isGoingHome && (
                            <div className="rounded-md shadow-sm -space-y-px">
                                <div>
                                    <label
                                        htmlFor="email-address"
                                        className="sr-only"
                                    >
                                        Email address
                                    </label>
                                    <input
                                        ref={emailRef}
                                        id="email-address"
                                        name="email"
                                        type="email"
                                        autoComplete="email"
                                        required
                                        className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                        placeholder="Email address"
                                    />
                                </div>
                            </div>
                        )}

                        {!isGoingHome && message && (
                            <div className="flex items-center justify-center text-red-600">
                                <div>{message}</div>
                            </div>
                        )}

                        {!isGoingHome && (
                            <div>
                                <button
                                    type="submit"
                                    className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                                    onClick={handleSubmit}
                                >
                                    <span className="absolute left-0 inset-y-0 flex items-center pl-3">
                                        {!isLoading && (
                                            <LockClosedIcon
                                                className="h-5 w-5 text-indigo-500 group-hover:text-indigo-400"
                                                aria-hidden="true"
                                            />
                                        )}

                                        {isLoading && <ClipLoader size={20} />}
                                    </span>
                                    送出
                                </button>
                            </div>
                        )}
                    </form>
                </div>
            </div>
        </div>
    )
}
