import { useState } from 'react'
import { FirebaseApp } from '@firebase/app'
import Environment from '../env/Environment'
import { initializeApp } from 'firebase/app'
import { getAuth } from 'firebase/auth'
import { Auth } from '@firebase/auth'

const firebaseConfig = {
    apiKey: Environment.apiKey,
    authDomain: Environment.authDomain,
    projectId: Environment.projectId,
    messagingSenderId: Environment.messagingSenderId,
}
const app = initializeApp(firebaseConfig)
const auth = getAuth(app)
const store = {
    app,
    auth,
}

export const useFirebase = () => {
    const app = useState<FirebaseApp>(store.app)[0]
    const auth = useState<Auth>(store.auth)[0]

    return [app, auth]
}
