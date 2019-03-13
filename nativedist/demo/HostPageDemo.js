// { "framework": "Vue"} 

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 272);
/******/ })
/************************************************************************/
/******/ ({

/***/ 272:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _HostPageDemo = __webpack_require__(273);

var _HostPageDemo2 = _interopRequireDefault(_HostPageDemo);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_HostPageDemo2.default.el = '#root';
new Vue(_HostPageDemo2.default);

/***/ }),

/***/ 273:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(274)
)

/* script */
__vue_exports__ = __webpack_require__(275)

/* template */
var __vue_template__ = __webpack_require__(276)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "D:\\MyPricticePro\\myWeexProject\\weexproject_test\\YoloVideoApp\\src\\demo\\HostPageDemo.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-5e416386"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),

/***/ 274:
/***/ (function(module, exports) {

module.exports = {
  "host": {
    "width": "750",
    "position": "absolute",
    "left": 0,
    "top": 0,
    "bottom": "120"
  },
  "tabBar": {
    "borderTopWidth": "1",
    "borderColor": "#999999",
    "height": "120",
    "justifyContent": "space-between",
    "flexDirection": "row",
    "position": "absolute",
    "left": 0,
    "bottom": 0,
    "width": "750"
  },
  "tabItem": {
    "height": "120",
    "width": "250",
    "alignItems": "center",
    "justifyContent": "center"
  }
}

/***/ }),

/***/ 275:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var modal = weex.requireModule('modal');
exports.default = {
    name: "host-page-demo",
    data: {
        urls: ['./weex-ui.js', './Image.js', './video.js'],
        position: 0
    },
    methods: {
        allPageFinished: function allPageFinished(e) {
            // modal.alert({message:e.index})
        },
        onPageScrolled: function onPageScrolled(e) {
            console.log(e.positionOffset);
        },
        onPageSelected: function onPageSelected(e) {
            this.position = e.position;
            // modal.toast({message:e.position})
        },
        tabClick: function tabClick(index) {
            this.position = index;
            this.$refs.host.setIndex(index);
        }
    }
};

/***/ }),

/***/ 276:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('hostPage', {
    ref: "host",
    staticClass: ["host"],
    attrs: {
      "index": _vm.position,
      "pages": _vm.urls
    },
    on: {
      "pageFinish": _vm.allPageFinished,
      "onPageScrolled": _vm.onPageScrolled,
      "onPageSelected": _vm.onPageSelected
    }
  }), _c('div', {
    staticClass: ["tabBar"]
  }, [_c('div', {
    staticClass: ["tabItem"],
    style: {
      backgroundColor: _vm.position == 0 ? '#17acf6' : '#ffffff'
    },
    on: {
      "click": function($event) {
        _vm.tabClick(0)
      }
    }
  }, [_c('text', [_vm._v("首页")])]), _c('div', {
    staticClass: ["tabItem"],
    style: {
      backgroundColor: _vm.position == 1 ? '#17acf6' : '#ffffff'
    },
    on: {
      "click": function($event) {
        _vm.tabClick(1)
      }
    }
  }, [_c('text', [_vm._v("图片")])]), _c('div', {
    staticClass: ["tabItem"],
    style: {
      backgroundColor: _vm.position == 2 ? '#17acf6' : '#ffffff'
    },
    on: {
      "click": function($event) {
        _vm.tabClick(2)
      }
    }
  }, [_c('text', [_vm._v("视频")])])])], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ })

/******/ });