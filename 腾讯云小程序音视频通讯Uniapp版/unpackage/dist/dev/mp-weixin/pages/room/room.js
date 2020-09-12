(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["pages/room/room"],{

/***/ 19:
/*!***********************************************************************************!*\
  !*** D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/main.js?{"page":"pages%2Froom%2Froom"} ***!
  \***********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(createPage) {__webpack_require__(/*! uni-pages */ 4);
var _vue = _interopRequireDefault(__webpack_require__(/*! vue */ 2));
var _room = _interopRequireDefault(__webpack_require__(/*! ./pages/room/room.vue */ 20));function _interopRequireDefault(obj) {return obj && obj.__esModule ? obj : { default: obj };}
createPage(_room.default);
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 1)["createPage"]))

/***/ }),

/***/ 20:
/*!****************************************************************!*\
  !*** D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./room.vue?vue&type=template&id=3308d434& */ 21);
/* harmony import */ var _room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./room.vue?vue&type=script&lang=js& */ 23);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var _room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./room.vue?vue&type=style&index=0&lang=css& */ 27);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/runtime/componentNormalizer.js */ 10);

var renderjs





/* normalize component */

var component = Object(_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__["default"])(
  _room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__["default"],
  _room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["render"],
  _room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"],
  false,
  null,
  null,
  null,
  false,
  _room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["components"],
  renderjs
)

component.options.__file = "pages/room/room.vue"
/* harmony default export */ __webpack_exports__["default"] = (component.exports);

/***/ }),

/***/ 21:
/*!***********************************************************************************************!*\
  !*** D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue?vue&type=template&id=3308d434& ***!
  \***********************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./room.vue?vue&type=template&id=3308d434& */ 22);
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "render", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["render"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["recyclableRender"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "components", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_template_id_3308d434___WEBPACK_IMPORTED_MODULE_0__["components"]; });



/***/ }),

/***/ 22:
/*!***********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue?vue&type=template&id=3308d434& ***!
  \***********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "render", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return staticRenderFns; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return recyclableRender; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "components", function() { return components; });
var components = {
  trtcRoom: function() {
    return Promise.all(/*! import() | components/trtc-room/trtc-room */[__webpack_require__.e("common/vendor"), __webpack_require__.e("components/trtc-room/trtc-room")]).then(__webpack_require__.bind(null, /*! @/components/trtc-room/trtc-room.vue */ 45))
  }
}
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
}
var recyclableRender = false
var staticRenderFns = []
render._withStripped = true



/***/ }),

/***/ 23:
/*!*****************************************************************************************!*\
  !*** D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue?vue&type=script&lang=js& ***!
  \*****************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/babel-loader/lib!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./room.vue?vue&type=script&lang=js& */ 24);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 24:
/*!************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue?vue&type=script&lang=js& ***!
  \************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports, "__esModule", { value: true });exports.default = void 0;












var _GenerateTestUserSig = __webpack_require__(/*! ../../debug/GenerateTestUserSig */ 25); //
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
var trtcRoom = function trtcRoom() {Promise.all(/*! require.ensure | components/trtc-room/trtc-room */[__webpack_require__.e("common/vendor"), __webpack_require__.e("components/trtc-room/trtc-room")]).then((function () {return resolve(__webpack_require__(/*! ../../components/trtc-room/trtc-room */ 45));}).bind(null, __webpack_require__)).catch(__webpack_require__.oe);};var _default = { data: function data() {return { rtcConfig: { sdkAppID: '', // 必要参数 开通实时音视频服务创建应用后分配的 sdkAppID
        userID: '', // 必要参数 用户 ID 可以由您的帐号系统指定
        userSig: '', // 必要参数 身份签名，相当于登录密码的作用
        template: '' // 必要参数 组件模版，支持的值 1v1 grid custom ，注意：不支持动态修改, iOS 不支持 pusher 动态渲染
      }, showTipToast: false };

  },

  components: {
    trtcRoom: trtcRoom },

  props: {},

  /**
              * 生命周期函数--监听页面加载
              * @param {*} options 配置项
              */
  onLoad: function onLoad(options) {
    console.log('room onload', options);
    wx.setKeepScreenOn({
      keepScreenOn: true });
    // 获取 rtcroom 实例

    this.trtcComponent = this.$refs['trtc-component']; // 监听TRTC Room 关键事件
    console.log('this.trtcComponent', this.trtcComponent.enterRoom);

    this.bindTRTCRoomEvent(); // 将String 类型的 true false 转换成 boolean

    Object.getOwnPropertyNames(options).forEach(function (key) {
      if (options[key] === 'true') {
        options[key] = true;
      }

      if (options[key] === 'false') {
        options[key] = false;
      }
    });
    this.options = options; // querystring 只支持传递 String 类型, 注意类型转换

    this.enterRoom({
      roomID: Number(options.roomID),
      userID: options.userID,
      template: options.template,
      debugMode: options.debugMode,
      cloudenv: options.cloudenv,
      frontCamera: options.frontCamera,
      localVideo: options.localVideo,
      localAudio: options.localAudio,
      enableEarMonitor: options.enableEarMonitor,
      enableAutoFocus: options.enableAutoFocus,
      localMirror: options.localMirror,
      enableAgc: options.enableAgc,
      enableAns: options.enableAns,
      encsmall: options.encsmall,
      videoHeight: options.videoHeight,
      videoWidth: options.videoWidth,
      scene: options.scene,
      maxBitrate: Number(options.maxBitrate),
      minBitrate: Number(options.minBitrate) });

  },

  /**
      * 生命周期函数--监听页面初次渲染完成
      */
  onReady: function onReady() {
    console.log('room ready');
  },

  /**
      * 生命周期函数--监听页面显示
      */
  onShow: function onShow() {
    console.log('room show');
  },

  /**
      * 生命周期函数--监听页面隐藏
      */
  onHide: function onHide() {
    console.log('room hide');
  },

  /**
      * 生命周期函数--监听页面卸载
      */
  onUnload: function onUnload() {
    console.log('room unload');
  },

  /**
      * 页面相关事件处理函数--监听用户下拉动作
      */
  onPullDownRefresh: function onPullDownRefresh() {},

  /**
                                                       * 页面上拉触底事件的处理函数
                                                       */
  onReachBottom: function onReachBottom() {},

  /**
                                               * 用户点击右上角分享
                                               */
  onShareAppMessage: function onShareAppMessage() {},
  methods: {
    setData: _GenerateTestUserSig.setData,
    enterRoom: function enterRoom(params) {var _this = this;
      params.template = params.template || '1v1';
      params.roomID = params.roomID || 2333;
      params.userID = params.userID || new Date().getTime().toString(16);
      console.log('* room enterRoom', params);
      var Signature = (0, _GenerateTestUserSig.genTestUserSig)(params.userID);
      params.sdkAppID = Signature.sdkAppID;
      params.userSig = Signature.userSig;
      this.template = params.template;

      if (params.template === 'grid') {
        this.rtcConfig = {
          sdkAppID: params.sdkAppID,
          // 您的实时音视频服务创建应用后分配的 sdkAppID
          userID: params.userID,
          userSig: params.userSig,
          template: params.template,
          // 1v1 grid custom
          debugMode: params.debugMode,
          // 非必要参数，打开组件的调试模式，开发调试时建议设置为 true
          // cloudenv: params.cloudenv, // 非必要参数
          frontCamera: params.frontCamera,
          enableEarMonitor: params.enableEarMonitor,
          enableAutoFocus: params.enableAutoFocus,
          localMirror: params.localMirror,
          enableAgc: params.enableAgc,
          enableAns: params.enableAns,
          encsmall: params.encsmall ? 1 : 0,
          videoWidth: params.videoWidth,
          videoHeight: params.videoHeight,
          scene: params.scene,
          maxBitrate: params.maxBitrate,
          minBitrate: params.minBitrate,
          beautyLevel: 9 // 默认开启美颜
        };

      } else {
        this.rtcConfig = {
          sdkAppID: params.sdkAppID,
          // 您的实时音视频服务创建应用后分配的 sdkAppID
          userID: params.userID,
          userSig: params.userSig,
          template: params.template,
          // 1v1 grid custom
          debugMode: params.debugMode,
          // 非必要参数，打开组件的调试模式，开发调试时建议设置为 true
          beautyLevel: 9 // 默认开启美颜
          // cloudenv: params.cloudenv, // 非必要参数
        };

      }

      this.setData({
        rtcConfig: this.rtcConfig },
      function () {
        // 进房前决定是否推送视频或音频 部分机型会出现画面卡死，暂不支持进房前设置，必须放到进房成功事件后设置
        // console.log('rtcConfig', this.data.rtcConfig)
        // if (params.localVideo === true || params.template === '1v1') {
        //   this.trtcComponent.publishLocalVideo()
        // }
        // if (params.localAudio === true || params.template === '1v1') {
        //   this.trtcComponent.publishLocalAudio()
        // }
        // roomID 取值范围 1 ~ 4294967295
        _this.trtcComponent.enterRoom({
          roomID: params.roomID }).
        then(function () {
          if (_this.template === 'custom') {
            // 设置推流端视窗的坐标和尺寸
            _this.trtcComponent.setViewRect({
              userID: params.userID,
              xAxis: '480rpx',
              yAxis: '160rpx',
              width: '240rpx',
              height: '320rpx' });

          }
        }).catch(function (res) {
          console.error('* room joinRoom 进房失败:', res);
        });
      });
    },
    bindTRTCRoomEvent: function bindTRTCRoomEvent() {var _this2 = this;
      var TRTC_EVENT = this.trtcComponent.EVENT;
      this.timestamp = []; // 初始化事件订阅

      this.trtcComponent.on(TRTC_EVENT.LOCAL_JOIN, function (event) {
        console.log('* room LOCAL_JOIN', event); // 进房成功，触发该事件后可以对本地视频和音频进行设置

        if (_this2.options.localVideo === true || _this2.options.template === '1v1') {
          _this2.trtcComponent.publishLocalVideo();
        }

        if (_this2.options.localAudio === true || _this2.options.template === '1v1') {
          _this2.trtcComponent.publishLocalAudio();
        }
      });
      this.trtcComponent.on(TRTC_EVENT.LOCAL_LEAVE, function (event) {
        console.log('* room LOCAL_LEAVE', event);
      });
      this.trtcComponent.on(TRTC_EVENT.ERROR, function (event) {
        console.log('* room ERROR', event);
      }); // 远端用户进房

      this.trtcComponent.on(TRTC_EVENT.REMOTE_USER_JOIN, function (event) {
        console.log('* room REMOTE_USER_JOIN ---  room.vue', event, _this2.trtcComponent.getRemoteUserList(), _this2.template);
        _this2.timestamp.push(new Date()); // 1v1视频通话时限制人数为两人的简易逻辑，建议通过后端实现房间人数管理
        // 2人以上同时进行通话请选择网格布局

        if (_this2.template === '1v1' && _this2.timestamp.length > 1) {
          var interval = _this2.timestamp[1] - _this2.timestamp[0];

          if (interval < 1000) {
            // 房间里已经有两个人
            _this2.setData({
              showTipToast: true },
            function () {
              setTimeout(function () {
                _this2.setData({
                  showTipToast: false });

                wx.navigateBack({
                  delta: 1 });

              }, 4000);
            });
          }
        }
      }); // 远端用户退出

      this.trtcComponent.on(TRTC_EVENT.REMOTE_USER_LEAVE, function (event) {
        console.log('* room REMOTE_USER_LEAVE', event, _this2.trtcComponent.getRemoteUserList());

        if (_this2.template === '1v1') {
          _this2.timestamp = [];
        }

        if (_this2.template === '1v1' && _this2.remoteUser === event.data.userID) {
          _this2.remoteUser = null;
        }
      }); // 远端用户推送视频

      this.trtcComponent.on(TRTC_EVENT.REMOTE_VIDEO_ADD, function (event) {
        console.log('* room REMOTE_VIDEO_ADD', event, _this2.trtcComponent.getRemoteUserList()); // 订阅视频

        var userList = _this2.trtcComponent.getRemoteUserList();
        var data = event.data;

        if (_this2.template === '1v1' && (!_this2.remoteUser || _this2.remoteUser === data.userID)) {
          // 1v1 只订阅第一个远端流
          _this2.remoteUser = data.userID;
          _this2.trtcComponent.subscribeRemoteVideo({
            userID: data.userID,
            streamType: data.streamType });

        } else if (_this2.template === 'grid') {
          _this2.trtcComponent.subscribeRemoteVideo({
            userID: data.userID,
            streamType: data.streamType });

        }

        if (_this2.template === 'custom' && data.userID && data.streamType) {
          var index = userList.findIndex(function (item) {
            return item.userID === data.userID;
          });
          index++;
          var y = 320 * index + 160; // 设置远端视图坐标和尺寸

          _this2.trtcComponent.setViewRect({
            userID: data.userID,
            streamType: data.streamType,
            xAxis: '480rpx',
            yAxis: y + 'rpx',
            width: '240rpx',
            height: '320rpx' });

        }
      }); // 远端用户取消推送视频

      this.trtcComponent.on(TRTC_EVENT.REMOTE_VIDEO_REMOVE, function (event) {
        console.log('* room REMOTE_VIDEO_REMOVE', event, _this2.trtcComponent.getRemoteUserList());
      }); // 远端用户推送音频

      this.trtcComponent.on(TRTC_EVENT.REMOTE_AUDIO_ADD, function (event) {
        console.log('* room REMOTE_AUDIO_ADD', event, _this2.trtcComponent.getRemoteUserList()); // 订阅音频

        var data = event.data;

        if (_this2.template === '1v1' && (!_this2.remoteUser || _this2.remoteUser === data.userID)) {
          _this2.remoteUser = data.userID;
          _this2.trtcComponent.subscribeRemoteAudio({
            userID: data.userID });

        } else if (_this2.template === 'grid' || _this2.template === 'custom') {
          _this2.trtcComponent.subscribeRemoteAudio({
            userID: data.userID });

        } // 如果不订阅就不会自动播放音频
        // this.trtcComponent.subscribeRemoteAudio({ userID: data.userID })

      }); // 远端用户取消推送音频

      this.trtcComponent.on(TRTC_EVENT.REMOTE_AUDIO_REMOVE, function (event) {
        console.log('* room REMOTE_AUDIO_REMOVE', event, _this2.trtcComponent.getRemoteUserList());
      });
    } } };exports.default = _default;

/***/ }),

/***/ 27:
/*!*************************************************************************************************!*\
  !*** D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue?vue&type=style&index=0&lang=css& ***!
  \*************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/css-loader/dist/cjs.js??ref--6-oneOf-1-1!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-2!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/postcss-loader/src??ref--6-oneOf-1-3!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./room.vue?vue&type=style&index=0&lang=css& */ 28);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_room_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 28:
/*!*****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/css-loader/dist/cjs.js??ref--6-oneOf-1-1!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-2!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/workspace/text/腾讯云小程序音视频通讯Uniapp版/pages/room/room.vue?vue&type=style&index=0&lang=css& ***!
  \*****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin
    if(false) { var cssReload; }
  

/***/ })

},[[19,"common/runtime","common/vendor"]]]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/room/room.js.map