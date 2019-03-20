IWeex
===================================
# Introduction

这是一个基于weex框架搭建的APP快速开发的模板项目，目前只支持android端，会逐渐扩展android端和ios端的功能。

# 使用说明  

1、直接在git上下载本项目  
2、在项目的根目录下执行npm install命令安装所需依赖项  
3、在你的编辑器（最好是webstorm）中打开项目，并执行npm run serve，即可在浏览器上预览界面，至此你也已经可以在src下使用vue的语法进行APP的开发了，如果想要在手机上预览效果或者打出发布包需要如下几步。  
Android：  
1、使用Android studio 打开项目中的Android工程，Android工程在platform/android目录下。  
2、在Android工程下的我们打开项目的Android面板，然后打开res/xml下的app_Config.xml文件，在此文件中我们可以配置打包模式、启动界面等。  
3、使用手机链接电脑，打开开发者模式和USB调试，修改app_config.xml中的debug属性为true，使用Androidstudio给你的手机打安装测试APP。  
4、使用测试APP扫描浏览器上的二维码即可在手机上预览界面  
iOS：  
敬请期待...  
# 体验IWeex  

[![image](https://github.com/liuxinyea/IWeex/blob/master/doc/image/downLoad.png)](https://www.pgyer.com/VPzf)    

 [点击下载体验](https://www.pgyer.com/RTEV)


# 框架扩展

1、navigator路由模块扩展
本项目扩展了weex的路由导航模块，支持相对路径的写法加载进行导航，并且可以传参  

跳转：   
navigator.push({  
                url: './twoPage.js',  
                param:{a:11111},  
                animated: "true"  
            }, event => {  
                // modal.toast({ message: 'callback: ' + event })  
            })  
返回：    
nav.pop({  
                  url: '',  
                  animated: "true"  
              }, event => {  
              }) 
获取前一个界面的传参：    
 params.getParam(function (res) {  
                //res就是前一个界面传递过来的参数
                modal.toast({message:res});  
            });  

2、image扩展
扩展了image标签支持加载本地图片，图片需要放在nativie目录下，支持相对路径写法。  

         ../ 跳出当前目录 ：  
         src="../image/iclauncher.png"  
         ./ 当前目录下    ：  
         src="./image/iclauncher.png"  
         相对于根目录下   ：  
         src="app:image/iclauncher.png"  
         
3、新增组件  
hostPage：可以加载多个子界面，通过加载js文件的方式加载，支持滑动切换界面   
superHostPage：跟hostPage的效果一直，写法不同，通过加载子元素的方式加载子界面，每个子界面是其一级儿子元素。  
map：高德地图组件  
mapMarker：地图标记组件，只能作为map的子组件使用  
mapCircle：地图上绘制圆形区域组件，只能作为map的子组件使用   

4、新增模块  
location：获取用户当前位置信息、获取两点坐标之间距离等  
wxPay：微信支付  
iwx_utils.turnToContact()：获取手机联系人  
iwx_utils.openvoiceCog():语义识别  
iwx_utils.call():拨打电话  
iwx_utils.getPhoneInfo():获取设备信息  

5、支持极光推送
   
# weex开发相关文档
https://blog.csdn.net/qq_33718648/column/info/32906
  


