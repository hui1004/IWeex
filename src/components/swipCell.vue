<template>
    <div class="cell" :style="cell_style" ref="cell" @swipe="handleSwipe">
        <slot  name="cell_content" ></slot>
        <div class="rightItems" @click="itemClick(item)" v-for="item in rightItems"
             :style="{height:rightItems_height,backgroundColor:item.backgroundColor,width:item.width}">
            <text :style="item.textStyle">{{item.text}}</text>
        </div>
    </div>
</template>

<script>
    var animation=weex.requireModule('animation');
    var modal=weex.requireModule('modal');
    const domModule = weex.requireModule('dom')
    export default {
        name: "swip-cell",
        props:{
            rightItems:{
               default:[
                   {
                       backgroundColor:"#FF0000",
                       textStyle:{
                           color:"#ffffff",
                           fontSize:30
                       },
                       width:100,
                       text:"删除",
                       icon:"root:img/logo.png"
                   }
               ],
               type:Array
            },
            width:{
                default:750,
                type:Number,
            },
        },
        data(){
            return{
                cell_style:{
                    width:750
                },
                rightItems_height:0
            }
        },
        created(){
            let self=this;
            this.cell_style={width:this.width+this.getRightWidth()};
        },
        mounted(){

        },
        methods:{
            handleSwipe(e){
                let self=this;
                let cell=this.$refs.cell.ref;
                if(e.direction=='left'){
                    //动态获取右侧操作项的高度
                    this.rightItems_height=domModule
                        .getComponentRect(this.$refs.cell, function (res) {
                             self.rightItems_height=res.size.height;
                        });
                    this.move(cell,-this.getRightWidth());
                }else {
                   this.move(cell,0);
                }
            },
            move(ref,position){
                animation.transition(ref,{
                    styles: {
                        transform:`translateX(${position})`,
                    },
                    duration: 300, //ms
                    timingFunction: 'ease-out',
                    needLayout:false,
                    delay: 0 //ms
                },function (res) {

                })
            },
            getRightWidth(){
                let right_w=0;
                this.rightItems.forEach(item=>{
                    right_w+=item.width;
                });
                return right_w;
            },
            itemClick(item){
                let cell=this.$refs.cell.ref;
                this.move(cell,0);
                this.$emit("swipe_item_click",item);
            }
        }
    }
</script>

<style scoped>
    .rightItems{
        align-items: center;
        justify-content: center;
    }
    .cell{
        flex-direction: row;
    }

</style>
