<template>
    <div>
        <head :title="vueTile" :canBack="vueBack"></head>
        <div style="width: 750px;flex-direction:column;justify-content: center;align-items: center">
            <div class="button" @click="setVueTitle">
                <text class="button_text">设置vue组件title</text>
            </div>
            <div class="button" @click="setNativeTitle">
                <text class="button_text">设置原生组件title</text>
            </div>
            <div class="button" @click="showNativeHead">
                <text v-if="showNative" class="button_text">隐藏原生head</text>
                <text v-else class="button_text">显示原生head</text>
            </div>
            <div class="button"  @click="setNativeBack">
                <text v-if="nativeBack" class="button_text">隐藏原生headLeft</text>
                <text v-else class="button_text">显示原生headLeft</text>
            </div>
            <div class="button" @click="vueBack=!vueBack">
                <text v-if="vueBack"  class="button_text">隐藏vue head返回</text>
                <text v-else class="button_text">显示vue head返回</text>
            </div>
        </div>
    </div>
</template>

<script>
    import Head from "../components/head";
    var modal=weex.requireModule('modal');
    var navigator = weex.requireModule('navigator')
    export default {
        components: {Head},
        name: "head-nav-demo",
        data:{
          vueTile:"vue组件head",
          nativeTitle:"原生head组件",
          showNative:true,
          vueBack:false,
          nativeBack:false
        },
        methods:{
            setNativeBack(){
                this.nativeBack=!this.nativeBack;
                if(!this.nativeBack){
                    navigator.clearNavBarLeftItem('1');
                }else{
                    navigator.setNavBarLeftItem('1',function () {
                    });
                }
            },
            setVueTitle(){
                let self=this;
                modal.prompt({
                    message:"设置vue组件title",
                    okTitle:"确定",
                    cancelTitle:"取消"
                },function (res) {
                    if(res.result==="确定"&&res.data!==""){
                        self.vueTile=res.data;
                    }
                })
            },
            setNativeTitle(){
                let self=this;
                modal.prompt({
                    message:"设置原生组件title",
                    okTitle:"确定",
                    cancelTitle:"取消"
                },function (res) {
                    if(res.result==="确定"&&res.data!==""){
                        navigator.setNavBarTitle(res.data,function (res) {
                        });
                    }
                })
            },
            showNativeHead(){
                this.showNative=!this.showNative;
                if(this.showNative){
                    navigator.setNavBarHidden("0",function (res) {
                    });
                }else{
                    navigator.setNavBarHidden("1",function (res) {
                    });
                }
            }
        },
        created(){
            if(!this.nativeBack){
                navigator.clearNavBarLeftItem('1');
            }else{
                navigator.setNavBarLeftItem('1',function () {
                });
            }
            navigator.setNavBarTitle(this.nativeTitle,function (res) {
            });
            // nav.setNavBarHidden("aaa",function (res) {
            // });
        }
    }
</script>

<style scoped>
    .button{
        width: 300px;
        height: 90px;
        border-radius:20px;
        align-items: center;
        justify-content: center;
        background-color: orange;
        margin-top: 20px;
    }
    .button:active{
        width: 300px;
        height: 90px;
        border-radius:20px;
        align-items: center;
        justify-content: center;
        background-color: orangered;
        margin-top: 20px;
    }
    .button_text{
        font-size: 30px;
        font-weight: bold;
        color: white;
    }

</style>