import {FunctionComponent} from "react";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import App from "../../App";
import HomePage from "./HomePage";
import CarsPage from "./CarsPage";

type Props = {

}

const AppRoute: FunctionComponent = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App />} >
                    <Route path="home" element={<HomePage />} />
                    <Route path="cars" element={<CarsPage />} />
                    <Route path="" element={<Navigate replace to="home" />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default AppRoute;
