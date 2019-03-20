<template>
  <div class="wrapper">
    <head title="首页" :canBack="false"></head>
    <text class="greeting">The environment is ready!!!!</text>
    <div class="content">
      <div class="cell" @click="turn">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">PopDemo</text>
      </div>
      <div class="cell" @click="toVideo">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">videoDemo</text>
      </div>
      <div class="cell" @click="toImage">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">Image</text>
      </div>
      <div class="cell" @click="toWeeUi">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">weex-ui示例</text>
      </div>
      <div class="cell" @click="TabBar">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">TabBar示例</text>
      </div>
      <div class="cell" @click="TabPage">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">TabPage示例</text>
      </div>
      <div class="cell" @click="hostPage">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">原生组件hostPage示例</text>
      </div>
      <div class="cell" @click="hostPage1">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">原生组件superHostPage示例,性能更佳</text>
      </div>

      <div class="cell" @click="map">
        <text style="font-weight: bold;font-size: 29px;color: #ffffff">地图组件</text>
      </div>
    </div>
  </div>
</template>
<script>
import HelloWorld from './components/HelloWorld.vue'
import head from './components/head.vue'
var navigator = weex.requireModule('navigator')
var modal = weex.requireModule('modal')
export default {
  name: 'App',
  components: {
    HelloWorld,
      head,
  },
  data () {
    return {
      logo: 'https://gw.alicdn.com/tfs/TB1yopEdgoQMeJjy1XaXXcSsFXa-640-302.png'
    }
  },
    methods:{
        turn(){
            navigator.push({
                url: './twoPage.js',
                param:{a:11111},
                animated: "true"
            }, event => {
                // modal.toast({ message: 'callback: ' + event })
            })
        },
        map(){
            navigator.push({
                url: './demo/MapDemo.js',
                param:{a:11111},
                animated: "true"
            }, event => {
                // modal.toast({ message: 'callback: ' + event })
            })
        },
        toVideo(){
            navigator.push({
                url: './demo/video.js',
                param:{b:22222},
                animated: "true"
            }, event => {
            })
        },
        toImage(){
            navigator.push({
                url: './demo/Image.js',
                animated: "true"
            }, event => {
            })
        },
        toWeeUi(){
            navigator.push({
                url: './demo/weex-ui.js',
                animated: "true"
            }, event => {
            })
        },
        TabBar(){
            navigator.push({
                url: './demo/TabBarDemo.js',
                animated: "true"
            }, event => {
            })
        },
        TabPage(){
            navigator.push({
                url: './demo/TabPageDemo.js',
                animated: "true"
            }, event => {
            })
        },
        hostPage(){
            navigator.push({
                url: './demo/HostPageDemo.js',
                animated: "true"
            }, event => {
            })
        },
        hostPage1(){
            navigator.push({
                url: './demo/SuperHostDemo.js',
                animated: "true"
            }, event => {
            })
        }
    },
    created(){
        var modal=weex.requireModule('modal');
        // modal.alert({message: weex.config.env});

    }
}
</script>

<style scoped>
  .content{
    width: 750px;
    flex-direction: row;
    flex-wrap: wrap;
  }
  .cell{
    height: 90px;padding:30px;background-color: #00B4FF;align-items: center;justify-content: center;margin-top: 30px;
    border-radius: 10px;margin-left: 15px;
  }
  .wrapper {
    /*justify-content: center;*/
    align-items: center;
  }
  .logo {
    width: 424px;
    height: 200px;
  }
  .greeting {
    text-align: center;
    /*margin-top: 20px;*/
    font-size: 50px;
    color: #41B883;
  }
  .message {
    margin: 30px;
    font-size: 32px;
    color: #727272;
  }
</style>
