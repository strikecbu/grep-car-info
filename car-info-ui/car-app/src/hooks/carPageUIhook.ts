import { useState } from 'react'
import { isMobileOnly, isTablet } from 'react-device-detect'

export enum DeviceType {
    PHONE = 0,
    TABLET = 1,
    DESKTOP = 2,
}

export type pageUI = {
    cardCount: number
    landPicOffset: number
    footerOffset: number
}

let nowDevice = DeviceType.DESKTOP
let pageSettings: pageUI = {
    cardCount: 48,
    landPicOffset: 1.6,
    footerOffset: 2.5,
}
if (isTablet) nowDevice = DeviceType.TABLET
if (isMobileOnly) nowDevice = DeviceType.PHONE

switch (nowDevice) {
    case DeviceType.TABLET:
        pageSettings = {
            cardCount: 24,
            landPicOffset: 1.6,
            footerOffset: 2.5,
        }
        break
    case DeviceType.PHONE:
        pageSettings = {
            cardCount: 9,
            landPicOffset: 1.4,
            footerOffset: 2.1,
        }
}

export const useCarPage = (): pageUI => {
    return useState<pageUI>(pageSettings)[0]
}
