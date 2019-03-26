<template>
    <div>
        <superHostPage ref="host" class="host" :index="position"   @pageFinish="allPageFinished" @onPageScrolled="onPageScrolled"
                  @onPageSelected="onPageSelected">
            <!--几个子界面必须是根节点，并与tabBar数目对应-->
            <div class="page">
                <head title="首页"></head>
                <div>
                    <text style="font-size: 40px;margin-top: 300px">首页</text>
                </div>
            </div>
            <div class="page">
                <head title="图片"></head>
                <div>
                    <text style="font-size: 40px;margin-top: 300px">图片</text>
                </div>
            </div >
            <div class="page">
                <head title="视频"></head>
                <video class="video" :src="src" autoplay controls
                       @start="onstart" @pause="onpause" @finish="onfinish" @fail="onfail"></video>
                <text class="info">state: {{state}}</text>
                <div>
                    <text style="font-size: 40px;margin-top: 300px">视频</text>
                </div>
            </div>
        </superHostPage>
        <div class="tabBar">
            <div class="tabItem" @click="tabClick(0)" :style="{backgroundColor:position==0?'#17acf6':'#ffffff'}">
                <text>首页</text>
            </div>
            <div class="tabItem" @click="tabClick(1)" :style="{backgroundColor:position==1?'#17acf6':'#ffffff'}">
                <text>图片</text>
            </div>
            <div class="tabItem" @click="tabClick(2)" :style="{backgroundColor:position==2?'#17acf6':'#ffffff'}">
                <text>视频</text>
            </div>
        </div>
    </div>
</template>

<script>
    import Head from "../components/head";
    var nav=weex.requireModule('navigator');
    var modal=weex.requireModule('modal');
    export default {
        components: {Head},
        name: "host-page-demo",
        data:{
            urls:['./weex-ui.js','./Image.js','./video.js'],
            position:1,
            state: '----',
            src:'http://flv2.bn.netease.com/videolib3/1611/01/XGqSL5981/SD/XGqSL5981-mobile.mp4'
        },
        methods:{
            allPageFinished(e){
                // modal.alert({message:e.index})
            },
            onPageScrolled(e){
                console.log(e.positionOffset);
            },
            onPageSelected(e){
                this.position=e.position;
                // modal.toast({message:e.position})
            },
            tabClick(index){
                this.position=index;
                // this.$refs.host.setIndex(index);
            }
        },
        created(){
            var modal=weex.requireModule('modal');
            nav.setNavBarHidden("1",function (res) {
            });
            // modal.alert({message:'11'})
        }
    }
</script>

<style scoped>
    .host{
        width: 750px;
        position: absolute;
        left: 0;
        top: 0;
        bottom: 120px;
    }
    .page{
        width: 750px;
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        align-items: center;
        /*justify-content: center;*/
    }
    .tabBar{
        border-top-width: 1px;
        border-color: #999999;
        height: 120px;
        justify-content: space-between;
        flex-direction: row;
        position: absolute;
        left: 0;
        bottom:0;
        width: 750px;
    }
    .tabItem{
        height: 120px;
        width: 250px;
        align-items: center;
        justify-content: center;
        /*width: 250px;*/
    }
    .video {
        width: 630px;
        height: 350px;
        margin-top: 60px;
        margin-left: 60px;
    }
    .info {
        margin-top: 40px;
        font-size: 40px;
        text-align: center;
    }
</style>
