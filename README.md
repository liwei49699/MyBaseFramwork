# MyBaseFramework
## app的简单封装
### 1.基本的应用程序框架
* BaseActivity的抽取
* MVP架构的简单封装
* 各组件的分包
* 各类工具的收集

### 2.RxJava全家桶的简单使用
* RxJava的各种操作符(背压:事件的发布速度远大于事件的处理速度)
* RxLifecycle绑定生命周期,避免内存泄漏
* RxBus,otto,EventBus的简单使用(含TAG)
* RxPremissions的使用,永远拒绝跳到设置界面

### 3.音频的相关操作
* 音频的录制实现方式
* 音频的合并
* 音频的转码

### 4.JNI的简单使用
* NDK环境的配置
* 利用JNI调用C/C++代码
* 生成so文件,直接使用so文件

### 5.leakCanary的简单使用
* activity中的内存泄漏

### 6.stetho Chrome调试神器
* 调试网络 （状态，response）
* 调试sp （可直接编辑，并能即时响应）
* 调试数据库 (可执行SQL语句)

### 7.系统悬浮框
* 各平台的悬浮框权限适配
* 悬浮球的移动边界判断及动画
* 悬浮菜单的显示
* 桌面直接跳activity不延时
