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
/******/ 	return __webpack_require__(__webpack_require__.s = 525);
/******/ })
/************************************************************************/
/******/ ({

/***/ 525:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _video = __webpack_require__(526);

var _video2 = _interopRequireDefault(_video);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_video2.default.el = '#root';
new Vue(_video2.default);

/***/ }),

/***/ 526:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(527)
)

/* script */
__vue_exports__ = __webpack_require__(528)

/* template */
var __vue_template__ = __webpack_require__(529)
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
__vue_options__.__file = "D:\\Myproject\\YoloVideoAppByWeex\\YoloVideoApp\\src\\demo\\video.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-f367c370"
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

/***/ 527:
/***/ (function(module, exports) {

module.exports = {
  "video": {
    "width": "630",
    "height": "350",
    "marginTop": "60",
    "marginLeft": "60"
  },
  "info": {
    "marginTop": "40",
    "fontSize": "40",
    "textAlign": "center"
  }
}

/***/ }),

/***/ 528:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _head = __webpack_require__(6);

var _head2 = _interopRequireDefault(_head);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var modal = weex.requireModule('modal'); //
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
//
//
//

var nav = weex.requireModule('navigator');
var params = weex.requireModule('params');
exports.default = {
    components: { Head: _head2.default },
    data: function data() {
        return {
            state: '----',
            src: 'http://flv2.bn.netease.com/videolib3/1611/01/XGqSL5981/SD/XGqSL5981-mobile.mp4'
        };
    },

    methods: {
        onstart: function onstart(event) {
            this.state = 'onstart';
        },
        onpause: function onpause(event) {
            this.state = 'onpause';
        },
        onfinish: function onfinish(event) {
            this.state = 'onfinish';
        },
        onfail: function onfail(event) {
            this.state = 'onfinish';
        }
    },
    created: function created() {
        params.getParam(function (res) {
            modal.toast({ message: res });
        });
    }
};

/***/ }),

/***/ 529:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('head', {
    attrs: {
      "title": "videoDemo"
    }
  }), _c('video', {
    staticClass: ["video"],
    attrs: {
      "src": _vm.src,
      "autoplay": "",
      "controls": ""
    },
    on: {
      "start": _vm.onstart,
      "pause": _vm.onpause,
      "finish": _vm.onfinish,
      "fail": _vm.onfail
    }
  }), _c('text', {
    staticClass: ["info"]
  }, [_vm._v("state: " + _vm._s(_vm.state))])], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 6:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(7)
)

/* script */
__vue_exports__ = __webpack_require__(8)

/* template */
var __vue_template__ = __webpack_require__(9)
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
__vue_options__.__file = "D:\\Myproject\\YoloVideoAppByWeex\\YoloVideoApp\\src\\components\\head.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-573557d0"
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

/***/ 7:
/***/ (function(module, exports) {

module.exports = {
  "titleArea": {
    "width": "450",
    "height": "140",
    "alignItems": "center",
    "justifyContent": "center",
    "marginTop": "55"
  },
  "right": {
    "height": "140",
    "width": "150",
    "alignItems": "center",
    "justifyContent": "center",
    "position": "absolute",
    "right": "0",
    "top": "25"
  },
  "back": {
    "height": "140",
    "width": "150",
    "alignItems": "center",
    "justifyContent": "center",
    "position": "absolute",
    "left": "0",
    "top": "25"
  },
  "header": {
    "height": "140",
    "width": "750",
    "backgroundColor": "#17acf6",
    "alignItems": "center",
    "flexDirection": "row",
    "justifyContent": "center"
  },
  "title": {
    "fontSize": "35",
    "color": "#FFFFFF"
  }
}

/***/ }),

/***/ 8:
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

exports.default = {
    name: "head",
    props: {
        title: {
            default: '标题2'
        },
        canBack: {
            default: true
        },
        showRight: {
            default: false
        }
    },
    methods: {
        back: function back() {
            // var modal=weex.requireModule('modal');
            // modal.alert({message:'pop'})
            var nav = weex.requireModule('navigator');
            nav.pop({}, function (res) {});
        },
        rightItemClick: function rightItemClick() {
            this.$emit("rightItemClick", {});
        }
    }
};

/***/ }),

/***/ 9:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["header"]
  }, [(_vm.canBack) ? _c('div', {
    staticClass: ["back"],
    on: {
      "click": _vm.back
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "28px",
      color: "white"
    }
  }, [_vm._v("返回")])]) : _vm._e(), _c('div', {
    staticClass: ["titleArea"]
  }, [_c('text', {
    staticClass: ["title"]
  }, [_vm._v(_vm._s(_vm.title))])]), (_vm.showRight) ? _c('div', {
    staticClass: ["right"],
    on: {
      "click": _vm.rightItemClick
    }
  }, [_vm._t("rightItem")], 2) : _vm._e()])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ })

/******/ });