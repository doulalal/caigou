import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "./assets/global.css"
import request from "@/tool/request";
import router from "@/router";
import VueRouter from "vue-router";
//全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'	//解决编辑器的功能显示问题
Vue.use(mavonEditor)

Vue.use(VueRouter)
Vue.prototype.request=request
Vue.config.productionTip = false
Vue.use(ElementUI,{size:"mini"})


new Vue({
  render: h => h(App),
    router: router,
  beforeCreate(){
    Vue.prototype.$bus=this
  }
}).$mount('#app')
