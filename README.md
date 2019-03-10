IWeex
===================================
# Introduction

这是一个基于weex框架搭建的APP项目，目前只支持android端，会逐渐扩展android端和ios端的功能。

# 使用说明  

1、直接在git上下载本项目  
2、在项目的根目录下执行npm install命令安装所需依赖项  
3、在你的编辑器（最好是webstorm）中打开项目，并执行npm run serve，即可在浏览器上预览界面，至此你也已经可以在src下使用vue的语法进行APP的开发了，如果想要在手机上预览效果或者打出发布包需要如下几步。  
4、使用Android studio 打开项目中的Android工程，Android工程在platform/android目录下。  
5、在Android工程下的我们打开项目的Android面板，然后打开res/xml下的app_Config.xml文件，在此文件中我们可以配置打包模式、启动界面等。  
6、使用手机链接电脑，打开开发者模式和USB调试，修改app_config.xml中的debug属性为true，使用Androidstudio给你的手机打安装测试APP。  
7、使用测试APP扫描浏览器上的二维码即可在手机上预览界面

# 扩展

1、本项目扩展了weex的路由导航模块，支持相对路径的写法加载进行导航，并且可以传参  

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

2、扩展了image标签支持加载本地图片，图片需要放在nativie目录下
   

# 相关文档
https://blog.csdn.net/qq_33718648/column/info/32906
  


