import { FunctionComponent } from 'react'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import App from '../../App'
import HomePage from './HomePage'
import CarsPage from './CarsPage'
import LoginPage from './LoginPage'
import { useSelector } from 'react-redux'
import { RootState } from '../../store'
import NotFoundPage from './NotFoundPage'
import ResetPage from './ResetPage'

const AppRoute: FunctionComponent = () => {
    const token = useSelector((state: RootState) => state.account.token)

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App />}>
                    <Route path="home" element={<HomePage />} />
                    {token && <Route path="cars" element={<CarsPage />} />}
                    <Route path="" element={<Navigate replace to="home" />} />
                    <Route path="/*" element={<NotFoundPage />} />
                    <Route path="/account">
                        <Route path="login" element={<LoginPage />} />
                        <Route path="signup" element={<LoginPage />} />
                        <Route path="reset" element={<ResetPage />} />
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AppRoute
