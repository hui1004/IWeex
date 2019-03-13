<template>
  <div class="wrapper">
    <head title="首页" :canBack="false"></head>
    <text class="greeting">The environment is ready!!!!</text>
    <HelloWorld/>
    <div style="height: 90px;width: 200px;background-color: #00B4FF;align-items: center;justify-content: center;
     border-radius: 10px" @click="turn">
      <text style="font-weight: bold;font-size: 29px;color: #ffffff">PopDemo</text>
    </div>
    <div style="height: 90px;width: 200px;background-color: #00B4FF;align-items: center;justify-content: center;margin-top: 30px;
      border-radius: 10px" @click="toVideo">
      <text style="font-weight: bold;font-size: 29px;color: #ffffff">videoDemo</text>
    </div>
    <div style="height: 90px;width: 200px;background-color: #00B4FF;align-items: center;justify-content: center;margin-top: 30px;
      border-radius: 10px" @click="toImage">
      <text style="font-weight: bold;font-size: 29px;color: #ffffff">Image</text>
    </div>
    <div style="height: 90px;width: 200px;background-color: #00B4FF;align-items: center;justify-content: center;margin-top: 30px;
      border-radius: 10px" @click="toWeeUi">
      <text style="font-weight: bold;font-size: 29px;color: #ffffff">weex-ui示例</text>
    </div>
    <div style="height: 90px;width: 200px;background-color: #00B4FF;align-items: center;justify-content: center;margin-top: 30px;
      border-radius: 10px" @click="TabBar">
      <text style="font-weight: bold;font-size: 29px;color: #ffffff">TabBar示例</text>
    </div>
    <div style="height: 90px;width: 200px;background-color: #00B4FF;align-items: center;justify-content: center;margin-top: 30px;
      border-radius: 10px" @click="TabPage">
      <text style="font-weight: bold;font-size: 29px;color: #ffffff">TabPage示例</text>
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
        }
    },
    created(){
        var modal=weex.requireModule('modal');
        // modal.alert({message: weex.config.env});

    }
}
</script>

<style scoped>
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
