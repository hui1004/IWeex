<template>
    <div class="header">
        <div v-if="canBack" class="back" @click="back">
            <text style="font-size: 28px;color: white">返回</text>
        </div>
        <div class="titleArea">
            <text class="title">{{title}}</text>
        </div>
        <div v-if="showRight" class="right" @click="rightItemClick">
            <slot name="rightItem"></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "head",
        props:{
            title:{
                default:'标题2'
            },
            canBack:{
                default:true
            },
            showRight:{
                default:false
            }
        },
        methods:{
            back(){
                // var modal=weex.requireModule('modal');
                // modal.alert({message:'pop'})
                var nav=weex.requireModule('navigator');
                nav.pop({},function (res) {
                });
            },
            rightItemClick(){
                this.$emit("rightItemClick",{})
            }
        }
    }
</script>

<style scoped>
    .titleArea{
        width: 450px;
        height: 140px;
        align-items: center;
        justify-content: center;
        margin-top:55px;
    }
    .right{
        height: 140px;
        width: 150px;
        align-items: center;
        justify-content: center;
        position: absolute;
        right:0px;
        top:25px;
    }
    .back{
        height: 140px;
        width: 150px;
        align-items: center;
        justify-content: center;
        position: absolute;
        left:0px;
        top:25px;
        /*background-color: yellow;*/
    }
    .header{
        height: 140px;
        width: 750px;
        background-color: #17acf6;
        align-items: center;
        flex-direction: row;
        justify-content: center;
    }
    .title{
        font-size: 35px;
        color: white;
    }
</style>
