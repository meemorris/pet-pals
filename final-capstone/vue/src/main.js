import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import moment from 'moment'
//import * as VueGoogleMaps from 'vue2-google-maps'

Vue.prototype.moment = moment
Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

Vue.use(BootstrapVue);

Vue.use(IconsPlugin);

// Vue.use(VueGoogleMaps, {
//   load: {
//     key: 'AIzaSyCMDIDFLUZUj0iH6vHHsSb-hFw4ZQmhgus',
//     libraries: 'places',
//   }
// });

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
