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
/******/ 	return __webpack_require__(__webpack_require__.s = 611);
/******/ })
/************************************************************************/
/******/ ({

/***/ 1:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
var interfaces = exports.interfaces = {
    // env:"UAT",
    // nowVersion:"1.0.9",
    // title:"应用门户(预生产)",

    // env:"QRD",
    // nowVersion:"1.0.0",
    // title:"应用门户",

    env: "DEV",
    nowVersion: "1.0.0",
    /*后台接口地址*/
    host: "http://10.39.39.33:8088",
    // host:"http://10.2.114.172:8088",
    /*微应用页面相关资源地址*/
    // appHost:"http://10.2.114.172/",
    appHost: "http://10.39.39.33/",
    title: "应用门户（测试）",

    // env:"DEV",
    // nowVersion:"1.5.0",
    // title:"应用门户（开发）",
    imgUrl: 'http://10.39.39.33:8088/file/preview?url=',
    login: "/login/userLogin",
    logOut: "/login/logout",
    appType: '/appType/pageData',
    appList: "/apps/pageData",
    appsByUserCode: "/userApp/queryByUserCode",
    downLoadApp: '/appVersions/downloadAPP/',
    updateCheck: "/version/findNewest",
    /*查询所有分类*/
    classifyApp: '/appType/pageData',
    /*分类查询app*/
    queryApp: '/apps/pageData',
    /*版本更新*/
    update: "/version/download",
    /*我的app*/
    myApp: "/userApp/queryByUserCode",
    addApp: '/userApp/insert',
    deleteApp: '/userApp/delete',
    getBanner: '/homePageImages/queryPageList',
    getNews: '/news/queryPageData'
};

/*线索筛选条件传值*/
//clueMenuFlag
/* COMMON   公共线索
* ME_SEND  我发的
* ME_GRAB  我抢的
* CHECK_PENDING  待审核
* CHECK_FINISH   审核通过
* CHECK_REJECT   审核驳回
* FROZEN  已冻结
 *  */

// ME_ALL, 我的商机 全部
//     /**
//      * 进行中  ME_CHECK_FINISH,
//     /**
//      * 审批中 ME_CHECKING,
//     /**
//      * 已完成  ME_FINISH,
//     /**
//      * 资料审批(提交资料后待审批的商机)   CHECKING,
//     /**
//      * 未提交资料  SUBMIT,
//     /**
//      * 预警商机  ALARM,
//     /**
//      * 已冻结商机  FROZEN


/*线索状态显示对应关系*/
//UNRELEASED  未发布
//CHECKPENDING 待审核
//ORDERPENDING 待抢单、待派单
//FINISH 已转商机
//REJECT 已驳回
//CANCEL 已作废
//FROZEN 已冻结

/*商机状态对应关系*/
//SUBMIT 待提交资料
//CHECKPENDING 待审核
//CHECKFINISH 审核完成、进行中
//REJECT 审批驳回
//FINISH 已完成
//CANCEL 已作废
//FROZEN 已冻结


//CONFIRM  商机确认
//FOLLOWUP 项目跟进
//ASSESSMENT 项目评估
//TENDER 项目投标
//CONTRACT 签订合同

/***/ }),

/***/ 26:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _interfaces = __webpack_require__(1);

var stream = weex.requireModule('net'); // var host='http://cdtpwebgateway-hotfix-xdgc-idesign-dev.opaas.enncloud.cn/CDTPWebGateway/file/download';

var modal = weex.requireModule('modal');
var st = weex.requireModule("static");
// "UAT"
exports.default = {
    setHost: function setHost(call) {
        // stream.fetch({
        //     url:"http://xindi-version-xdgc-idesign-demo.ipaas.enncloud.cn/appAddress/getAddressByType",
        //     method: 'POST',
        //     headers: {
        //
        //     },
        //     type:'json',
        //     body: {appType:interfaces.env}
        // },function(e){
        //     var st = weex.requireModule('static') ;
        //     // modal.alert({message:e})
        //     st.set('host',{host:e.res.address});
        // });
        // http://staticresources-xdgc-uat.ipaas.enncloud.cn/app_version_server.json
        //
        var self = this;

        stream.get("http://mt.yunyigc.com:8002/StaticResources/app_version_server.json", {}, {}, function () {
            //start
        }, function (e) {
            //success
            var st = weex.requireModule('static');
            st.set('host', { host: e.res });
            call.call(this, { res: e });
        }, function (e) {}, function (e) {
            // exception
            modal.toast({ message: "网络异常" });
        });
        // stream.get("http://staticresources-xdgc-uat.ipaas.enncloud.cn/app_version_server.json",
        //     {},{},function(){
        //         //start
        //     },function(e){
        //         //success
        //         modal.alert({message:e})
        //         var st = weex.requireModule('static') ;
        //         // modal.alert({message:e})
        //         st.set('host',{host:e.res.address});
        //     },function(e){
        //
        //
        //     },function(e){
        //         // exception
        //
        //     });
    },
    getHost: function getHost() {
        var st = weex.requireModule('static');
        // modal.alert({message:e})
        var url = st.get("host").host;
        return url;
    }
};

/***/ }),

/***/ 29:
/***/ (function(module, exports) {

module.exports = {
  "bar": {
    "backgroundColor": "#EBF4FC",
    "height": "30",
    "marginTop": "8",
    "flexDirection": "row",
    "marginLeft": "10",
    "borderRadius": "15"
  },
  "bared": {
    "backgroundColor": "#268CF0",
    "height": "30"
  },
  "title": {
    "color": "#ffffff",
    "fontSize": "30",
    "position": "absolute",
    "top": "106",
    "left": "30",
    "fontWeight": "bold"
  },
  "titles": {
    "color": "#ffffff",
    "fontSize": "24",
    "position": "absolute",
    "top": "140",
    "left": "30",
    "fontWeight": "bold"
  },
  "img": {
    "width": "600",
    "height": "305"
  },
  "container": {
    "position": "fixed",
    "left": 0,
    "top": 0,
    "width": "750",
    "paddingBottom": "1450",
    "backgroundColor": "rgba(1,1,1,0.3)"
  },
  "button_pan": {
    "width": "600",
    "flexDirection": "row",
    "justifyContent": "space-between",
    "height": "86",
    "borderTopStyle": "solid",
    "borderTopColor": "#dddddd",
    "borderTopWidth": "1"
  },
  "button_pan1": {
    "width": "600",
    "flexDirection": "row",
    "justifyContent": "center",
    "height": "86",
    "borderTopStyle": "solid",
    "borderTopColor": "#dddddd",
    "borderTopWidth": "1"
  },
  "cencle": {
    "justifyContent": "center",
    "alignItems": "center",
    "width": "299",
    "height": "86"
  },
  "update": {
    "justifyContent": "center",
    "alignItems": "center",
    "width": "299",
    "height": "86"
  },
  "update1": {
    "justifyContent": "center",
    "alignItems": "center",
    "width": "300",
    "height": "86"
  },
  "cencle_text": {
    "color": "#666666",
    "fontSize": "28",
    "fontWeight": "bold"
  },
  "update_text": {
    "color": "#268CF0",
    "fontSize": "30"
  },
  "dialogPan": {
    "backgroundColor": "#ffffff",
    "width": "600",
    "marginLeft": "75",
    "marginTop": "380",
    "alignItems": "center",
    "borderRadius": "10"
  },
  "title_pan": {
    "height": "70",
    "width": "530",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "content_pan": {
    "marginBottom": "50",
    "width": "600",
    "marginLeft": "50"
  },
  "content": {
    "fontSize": "29",
    "fontWeight": "bold"
  },
  "content1": {
    "fontSize": "27",
    "marginTop": "10",
    "lineHeight": "40",
    "color": "#666666"
  }
}

/***/ }),

/***/ 30:
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
    name: 'update-dialog',
    props: {
        persent: {
            default: 0
        },
        must: {
            default: true
        },
        title: {
            default: "版本更新"
        },
        content: {
            default: "1.可服务地区优化\n2.常规bug修复"
        },
        num: {
            default: '1.0.0'
        },
        cencel: {
            default: "稍后再说"
        },
        confirm: {
            default: "马上更新"
        }
    },
    methods: {
        confirmc: function confirmc() {
            this.$emit('confirm', null);
        },
        cencelc: function cencelc() {
            this.$emit('cencel', null);
        },
        aaa: function aaa() {}
    }
};

/***/ }),

/***/ 31:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["container"],
    on: {
      "click": _vm.aaa
    }
  }, [_c('div', {
    staticClass: ["dialogPan"]
  }, [_c('image', {
    staticClass: ["img"],
    attrs: {
      "src": "root:img/update.png"
    }
  }), _c('text', {
    staticClass: ["title"]
  }, [_vm._v("升级到新版本")]), _c('text', {
    staticClass: ["titles"]
  }, [_vm._v("v" + _vm._s(_vm.num))]), _c('div', {
    staticClass: ["content_pan"]
  }, [_c('text', {
    staticClass: ["content"]
  }, [_vm._v("更新内容:")]), _c('text', {
    staticClass: ["content1"]
  }, [_vm._v(_vm._s(_vm.content))])]), (!_vm.must) ? _c('div', {
    staticClass: ["button_pan"]
  }, [_c('div', {
    staticClass: ["cencle"],
    on: {
      "click": _vm.cencelc
    }
  }, [_c('text', {
    staticClass: ["cencle_text"]
  }, [_vm._v(_vm._s(_vm.cencel))])]), _c('div', {
    staticStyle: {
      width: "2px",
      height: "50px",
      marginTop: "18px",
      backgroundColor: "#ddd"
    }
  }), _c('div', {
    staticClass: ["update"],
    on: {
      "click": _vm.confirmc
    }
  }, [_c('text', {
    staticClass: ["update_text"]
  }, [_vm._v(_vm._s(_vm.confirm))])])]) : _c('div', {
    staticClass: ["button_pan1"]
  }, [_c('div', {
    staticClass: ["update1"],
    on: {
      "click": _vm.confirmc
    }
  }, [_c('text', {
    staticClass: ["update_text"]
  }, [_vm._v(_vm._s(_vm.confirm))])])])])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 36:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _net = __webpack_require__(6);

var _net2 = _interopRequireDefault(_net);

var _Host = __webpack_require__(26);

var _Host2 = _interopRequireDefault(_Host);

var _interfaces = __webpack_require__(1);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var updater = weex.requireModule('updater'); // var host='http://cdtpwebgateway-hotfix-xdgc-idesign-dev.opaas.enncloud.cn/CDTPWebGateway/file/download';

var modal = weex.requireModule('modal');
var st = weex.requireModule("static");
exports.default = {
    update: function update(nowVersion, params, callback) {
        _net2.default.post(_interfaces.interfaces.updateCheck, params, function (res) {
            if (res.data == null) {
                callback(null);
                return;
            }
            var updateType = 0;
            //是不是必须更新 1必须2可选0不弹框
            var mustUpdate = res.data.mandatoryUpdate;
            //新版本
            var newVersion = res.data.version;
            //更新日志
            var updateContent = res.data.updateLog;

            var nowVersions = nowVersion.split('.');
            nowVersion = nowVersions[0] + nowVersions[1] + nowVersions[2]; //当前版本

            var newVersions = newVersion.split('.');
            newVersion = newVersions[0] + newVersions[1] + newVersions[2]; //最新版本

            var minVersion = res.data.versionMin;
            var minVersions = minVersion.split('.');
            minVersion = minVersions[0] + minVersions[1] + minVersions[2]; //最小必须更新版本
            if (weex.config.env.platform != "android") {
                var downloadUrl = _Host2.default.getHost() + _interfaces.interfaces.update + "?url=" + res.data.downloadUrl + "&fileName=" + res.data.fullName;
            } else {
                downloadUrl = _net2.default.getHost() + _interfaces.interfaces.update + "?url=" + res.data.downloadUrl + "&fileName=" + res.data.fullName;
            }
            st.remove("update");
            if (nowVersion < minVersion) {
                //如果当前版本小与最小版本
                updateType = 1;
                st.set("update", { update: 1 });
            } else if (nowVersion < newVersion && !mustUpdate) {
                //如果当前版本小与最新版本
                updateType = 2;
                st.set("update", { update: 1 });
            } else if (nowVersion < newVersion && mustUpdate) {
                updateType = 1;
                st.set("update", { update: 1 });
            }

            //             modal.alert({message:nowVersion<newVersion&&!mustUpdate})
            var callbackContent = { updateLog: updateContent,
                updateType: updateType,
                typeCode: res.data.typeCode,
                downloadUrl: downloadUrl,
                fullName: res.data.fullName,
                platform: weex.config.env.platform,
                nowVersion: nowVersion,
                minVersion: minVersion,
                newVersion: newVersion,
                remindUpdate: res.data.remindUpdate };
            callback(callbackContent); //updateType 0不需要更新，1必须更新，2可选择不更新
        });
    }
};

/***/ }),

/***/ 51:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(29)
)

/* script */
__vue_exports__ = __webpack_require__(30)

/* template */
var __vue_template__ = __webpack_require__(31)
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
__vue_options__.__file = "D:\\forWork\\work\\Project\\AppGroup\\AppGroup\\src\\components\\updateDialog.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-1542e38a"
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

/***/ 6:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _interfaces = __webpack_require__(1);

var modal = weex.requireModule('modal');
var pref = weex.requireModule('pref');
exports.default = {
    postShort: function postShort(type, appCode, weg, param, header, start, success, compelete) {
        var modal = weex.requireModule("modal");
        this.postFull(type, appCode, weg, param, header, start, success, function (res) {
            //fail
            modal.toast({ message: '连接失败' });
        }, function (res) {
            //exception
            modal.toast({ message: "网络异常" });
        }, function () {
            //compelete
            compelete();
        });
    },

    postFull: function postFull(type, appCode, weg, param, header, start, success, fail, exception, compelete) {
        var net = weex.requireModule("net");
        var st = weex.requireModule('static');
        var self = this;
        var url;
        url = _interfaces.interfaces.host + weg;
        var objkey = st.get('userInfo');
        if (objkey != undefined && objkey != '') {
            if (weex.config.env.platform == "iOS") {
                header = {
                    "x-auth-token": objkey.headers['x-auth-token']
                };
            } else {
                header = {
                    "x-auth-token": objkey.token
                };
            }
        }
        net.post(url, param, header, function () {
            //start
            start();
        }, function (e) {
            //success
            //可以在这里写过滤返回的数据信息
            if (e.res.code == -99) {
                modal.toast({ message: e.res.msg });
                //登录过期前往重新登录
                st.remove('userInfo');
                pref.remove('userInfo');
                var nav = weex.requireModule("navigator");
                var notify = weex.requireModule("notify");
                nav.push('root:login.js');
                if (weex.config.env.platform != "iOS") {
                    // modal.alert({message:weex.config.env.platform})
                    setTimeout(function () {
                        notify.sendNative("allDismiss", { data: '' });
                    }, 1000);
                } else {
                    return;
                }
            } else {
                //如果是登录接口，返回包括token的所有信息
                if (type == "login") {
                    success(e);
                } else {
                    success(e.res);
                }
            }
        }, function (e) {
            //compelete
            compelete();
        }, function (e) {
            // exception
            exception();
        });
    },
    get: function get(weg, param, success, appCode) {
        var progress = weex.requireModule("progress");
        this.getShort(appCode, weg, param, {}, function () {
            progress.show();
        }, success, function () {
            progress.dismiss();
        });
    },
    postNoProgress: function postNoProgress(weg, param, success, appCode, type) {
        this.postShort(type, appCode, weg, param, {}, function () {}, success, function () {});
    },
    post: function post(weg, param, success, appCode, type) {
        var self = this;
        var progress = weex.requireModule("progress");
        this.postShort(type, appCode, weg, param, {}, function () {
            progress.show();
        }, success, function () {
            progress.dismiss();
        });
        //      var st = weex.requireModule('static');
        //      if(st.get("host").host!=null&&st.get("host").host!=undefined){
        //          // modal.toast({message:"havehost"})
        //          var progress = weex.requireModule("progress")
        //          this.postShort(type,appCode, weg, param, {}, function () {
        //              progress.show();
        //          }, success, function () {
        //              progress.dismiss();
        //          });
        //      }else{
        //          var progress = weex.requireModule("progress")
        //          progress.show();
        //          // modal.toast({message:"donthavehost"})
        //          Host.setHost(function(res){
        //              // modal.toast({message:"hostCallBack"})
        //              self.postShort(type, appCode,weg, param, {}, function () {
        //              }, success, function () {
        //                  progress.dismiss();
        //              });
        //          });
        //      }
    },

    postJsonStr: function postJsonStr(weg, param, success, appCode) {
        var stream = weex.requireModule('stream');
        var st = weex.requireModule('static');
        var xToken;
        var url;
        url = _interfaces.interfaces.host + weg;
        var objkey = st.get('userInfo');
        if (objkey != undefined && objkey != '') {
            if (weex.config.env.platform == "iOS") {
                xToken = objkey.headers['x-auth-token'];
            } else {
                xToken = objkey.token;
            }
        }
        var data = JSON.stringify(param);
        stream.fetch({
            url: url,
            method: 'POST',
            headers: {
                "x-auth-token": xToken,
                'accept': 'application/json',
                'content-Type': 'application/json'
            },
            type: 'json',
            body: data
        }, success);
    },
    //     GET
    getShort: function getShort(appCode, weg, param, header, start, success, compelete) {
        var modal = weex.requireModule("modal");
        this.getFull(appCode, weg, param, header, start, success, function (res) {
            //          modal.toast({message:res.msg})
        }, function () {
            //          modal.toast({message:'网络异常！'})
        }, function () {
            //compelete

            compelete();
        });
    },
    getFull: function getFull(appCode, weg, param, header, start, success, fail, exception, compelete) {
        var net = weex.requireModule("net");
        var st = weex.requireModule('static');
        var url = _interfaces.interfaces.host + weg;
        var objkey = st.get('userInfo');
        if (objkey != undefined && objkey != '') {
            if (weex.config.env.platform == "iOS") {
                header = {
                    "x-auth-token": objkey.headers['x-auth-token']
                };
            } else {
                header = {
                    "x-auth-token": objkey.token
                };
            }
        }
        net.get(url, param, header, function () {
            //start
            start();
        }, function (e) {
            //success
            success(e.res);
        }, function (e) {
            //compelete
            compelete();
        }, function (e) {
            // exception
            exception();
        });
    },
    getNoHeader: function getNoHeader(weg, param, success) {
        var progress = weex.requireModule("progress");
        this.getShort(weg, param, {}, function () {
            progress.show();
        }, success, function () {
            progress.dismiss();
        });
    },
    getSlient: function getSlient(weg, param, success) {

        this.getFull(weg, param, {}, function () {}, success, function (res) {
            //fail

        }, function () {
            //exception

        }, function () {
            //compelete


        });
    }
};

/***/ }),

/***/ 611:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(612)
)

/* script */
__vue_exports__ = __webpack_require__(613)

/* template */
var __vue_template__ = __webpack_require__(614)
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
__vue_options__.__file = "D:\\forWork\\work\\Project\\AppGroup\\AppGroup\\src\\login.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-d3d7cda6"
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
module.exports.el = 'true'
new Vue(module.exports)


/***/ }),

/***/ 612:
/***/ (function(module, exports) {

module.exports = {
  "deleteImg": {
    "position": "absolute",
    "width": "36",
    "height": "36",
    "top": "37",
    "right": "20"
  },
  "bgt": {
    "width": "750"
  },
  "img": {
    "width": "80",
    "height": "80"
  },
  "topimg": {
    "width": "520",
    "height": "160",
    "position": "absolute",
    "top": "180",
    "left": "115"
  },
  "bgimg": {
    "width": "750",
    "marginTop": "148",
    "alignItems": "center",
    "textAlign": "center",
    "flexDirection": "row",
    "justifyContent": "center"
  },
  "inputdiv": {
    "marginLeft": "50",
    "width": "650",
    "height": "100",
    "flexDirection": "row",
    "borderBottomWidth": "1",
    "borderBottomColor": "#d3d3d3",
    "borderBottomStyle": "solid",
    "paddingTop": "30"
  },
  "inputimg": {
    "fontSize": "30",
    "color": "#333333",
    "width": "100",
    "lineHeight": "50"
  },
  "content": {
    "marginTop": "140"
  },
  "input": {
    "width": "500",
    "height": "50",
    "marginLeft": "30"
  },
  "textchar": {
    "fontSize": "29",
    "color": "#ffffff",
    "fontWeight": "bold"
  },
  "btn": {
    "marginTop": "100",
    "marginLeft": "40",
    "backgroundColor": "#268CF0",
    "width": "670",
    "height": "86",
    "justifyContent": "center",
    "alignItems": "center",
    "borderRadius": "43"
  },
  "btns": {
    "marginTop": "30",
    "marginLeft": "40",
    "width": "670",
    "flexDirection": "row",
    "justifyContent": "space-between"
  },
  "lichar": {
    "color": "#268CF0",
    "fontSize": "26"
  }
}

/***/ }),

/***/ 613:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _updateDialog = __webpack_require__(51);

var _updateDialog2 = _interopRequireDefault(_updateDialog);

var _net = __webpack_require__(6);

var _net2 = _interopRequireDefault(_net);

var _interfaces = __webpack_require__(1);

var _update = __webpack_require__(36);

var _update2 = _interopRequireDefault(_update);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var notify = weex.requireModule('notify'); //
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
//
//
//
//
//

var nav = weex.requireModule('navigator');

var pref = weex.requireModule('pref');
var utils = weex.requireModule('utils');
var st = weex.requireModule('static');

var modal = weex.requireModule('modal');
var updater = weex.requireModule('updater');
exports.default = {
    components: {
        UpdateDialog: _updateDialog2.default
    },
    data: {
        userInfo: {},
        remember: false,
        userName: "",
        password: "",
        dialogShow: false,
        pogressShow: false,
        nowVertion: "",
        must: false,
        updateInfo: {},
        value: 0,
        progress: 0
    },
    computed: {
        showName: function showName() {
            if (this.userName == "") {
                return false;
            } else {
                return true;
            }
        },
        showPwd: function showPwd() {
            if (this.password == "") {
                return false;
            } else {
                return true;
            }
        }
    },
    methods: {
        // thirdLogin(){
        //   modal.toast({message:'正在开发~~'})
        // },
        // ksdl(){
        //     modal.toast({message:'正在开发~~'})
        // },
        deleteImg: function deleteImg(index) {
            if (index == 1) {
                this.userName = "";
            } else {
                this.password = "";
            }
        },
        cencels: function cencels() {
            this.dialogShow = false;
        },
        confirms: function confirms() {
            var self = this;
            self.update();
            this.dialogShow = false;
        },
        update: function update() {
            var self = this;
            if (weex.config.env.platform != "iOS") {
                self.pogressShow = true;
                if (self.updateInfo.typeCode == "APP-Android") {
                    updater.download(self.updateInfo.downloadUrl, function (start) {
                        self.pogressShow = true;
                    }, function (progress) {
                        self.progress = progress;
                    }, function (complete) {
                        self.pogressShow = false;
                    }, function (error) {
                        self.pogressShow = false;
                        modal.alert({ message: "下载失败" });
                    });
                } else {
                    self.pogressShow = true;
                    updater.hotUpdate(self.updateInfo.downloadUrl, function () {
                        // modal.alert({message:"更新开始"});
                    }, function (percent) {
                        self.progress = percent;
                    }, function () {
                        self.pogressShow = false;
                        modal.toast({ message: "更新完成" });
                    }, function (error) {
                        modal.alert({ message: "更新失败" + error });
                        self.pogressShow = false;
                    });
                }
            } else {

                if (self.updateInfo.typeCode == "APP-Android") {
                    utils.openUrlBySafari("https://www.pgyer.com/xindiMarketManage");
                } else {
                    self.pogressShow = true;
                    updater.hotUpdate(self.updateInfo.downloadUrl, function () {
                        // modal.alert({message:"更新开始"});
                    }, function (percent) {
                        self.progress = percent;
                    }, function () {
                        modal.toast({ message: "更新完成" });
                        self.pogressShow = false;
                    }, function (error) {
                        modal.alert({ message: "更新失败" + error });
                        self.pogressShow = false;
                    });
                }
            }
        },
        login: function login() {
            var self = this;
            var nav = weex.requireModule('navigator');
            //记住密码
            // if(self.userName!=''&&self.password!=null){
            //     pref.set('loginInfo',{pwd:self.password,userName:self.userName,clientCode:'mb-app'});
            //     st.set('loginInfo',{pwd:self.password,u
            // serName:self.userName,clientCode:'mb-app'});
            //     modal.toast({message:'登录成功'})
            // // nav.pushWebApp("http://10.2.114.4:8890/js/www");
            // return;
            // }
            _net2.default.post(_interfaces.interfaces.login, { userCode: self.userName, userPassword: self.password, clientCode: 'app' }, function (res) {
                var data = res.res;
                if (data.code == 0) {
                    //是否记住密码
                    // if(self.remember){
                    // }
                    //记住密码
                    pref.set('userInfo', res);
                    st.set('userInfo', res);
                    pref.set('loginInfo', { userCode: self.userName, password: self.password });
                    st.setPUserInfo({ userCode: self.userName, password: self.password });
                    utils.setSchema('js');
                    nav.push('host.js');
                    // utils.JPushLogin(interfaces.env+"_"+res.res.data.currentUser.userCode);
                } else {
                    modal.toast({ message: data.msg });
                }
            }, "MobileApplication", "login");
        }
    },
    created: function created() {
        var self = this;
        if (weex.config.env.platform != "iOS") {
            notify.regist("login_dismiss", function (res) {
                nav.dismiss();
            });
        }
        var globalEvent = weex.requireModule('globalEvent');
        globalEvent.addEventListener("onPageInit", function (e) {
            self.userInfo = pref.get('userInfo');
            var notify = weex.requireModule('notify');
            notify.send('guide_dismiss', { a: '1' });
            notify.send('index_dismiss', { a: '1' });
        });
    }
};

/***/ }),

/***/ 614:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["bgt"]
  }, [_vm._m(0), _c('div', {
    staticClass: ["content"]
  }, [_c('div', {
    staticClass: ["inputdiv"]
  }, [_c('text', {
    staticClass: ["inputimg"]
  }, [_vm._v("账号")]), _c('input', {
    staticClass: ["input"],
    attrs: {
      "placeholder": "输入账号",
      "value": (_vm.userName)
    },
    on: {
      "input": function($event) {
        _vm.userName = $event.target.attr.value
      }
    }
  }), (_vm.showName) ? _c('image', {
    staticClass: ["deleteImg"],
    attrs: {
      "src": "root:img/delete.png"
    },
    on: {
      "click": function($event) {
        _vm.deleteImg(1)
      }
    }
  }) : _vm._e()]), _c('div', {
    staticClass: ["inputdiv"]
  }, [_c('text', {
    staticClass: ["inputimg"]
  }, [_vm._v("密码")]), _c('input', {
    staticClass: ["input"],
    attrs: {
      "placeholder": "输入密码",
      "type": "password",
      "value": (_vm.password)
    },
    on: {
      "input": function($event) {
        _vm.password = $event.target.attr.value
      }
    }
  }), (_vm.showPwd) ? _c('image', {
    staticClass: ["deleteImg"],
    attrs: {
      "src": "root:img/delete.png"
    },
    on: {
      "click": function($event) {
        _vm.deleteImg(2)
      }
    }
  }) : _vm._e()])]), _c('div', {
    staticClass: ["btn"],
    on: {
      "click": _vm.login
    }
  }, [_c('text', {
    staticClass: ["textchar"]
  }, [_vm._v("登录")])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["bgimg"]
  }, [_c('image', {
    staticClass: ["img"],
    attrs: {
      "src": "root:img/item8.png"
    }
  }), _c('text', {
    staticStyle: {
      fontSize: "34px",
      fontWeight: "bold",
      marginLeft: "30px"
    }
  }, [_vm._v("新地应用门户")])])
}]}
module.exports.render._withStripped = true

/***/ })

/******/ });