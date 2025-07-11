import { Suspense, lazy } from "react";
import todoRouter from "./todoRouter";
import productsRouter from "./productsRouter";
// Suspense는 아직 렌더링이 준비되지 않은 컴포넌트가 있을 때 로딩 화면을 보여주고 로딩이 완료되면 해당 컴포넌트를 보여주는 React에 내장되어 있는 기능이다

const { createBrowserRouter } = require("react-router-dom"); 

const Loading = <div>Loading....</div>;

const Main = lazy(() => import("../pages/MainPage"));
const About = lazy(() => import("../pages/AboutPage"));
const TodoIndex = lazy(() => import("../pages/todo/IndexPage"));
const ProductsIndex = lazy(() => import("../pages/products/IndexPage"));

const root = createBrowserRouter([
    {
        path: "",
        element: <Suspense fallback={Loading}><Main/></Suspense>
    },
    {
        path: "about",
        element: <Suspense fallback={Loading}><About/></Suspense>
    },
    {
        path: "todo",
        element: <Suspense fallback={Loading}><TodoIndex/></Suspense>,
        children: todoRouter()      // 중첩 라우팅 분리
    },{
        path: "products",
        element: <Suspense fallback={Loading}><ProductsIndex /></Suspense>,
        children: productsRouter()
    }
 

]);

export default root;
