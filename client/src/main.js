import Vue from "vue";
import App from "./App.vue";
import Snotify from "vue-snotify";
import VueTheMask from "vue-the-mask";
import "vue-snotify/styles/material.css";

Vue.use(Snotify);
Vue.use(VueTheMask);
Vue.config.productionTip = false;

require("./assets/main.scss");

new Vue({
  render: h => h(App)
}).$mount("#app");
