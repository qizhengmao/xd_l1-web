import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from 'axios';
import VueRouter from 'vue-router';
import router from '../src/router';
import store from '../src/store'
import VCharts from 'v-charts';
import * as echarts from 'echarts';
import china from 'echarts/map/json/china.json'

echarts.registerMap('china', china)
Vue.prototype.$echarts = echarts
Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});
Vue.use(VCharts);
Vue.use(VueRouter);
Vue.prototype.$axios=axios;
Vue.prototype.$httpUrl='http://localhost:8080'

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
