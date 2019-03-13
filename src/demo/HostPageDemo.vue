<template>
    <div>
        <hostPage ref="host" class="host" :index="position" :pages="urls"  @pageFinish="allPageFinished" @onPageScrolled="onPageScrolled"
                  @onPageSelected="onPageSelected">
        </hostPage>
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
    var modal=weex.requireModule('modal');
    export default {
        name: "host-page-demo",
        data:{
            urls:['./weex-ui.js','./Image.js','./video.js'],
            position:0
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
</style>
