import VueRouter from "vue-router";

const  routes= [
    {
        path:'/',
        name : 'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/Index',
        name : 'index',
        meta:{
            requireAuth: true
        },
        component:()=>import('../components/Index'),
        children:[
            {
                path:'/Home',
                name: 'home',
                meta:{
                    title:"首页",
                    requireAuth: true
                },
                component:()=>import('../components/Home')
            },
            // {
            //     path: '/Admin',
            //     name: 'admin',
            //     meta: {
            //         title: "管理员管理",
            //         requireAuth: true
            //     },
            //     component: () => import('../components/admin/AdminManage')
            // },
            // {
            //     path: '/User',
            //     name: 'user',
            //     meta: {
            //         title: "用户管理",
            //         requireAuth: true
            //     },
            //     component: () => import('../components/user/UserManage')
            // }
        ]
    }
]

const router =new VueRouter({
    mode:'history',
    routes
});

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes:[]
    }).matcher
}

router.beforeEach((to, from, next) => {
    const isLogin  = sessionStorage.getItem('isLogin'); //获取本地存储的登陆信息
    if (to.name == 'login') { //判断是否进入的login页
        if (isLogin  == "true") {  //判断是否登陆
            next({ name: 'index'});  //已登录，跳转首页（a页面）
            console.log(to.name)
        } else {
            next();  //没登录，继续进入login页
            console.log(to.name)
        }
    } else { //如果进入的非login页
        if (isLogin  == "true") {   //同样判断是否登陆
            next();  //已登录，正常进入
            console.log(to.name)
        } else {
            next({ name: 'login'});  //没登录，跳转到login页
            console.log(to.name)
        }
    }
});
export default router;