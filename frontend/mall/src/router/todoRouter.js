import { Suspense, lazy } from "react"; 
import { Navigate } from "react-router-dom";

const Loading = <div>Loading....</div>

const TodoList = lazy(() => import("../pages/todo/ListPage"))
const TodoRead = lazy(() => import("../pages/todo/ReadPage"))
const TodoAdd = lazy(() => import("../pages/todo/AddPage"))
const TodoModify = lazy(() => import("../pages/todo/ModifyPage"))

const todoRouter = () => {
     
    return [
        {
            path: "list",
            element: <Suspense fallback={Loading}><TodoList/></Suspense>
        },
        {
            // <Navitage>의 replace 속성을 이용해서 특정 경로로 진입 시에 자동으로 리다이렉션을 처리할 수 있다. >>> todo 클릭시 자동으로 todo/list 
            path: "",
            element: <Navigate replace to="list" />
        },
        {
            // URL 파라미터
            path: "read/:tno",
            element: <Suspense fallback={Loading}><TodoRead /></Suspense>
        },
        {
            path: "add",
            element: <Suspense fallback={Loading}><TodoAdd/></Suspense>
        },
        {
            path: "modify/:tno",
            element: <Suspense fallback={Loading}><TodoModify /></Suspense>
        },
    ]

}

export default todoRouter;
