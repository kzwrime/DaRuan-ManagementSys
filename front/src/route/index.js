import {createWebHistory, createRouter} from "vue-router";

const routes = [
    {
        path: '/:catchAll(.*)',
        name: '/404',
        component: () => import("../pages/404")
    },
    {
        path: '/',
        name: "Home",
        component: () => import("../pages/Home")
    },
    {
        path: "/login",
        name: "login",
        component: () => import("../pages/loginPage")
    },
    {
        path: "/userPage",
        name: "userPage",
        component: () => import ("../pages/userPage"),
        meta: {
            needLogin: true
        }
    },
    {
        path: "/userRegister",
        name: "userReg",
        component: () => import("../pages/regPage")
    },
    {
        path: "/visitor",
        name: "visitorPage",
        component: () => import("../pages/visitorPage")
    }, {
        path: "/test",
        name: "testPage",
        component: () => import("../pages/test"),

    },
    {
        path: "/book_manage",
        component: () =>
            import ("@/pages/book_manage"),
        meta: {
            needLogin: true
        }
    },
    {
        path: "/paper_manage",
        component: () =>
            import ("@/pages/paper_manage"),
        meta: {
            needLogin: true
        }
    },
    {
        path: "/people_manage",
        component: () =>
            import ("@/pages/people_manage"),
        meta: {
            needLogin: true
        }
    },
    {
        path: "/borrow_manage",
        component: () =>
            import ("@/pages/borrow_manage"),
        meta: {
            needLogin: true
        }
    },

]
const router = createRouter(
    {
        history: createWebHistory(),
        routes
    }
)
export default router