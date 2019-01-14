<template>
  <div class="wrapper">
    <image :src="logo" class="logo"  @click="turn"/>
    <text class="greeting">The environment is ready!</text>
    <HelloWorld/>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld'
var navigator = weex.requireModule('navigator')
var modal = weex.requireModule('modal')
export default {
  name: 'App',
  components: {
    HelloWorld
  },
  data () {
    return {
      logo: 'https://gw.alicdn.com/tfs/TB1yopEdgoQMeJjy1XaXXcSsFXa-640-302.png'
    }
  },
    methods:{
        turn(){
            navigator.push({
                url: 'http://192.168.1.103:8081/dist/twoPage.web.js',
                animated: "true"
            }, event => {
                modal.alert({ message: 'callback: ' + event })
            })
        }
    }
}
</script>

<style scoped>
  .wrapper {
    justify-content: center;
    align-items: center;
  }
  .logo {
    width: 424px;
    height: 200px;
  }
  .greeting {
    text-align: center;
    margin-top: 70px;
    font-size: 50px;
    color: #41B883;
  }
  .message {
    margin: 30px;
    font-size: 32px;
    color: #727272;
  }
</style>
