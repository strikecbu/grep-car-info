import React from 'react'
import { animated, useSpring } from 'react-spring'

export type Props = {
    value: number
    handle: () => void
}

export const AniNumber: React.FC<Props> = ({ value, handle }) => {
    const { number } = useSpring({
        from: { number: 0 },
        number: value,
        delay: 300,
        config: {
            duration: 1000,
        },
        onRest: handle,
    })

    return <animated.span>{number.to((n) => n.toFixed(0))}</animated.span>
}
