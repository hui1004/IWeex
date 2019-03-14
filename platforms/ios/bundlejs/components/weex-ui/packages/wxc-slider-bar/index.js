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
/******/ 	return __webpack_require__(__webpack_require__.s = 245);
/******/ })
/************************************************************************/
/******/ ({

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; /**
                                                                                                                                                                                                                                                                                * CopyRight (C) 2017-2022 Alibaba Group Holding Limited.
                                                                                                                                                                                                                                                                                * Created by Tw93 on 17/11/01
                                                                                                                                                                                                                                                                                */

var _urlParse = __webpack_require__(1);

var _urlParse2 = _interopRequireDefault(_urlParse);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var Utils = {
  UrlParser: _urlParse2.default,
  _typeof: function _typeof(obj) {
    return Object.prototype.toString.call(obj).slice(8, -1).toLowerCase();
  },
  isPlainObject: function isPlainObject(obj) {
    return Utils._typeof(obj) === 'object';
  },
  isString: function isString(obj) {
    return typeof obj === 'string';
  },
  isNonEmptyArray: function isNonEmptyArray() {
    var obj = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];

    return obj && obj.length > 0 && Array.isArray(obj) && typeof obj !== 'undefined';
  },
  isObject: function isObject(item) {
    return item && (typeof item === 'undefined' ? 'undefined' : _typeof2(item)) === 'object' && !Array.isArray(item);
  },
  isEmptyObject: function isEmptyObject(obj) {
    return Object.keys(obj).length === 0 && obj.constructor === Object;
  },
  decodeIconFont: function decodeIconFont(text) {
    // 正则匹配 图标和文字混排 eg: 我去上学校&#xe600;,天天不&#xe600;迟到
    var regExp = /&#x[a-z|0-9]{4,5};?/g;
    if (regExp.test(text)) {
      return text.replace(new RegExp(regExp, 'g'), function (iconText) {
        var replace = iconText.replace(/&#x/, '0x').replace(/;$/, '');
        return String.fromCharCode(replace);
      });
    } else {
      return text;
    }
  },
  mergeDeep: function mergeDeep(target) {
    for (var _len = arguments.length, sources = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
      sources[_key - 1] = arguments[_key];
    }

    if (!sources.length) return target;
    var source = sources.shift();
    if (Utils.isObject(target) && Utils.isObject(source)) {
      for (var key in source) {
        if (Utils.isObject(source[key])) {
          if (!target[key]) {
            Object.assign(target, _defineProperty({}, key, {}));
          }
          Utils.mergeDeep(target[key], source[key]);
        } else {
          Object.assign(target, _defineProperty({}, key, source[key]));
        }
      }
    }
    return Utils.mergeDeep.apply(Utils, [target].concat(sources));
  },
  appendProtocol: function appendProtocol(url) {
    if (/^\/\//.test(url)) {
      var bundleUrl = weex.config.bundleUrl;

      return 'http' + (/^https:/.test(bundleUrl) ? 's' : '') + ':' + url;
    }
    return url;
  },
  encodeURLParams: function encodeURLParams(url) {
    var parsedUrl = new _urlParse2.default(url, true);
    return parsedUrl.toString();
  },
  goToH5Page: function goToH5Page(jumpUrl) {
    var animated = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
    var callback = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

    var Navigator = weex.requireModule('navigator');
    var jumpUrlObj = new Utils.UrlParser(jumpUrl, true);
    var url = Utils.appendProtocol(jumpUrlObj.toString());
    Navigator.push({
      url: Utils.encodeURLParams(url),
      animated: animated.toString()
    }, callback);
  },

  env: {
    isTaobao: function isTaobao() {
      var appName = weex.config.env.appName;

      return (/(tb|taobao|淘宝)/i.test(appName)
      );
    },
    isTrip: function isTrip() {
      var appName = weex.config.env.appName;

      return appName === 'LX';
    },
    isBoat: function isBoat() {
      var appName = weex.config.env.appName;

      return appName === 'Boat' || appName === 'BoatPlayground';
    },
    isWeb: function isWeb() {
      var platform = weex.config.env.platform;

      return (typeof window === 'undefined' ? 'undefined' : _typeof2(window)) === 'object' && platform.toLowerCase() === 'web';
    },
    isIOS: function isIOS() {
      var platform = weex.config.env.platform;

      return platform.toLowerCase() === 'ios';
    },

    /**
     * 是否为 iPhone X or iPhoneXS or iPhoneXR or iPhoneXS Max
     * @returns {boolean}
     */
    isIPhoneX: function isIPhoneX() {
      var deviceHeight = weex.config.env.deviceHeight;

      if (Utils.env.isWeb()) {
        return (typeof window === 'undefined' ? 'undefined' : _typeof2(window)) !== undefined && window.screen && window.screen.width && window.screen.height && (parseInt(window.screen.width, 10) === 375 && parseInt(window.screen.height, 10) === 812 || parseInt(window.screen.width, 10) === 414 && parseInt(window.screen.height, 10) === 896);
      }
      return Utils.env.isIOS() && (deviceHeight === 2436 || deviceHeight === 2688 || deviceHeight == 1792);
    },
    isAndroid: function isAndroid() {
      var platform = weex.config.env.platform;

      return platform.toLowerCase() === 'android';
    },
    isAlipay: function isAlipay() {
      var appName = weex.config.env.appName;

      return appName === 'AP';
    },
    isTmall: function isTmall() {
      var appName = weex.config.env.appName;

      return (/(tm|tmall|天猫)/i.test(appName)
      );
    },
    isAliWeex: function isAliWeex() {
      return Utils.env.isTmall() || Utils.env.isTrip() || Utils.env.isTaobao();
    },

    /**
     * 获取weex屏幕真实的设置高度，需要减去导航栏高度
     * @returns {Number}
     */
    getPageHeight: function getPageHeight() {
      var env = weex.config.env;

      var navHeight = Utils.env.isWeb() ? 0 : Utils.env.isIPhoneX() ? 176 : 132;
      return env.deviceHeight / env.deviceWidth * 750 - navHeight;
    },

    /**
     * 获取weex屏幕真实的设置高度
     * @returns {Number}
     */
    getScreenHeight: function getScreenHeight() {
      var env = weex.config.env;

      return env.deviceHeight / env.deviceWidth * 750;
    }
  },

  /**
   * 版本号比较
   * @memberOf Utils
   * @param currVer {string}
   * @param promoteVer {string}
   * @returns {boolean}
   * @example
   *
   * const { Utils } = require('@ali/wx-bridge');
   * const { compareVersion } = Utils;
   * console.log(compareVersion('0.1.100', '0.1.11')); // 'true'
   */
  compareVersion: function compareVersion() {
    var currVer = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '0.0.0';
    var promoteVer = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '0.0.0';

    if (currVer === promoteVer) return true;
    var currVerArr = currVer.split('.');
    var promoteVerArr = promoteVer.split('.');
    var len = Math.max(currVerArr.length, promoteVerArr.length);
    for (var i = 0; i < len; i++) {
      var proVal = ~~promoteVerArr[i];
      var curVal = ~~currVerArr[i];
      if (proVal < curVal) {
        return true;
      } else if (proVal > curVal) {
        return false;
      }
    }
    return false;
  },

  /**
   * 分割数组
   * @param arr 被分割数组
   * @param size 分割数组的长度
   * @returns {Array}
   */
  arrayChunk: function arrayChunk() {
    var arr = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];
    var size = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 4;

    var groups = [];
    if (arr && arr.length > 0) {
      groups = arr.map(function (e, i) {
        return i % size === 0 ? arr.slice(i, i + size) : null;
      }).filter(function (e) {
        return e;
      });
    }
    return groups;
  },

  /*
   * 截断字符串
   * @param str 传入字符串
   * @param len 截断长度
   * @param hasDot 末尾是否...
   * @returns {String}
   */
  truncateString: function truncateString(str, len) {
    var hasDot = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : true;

    var newLength = 0;
    var newStr = '';
    var singleChar = '';
    var chineseRegex = /[^\x00-\xff]/g;
    var strLength = str.replace(chineseRegex, '**').length;
    for (var i = 0; i < strLength; i++) {
      singleChar = str.charAt(i).toString();
      if (singleChar.match(chineseRegex) !== null) {
        newLength += 2;
      } else {
        newLength++;
      }
      if (newLength > len) {
        break;
      }
      newStr += singleChar;
    }

    if (hasDot && strLength > len) {
      newStr += '...';
    }
    return newStr;
  },

  /*
   * 转换 obj 为 url params参数
   * @param obj 传入字符串
   * @returns {String}
   */
  objToParams: function objToParams(obj) {
    var str = "";
    for (var key in obj) {
      if (str !== "") {
        str += "&";
      }
      str += key + "=" + encodeURIComponent(obj[key]);
    }
    return str;
  },

  /*
   * 转换 url params参数为obj
   * @param str 传入url参数字符串
   * @returns {Object}
   */
  paramsToObj: function paramsToObj(str) {
    var obj = {};
    try {
      obj = JSON.parse('{"' + decodeURI(str).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}');
    } catch (e) {
      console.log(e);
    }
    return obj;
  },

  animation: {
    /**
     * 返回定义页面转场动画起初的位置
     * @param ref
     * @param transform 运动类型
     * @param status
     * @param callback 回调函数
     */
    pageTransitionAnimation: function pageTransitionAnimation(ref, transform, status, callback) {
      var animation = weex.requireModule('animation');
      animation.transition(ref, {
        styles: {
          transform: transform
        },
        duration: status ? 250 : 300, // ms
        timingFunction: status ? 'ease-in' : 'ease-out',
        delay: 0 // ms
      }, function () {
        callback && callback();
      });
    }
  },
  uiStyle: {
    /**
     * 返回定义页面转场动画起初的位置
     * @param animationType 页面转场动画的类型 push，model
     * @param size 分割数组的长度
     * @returns {}
     */
    pageTransitionAnimationStyle: function pageTransitionAnimationStyle(animationType) {
      if (animationType === 'push') {
        return {
          left: '750px',
          top: '0px',
          height: weex.config.env.deviceHeight / weex.config.env.deviceWidth * 750 + 'px'
        };
      } else if (animationType === 'model') {
        return {
          top: weex.config.env.deviceHeight / weex.config.env.deviceWidth * 750 + 'px',
          left: '0px',
          height: weex.config.env.deviceHeight / weex.config.env.deviceWidth * 750 + 'px'
        };
      }
      return {};
    }
  }
};

exports.default = Utils;

/***/ }),

/***/ 1:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var required = __webpack_require__(2)
  , qs = __webpack_require__(3)
  , protocolre = /^([a-z][a-z0-9.+-]*:)?(\/\/)?([\S\s]*)/i
  , slashes = /^[A-Za-z][A-Za-z0-9+-.]*:\/\//;

/**
 * These are the parse rules for the URL parser, it informs the parser
 * about:
 *
 * 0. The char it Needs to parse, if it's a string it should be done using
 *    indexOf, RegExp using exec and NaN means set as current value.
 * 1. The property we should set when parsing this value.
 * 2. Indication if it's backwards or forward parsing, when set as number it's
 *    the value of extra chars that should be split off.
 * 3. Inherit from location if non existing in the parser.
 * 4. `toLowerCase` the resulting value.
 */
var rules = [
  ['#', 'hash'],                        // Extract from the back.
  ['?', 'query'],                       // Extract from the back.
  function sanitize(address) {          // Sanitize what is left of the address
    return address.replace('\\', '/');
  },
  ['/', 'pathname'],                    // Extract from the back.
  ['@', 'auth', 1],                     // Extract from the front.
  [NaN, 'host', undefined, 1, 1],       // Set left over value.
  [/:(\d+)$/, 'port', undefined, 1],    // RegExp the back.
  [NaN, 'hostname', undefined, 1, 1]    // Set left over.
];

/**
 * These properties should not be copied or inherited from. This is only needed
 * for all non blob URL's as a blob URL does not include a hash, only the
 * origin.
 *
 * @type {Object}
 * @private
 */
var ignore = { hash: 1, query: 1 };

/**
 * The location object differs when your code is loaded through a normal page,
 * Worker or through a worker using a blob. And with the blobble begins the
 * trouble as the location object will contain the URL of the blob, not the
 * location of the page where our code is loaded in. The actual origin is
 * encoded in the `pathname` so we can thankfully generate a good "default"
 * location from it so we can generate proper relative URL's again.
 *
 * @param {Object|String} loc Optional default location object.
 * @returns {Object} lolcation object.
 * @public
 */
function lolcation(loc) {
  var globalVar;

  if (typeof window !== 'undefined') globalVar = window;
  else if (typeof global !== 'undefined') globalVar = global;
  else if (typeof self !== 'undefined') globalVar = self;
  else globalVar = {};

  var location = globalVar.location || {};
  loc = loc || location;

  var finaldestination = {}
    , type = typeof loc
    , key;

  if ('blob:' === loc.protocol) {
    finaldestination = new Url(unescape(loc.pathname), {});
  } else if ('string' === type) {
    finaldestination = new Url(loc, {});
    for (key in ignore) delete finaldestination[key];
  } else if ('object' === type) {
    for (key in loc) {
      if (key in ignore) continue;
      finaldestination[key] = loc[key];
    }

    if (finaldestination.slashes === undefined) {
      finaldestination.slashes = slashes.test(loc.href);
    }
  }

  return finaldestination;
}

/**
 * @typedef ProtocolExtract
 * @type Object
 * @property {String} protocol Protocol matched in the URL, in lowercase.
 * @property {Boolean} slashes `true` if protocol is followed by "//", else `false`.
 * @property {String} rest Rest of the URL that is not part of the protocol.
 */

/**
 * Extract protocol information from a URL with/without double slash ("//").
 *
 * @param {String} address URL we want to extract from.
 * @return {ProtocolExtract} Extracted information.
 * @private
 */
function extractProtocol(address) {
  var match = protocolre.exec(address);

  return {
    protocol: match[1] ? match[1].toLowerCase() : '',
    slashes: !!match[2],
    rest: match[3]
  };
}

/**
 * Resolve a relative URL pathname against a base URL pathname.
 *
 * @param {String} relative Pathname of the relative URL.
 * @param {String} base Pathname of the base URL.
 * @return {String} Resolved pathname.
 * @private
 */
function resolve(relative, base) {
  var path = (base || '/').split('/').slice(0, -1).concat(relative.split('/'))
    , i = path.length
    , last = path[i - 1]
    , unshift = false
    , up = 0;

  while (i--) {
    if (path[i] === '.') {
      path.splice(i, 1);
    } else if (path[i] === '..') {
      path.splice(i, 1);
      up++;
    } else if (up) {
      if (i === 0) unshift = true;
      path.splice(i, 1);
      up--;
    }
  }

  if (unshift) path.unshift('');
  if (last === '.' || last === '..') path.push('');

  return path.join('/');
}

/**
 * The actual URL instance. Instead of returning an object we've opted-in to
 * create an actual constructor as it's much more memory efficient and
 * faster and it pleases my OCD.
 *
 * It is worth noting that we should not use `URL` as class name to prevent
 * clashes with the global URL instance that got introduced in browsers.
 *
 * @constructor
 * @param {String} address URL we want to parse.
 * @param {Object|String} [location] Location defaults for relative paths.
 * @param {Boolean|Function} [parser] Parser for the query string.
 * @private
 */
function Url(address, location, parser) {
  if (!(this instanceof Url)) {
    return new Url(address, location, parser);
  }

  var relative, extracted, parse, instruction, index, key
    , instructions = rules.slice()
    , type = typeof location
    , url = this
    , i = 0;

  //
  // The following if statements allows this module two have compatibility with
  // 2 different API:
  //
  // 1. Node.js's `url.parse` api which accepts a URL, boolean as arguments
  //    where the boolean indicates that the query string should also be parsed.
  //
  // 2. The `URL` interface of the browser which accepts a URL, object as
  //    arguments. The supplied object will be used as default values / fall-back
  //    for relative paths.
  //
  if ('object' !== type && 'string' !== type) {
    parser = location;
    location = null;
  }

  if (parser && 'function' !== typeof parser) parser = qs.parse;

  location = lolcation(location);

  //
  // Extract protocol information before running the instructions.
  //
  extracted = extractProtocol(address || '');
  relative = !extracted.protocol && !extracted.slashes;
  url.slashes = extracted.slashes || relative && location.slashes;
  url.protocol = extracted.protocol || location.protocol || '';
  address = extracted.rest;

  //
  // When the authority component is absent the URL starts with a path
  // component.
  //
  if (!extracted.slashes) instructions[3] = [/(.*)/, 'pathname'];

  for (; i < instructions.length; i++) {
    instruction = instructions[i];

    if (typeof instruction === 'function') {
      address = instruction(address);
      continue;
    }

    parse = instruction[0];
    key = instruction[1];

    if (parse !== parse) {
      url[key] = address;
    } else if ('string' === typeof parse) {
      if (~(index = address.indexOf(parse))) {
        if ('number' === typeof instruction[2]) {
          url[key] = address.slice(0, index);
          address = address.slice(index + instruction[2]);
        } else {
          url[key] = address.slice(index);
          address = address.slice(0, index);
        }
      }
    } else if ((index = parse.exec(address))) {
      url[key] = index[1];
      address = address.slice(0, index.index);
    }

    url[key] = url[key] || (
      relative && instruction[3] ? location[key] || '' : ''
    );

    //
    // Hostname, host and protocol should be lowercased so they can be used to
    // create a proper `origin`.
    //
    if (instruction[4]) url[key] = url[key].toLowerCase();
  }

  //
  // Also parse the supplied query string in to an object. If we're supplied
  // with a custom parser as function use that instead of the default build-in
  // parser.
  //
  if (parser) url.query = parser(url.query);

  //
  // If the URL is relative, resolve the pathname against the base URL.
  //
  if (
      relative
    && location.slashes
    && url.pathname.charAt(0) !== '/'
    && (url.pathname !== '' || location.pathname !== '')
  ) {
    url.pathname = resolve(url.pathname, location.pathname);
  }

  //
  // We should not add port numbers if they are already the default port number
  // for a given protocol. As the host also contains the port number we're going
  // override it with the hostname which contains no port number.
  //
  if (!required(url.port, url.protocol)) {
    url.host = url.hostname;
    url.port = '';
  }

  //
  // Parse down the `auth` for the username and password.
  //
  url.username = url.password = '';
  if (url.auth) {
    instruction = url.auth.split(':');
    url.username = instruction[0] || '';
    url.password = instruction[1] || '';
  }

  url.origin = url.protocol && url.host && url.protocol !== 'file:'
    ? url.protocol +'//'+ url.host
    : 'null';

  //
  // The href is just the compiled result.
  //
  url.href = url.toString();
}

/**
 * This is convenience method for changing properties in the URL instance to
 * insure that they all propagate correctly.
 *
 * @param {String} part          Property we need to adjust.
 * @param {Mixed} value          The newly assigned value.
 * @param {Boolean|Function} fn  When setting the query, it will be the function
 *                               used to parse the query.
 *                               When setting the protocol, double slash will be
 *                               removed from the final url if it is true.
 * @returns {URL} URL instance for chaining.
 * @public
 */
function set(part, value, fn) {
  var url = this;

  switch (part) {
    case 'query':
      if ('string' === typeof value && value.length) {
        value = (fn || qs.parse)(value);
      }

      url[part] = value;
      break;

    case 'port':
      url[part] = value;

      if (!required(value, url.protocol)) {
        url.host = url.hostname;
        url[part] = '';
      } else if (value) {
        url.host = url.hostname +':'+ value;
      }

      break;

    case 'hostname':
      url[part] = value;

      if (url.port) value += ':'+ url.port;
      url.host = value;
      break;

    case 'host':
      url[part] = value;

      if (/:\d+$/.test(value)) {
        value = value.split(':');
        url.port = value.pop();
        url.hostname = value.join(':');
      } else {
        url.hostname = value;
        url.port = '';
      }

      break;

    case 'protocol':
      url.protocol = value.toLowerCase();
      url.slashes = !fn;
      break;

    case 'pathname':
    case 'hash':
      if (value) {
        var char = part === 'pathname' ? '/' : '#';
        url[part] = value.charAt(0) !== char ? char + value : value;
      } else {
        url[part] = value;
      }
      break;

    default:
      url[part] = value;
  }

  for (var i = 0; i < rules.length; i++) {
    var ins = rules[i];

    if (ins[4]) url[ins[1]] = url[ins[1]].toLowerCase();
  }

  url.origin = url.protocol && url.host && url.protocol !== 'file:'
    ? url.protocol +'//'+ url.host
    : 'null';

  url.href = url.toString();

  return url;
}

/**
 * Transform the properties back in to a valid and full URL string.
 *
 * @param {Function} stringify Optional query stringify function.
 * @returns {String} Compiled version of the URL.
 * @public
 */
function toString(stringify) {
  if (!stringify || 'function' !== typeof stringify) stringify = qs.stringify;

  var query
    , url = this
    , protocol = url.protocol;

  if (protocol && protocol.charAt(protocol.length - 1) !== ':') protocol += ':';

  var result = protocol + (url.slashes ? '//' : '');

  if (url.username) {
    result += url.username;
    if (url.password) result += ':'+ url.password;
    result += '@';
  }

  result += url.host + url.pathname;

  query = 'object' === typeof url.query ? stringify(url.query) : url.query;
  if (query) result += '?' !== query.charAt(0) ? '?'+ query : query;

  if (url.hash) result += url.hash;

  return result;
}

Url.prototype = { set: set, toString: toString };

//
// Expose the URL parser and some additional properties that might be useful for
// others or testing.
//
Url.extractProtocol = extractProtocol;
Url.location = lolcation;
Url.qs = qs;

module.exports = Url;


/***/ }),

/***/ 2:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Check if we're required to add a port number.
 *
 * @see https://url.spec.whatwg.org/#default-port
 * @param {Number|String} port Port number we need to check
 * @param {String} protocol Protocol we need to check against.
 * @returns {Boolean} Is it a default port for the given protocol
 * @api private
 */
module.exports = function required(port, protocol) {
  protocol = protocol.split(':')[0];
  port = +port;

  if (!port) return false;

  switch (protocol) {
    case 'http':
    case 'ws':
    return port !== 80;

    case 'https':
    case 'wss':
    return port !== 443;

    case 'ftp':
    return port !== 21;

    case 'gopher':
    return port !== 70;

    case 'file':
    return false;
  }

  return port !== 0;
};


/***/ }),

/***/ 245:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _index = __webpack_require__(246);

var _index2 = _interopRequireDefault(_index);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_index2.default.el = '#root';
new Vue(_index2.default);

/***/ }),

/***/ 246:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(247)
)

/* script */
__vue_exports__ = __webpack_require__(248)

/* template */
var __vue_template__ = __webpack_require__(249)
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
__vue_options__.__file = "/Users/xindi/Desktop/iosPrictice/IWeex/IWeex/src/components/weex-ui/packages/wxc-slider-bar/index.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-fe7d4e70"
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

/***/ 247:
/***/ (function(module, exports) {

module.exports = {
  "slider-bar-container": {
    "height": "56",
    "justifyContent": "center",
    "alignItems": "center",
    "overflow": "hidden"
  },
  "range-bar": {
    "overflow": "hidden"
  },
  "value-bar": {
    "height": "4",
    "overflow": "hidden"
  },
  "slide-block": {
    "width": "56",
    "height": "56",
    "backgroundColor": "#ffffff",
    "borderRadius": "28",
    "borderWidth": "1",
    "borderColor": "rgba(0,0,0,0.1)",
    "position": "absolute",
    "left": 0,
    "bottom": 0
  }
}

/***/ }),

/***/ 248:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _utils = __webpack_require__(0);

var _utils2 = _interopRequireDefault(_utils);

var _bindEnv = __webpack_require__(5);

var _bindEnv2 = _interopRequireDefault(_bindEnv);

var _indexWeex = __webpack_require__(4);

var _indexWeex2 = _interopRequireDefault(_indexWeex);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var animation = weex.requireModule('animation'); //
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

var dom = weex.requireModule('dom');

exports.default = {
  data: function data() {
    return {
      env: 'weex',
      diffX1: 0,
      diffX2: 0,
      barWidth: 0,
      preventMoveEvent: true,
      minDist: 0,
      selectRange: [0, 0],
      blockRadius: 28,
      DPR: 1,
      timeout: 100,
      isAndroid: _utils2.default.env.isAndroid()
    };
  },
  props: {
    length: {
      type: Number,
      default: 500
    },
    height: {
      type: Number,
      default: 4
    },
    // 是否双滑块模式
    range: {
      type: Boolean,
      default: false
    },
    // 最小值
    min: {
      type: Number,
      default: 0
    },
    // 最大值
    max: {
      type: Number,
      default: 100
    },
    // 最小取值范围，用于范围选择范围最小差值
    minDiff: {
      type: Number,
      default: 5
    },
    // 设置当前取值。当 range 为 false 时，使用 number，否则用 [number, number]
    value: {
      type: [Number, Array],
      default: 0
    },
    // 设置初始取值。当 range 为 false 时，使用 number，否则用 [number, number]
    defaultValue: {
      type: [Number, Array],
      default: 0
    },
    // 值为 true 时，滑块为禁用状态
    disabled: {
      type: Boolean,
      default: false
    },
    invalidColor: {
      type: String,
      default: '#E0E0E0'
    },
    validColor: {
      type: String,
      default: '#EE9900'
    },
    disabledColor: {
      type: String,
      default: '#AAA'
    }
  },
  watch: {
    value: function value(e) {
      if (!this.range) {
        this.diffX1 = this._getDiffX(e || this.defaultValue);
      } else {
        this.diffX1 = this._getDiffX(e[0] || this.defaultValue[0]);
        this.diffX2 = this._getDiffX(e[1] || this.defaultValue[1]);
        this.barWidth = this.diffX2 - this.diffX1;
      }
    }
  },
  created: function created() {
    if (_utils2.default.env.isWeb()) {
      this.env = 'web';
      this.DPR = window.devicePixelRatio ? window.devicePixelRatio : 1;
    } else {
      this.DPR = weex.config.env.scale;
    }
  },
  mounted: function mounted() {
    var _this = this;

    this.block1 = this.$refs['slide-block-1']; // 左侧滑块
    this.block2 = this.$refs['slide-block-2']; // 右侧滑块
    this.valueBar = this.$refs['value-bar']; // 黄色值条
    this.barContainer = this.$refs['bar-container']; // 滚动条容器

    if (!this.range) {
      this.diffX1 = this._getDiffX(this.value || this.defaultValue);
    } else {
      this.diffX1 = this._getDiffX(this.value[0] || this.defaultValue[0]);
      this.diffX2 = this._getDiffX(this.value[1] || this.defaultValue[1]);
      this.barWidth = this.diffX2 - this.diffX1;
    }

    // 是否支持expresstionBinding
    if (_bindEnv2.default.supportsEB() && _indexWeex2.default.prepare) {
      this.block1 && _indexWeex2.default.prepare({
        anchor: this.block1.ref,
        eventType: 'pan'
      });
      this.block2 && _indexWeex2.default.prepare({
        anchor: this.block2.ref,
        eventType: 'pan'
      });
      this.valueBar && _indexWeex2.default.prepare({
        anchor: this.valueBar.ref,
        eventType: 'pan'
      });
    }

    if (this.range) {
      this.selectRange = this.value || this.defaultValue; // 初始化范围选择返回数据
      this.minDist = this.minDiff / (this.max - this.min) * this.length; // 滑块1、2之前最小间距
    }

    // 由于weex在mounted后渲染是异步的不能确保元素渲染完成，需要异步执行
    setTimeout(function () {
      dom.getComponentRect(_this.barContainer, function (option) {
        var left = option.size.left;

        _this.leftDiffX = left;
      });
    }, 100);
  },


  computed: {
    containerStyle: function containerStyle() {
      return {
        width: this.length + 56 + 'px',
        height: '56px'
      };
    },
    rangeBarStyle: function rangeBarStyle() {
      return {
        width: this.length + 'px',
        height: this.height + 'px',
        flexDirection: 'row',
        backgroundColor: this.invalidColor
      };
    },
    valueBarStyle: function valueBarStyle() {
      var left = 0;
      var width = 0;

      if (!this.range) {
        left = this.diffX1 - this.length;
        width = this.length;
      } else {
        left = this.diffX1;
        width = this.diffX2 - this.diffX1;
      }

      return {
        width: width + 'px',
        height: this.height + 'px',
        transform: 'translateX(' + left + 'px)',
        backgroundColor: this.disabled ? this.disabledColor : this.validColor
      };
    },
    blockStyle1: function blockStyle1() {
      var left = this.diffX1;
      return {
        transform: 'translateX(' + left + 'px)'
      };
    },
    blockStyle2: function blockStyle2() {
      return {
        transform: 'translateX(' + this.diffX2 + 'px)'
      };
    }
  },
  methods: {

    // 更新单选值或最小值
    weexHandler1: function weexHandler1(e) {
      var _this2 = this;

      var self = this;
      switch (e.state) {
        case 'start':
          self.bindBlock1();
          break;
        case 'move':
          dom.getComponentRect(this.block1, function (option) {
            var left = option.size.left;

            var value = _this2._getValue(left - _this2.leftDiffX);
            if (!_this2.range) {
              _this2.$emit('updateValue', value);
            } else {
              _this2.selectRange[0] = value;
              _this2.$emit('updateValue', _this2.selectRange);
            }
          });
          break;
        case 'end':
          break;
        default:
          break;
      }
    },


    // 更新最大值
    weexHandler2: function weexHandler2(e) {
      var _this3 = this;

      var self = this;

      switch (e.state) {
        case 'start':
          self.bindBlock2();
          break;
        case 'move':
          dom.getComponentRect(this.block2, function (option) {
            var left = option.size.left;

            _this3.selectRange[1] = _this3._getValue(left - _this3.leftDiffX);
            _this3.$emit('updateValue', _this3.selectRange);
          });
          break;
        case 'end':
          break;
        default:
          break;
      }
    },
    weexStartHandler1: function weexStartHandler1() {
      var _this4 = this;

      // 由于android端不支持 horizontalpan 的move事件，使用setInterval hack方案
      if (!this.isAndroid) {
        return;
      }
      this.firstInterval = setInterval(function () {
        dom.getComponentRect(_this4.block1, function (option) {
          var left = option.size.left;

          var value = _this4._getValue(left - _this4.leftDiffX);
          if (!_this4.range) {
            _this4.$emit('updateValue', value);
          } else {
            _this4.selectRange[0] = value;
            _this4.$emit('updateValue', _this4.selectRange);
          }
        });
      }, this.timeout);
    },
    weexStartHandler2: function weexStartHandler2() {
      var _this5 = this;

      if (!this.isAndroid) {
        return;
      }
      // 由于android端不支持 horizontalpan 的move事件，使用setInterval hack方案
      this.secondInterval = setInterval(function () {
        dom.getComponentRect(_this5.block2, function (option) {
          var left = option.size.left;

          _this5.selectRange[1] = _this5._getValue(left - _this5.leftDiffX);
          _this5.$emit('updateValue', _this5.selectRange);
        });
      }, this.timeout);
    },


    // 清除定时器
    weexEndHandler: function weexEndHandler() {
      if (!this.isAndroid) {
        return;
      }
      this.firstInterval && clearInterval(this.firstInterval);
      this.secondInterval && clearInterval(this.secondInterval);
    },
    webStartHandler: function webStartHandler(e) {
      if (this.env === 'weex') {
        return;
      }
      this.startX = e.touch.clientX;
      this.startDiffX1 = this.diffX1;
      this.startDiffX2 = this.diffX2;
    },
    webMoveHandler1: function webMoveHandler1(e) {
      if (this.env === 'weex' || this.disabled) {
        return;
      }

      var deltaX = (e.touch.clientX - this.startX) * this.DPR;
      var diff = this.startDiffX1 + deltaX;

      var max = this.length;
      if (this.range) {
        max = this.diffX2 - this.minDist;
      }

      if (diff > 0 && diff < max) {
        this.diffX1 = diff;
        animation.transition(this.block1, {
          styles: {
            transform: 'translateX(' + this.diffX1 + 'px)'
          }
        }, function () {});
        if (!this.range) {
          this.$emit('updateValue', this._getValue(this.diffX1));
        } else {
          this.selectRange[0] = this._getValue(this.diffX1);
          this.$emit('updateValue', this.selectRange);
        }
      }
    },
    webMoveHandler2: function webMoveHandler2(e) {
      if (this.env === 'weex' || this.disabled) {
        return;
      }

      var deltaX = (e.touch.clientX - this.startX) * this.DPR;
      var diff = this.startDiffX2 + deltaX;
      var min = this.diffX1 + this.minDist;
      var max = this.length;

      if (diff > min && diff < max) {
        this.diffX2 = diff;
        animation.transition(this.block2, {
          styles: {
            transform: 'translateX(' + this.diffX2 + 'px)'
          }
        }, function () {});
        if (!this.range) {
          this.$emit('updateValue', this._getValue(this.diffX2));
        } else {
          this.selectRange[1] = this._getValue(this.diffX2);
          this.$emit('updateValue', this.selectRange);
        }
      }
    },
    bindBlock1: function bindBlock1() {
      var self = this;

      // 如果禁用，不行进行表达式绑定
      if (self.disabled) {
        _indexWeex2.default.unbind({
          token: this.gesToken1,
          eventType: 'pan'
        });
        this.gesToken1 = 0;
        return;
      }

      // 初始化按钮&条的大小范围
      var blockMax1 = 0;
      if (self.range) {
        blockMax1 = self.diffX2 - self.minDist;
      } else {
        blockMax1 = self.length;
      }

      var barMax1 = self.diffX2;

      if (!self.range) {

        var startLeft = self.diffX1 - blockMax1 - self.minDist;

        var props = [{
          element: self.block1.ref,
          property: 'transform.translateX',
          expression: 'min(' + blockMax1 + ', max(x + ' + self.diffX1 + ', 0))'
        }, {
          element: self.valueBar.ref,
          property: 'transform.translateX',
          expression: 'min(0, max(x + ' + startLeft + ', -' + blockMax1 + '))'
        }];

        var gesTokenObj = _indexWeex2.default.bind({
          anchor: self.block1.ref,
          eventType: 'pan',
          props: props
        }, function (e) {
          if (e.state === 'end' || e.state === 'cancel' || e.state === 'exit') {
            var range = self.getRange();
            // 限制diffX1范围
            self.diffX1 = self._restrictValue(range.rangeX1, self.diffX1 + e.deltaX);
            self.bindBlock1();
          }
        });
        this.gesToken1 = gesTokenObj.token;
      } else {

        // 选范围
        var _props = [{
          element: self.block1.ref,
          property: 'transform.translateX',
          expression: 'min(' + blockMax1 + ', max(x + ' + self.diffX1 + ', 0))'
        }, {
          element: self.valueBar.ref,
          property: 'transform.translateX',
          expression: 'min(' + blockMax1 + ', max(x + ' + self.diffX1 + ', 0))'
        }, {
          element: self.valueBar.ref,
          property: 'width',
          expression: 'min(' + barMax1 + ', max(0, ' + self.barWidth + ' - x))'
        }];

        var _gesTokenObj = _indexWeex2.default.bind({
          anchor: self.block1.ref,
          eventType: 'pan',
          props: _props
        }, function (e) {
          if (e.state === 'end' || e.state === 'cancel' || e.state === 'exit') {
            var range = self.getRange();
            self.barWidth = self._restrictValue(range.rangeX1, self.barWidth - e.deltaX);
            self.diffX1 = self._restrictValue(range.rangeX1, self.diffX1 + e.deltaX);
            self.bindBlock1();
          }
        });
        this.gesToken1 = _gesTokenObj.token;
      }
    },
    bindBlock2: function bindBlock2() {
      var self = this;

      // 如果禁用，不行进行表达式绑定
      if (self.disabled) {
        _indexWeex2.default.unbind({
          token: this.gesToken2,
          eventType: 'pan'
        });
        this.gesToken2 = 0;
        return;
      }

      // 初始化按钮&条的大小范围
      var blockMax1 = 0;
      if (self.range) {
        blockMax1 = self.diffX2 - self.minDist;
      } else {
        blockMax1 = self.length;
      }

      var blockMax2 = self.length;
      var blockMin2 = self.diffX1 + self.minDist;
      var barMax2 = self.length - self.diffX1;

      var props = [{
        element: self.block2.ref,
        property: 'transform.translateX',
        expression: 'min(' + blockMax2 + ', max(x + ' + self.diffX2 + ', ' + blockMin2 + '))'
      }, {
        element: self.valueBar.ref,
        property: 'width',
        expression: 'min(' + barMax2 + ', max(0, x + ' + self.barWidth + '))'
      }];

      var gesTokenObj = _indexWeex2.default.bind({
        anchor: self.block2.ref,
        eventType: 'pan',
        props: props
      }, function (e) {
        if (e.state === 'end' || e.state === 'cancel' || e.state === 'exit') {
          var range = self.getRange();
          self.barWidth = self._restrictValue([0, self.length - self.diffX1], self.barWidth + e.deltaX);
          self.diffX2 = self._restrictValue(range.rangeX2, self.diffX2 + e.deltaX);
          self.bindBlock2();
        }
      });
      this.gesToken2 = gesTokenObj.token;
    },


    // 获取diffx1 diffx2 取值范围
    getRange: function getRange() {
      if (!this.range) {
        return {
          rangeX1: [0, this.length]
        };
      } else {
        return {
          rangeX1: [0, this.diffX2 - this.minDist],
          rangeX2: [this.diffX1 + this.minDist, this.length]
        };
      }
    },


    // 限制取值范围
    _restrictValue: function _restrictValue(range, value) {
      if (range && range.length && range.length === 2) {
        if (value < range[0]) {
          return range[0];
        } else if (value > range[1]) {
          return range[1];
        } else {
          return value;
        }
      }
      return;
    },


    // 根据x方向偏移量计算value
    _getValue: function _getValue(diffX) {
      return Math.round(diffX / this.length * (this.max - this.min) + this.min);
    },


    // 根据value和length计算x方向偏移值
    _getDiffX: function _getDiffX(value) {
      return (value - this.min) / (this.max - this.min) * this.length;
    }
  }
};

/***/ }),

/***/ 249:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    ref: "bar-container",
    staticClass: ["slider-bar-container"],
    style: _vm.containerStyle
  }, [_c('div', {
    staticClass: ["range-bar"],
    style: _vm.rangeBarStyle
  }, [_c('div', {
    ref: "value-bar",
    staticClass: ["value-bar"],
    style: _vm.valueBarStyle
  }, [_c('div')])]), _c('div', {
    ref: "slide-block-1",
    staticClass: ["slide-block"],
    style: _vm.blockStyle1,
    attrs: {
      "preventMoveEvent": _vm.preventMoveEvent
    },
    on: {
      "panstart": _vm.webStartHandler,
      "panmove": _vm.webMoveHandler1,
      "touchstart": _vm.weexStartHandler1,
      "touchend": _vm.weexEndHandler,
      "horizontalpan": _vm.weexHandler1
    }
  }, [_c('div')]), (_vm.range) ? _c('div', {
    ref: "slide-block-2",
    staticClass: ["slide-block"],
    style: _vm.blockStyle2,
    attrs: {
      "preventMoveEvent": _vm.preventMoveEvent
    },
    on: {
      "panstart": _vm.webStartHandler,
      "panmove": _vm.webMoveHandler2,
      "touchstart": _vm.weexStartHandler2,
      "touchend": _vm.weexEndHandler,
      "horizontalpan": _vm.weexHandler2
    }
  }, [_c('div')]) : _vm._e()])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 3:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var has = Object.prototype.hasOwnProperty
  , undef;

/**
 * Decode a URI encoded string.
 *
 * @param {String} input The URI encoded string.
 * @returns {String} The decoded string.
 * @api private
 */
function decode(input) {
  return decodeURIComponent(input.replace(/\+/g, ' '));
}

/**
 * Simple query string parser.
 *
 * @param {String} query The query string that needs to be parsed.
 * @returns {Object}
 * @api public
 */
function querystring(query) {
  var parser = /([^=?&]+)=?([^&]*)/g
    , result = {}
    , part;

  while (part = parser.exec(query)) {
    var key = decode(part[1])
      , value = decode(part[2]);

    //
    // Prevent overriding of existing properties. This ensures that build-in
    // methods like `toString` or __proto__ are not overriden by malicious
    // querystrings.
    //
    if (key in result) continue;
    result[key] = value;
  }

  return result;
}

/**
 * Transform a query string to an object.
 *
 * @param {Object} obj Object that should be transformed.
 * @param {String} prefix Optional prefix.
 * @returns {String}
 * @api public
 */
function querystringify(obj, prefix) {
  prefix = prefix || '';

  var pairs = []
    , value
    , key;

  //
  // Optionally prefix with a '?' if needed
  //
  if ('string' !== typeof prefix) prefix = '?';

  for (key in obj) {
    if (has.call(obj, key)) {
      value = obj[key];

      //
      // Edge cases where we actually want to encode the value to an empty
      // string instead of the stringified value.
      //
      if (!value && (value === null || value === undef || isNaN(value))) {
        value = '';
      }

      pairs.push(encodeURIComponent(key) +'='+ encodeURIComponent(value));
    }
  }

  return pairs.length ? prefix + pairs.join('&') : '';
}

//
// Expose the module.
//
exports.stringify = querystringify;
exports.parse = querystring;


/***/ }),

/***/ 4:
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

;(function (fn) {
  if (( false ? "undefined" : _typeof2(exports)) === "object" && typeof module !== "undefined") {
    module.exports = fn();
  } else if (true) {
    !(__WEBPACK_AMD_DEFINE_RESULT__ = (function (require, exports, module) {
      module.exports = fn();
    }).call(exports, __webpack_require__, exports, module),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
  } else {
    var root;
    if (typeof window !== "undefined") {
      root = window;
    } else if (typeof self !== "undefined") {
      root = self;
    } else if (typeof global !== "undefined") {
      root = global;
    } else {
      // NOTICE: In JavaScript strict mode, this is null
      root = this;
    }
    root["index"] = fn();
  }
})(function () {
  return (/******/function (modules) {
      // webpackBootstrap
      /******/ // The module cache
      /******/var installedModules = {};
      /******/
      /******/ // The require function
      /******/function __webpack_require__(moduleId) {
        /******/
        /******/ // Check if module is in cache
        /******/if (installedModules[moduleId]) {
          /******/return installedModules[moduleId].exports;
          /******/
        }
        /******/ // Create a new module (and put it into the cache)
        /******/var module = installedModules[moduleId] = {
          /******/i: moduleId,
          /******/l: false,
          /******/exports: {}
          /******/ };
        /******/
        /******/ // Execute the module function
        /******/modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
        /******/
        /******/ // Flag the module as loaded
        /******/module.l = true;
        /******/
        /******/ // Return the exports of the module
        /******/return module.exports;
        /******/
      }
      /******/
      /******/
      /******/ // expose the modules object (__webpack_modules__)
      /******/__webpack_require__.m = modules;
      /******/
      /******/ // expose the module cache
      /******/__webpack_require__.c = installedModules;
      /******/
      /******/ // define getter function for harmony exports
      /******/__webpack_require__.d = function (exports, name, getter) {
        /******/if (!__webpack_require__.o(exports, name)) {
          /******/Object.defineProperty(exports, name, {
            /******/configurable: false,
            /******/enumerable: true,
            /******/get: getter
            /******/ });
          /******/
        }
        /******/
      };
      /******/
      /******/ // getDefaultExport function for compatibility with non-harmony modules
      /******/__webpack_require__.n = function (module) {
        /******/var getter = module && module.__esModule ?
        /******/function getDefault() {
          return module['default'];
        } :
        /******/function getModuleExports() {
          return module;
        };
        /******/__webpack_require__.d(getter, 'a', getter);
        /******/return getter;
        /******/
      };
      /******/
      /******/ // Object.prototype.hasOwnProperty.call
      /******/__webpack_require__.o = function (object, property) {
        return Object.prototype.hasOwnProperty.call(object, property);
      };
      /******/
      /******/ // __webpack_public_path__
      /******/__webpack_require__.p = "";
      /******/
      /******/ // Load entry module and return exports
      /******/return __webpack_require__(__webpack_require__.s = 0);
      /******/
    }(
    /************************************************************************/
    /******/[
    /* 0 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /**
       Copyright 2018 Alibaba Group
      
       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at
      
       http://www.apache.org/licenses/LICENSE-2.0
      
       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.
       */

      Object.defineProperty(exports, "__esModule", {
        value: true
      });

      var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
        return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
      } : function (obj) {
        return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
      };

      var _bindingxParser = __webpack_require__(1);

      var isWeb = false;
      var isWeex = true;

      function requireModule(moduleName) {
        try {
          if ((typeof weex === 'undefined' ? 'undefined' : _typeof(weex)) !== undefined && weex.requireModule) {
            // eslint-disable-line
            return weex.requireModule(moduleName); // eslint-disable-line
          }
        } catch (err) {}
        return window.require('@weex-module/' + moduleName);
      }

      var isSupportNewBinding = true;
      var isSupportBinding = true;
      var WeexBinding = void 0;
      var WebBinding = {};

      try {
        WeexBinding = requireModule('bindingx');
        isSupportNewBinding = true;
      } catch (e) {
        isSupportNewBinding = false;
      }
      if (!WeexBinding || !WeexBinding.bind) {
        try {
          WeexBinding = requireModule('binding');
          isSupportNewBinding = true;
        } catch (e) {
          isSupportNewBinding = false;
        }
      }
      isSupportNewBinding = !!(WeexBinding && WeexBinding.bind && WeexBinding.unbind);
      if (!isSupportNewBinding) {
        try {
          WeexBinding = requireModule('expressionBinding');
          isSupportBinding = true;
        } catch (err) {
          isSupportBinding = false;
        }
      }
      isSupportBinding = !!(WeexBinding && (WeexBinding.bind || WeexBinding.createBinding));

      function formatExpression(expression) {
        if (expression === undefined) return;
        try {
          expression = JSON.parse(expression);
        } catch (err) {}
        var resultExpression = {};
        if (typeof expression === 'string') {
          resultExpression.origin = expression;
        } else if (expression) {
          resultExpression.origin = expression.origin;
          resultExpression.transformed = expression.transformed;
        }
        if (!resultExpression.transformed && !resultExpression.origin) return;
        resultExpression.transformed = resultExpression.transformed || (0, _bindingxParser.parse)(resultExpression.origin);
        return resultExpression;
      }

      // 统一回调参数
      function fixCallback(callback) {
        return function () {
          var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

          if (typeof callback === 'function') {
            return callback({
              state: params.state === 'end' ? 'exit' : params.state,
              t: params.t !== undefined ? params.t : params.deltaT
            });
          }
        };
      }

      exports.default = {
        // 是否支持新版本的binding
        isSupportNewBinding: isSupportNewBinding,
        // 是否支持binding
        isSupportBinding: isSupportBinding,
        _bindingInstances: [],
        /**
         * 绑定
         * @param options 参数
         * @example
         {
           anchor:blockRef,
           eventType:'pan',
           props: [
           {
             element:blockRef,
             property:'transform.translateX',
             expression:{
               origin:"x+1",
               transformed:"{\"type\":\"+\",\"children\":[{\"type\":\"Identifier\",\"value\":\"x\"},{\"type\":\"NumericLiteral\",\"value\":1}]}"
             }
           }
          ]
         }
         */
        bind: function bind(options) {
          var callback = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : function () {};

          if (!options) {
            throw new Error('should pass options for binding');
          }

          options.exitExpression = formatExpression(options.exitExpression);

          if (options.props) {
            options.props.forEach(function (prop) {
              prop.expression = formatExpression(prop.expression);
            });
          }

          if (WeexBinding && isSupportBinding) {
            if (isSupportNewBinding) {
              return WeexBinding.bind(options, options && options.eventType === 'timing' ? fixCallback(callback) : callback);
            } else {
              WeexBinding.enableBinding(options.anchor, options.eventType);
              // 处理expression的参数格式
              var expressionArgs = options.props.map(function (prop) {
                return {
                  element: prop.element,
                  property: prop.property,
                  expression: prop.expression.transformed
                };
              });
              WeexBinding.createBinding(options.anchor, options.eventType, '', expressionArgs, callback);
            }
          }
        },

        /**
         *  @param {object} options
         *  @example
         *  {eventType:'pan',
         *   token:self.gesToken}
         */
        unbind: function unbind(options) {
          if (!options) {
            throw new Error('should pass options for binding');
          }

          if (WeexBinding && isSupportBinding) {
            if (isSupportNewBinding) {
              return WeexBinding.unbind(options);
            } else {
              return WeexBinding.disableBinding(options.anchor, options.eventType);
            }
          }
        },
        unbindAll: function unbindAll() {
          if (WeexBinding && isSupportBinding) {
            if (isSupportNewBinding) {
              return WeexBinding.unbindAll();
            } else {
              return WeexBinding.disableAll();
            }
          }
        },
        prepare: function prepare(options) {
          if (WeexBinding && isSupportBinding) {
            if (isSupportNewBinding) {
              return WeexBinding.prepare(options);
            } else {
              return WeexBinding.enableBinding(options.anchor, options.eventType);
            }
          }
        },
        getComputedStyle: function getComputedStyle(el) {
          if (isSupportNewBinding) {
            return WeexBinding.getComputedStyle(el);
          } else {
            return {};
          }
        }
      };
      module.exports = exports['default'];

      /***/
    },
    /* 1 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";

      module.exports = __webpack_require__(2);

      /***/
    },
    /* 2 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";

      var lex = {
        InputElementDiv: '<WhiteSpace>|<LineTerminator>|<ReservedWord>|<Identifier>|<NumericLiteral>|<Punctuator>|<StringLiteral>',
        InputElementRegExp: '<WhiteSpace>|<LineTerminator>|<ReservedWord>|<Identifier>|<NumericLiteral>|<Punctuator>|<StringLiteral>',
        ReservedWord: '<Keyword>|<NullLiteral>|<BooleanLiteral>',
        WhiteSpace: /[\t\v\f\u0020\u00A0\u1680\u180E\u2000-\u200A\u202F\u205f\u3000\uFEFF]/,
        LineTerminator: /[\n\r\u2028\u2029]/,
        Keyword: /new(?![_$a-zA-Z0-9])|void(?![_$a-zA-Z0-9])|delete(?![_$a-zA-Z0-9])|in(?![_$a-zA-Z0-9])|instanceof(?![_$a-zA-Z0-9])|typeof(?![_$a-zA-Z0-9])/,
        NullLiteral: /null(?![_$a-zA-Z0-9])/,
        BooleanLiteral: /(?:true|false)(?![_$a-zA-Z0-9])/,
        Identifier: /[_$a-zA-Z][_$a-zA-Z0-9]*/,
        Punctuator: /\/|=>|\*\*|>>>=|>>=|<<=|===|!==|>>>|<<|%=|\*=|-=|\+=|<=|>=|==|!=|\^=|\|=|\|\||&&|&=|>>|\+\+|--|\:|}|\*|&|\||\^|!|~|-|\+|\?|%|=|>|<|,|;|\.(?![0-9])|\]|\[|\)|\(|{/,
        DivPunctuator: /\/=|\//,
        NumericLiteral: /(?:0[xX][0-9a-fA-F]*|\.[0-9]+|(?:[1-9]+[0-9]*|0)(?:\.[0-9]*|\.)?)(?:[eE][+-]{0,1}[0-9]+)?(?![_$a-zA-Z0-9])/,
        StringLiteral: /"(?:[^"\n\\\r\u2028\u2029]|\\(?:['"\\bfnrtv\n\r\u2028\u2029]|\r\n)|\\x[0-9a-fA-F]{2}|\\u[0-9a-fA-F]{4}|\\[^0-9ux'"\\bfnrtv\n\\\r\u2028\u2029])*"|'(?:[^'\n\\\r\u2028\u2029]|\\(?:['"\\bfnrtv\n\r\u2028\u2029]|\r\n)|\\x[0-9a-fA-F]{2}|\\u[0-9a-fA-F]{4}|\\[^0-9ux'"\\bfnrtv\n\\\r\u2028\u2029])*'/,
        RegularExpressionLiteral: /\/(?:\[(?:\\[\s\S]|[^\]])*\]|[^*\/\\\n\r\u2028\u2029]|\\[^\n\r\u2028\u2029])(?:\[(?:\\[\s\S]|[^\]])*\]|[^\/\\\n\r\u2028\u2029]|\\[^\n\r\u2028\u2029])*\/[0-9a-zA-Z]*/
      };

      function XRegExp(xregexps, rootname, flag) {
        var expnames = [rootname];

        function buildRegExp(source) {
          var regexp = new RegExp();
          regexp.compile(source.replace(/<([^>]+)>/g, function (all, expname) {
            if (!xregexps[expname]) return '';
            expnames.push(expname);
            if (xregexps[expname] instanceof RegExp) return '(' + xregexps[expname].source + ')';
            return '(' + buildRegExp(xregexps[expname]).source + ')';
          }), flag);
          return regexp;
        }

        var regexp = buildRegExp(xregexps[rootname]);
        this.exec = function (string) {
          var matches = regexp.exec(string);
          if (matches == null) return null;
          var result = new String(matches[0]);
          for (var i = 0; i < expnames.length; i++) {
            if (matches[i]) result[expnames[i]] = matches[i];
          }return result;
        };
        Object.defineProperty(this, 'lastIndex', {
          'get': function get() {
            return regexp.lastIndex;
          },
          'set': function set(v) {
            regexp.lastIndex = v;
          }
        });
      }

      function LexicalParser() {
        var inputElementDiv = new XRegExp(lex, 'InputElementDiv', 'g');
        var inputElementRegExp = new XRegExp(lex, 'InputElementRegExp', 'g');
        var source;
        Object.defineProperty(this, 'source', {
          'get': function get() {
            return source;
          },
          'set': function set(v) {
            source = v;
            inputElementDiv.lastIndex = 0;
            inputElementRegExp.lastIndex = 0;
          }
        });
        this.reset = function () {
          inputElementDiv.lastIndex = 0;
          inputElementRegExp.lastIndex = 0;
        };
        this.getNextToken = function (useDiv) {
          var lastIndex = inputElementDiv.lastIndex;
          var inputElement;
          if (useDiv) inputElement = inputElementDiv;else inputElement = inputElementRegExp;
          var token = inputElement.exec(source);
          if (token && inputElement.lastIndex - lastIndex > token.length) {
            throw new SyntaxError('Unexpected token ILLEGAL');
          }
          inputElementDiv.lastIndex = inputElement.lastIndex;
          inputElementRegExp.lastIndex = inputElement.lastIndex;
          return token;
        };
      }

      var rules = {
        'IdentifierName': [['Identifier']],
        'Literal': [['NullLiteral'], ['BooleanLiteral'], ['NumericLiteral'], ['StringLiteral'], ['RegularExpressionLiteral']],
        'PrimaryExpression': [['Identifier'], ['Literal'], ['(', 'Expression', ')']],
        'CallExpression': [['PrimaryExpression', 'Arguments'], ['CallExpression', 'Arguments']],
        'Arguments': [['(', ')'], ['(', 'ArgumentList', ')']],
        'ArgumentList': [['ConditionalExpression'], ['ArgumentList', ',', 'ConditionalExpression']],
        'LeftHandSideExpression': [['PrimaryExpression'], ['CallExpression']],
        'UnaryExpression': [['LeftHandSideExpression'], ['void', 'UnaryExpression'], ['+', 'UnaryExpression'], ['-', 'UnaryExpression'], ['~', 'UnaryExpression'], ['!', 'UnaryExpression']],
        'ExponentiationExpression': [['UnaryExpression'], ['ExponentiationExpression', '**', 'UnaryExpression']],
        'MultiplicativeExpression': [['MultiplicativeExpression', '/', 'ExponentiationExpression'], ['ExponentiationExpression'], ['MultiplicativeExpression', '*', 'ExponentiationExpression'], ['MultiplicativeExpression', '%', 'ExponentiationExpression']],
        'AdditiveExpression': [['MultiplicativeExpression'], ['AdditiveExpression', '+', 'MultiplicativeExpression'], ['AdditiveExpression', '-', 'MultiplicativeExpression']],
        'ShiftExpression': [['AdditiveExpression'], ['ShiftExpression', '<<', 'AdditiveExpression'], ['ShiftExpression', '>>', 'AdditiveExpression'], ['ShiftExpression', '>>>', 'AdditiveExpression']],
        'RelationalExpression': [['ShiftExpression'], ['RelationalExpression', '<', 'ShiftExpression'], ['RelationalExpression', '>', 'ShiftExpression'], ['RelationalExpression', '<=', 'ShiftExpression'], ['RelationalExpression', '>=', 'ShiftExpression'], ['RelationalExpression', 'instanceof', 'ShiftExpression'], ['RelationalExpression', 'in', 'ShiftExpression']],
        'EqualityExpression': [['RelationalExpression'], ['EqualityExpression', '==', 'RelationalExpression'], ['EqualityExpression', '!=', 'RelationalExpression'], ['EqualityExpression', '===', 'RelationalExpression'], ['EqualityExpression', '!==', 'RelationalExpression']],
        'BitwiseANDExpression': [['EqualityExpression'], ['BitwiseANDExpression', '&', 'EqualityExpression']],
        'BitwiseXORExpression': [['BitwiseANDExpression'], ['BitwiseXORExpression', '^', 'BitwiseANDExpression']],
        'BitwiseORExpression': [['BitwiseXORExpression'], ['BitwiseORExpression', '|', 'BitwiseXORExpression']],
        'LogicalANDExpression': [['BitwiseORExpression'], ['LogicalANDExpression', '&&', 'BitwiseORExpression']],
        'LogicalORExpression': [['LogicalANDExpression'], ['LogicalORExpression', '||', 'LogicalANDExpression']],
        'ConditionalExpression': [['LogicalORExpression'], ['LogicalORExpression', '?', 'LogicalORExpression', ':', 'LogicalORExpression']],
        'Expression': [['ConditionalExpression'], ['Expression', ',', 'ConditionalExpression']],
        'Program': [['Expression']]

      };

      function _Symbol(symbolName, token) {
        this.name = symbolName;
        this.token = token;
        this.childNodes = [];
        this.toString = function (indent) {
          if (!indent) indent = '';
          if (this.childNodes.length == 1) return this.childNodes[0].toString(indent);
          var str = indent + this.name + (this.token != undefined && this.name != this.token ? ':' + this.token : '') + '\n';
          for (var i = 0; i < this.childNodes.length; i++) {
            str += this.childNodes[i].toString(indent + '    ');
          }return str;
        };
      }

      function SyntacticalParser() {
        var currentRule;
        var root = {
          Program: '$'
        };
        var hash = {};

        function closureNode(node) {

          hash[JSON.stringify(node)] = node;

          var queue = Object.getOwnPropertyNames(node);
          while (queue.length) {
            var symbolName = queue.shift();
            if (!rules[symbolName]) continue;
            rules[symbolName].forEach(function (rule) {
              if (!node[rule[0]]) queue.push(rule[0]);
              var rulenode = node;
              var lastnode = null;
              rule.forEach(function (symbol) {
                if (!rulenode[symbol]) rulenode[symbol] = {};
                lastnode = rulenode;
                rulenode = rulenode[symbol];
              });
              if (node[symbolName].$div) rulenode.$div = true;
              rulenode.$reduce = symbolName;
              rulenode.$count = rule.length;
            });
          }

          for (var p in node) {
            if (_typeof2(node[p]) != 'object' || p.charAt(0) == '$' || node[p].$closure) continue;
            if (hash[JSON.stringify(node[p])]) node[p] = hash[JSON.stringify(node[p])];else {
              closureNode(node[p]);
            }
          }
          node.$closure = true;
        }

        closureNode(root);
        var symbolStack = [];
        var statusStack = [root];
        var current = root;
        this.insertSymbol = function insertSymbol(symbol, haveLineTerminator) {
          while (!current[symbol.name] && current.$reduce) {
            var count = current.$count;
            var newsymbol = new _Symbol(current.$reduce);
            while (count--) {
              newsymbol.childNodes.push(symbolStack.pop()), statusStack.pop();
            }current = statusStack[statusStack.length - 1];
            this.insertSymbol(newsymbol);
          }
          current = current[symbol.name];
          symbolStack.push(symbol), statusStack.push(current);
          if (!current) throw new Error();
          return current.$div;
        };
        this.reset = function () {
          current = root;
          symbolStack = [];
          statusStack = [root];
        };
        Object.defineProperty(this, 'grammarTree', {
          'get': function get() {
            try {
              while (current.$reduce) {
                var count = current.$count;
                var newsymbol = new _Symbol(current.$reduce);
                while (count--) {
                  newsymbol.childNodes.push(symbolStack.pop()), statusStack.pop();
                }current = statusStack[statusStack.length - 1];
                this.insertSymbol(newsymbol);
              }
              if (symbolStack.length > 0 && current[';']) {
                this.insertSymbol(new _Symbol(';', ';'));
                return this.grammarTree;
              }
              if (symbolStack.length != 1 || symbolStack[0].name != 'Program') throw new Error();
            } catch (e) {
              throw new SyntaxError('Unexpected end of input');
            }
            return symbolStack[0];
          }
        });
      }

      function Parser() {
        this.lexicalParser = new LexicalParser();
        this.syntacticalParser = new SyntacticalParser();
        var terminalSymbols = ['NullLiteral', 'BooleanLiteral', 'NumericLiteral', 'StringLiteral', 'RegularExpressionLiteral', 'Identifier', '**', '=>', '{', '}', '(', ')', '[', ']', '.', ';', ',', '<', '>', '<=', '>=', '==', '!=', '===', '!==', '+', '-', '*', '%', '++', '--', '<<', '>>', '>>>', '&', '|', '^', '!', '~', '&&', '||', '?', ':', '=', '+=', '-=', '*=', '%=', '<<=', '>>=', '>>>=', '&=', '|=', '^=', '/', '/=', 'instanceof', 'typeof', 'new', 'void', 'debugger', 'this', 'delete', 'in'];
        var terminalSymbolIndex = {};
        terminalSymbols.forEach(function (e) {
          Object.defineProperty(terminalSymbolIndex, e, {});
        });
        this.reset = function () {
          this.lexicalParser.reset();
          this.syntacticalParser.reset();
        };
        this.parse = function (source, onInputElement) {
          var _this = this;

          var token;
          var haveLineTerminator = false;
          this.lexicalParser.source = source;
          var useDiv = false;
          while (token = this.lexicalParser.getNextToken(useDiv)) {
            if (onInputElement) onInputElement(token);
            try {
              if (Object.getOwnPropertyNames(token).some(function (e) {
                if (terminalSymbolIndex.hasOwnProperty(e)) {
                  useDiv = _this.syntacticalParser.insertSymbol(new _Symbol(e, token), haveLineTerminator);
                  haveLineTerminator = false;
                  return true;
                } else return false;
              })) continue;
              if ((token.Keyword || token.Punctuator || token.DivPunctuator) && terminalSymbolIndex.hasOwnProperty(token.toString())) {
                useDiv = this.syntacticalParser.insertSymbol(new _Symbol(token.toString(), token), haveLineTerminator);
              }
            } catch (e) {
              throw new SyntaxError('Unexpected token ' + token);
            }
          }
          return this.syntacticalParser.grammarTree;
        };
      }

      var parser = new Parser();

      function JavaScriptExpression(text) {
        parser.reset();
        this.tree = parser.parse(text);
        this.paths = [];
        var context = Object.create(null);
        var me = this;
        var pathIndex = Object.create(null);
        this.isSimple;
        this.isConst;
        walk(this.tree);
        checkSimple(this.tree);
        if (this.paths.length === 0) {
          this.isConst = true;
        }
        this.setter = function (path) {
          var curr = context;
          for (var i = 0; i < path.length - 1; i++) {
            if (!curr[path[i]]) curr[path[i]] = Object.create(null);
            curr = curr[path[i]];
          }
          return {
            isCompleted: function isCompleted() {
              for (var p in pathIndex) {
                if (!pathIndex[p]) return false;
              }return true;
            },
            set: function set(value) {
              if (!pathIndex[path.join('.')]) {
                pathIndex[path.join('.')] = true;
              }
              curr[path[i]] = value;
              if (this.isCompleted()) {
                return me.exec();
              } else {
                return undefined;
              }
            }
          };
        };

        this.valueOf = this.exec = function () {
          try {
            return function () {
              return eval(text);
            }.call(context);
          } catch (e) {}
        };

        function checkSimple(symbol) {

          var curr = symbol;
          while (curr.childNodes.length <= 1 && curr.name !== 'MemberExpression') {
            curr = curr.childNodes[0];
          }
          // TODO: need to point out "[……]"
          if (curr.name === 'MemberExpression') {
            me.isSimple = true;
          } else {
            me.isSimple = false;
          }
        }

        function walk(symbol) {
          if (symbol.name === 'CallExpression' && symbol.childNodes[symbol.childNodes.length - 1].name !== 'CallExpression') {
            var path = getPath(symbol.childNodes[1]);
            walk(symbol.childNodes[0]);
          } else if (symbol.name === 'NewExpression' && symbol.childNodes.length === 1) {
            var path = getPath(symbol.childNodes[0]);
          } else if (symbol.name === 'MemberExpression' && symbol.childNodes.length === 1) {
            var path = getPath(symbol);
          } else {
            for (var i = 0; i < symbol.childNodes.length; i++) {
              walk(symbol.childNodes[i]);
            }
          }
        }

        function getPath(symbol) {
          // [["PrimaryExpression"], ["MemberExpression", "[", "Expression", "]"], ["MemberExpression", ".", "IdentifierName"], ["new", "MemberExpression", "Arguments"]],

          if (symbol.childNodes[0].name === 'IdentifierName') {
            // MemberExpression : MemberExpression "." IdentifierName
            var path = getPath(symbol.childNodes[2]);
            if (path) path = path.concat(symbol.childNodes[0].childNodes[0].token.toString());
            createPath(path);
            return path;
          } else if (symbol.childNodes[0].name === 'PrimaryExpression') {
            // MemberExpression : PrimaryExpression
            if (symbol.childNodes[0].childNodes[0].name === 'Identifier') {
              var path = [symbol.childNodes[0].childNodes[0].token.toString()];
              createPath(path);
              return path;
            } else {
              return null;
            }
          } else if (symbol.childNodes[0].name === ']') {
            // MemberExpression : MemberExpression "[" Expression "]"
            getPath(symbol.childNodes[3]);
            walk(symbol.childNodes[1]);
            return null;
          } else if (symbol.childNodes[0].name === 'Arguments') {
            // MemberExpression : "new" MemberExpression Arguments
            walk(symbol.childNodes[0]);
            walk(symbol.childNodes[1]);
            return null;
          } else {
            for (var i = 0; i < symbol.childNodes.length; i++) {
              walk(symbol.childNodes[i]);
            }
          }
        }

        function createPath(path) {
          var curr = context;
          for (var i = 0; i < path.length - 1; i++) {
            if (!curr[path[i]]) curr[path[i]] = Object.create(null);
            curr = curr[path[i]];
          }
          me.paths.push(path);
          pathIndex[path.join('.')] = false;
        }
      }

      function visit(tree) {
        var childNodes = tree.childNodes.slice().reverse();
        var children = childNodes.filter(function (e) {
          return !e.token || !e.token.Punctuator;
        });
        if (tree.name === 'UnaryExpression') {
          // negative number support
          if (childNodes.length === 2 && childNodes[0].name === '-' && children.length === 1) {
            var res = visit(children[0]);
            res.value = -res.value;
            return res;
          }
        }

        if (tree.name === 'Arguments') {
          var argumentList = [];
          var listNode = children[0];
          while (listNode) {
            if (listNode.childNodes.length === 3) {
              argumentList.unshift(listNode.childNodes[0]);
              listNode = listNode.childNodes[2];
            }
            if (listNode.childNodes.length === 1) {
              argumentList.unshift(listNode.childNodes[0]);
              listNode = null;
            }
          }
          return {
            type: 'Arguments',
            children: argumentList.map(function (e) {
              return visit(e);
            })
          };
        }

        if (children && children.length === 1) {
          var res = visit(children[0]);
          return res;
        }

        if (tree.token && ['NullLiteral', 'BooleanLiteral', 'NumericLiteral', 'StringLiteral', 'Identifier'].some(function (e) {
          return tree.token[e];
        })) {
          var type = Object.keys(tree.token).filter(function (e) {
            return e.match(/Literal/) || e.match(/Identifier/);
          })[0];
          var value = {
            'NullLiteral': null,
            'BooleanLiteral': Boolean(tree.token),
            'NumericLiteral': Number(tree.token),
            'StringLiteral': tree.token,
            'Identifier': tree.token
          }[type];

          return {
            type: type,
            value: value
          };
        }

        if (tree.name === 'CallExpression') return {
          type: 'CallExpression',
          children: [visit(childNodes[0]), visit(childNodes[1])]
        };

        return {
          type: childNodes.filter(function (e) {
            return e.token && e.token.Punctuator;
          })[0].name,
          children: childNodes.filter(function (e) {
            return !e.token || !e.token.Punctuator;
          }).map(function (e) {
            return visit(e);
          })
        };
      }

      function parse(originExp) {
        var exp = new JavaScriptExpression(originExp);
        return JSON.stringify(visit(exp.tree), null);
      }

      module.exports = {
        parse: parse
      };

      /***/
    }]
    /******/)
  );
});;

/***/ }),

/***/ 5:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _indexWeex = __webpack_require__(4);

var _indexWeex2 = _interopRequireDefault(_indexWeex);

var _index = __webpack_require__(0);

var _index2 = _interopRequireDefault(_index);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * CopyRight (C) 2017-2022 Alibaba Group Holding Limited.
 * Created by Tw93 on 18/03/22
 */
var BindEnv = {
  supportsEB: function supportsEB() {
    return _indexWeex2.default.isSupportBinding && !_index2.default.env.isWeb();
  },


  /**
   * 判断Android容器是否支持是否支持expressionBinding(处理方式很不一致)
   * @returns {boolean}
   */
  supportsEBForAndroid: function supportsEBForAndroid() {
    return _index2.default.env.isAndroid() && BindEnv.supportsEB();
  },


  /**
   * 判断IOS容器是否支持是否支持expressionBinding
   * @returns {boolean}
   */
  supportsEBForIos: function supportsEBForIos() {
    return _index2.default.env.isIOS() && BindEnv.supportsEB();
  }
};

exports.default = BindEnv;

/***/ })

/******/ });