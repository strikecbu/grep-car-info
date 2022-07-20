import {
    createUserWithEmailAndPassword,
    getAuth,
    sendEmailVerification,
    signInWithEmailAndPassword,
} from 'firebase/auth'
import { LockClosedIcon } from '@heroicons/react/solid'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'
import React, { useRef, useState } from 'react'
import Environment from '../../env/Environment'
import { initializeApp } from 'firebase/app'
import { ClipLoader } from 'react-spinners'

const firebaseConfig = {
    apiKey: Environment.apiKey,
    authDomain: Environment.authDomain,
    projectId: Environment.projectId,
    messagingSenderId: Environment.messagingSenderId,
}

const app = initializeApp(firebaseConfig)

export default function Login() {
    const location = useLocation()
    const navigate = useNavigate()
    const isLogin = location.pathname.includes('login')
    const emailRef = useRef<HTMLInputElement | null>(null)
    const passwordRef = useRef<HTMLInputElement | null>(null)
    const [message, setMessage] = useState<string>('')
    const [isLoading, setLoading] = useState(false)

    async function handleSubmit(
        e: React.MouseEvent<HTMLButtonElement, MouseEvent>
    ) {
        e.preventDefault()
        const email = emailRef.current!.value
        const password = passwordRef.current!.value

        if (isLogin) {
            loginHandle(email, password)
        } else {
            signupHandle(email, password)
        }
    }

    function clearInputs() {
        emailRef.current!.value = ''
        passwordRef.current!.value = ''
    }

    async function loginHandle(email: string, password: string) {
        try {
            setLoading(true)
            const auth = getAuth(app)
            const userCredential = await signInWithEmailAndPassword(
                auth,
                email,
                password
            )
            setLoading(false)
            const user = userCredential.user
            if (!user.emailVerified) {
                setMessage('尚未驗證信箱，請先驗證後，再嘗試登錄')
                clearInputs()
                return
            }
            navigate('/home', { replace: true })
        } catch (e: any) {
            setMessage('帳號或密碼錯誤，請再重試')
            setLoading(false)
        }
    }

    async function signupHandle(email: string, password: string) {
        try {
            setLoading(true)
            const auth = getAuth(app)
            const userCredential = await createUserWithEmailAndPassword(
                auth,
                email,
                password
            )
            const user = userCredential.user
            await sendEmailVerification(user)
            setLoading(false)
            setMessage('請先收取驗證信件後，再嘗試登錄')
            clearInputs()
            navigate('../login', { replace: true })
        } catch (e: any) {
            setMessage(e.message && e.message.replaceAll('Firebase:', ''))
            setLoading(false)
        }
    }

    return (
        <div className="h-[65vh] bg-gray-50">
            <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
                <div className="max-w-md w-full space-y-8">
                    <div>
                        <h2 className="mt-6 text-center tracking-widest text-3xl font-extrabold text-gray-900">
                            {isLogin ? '登錄' : '註冊'}你的帳號
                        </h2>
                        <p className="mt-2 text-center text-sm text-gray-600">
                            Or{'  '}
                            {isLogin ? (
                                <NavLink
                                    className="font-medium text-indigo-600 hover:text-indigo-500"
                                    to="/account/signup"
                                >
                                    馬上註冊
                                </NavLink>
                            ) : (
                                <NavLink
                                    className="font-medium text-indigo-600 hover:text-indigo-500"
                                    to="/account/login"
                                >
                                    登錄
                                </NavLink>
                            )}
                        </p>
                    </div>
                    <form className="mt-8 space-y-6" action="#" method="POST">
                        <input
                            type="hidden"
                            name="remember"
                            defaultValue="true"
                        />
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
                            <div>
                                <label htmlFor="password" className="sr-only">
                                    Password
                                </label>
                                <input
                                    ref={passwordRef}
                                    id="password"
                                    name="password"
                                    type="password"
                                    autoComplete="current-password"
                                    required
                                    className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                    placeholder="Password"
                                />
                            </div>
                        </div>

                        {/*<div className="flex items-center justify-between">*/}
                        {/*    <div className="flex items-center">*/}
                        {/*        <input*/}
                        {/*            id="remember-me"*/}
                        {/*            name="remember-me"*/}
                        {/*            type="checkbox"*/}
                        {/*            className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"*/}
                        {/*        />*/}
                        {/*        <label*/}
                        {/*            htmlFor="remember-me"*/}
                        {/*            className="ml-2 block text-sm text-gray-900"*/}
                        {/*        >*/}
                        {/*            Remember me*/}
                        {/*        </label>*/}
                        {/*    </div>*/}

                        {/*    <div className="text-sm">*/}
                        {/*        <a*/}
                        {/*            href="#"*/}
                        {/*            className="font-medium text-indigo-600 hover:text-indigo-500"*/}
                        {/*        >*/}
                        {/*            Forgot your password?*/}
                        {/*        </a>*/}
                        {/*    </div>*/}
                        {/*</div>*/}

                        {message && (
                            <div className="flex items-center justify-center text-red-600">
                                <div>{message}</div>
                            </div>
                        )}

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
                                {isLogin ? '登錄' : '註冊'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}
