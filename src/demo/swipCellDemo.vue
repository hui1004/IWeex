<template>
    <div>
        <head title="swip-cell-dem"></head>
        <list  style="padding-left: 20px">
            <cell class="cell"  v-for="item in list">
                <div class="devideLine"></div>
                <swip-cell width="710" @swipe_item_click="itemClick" :rightItems="rightItems">
                    <div slot="cell_content" class="cell_content">
                        <text style="font-size: 34px;">{{item}}</text>
                    </div>
                </swip-cell>
                <div class="devideLine"></div>
            </cell>
        </list>
    </div>
</template>
<!--需要设置swip-cell的父容器的宽度与swip-cell的宽度一致，
并且对于整体的样式，如圆角等，需要给到组件的父组件-->
<script>
    import Head from "../components/head.vue";
    import SwipCell from "../components/swipCell.vue";
    var modal=weex.requireModule("modal");
    export default {
        components: {
            SwipCell,
            Head},
        data(){
            return{
                list:["aaaaaa","vvvvvvv","bbbbbbb"],
                rightItems:[
                    {
                        backgroundColor:"#FF0000",
                        textStyle:{
                            color:"#ffffff",
                            fontSize:30
                        },
                        width:150,
                        text:"删除",
                        icon:"root:img/logo.png"
                    },
                    {
                        backgroundColor:"#ff11dd",
                        textStyle:{
                            color:"#ffffff",
                            fontSize:30
                        },
                        width:100,
                        text:"收藏",
                        icon:"root:img/logo.png"
                    },
                    {
                        backgroundColor:"#268cf0",
                        textStyle:{
                            color:"#fffff",
                            fontSize:30
                        },
                        width:100,
                        text:"详情",
                        icon:"root:img/logo.png"
                    }
                ]
            }
        },
        methods:{
            itemClick(event){
                modal.toast({message:event.text})
            }
        }
     }
</script>

<style scoped>
    .cell_content{
        width: 710px;
        background-color: gold;
        padding: 50px;
        align-items: center;
        justify-content: center;
    }
    .devideLine{
        height: 20px;
        width: 600px;
    }
    .cell{
        width: 710px;
        border-radius: 100px;
    }

</style>
