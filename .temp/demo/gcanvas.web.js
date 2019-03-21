// noinspection JSAnnotator
import Vue from 'vue'
// noinspection JSAnnotator
import weex from 'weex-vue-render'

weex.init(Vue)

const App = require('..\\..\\src\\demo\\gcanvas.vue');
new Vue(Vue.util.extend({el: '#root'}, App));
