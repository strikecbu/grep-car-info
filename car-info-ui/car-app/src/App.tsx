import { Fragment, useEffect } from 'react'
import { Outlet } from 'react-router-dom'
import { useFirebase } from './hooks/firebaseHook'
import { Auth, onAuthStateChanged, getIdToken } from '@firebase/auth'
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
        const eventSource = new EventSource(Environment.announceNewsUrl)
        eventSource.onmessage = (message) => {
            setMessage(message.data)
        }
    }, [setMessage])
    return (
        <Fragment>
            <BannerNews announceWords={''} />
            <Outlet />
        </Fragment>
    )
}

export default App
