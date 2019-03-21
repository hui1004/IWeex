<template>
    <div style="width: 750px;align-items: center">
        <head title="lottie_demo"></head>
        <scroller style="width: 750px;align-items: center">
            <text style="font-size: 30px;margin-top: 20px;height: 80px;" @click="stop">点击暂停2s...</text>
            <lottie :ref="item" v-for="(item,index) in animalNames" :key="index" style="height:400px;width:750px;"
                    :animationName="item"
            loop="true">
            </lottie>
        </scroller>

    </div>
</template>
<script>
    import Head from "../components/head";
    var modal=weex.requireModule('modal');
    export default {
        components: {Head},
        data:{
            animalNames:["deliver","ren","jinguang","radar","number","testLottie"],
            loop:true,

        },
        methods:{
            stop(){
                var self=this;
                // this.$refs[ref.currentTarget.attr.animationName][0].stopAnimation();
                this.animalNames.forEach(item=>{
                    self.$refs[item][0].pauseAnimation();
                    setTimeout(function (res) {
                        self.$refs[item][0].starAnimation();
                    },2000);
                })
            }
        }
    }
</script>

<style scoped>

</style>
