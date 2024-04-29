import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import '@/assets/css/global.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/css/iconfont/iconfont.css'
import request from "@/utils/request"
import 'highlight.js/styles/monokai-sublime.css'
import { gsap } from 'gsap'
import lottie from 'vue-lottie'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
Vue.use(Antd);
Vue.component('lottie',lottie)
Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});
Vue.prototype.$request=request
Vue.prototype.$baseUrl=process.env.VUE_APP_BASEURL
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
