<template>
    <div >
        <head title="界面跳转" showRight="true" @rightItemClick="clickRight">
            <div slot="rightItem">
                <text>自定义右边</text>
            </div>
        </head>
        <div style="width: 750px;height: 700px;align-items: center;justify-content: center;">
            <div class="button" @click="popPage">
                <text style="font-size: 35px;font-weight: bold">返回上一页</text>
            </div>
        </div>
    </div>
</template>

<script>
    import head from './components/head.vue'
    let modal=weex.requireModule('modal');
    var nav=weex.requireModule('navigator');
    var params=weex.requireModule('params');
    export default {
        name: "two-page",
        components:{
            head
        },
        methods:{
          popPage(){
              nav.pop({
                  url: '',
                  animated: "true"
              }, event => {
              })
          },
            clickRight(){
              modal.toast({message:'点击顶部导航右部按钮'})
            }
        },
        created(){
            params.getParam(function (res) {
                modal.toast({message:res});
            });
            console.log("aaa");
        }
    }
</script>

<style scoped>

</style>
