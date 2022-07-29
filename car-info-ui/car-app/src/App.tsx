import { Fragment, useEffect } from 'react'
import { Outlet, useLocation } from 'react-router-dom'
import { useFirebase } from './hooks/firebaseHook'
import { Auth, getIdToken, onAuthStateChanged } from '@firebase/auth'
import { AppDispatch } from './store'
import { useDispatch } from 'react-redux'
import { AccountActions } from './store/account-slice'
import Environment from './env/Environment'
import { useAnnounce } from './hooks/announceHook'
import BannerNews from './components/UI/BannerNews'

function App() {
    const auth = useFirebase()[1] as Auth
    const dispatch: AppDispatch = useDispatch()
    const setMessage = useAnnounce()[1]

    const location = useLocation()
    useEffect(() => {
        const suffix = location.pathname.replaceAll('/', ' ')
        document.title = `ANDYSRV -${suffix}`
    }, [location])

    useEffect(() => {
        console.log('effect once')
        onAuthStateChanged(auth, (user) => {
            if (user) {
                const uid = user.uid
                console.log('Found user!! uid: ', uid)
                getIdToken(user)
                    .then((idToken) => {
                        dispatch(AccountActions.addToken(idToken))
                    })
                    .catch((error) => {
                        // Error occurred.
                        console.log('error occurred: ', error)
                    })
            } else {
            }
        })
    }, [auth, dispatch])

    useEffect(() => {
        console.log('註冊SSE接收')
        const eventSource = new EventSource(Environment.announceNewsUrl)
        eventSource.onmessage = (message) => {
            setMessage(message.data)
        }
    }, [])
    return (
        <Fragment>
            <BannerNews announceWords={''} />
            <Outlet />
        </Fragment>
    )
}

export default App
