# 组件详解

## 组件说明
**&lt;trtc-room&gt;** 标签是基于 &lt;live-pusher&gt; 和 &lt;live-player&gt; 实现的用于 TRTC 互通的自定义组件。

## 属性

&lt;trtc-room&gt; 只有一个 config 属性，通过该属性可以传入以下参数

| 参数                 | 类型    | 默认值    | 说明         |
|:---------------------|:--------|:----------|:-------------|
| sdkAppID             | String  | -         | 必要，开通实时音视频服务创建应用后分配的 SDKAppID。            |
| userID               | String  | -         | 必要，用户 ID，可以由您的帐号体系指定。 |
| userSig              | String  | -         | 必要，身份签名，由 userID[计算](https://cloud.tencent.com/document/product/647/17275) 得出，相当于登录密码的作用。    |
| template             | String  | custom    | 必要，组件内置的画面排版模式，支持如下三种模式：<br>1V1：大小画面上下叠加 <br>grid：网格模版，画面间相互重叠，最多显示6录画面 <br>custom：自定义，需要您通过 setViewRect 和 setViewZIndex 等接口自行处理。  |
| debugMode            | Boolean | false     | 是否打开组件的调试模式，开启后视频画面上会有一个半透明浮层展示音视频数据指标。    |
| scene                | String  | rtc       | rtc：实时通话，采用优质线路，同一房间中的人数不适宜超过20人； <br>live：直播模式，采用混合线路，房间人数无上限。  |
| enableCamera         | Boolean | true      | 是否开启摄像头   |
| enableMic            | Boolean | true      | 是否开启麦克风    |
| enableAgc            | Boolean | false     | 是否开启音频自动增益，该特性可以补偿部分手机麦克风音量太小的问题，但也会放大噪音，建议配合 ANS 同时开启。|
| enableAns            | Boolean | false     | 是否开启音频噪声抑制，该特性会自动检测背景噪音并进行过滤，但也会误伤周围的音乐，只有会议、教学等场景才适合开启。    |
| enableEarMonitor     | Boolean | false     | 是否开启耳返（目前只在iOS平台有效）。    |
| enableAutoFocus      | Boolean | true      | 是否开启摄像头自动对焦，如果关闭，需要用户手动点击摄像头中的预览画面进行对焦。 |
| enableZoom           | Boolean | false     | 是否支持双手滑动调整摄像头焦距。    |
| minBitrate           | String  | 200       | 最小码率，不建议设置太低。  |
| maxBitrate           | String  | 1000      | 最大码率，需要跟分辨率相匹配，建议参考[分辨率码率参照表](https://cloud.tencent.com/document/product/647/32236#.E5.88.86.E8.BE.A8.E7.8E.87.E7.A0.81.E7.8E.87.E5.8F.82.E7.85.A7.E8.A1.A8)。 |
| videoWidth           | String  | 360       | 视频宽（若设置了视频宽高就会忽略aspect）  |
| videoHeight          | String  | 640       | 视频高（若设置了视频宽高就会忽略aspect）  |
| beautyLevel          | Number  | 0         | 美颜，取值范围 0-9，0表示关闭。 |
| whitenessLevel       | Number  | 0         | 美白，取值范围 0-9，0表示关闭。 |
| videoOrientation     | String  | vertical  | 设置本地画面方向，可选值为：vertical 或 horizontal。 |
| videoAspect          | String  | 9:16      | 宽高比，可选值：3:4 或 9:16  |
| frontCamera          | String  | front     | 设置前置还是后置摄像头 可选值： front 或 back。 |
| enableRemoteMirror   | Boolean | false     | 设置观众端看到的画面的镜像效果，即该属性的变化不会影响到本地画面，而是影响到观众端看到的画面效果。 |
| localMirror          | String  | auto      | 设置主播本地摄像头预览画面的镜像效果，支持如下取值：<br>auto:前置摄像头镜像，后置摄像头不镜像（系统相机的表现）；<br>enable:前置摄像头和后置摄像头都镜像；<br>disable: 前置摄像头和后置摄像头都不镜像；|
| enableBackgroundMute | Boolean | false     | 设置主播端小程序切入后台时是否暂停声音的采集。 |
| audioQuality         | String  | high      | 高音质(48KHz)或低音质(16KHz)，可选值：high 或 low。 |
| audioVolumeType      | String  | voicecall | 系统音量类型，可选值为：<br> media: 媒体音量，voicecall: 通话音量。|
| audioReverbType      | Number  | 0         | 音频混响类型，可选值为： 0: 关闭 1: KTV 2: 小房间 3:大会堂 4:低沉 5:洪亮 6:金属声 7:磁性。|


示例代码：
``` 
 // index.wxml
<trtc-room id="trtcroom" config="{{trtcConfig}}"></trtc-room>
```

```
// videocall.js
trtcConfig = {
  sdkAppID: '1401000123', // 开通实时音视频服务创建应用后分配的 SDKAppID
  userID: 'test_user_001', // 用户 ID，可以由您的帐号系统指定
  userSig: 'xxxxxxxxxxxx', // 身份签名，相当于登录密码的作用
  template: 'grid', // 画面排版模式
}
```

## 方法

### 获取组件实例
组件实例提供了以下API，通过小程序提供的 this.selectComponent() 方法获取组件实例后即可调用，示例代码

```
let trtcRoomContext = this.selectComponent('#trtcroom')
trtcRoomContext.enterRoom({roomID: 2233})
```

-----------
### on(EventCode, handler, context)

**说明：**

用于监听组件派发的事件，详细事件请参考[事件表](#Event)。
>! 请在调用 enterRoom 前监听事件，避免漏掉组件派发的事件。

**参数：**

| 参数名    | 类型     | 默认值 | 说明           |
|:----------|:---------|:-------|:-------------|
| EventCode | String   | -      | 事件码         |
| handler   | Function | -      | 监听函数       |
| context   | Object   | -      | 当前执行上下文 |

**返回值：**

无

**示例代码：**
```
function onLocalJoin(event) {
  // 本地进房成功
}
trtcRoomContext.on(trtcRoomContext.EVENT.LOCAL_JOIN, onLocalJoin, this)
```

-----------
### off(EventCode, handler)

**说明：**

取消事件监听。

**参数：**

| 参数名    | 类型     | 默认值 | 说明                   |
|:----------|:---------|:-------|:---------------------|
| EventCode | String   | -      | 事件码                 |
| handler   | Function | -      | 需要取消的具名监听函数 |

**返回值：**

无

**示例代码：**
```
function onLocalJoin(event) {
  // 本地进房成功
}
trtcRoomContext.off(trtcRoomContext.EVENT.LOCAL_JOIN, onLocalJoin)
```

-----------
### enterRoom(params)

**说明：**
进入房间，调用参数必须传入roomID。

**参数：**

| 参数名 | 类型   | 默认值 | 说明                                         |
|:-------|:-------|:-------|:-------------------------------------------|
| roomID | String | -      | 房间 ID，由您的系统决定，取值范围 1~4294967295 |

**返回值：**
Promise

**示例代码：**
```
trtcRoomContext.enterRoom({roomID: 2233}).catch((error)=>{
  // 进入房间失败
  // 注意：进入房间成功通过事件 LOCAL_JOIN 通知
})
```

-----------
### exitRoom()
**说明：**

停止推流和取消订阅所有远端音视频，并退出房间。

**参数：**

无

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.exitRoom().then(()=>{
  // 退出房间成功
})
```

-----------
### publishLocalVideo()
**说明：**

发布本地视频，即开启本地摄像头采集并启动视频推流。一般要配合 `publishLocalAudio()` 一起使用。

**参数：**

无

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.publishLocalVideo().then(()=>{
  // 发布成功
})
```

-----------
### unpublishLocalVideo()
**说明：**

取消发布本地视频，关闭本地视频推流。

**参数：**

无

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.unpublishLocalVideo().then(()=>{
  // 取消发布成功
})
```

-----------
### publishLocalAudio()
**说明：**

发布本地音频，即开启本地麦克风采集并启动音频推流。如果是纯音频沟通场景，则不需要调用 `publishLocalVideo()`。 

**参数：**

无

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.publishLocalAudio().then(()=>{
  // 发布成功
})
```

-----------
### unpublishLocalAudio()
**说明：**

取消发布本地音频，关闭本地音频推流。

**参数：**

无

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.unpublishLocalAudio().then(()=>{
  // 取消发布成功
})
```

-----------
### subscribeRemoteVideo(params)
**说明：**

订阅远端用户的视频流，并进行播放。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                                                           |
|:-----------|:-------|:-------|:---------------------------------------------------------------------------------------------|
| userID     | String | -      | 必填，远端用户 ID，通过组件通知的 REMOTE_VIDEO_ADD 事件，可以获知（可以订阅的）远端视频流的 userID 和 streamType。 |
| streamType | String | -      | 必填，远端用户的流类型，可选值：<br> 'main'：大画面 <br> 'small'：小画面 <br>'aux'：辅流（即屏幕分享） |

**返回值：**

Promise

**示例代码：**

```
// 有可以订阅的远端视频流
function onRemoteVideoAdd(event) {
  trtcRoomContext.subscribeRemoteVideo({
    userID: event.data.userID,
    streamType: event.data.streamType
  }).then(()=>{
    // 订阅成功
  })
}

trtcRoomContext.on(trtcRoomContext.EVENT.REMOTE_VIDEO_ADD, onRemoteVideoAdd)
```

-----------
### unsubscribeRemoteVideo(params)
**说明：**

取消订阅远端用户的视频，并停止播放。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                              |
|:-----------|:-------|:-------|:----------------------------------------------------------------|
| userID     | String | -      | 必填，远端用户 ID，通过组件通知的 REMOTE_VIDEO_REMOVE 事件，可以获知（取消发布的）远端视频流的 userID 和 streamType。   |
| streamType | String | -      | 必填，远端用户的流类型，可选值：'main'：大画面；'aux'：辅流（即屏幕分享） |

**返回值：**

Promise

**示例代码：**

```
// 有一路远端视频流关闭了
function onRemoteVideoRemove(event) {
  trtcRoomContext.unsubscribeRemoteVideo({
    userID: event.data.userID,
    streamType: event.data.streamType
  }).then(()=>{
    // 取消订阅成功
  })
}
trtcRoomContext.on(trtcRoomContext.EVENT.REMOTE_VIDEO_REMOVE, onRemoteVideoRemove)
```

-----------
### subscribeRemoteAudio(params)
**说明：**

订阅远端用户的音频，并进行播放。

**参数：**

| 参数名 | 类型   | 默认值 | 说明                                                  |
|:-------|:-------|:-------|:----------------------------------------------------|
| userID | String | -      | 远端用户 ID，通过组件通知的 REMOTE_AUDIO_ADD 事件，可以获知（可以订阅的）远端音频流的 userID。 |

**返回值：**

Promise

**示例代码：**
```
// 有可以订阅的远端音频流
function onRemoteAudioAdd(event) {
  trtcRoomContext.subscribeRemoteAudio({
    userID: event.data.userID
  }).then(()=>{
    // 订阅成功
  })
}

trtcRoomContext.on(trtcRoomContext.EVENT.REMOTE_AUDIO_ADD, onRemoteAudioAdd)
```

-----------
### unsubscribeRemoteAudio(params)
**说明：**

取消订阅远端用户的音频，并且进行播放

**参数：**

| 参数名 | 类型   | 默认值 | 说明                                                     |
|:-------|:-------|:-------|:-------------------------------------------------------|
| userID | String | -      | 远端用户 ID，通过组件派发的事件 REMOTE_AUDIO_REMOVE 可以获知（取消发布的）远端音频流的 userID。 |

**返回值：**

Promise

**示例代码：**
```
// 有一路远端音频流关闭了
function onRemoteAudioRemove(event) {
  trtcRoomContext.unsubscribeRemoteAudio({
    userID: event.data.userID
  }).then(()=>{
    // 取消订阅成功
  })
}

trtcRoomContext.on(trtcRoomContext.EVENT.REMOTE_AUDIO_ADD, onRemoteAudioRemove)
```

-----------
### switchCamera()
**说明：**

切换本地前后摄像头。

**参数：**

无

**返回值：**

无

**示例代码：**
```
trtcRoomContext.switchCamera()
```

-----------
### getRemoteUserList()
**说明：**

获取远端用户列表。

**参数：**

无

**返回值：**

Array[Object]

**示例代码：**
```
let userList = trtcRoomContext.getRemoteUserList()
// 数据格式示例
// userList = [
//   {
//     userID:'xxx',       // 该用户 ID 
//     hasMainVideo: true, // 该用户是否有主流视频
//     hasMainAudio: true, // 该用户是否有主流音频
//     hasAudVideo: false  // 该用户是否有辅流（屏幕分享）视频
//   }
//   ...
// ]
```

-----------
### playBGM(params)
**说明：**

播放背景音乐。背景音乐会同麦克风采集的人声混合在一起发布到云端，也就是所谓的“背景混音”。

**参数：**

| 参数名 | 类型   | 默认值 | 说明                                              |
|:-------|:-------|:-------|:------------------------------------------------|
| url    | String | -      | 背景音播放地址，注意：仅支持 https 协议的在线音乐，音乐文件格式仅支持 mp3 以及 aac 格式。 |

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.playBGM({
  url: 'https://a.xxx.com/bgm.mp3' 
}).then(()=>{
    // 播放成功
}).catch(()=>{
    // 播放失败
})
```

-----------
### stopBGM()
**说明：**

停止播放背景音乐。

**参数：**

无

**返回值：**

无

**示例代码：**
```
trtcRoomContext.stopBGM()
```

-----------
### pauseBGM()
**说明：**

暂停播放背景音乐。

**参数：**

无

**返回值：**

无

**示例代码：**
```
trtcRoomContext.pauseBGM()
```

-----------
### resumeBGM()
**说明：**

恢复播放背景音乐。

**参数：**

无

**返回值：**

无

**示例代码：**
```
trtcRoomContext.resumeBGM()
```

-----------
### setBGMVolume(params)
**说明：**

设置混音中背景音乐的音量。

**参数：**

| 参数名 | 类型   | 默认值 | 说明                  |
|:-------|:-------|:-------|:--------------------|
| volume | number | -      | 音量大小，取值范围：0~1 |

**返回值：**

无

**示例代码：**
```
trtcRoomContext.setBGMVolume({
  volume: 0.5
})
```

-----------
### setMICVolume(params)
**说明：**

设置混音中麦克风采集的音量。

**参数：**

| 参数名 | 类型   | 默认值 | 说明                  |
|:-------|:-------|:-------|:--------------------|
| volume | number | -      | 音量大小，取值范围：0~1 |

**返回值：**

无

**示例代码：**
```
trtcRoomContext.setMICVolume({
  volume: 0.5
})
```

-----------
### snapshot(params)
**说明：**

截取指定远端视频或本地视频的图像，并保存到系统相册中。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                              |
|:-----------|:-------|:-------|:----------------------------------------------------------------|
| userID     | String | -      | 必填，用户 ID。 |
| streamType | String | -      | 必填，流类型，可选值为 'main'：主流 ；'aux'：辅流（屏幕分享）。本地视频流类型仅支持 'main'：主流|

**返回值：**

无

**示例代码：**
```
// 截取远端用户视频图像
trtcRoomContext.snapshot({
  userID: 'xxx',            // 远端用户 ID
  streamType: 'main'        // 远端用户流类型
}).then((event)=>{
  // 截图成功
})

// 截取本地用户视频图像
trtcRoomContext.snapshot({
  userID: 'xxx',            // 本地用户 ID
  streamType: 'main'        // 本地用户流类型仅支持 'main'：主流 
}).then((event)=>{
  // 截图成功
})
```

-----------
### enterFullscreen(params)
**说明：**

将远端视频切换为全屏播放，辅路（也就是屏幕分享）的画面一般适合全屏播放。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                              |
|:-----------|:-------|:-------|:----------------------------------------------------------------|
| userID     | String | -      | 必填，用户 ID。 |
| streamType | String | -      | 必填，流类型，可选值为 'main'：主流 ；'aux'：辅流（屏幕分享）。|

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.enterFullscreen({
  userID: 'xxx',
  streamType: 'main'
}).then((event)=>{
  // 全屏成功
}).catch((event)=>{
  // 全屏失败
})
```

-----------
### exitFullscreen(params)
**说明：**

取消远端视频的全屏模式。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                              |
|:-----------|:-------|:-------|:----------------------------------------------------------------|
| userID     | String | -      | 必填，用户 ID。 |
| streamType | String | -      | 必填，流类型，可选值为 'main'：主流 ；'aux'：辅流（屏幕分享）。|

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.exitFullscreen({
  userID: 'xxx',
  streamType: 'main'
}).then((event)=>{
  // 退出全屏成功
}).catch((event)=>{
  // 退出全屏失败
})
```

-----------
### setViewOrientation(params)
**说明：**

设置指定远端画面的显示方向。

**参数：**

| 参数名      | 类型   | 默认值 | 说明                                                                |
|:------------|:-------|:-------|:------------------------------------------------------------------|
| userID      | String | -      | 必填，远端用户 ID。 |
| streamType  | String | -      | 必填，远端用户的流类型，可选值为 'main'：主流 'aux'：辅流（屏幕分享）。 |
| orientation | String | -      | 必填，竖向：vertical, 横向：horizontal。                    |

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.setViewOrientation({
  userID: 'xxx',
  streamType: 'main',
  orientation: 'vertical'
}).then((event)=>{
  // 设置成功
})
```

-----------
### setViewFillMode(params)
**说明：**

设置指定远端画面的填充模式。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                                                                           |
|:-----------|:-------|:-------|:-------------------------------------------------------------------------------------------------------------|
| userID     | String | -      | 必填，远端用户 ID。                                                                                                   |
| streamType | String | -      | 必填，远端用户的流类型，可选值为 'main'：主流 'aux'：辅流（屏幕分享）。                                            |
| fillMode   | String | -      | 必填，可选值：<br>适应：contain（完整显示画面，可能会有黑边）<br>填充：fillCrop（铺满视图，可能会裁切部分画面） |

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.setViewFillMode({
  userID: 'xxx',
  streamType: 'main',
  fillMode: 'contain'
}).then((event)=>{
  // 设置成功
})
```

-----------
### setViewVisible(params)
**说明：**
显示或者隐藏某一路视频画面。
>!该方法只有在使用组件内置的画面排版模式时才有效，比如：template:'grid' 或者 template:'1v1'，template:"custom"。

**参数：**

| 参数名     | 类型    | 默认值 | 说明                                                                |
|:-----------|:--------|:-------|:------------------------------------------------------------------|
| userID     | String  | -      | 必填，用户 ID                                                        |
| streamType | String  | -      | 设置远端用户时必填，远端用户的流类型，可选值为 'main'：主流 'aux'：辅流（屏幕分享）。  |
| isVisible  | Boolean | -      | 必填，是否显示改视图                                                 |

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.setViewVisible({
  userID: 'xxx',
  streamType: 'main',
  isVisible: true
}).then((event)=>{
  // 设置成功
})
```

-----------
### setViewRect(params)
**说明：**

设置指定视频画面的坐标和尺寸。
>!该方法只有在 template:"custom" 时才有效。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                                |
|:-----------|:-------|:-------|:------------------------------------------------------------------|
| userID     | String | -      | 必填，用户 ID                                                        |
| streamType | String | -      | 设置远端用户时必填，远端用户的流类型，可选值为 'main'：主流 'aux'：辅流（屏幕分享）。  |
| xAxis      | Number | -      | 可选，视图的横坐标                                                   |
| yAxis      | Number | -      | 可选，视图的纵坐标                                                   |
| width      | Number | -      | 可选，设置视图的宽度                                                 |
| height     | Number | -      | 可选，设置视图的高度                                                 |

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.setViewRect({
  userID: 'xxx',
  streamType: 'main',
  xAxis: '480rpx',
  yAxis: '160rpx',
  width: '240rpx',
  height: '320rpx',
}).then((event)=>{
  // 设置成功
})
```

-----------
### setViewZIndex(params)
**说明：**

设置指定视频画面的层级。
>!该方法只有在 template:"custom" 时才有效。
> 该方法只有在小程序同层模式下有效，微信 7.0.8 之后的版本已经支持同层模式。

**参数：**

| 参数名     | 类型   | 默认值 | 说明                                                                |
|:-----------|:-------|:-------|:------------------------------------------------------------------|
| userID     | String | -      | 必填，用户 ID                                                        |
| streamType | String | -      | 设置远端用户时必填，远端用户的流类型，可选值为 'main'：主流 'aux'：辅流（屏幕分享）。  |
| zindex     | Number | -      | 必填，视图的层级，必须为整数                                          |

**返回值：**

Promise

**示例代码：**
```
trtcRoomContext.setViewZIndex({
  userID: 'xxx',
  streamType: 'main',
  zindex: 10
}).then((event)=>{
  // 设置成功
})
```

<h2 id="Event">事件表</h2>

通过组件实例的 EVENT 属性可以获取到事件常量字段，示例：

```
let EVENT = trtcRoomContext.EVENT
trtcRoomContext.on(EVENT.REMOTE_VIDEO_ADD,(event)=>{
    // 远端视频流添加事件，当远端用户取消发布音频流后会收到该通知
})
```
| CODE                       | 说明                                                     |
|----------------------------|----------------------------------------------------------|
| LOCAL_JOIN                 | 成功进入房间。                                               |
| LOCAL_LEAVE                | 成功离开房间。                                               |
| REMOTE_USER_JOIN           | 远端用户进入房间时触发。                                     |
| REMOTE_USER_LEAVE          | 远端用户退出房间时触发。                                     |
| REMOTE_VIDEO_ADD           | 远端视频流添加事件，当远端用户取消发布音频流后会收到该通知。 |
| REMOTE_VIDEO_REMOVE        | 远端视频流移出事件，当远端用户取消发布音频流后会收到该通知。 |
| REMOTE_AUDIO_ADD           | 远端音频流添加事件，当远端用户取消发布音频流后会收到该通知。 |
| REMOTE_AUDIO_REMOVE        | 远端音频流移除事件，当远端用户取消发布音频流后会收到该通知。 |
| REMOTE_STATE_UPDATE        | 远端用户播放状态变更通知。                                   |
| LOCAL_NET_STATE_UPDATE     | 本地推流网络状态变更通知。                                   |
| REMOTE_NET_STATE_UPDATE    | 远端用户网络状态变更通知。                                   |
| LOCAL_AUDIO_VOLUME_UPDATE  | 本地音量变更通知。                                           |
| REMOTE_AUDIO_VOLUME_UPDATE | 远端用户音量变更通知。                                       |
| VIDEO_FULLSCREEN_UPDATE    | 远端视图全屏状态变更通知。                                   |
| BGM_PLAY_PROGRESS          | BGM 播放时间戳变更通知。                                     |
| BGM_PLAY_COMPLETE          | BGM 播放结束通知。                                           |
| ERROR                      | 本地推流出现错误、渲染错误事件等。                           |


## 错误码
ERROR事件触发时会返回响应的错误码，错误码含义如下
```
let EVENT = trtcRoomContext.EVENT
trtcRoomContext.on(EVENT.ERROR,(event)=>{
  console.log(event.data.code)
})
```
| CODE  | 说明           |
|-------|--------------|
| -1301 | 打开摄像头失败 |
| -1302 | 打开麦克风失败 |
| -1303 | 视频编码失败   |
| -1304 | 音频编码失败   |
| -1307 | 推流连接断开   |
