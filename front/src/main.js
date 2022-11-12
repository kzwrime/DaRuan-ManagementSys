import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from "@/route";
import axios from "axios"
import {useStore} from "vuex"
import * as ElIconModules from '@element-plus/icons'
import qs from 'qs'
const app = createApp(App)

// 统一注册Icon图标
for (const iconName in ElIconModules) {
    if (Reflect.has(ElIconModules, iconName)) {
        const item = ElIconModules[iconName]
        app.component(iconName, item)
    }
}
router.beforeEach(function (to,from,next){
    if (to.meta.needLogin){
        if (sessionStorage.getItem("token")){
            next();
        }
        else {
            next({
                name:"login"
            });
        }
    }
    else {
        next()
    }
});

//让其他页面也要可以使用axios
app.config.globalProperties.$qs=qs
app.config.globalProperties.$axios=axios
app.config.globalProperties.$store=useStore

app.use(ElementPlus)
app.use(router)
app.mount('#app')