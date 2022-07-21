import { useEffect, useState } from 'react'

export type State = {
    message: string
}

let store: State = {
    message: '',
}
let listeners: React.Dispatch<React.SetStateAction<State>>[] = []

export const useAnnounce = (): [State, (message: string) => void] => {
    const setState = useState(store)[1]

    useEffect(() => {
        listeners.push(setState)
        return () => {
            listeners = listeners.filter((_setState) => _setState !== setState)
        }
    }, [])

    const setMessage = (message: string) => {
        store = {
            ...store,
            message,
        }
        for (let listener of listeners) {
            listener(store)
        }
    }
    return [store, setMessage]
}