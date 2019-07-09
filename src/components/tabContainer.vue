<template>
    <div class="container">
        <scroller scroll-direction="horizontal" show-scrollbar="false"show style="flex-direction: row;width: 750px;height:90px">
            <div class="tab_top">
                <div ref="tab_item" class="tab_item" @click="itemClick(index)"
                     :style="{width:itemWidth,backgroundColor:backgroundColor}" v-for="(item,index) in tabItems">
                    <text class="tab_item_text" :style="{fontSize:fontSize,
                    color:selectIndex===index?textSelectColor:textColor}">{{item.text}}</text>
                </div>
                <div ref="indicator" class="indicator" :style="{width:itemWidth-40,backgroundColor:indicatorColor}"></div>
            </div>
        </scroller>

        <div class="tab_content" @touchstart="bindingX" :prevent-move-event="true"
             :style="{width:tabItems.length*750}" ref="tab_content">
            <!--@touchstart="bindingX"-->
            <slot></slot>
        </div>

    </div>
</template>

<script>
    import Head from "./head.vue";
    let binding = weex.requireModule('binding');
    import { parse } from 'bindingx-parser';
    let modal=weex.requireModule('modal');
    let dom=weex.requireModule('dom')
    let animation=weex.requireModule('animation');
    export default {
        components: {Head},
        name: "tab-container",
        props:{
            tabItems:{
                default:
                  [{
                    text:"测试1",
                  }],
                type:Array
            },
            itemWidth:{
                default:200,
                type:Number
            },
            fontSize:{
                default:"28px",
                type:String
            },
            textSelectColor:{
                default:'#268cf0'
            },
            textColor:{
                default:'#333'
            },
            /*tab底部指针的偏移量*/
            offset:{
                default:250,
                type:Number
            },
            /*可以触发动画的偏移量*/
            dist:{
                default:250,
                type:Number
            },
            backgroundColor:{
                default:"#ffffff",
                type:String
            },
            indicatorColor:{
                default:"#17acf0",
                type:String
            },
            selectIndex:{
                default:0,
                type:Number
            },
            duration: {
                type: [Number, String],
                default: 100
            },
            timingFunction: {
                type: String,
                default: 'cubic-bezier(0.25, 0.46, 0.45, 0.94)'
            },
        },
        data(){
            return{
                dx:0,
                dx_i:0,
                animationIng:false,
                startScrollIndex:0
            }
        },
        watch:{
            selectIndex:function () {
                this.exeAnimationX()
            }
        },
        methods:{
            /*手势使用bindingX提高性能*/
            bindingX(element){
                let self=this;
                if(self.animationIng){
                    return;
                }
                let {dist}=this;
                let dx=-750*self.selectIndex;
                let dx_i=self.itemWidth*self.selectIndex;
                let indicator=self.$refs.indicator.ref;
                let tab_content=self.$refs.tab_content.ref;

                let tab_content_x=`x+${dx}>0?0:x+${dx}`;
                let indicator_x=`x+${dx}>0?0:${dx_i}-x/${750/this.itemWidth}`;
                if(-dx/750===self.tabItems.length-1){
                    indicator_x=`x<0?${dx_i}:${dx_i}-x/${750/this.itemWidth}`;
                    tab_content_x=`x<0?${dx}:x+${dx}`;
                }
                if(element.ref==null){
                    return;
                }
                self.token=binding.bind({
                    anchor:element.ref,
                    eventType:'pan',
                    props:[
                        {
                            element:tab_content,
                            property:'transform.translateX',
                            expression:parse(tab_content_x)
                        },
                        {
                            element:indicator,
                            property:'transform.translateX',
                            expression:parse(indicator_x)
                        }
                    ]
                },(event)=>{
                    // modal.alert({message:event});
                    let {deltaX,state}=event;

                    if(state==='end'){
                        if(deltaX>dist&&this.selectIndex>0){
                            this.selectIndex--;
                        }else if(deltaX<-dist&&this.selectIndex<self.tabItems.length-1){
                            this.selectIndex++;
                        }else{
                            /*复位*/
                            this.exeAnimationX()
                        }
                    }
                }).token;
            },
            /*动画尽量使用animation简化代码*/
            exeAnimationX(){
                let self=this;
                self.animationIng=true;
                let tab_content=self.$refs.tab_content;
                let indicator=self.$refs.indicator;
                const { duration, timingFunction,selectIndex} = this;
                const dist =selectIndex * 750;
                const dist_i=self.itemWidth*selectIndex;

                let animal1Done=false;
                let animal2Done=false;
                animation.transition(tab_content, {
                    styles: {
                        transform: `translateX(${-dist}px)`
                    },
                    duration: duration,
                    timingFunction,
                    delay: 0
                }, () => {
                    if(self.offset<dist_i){
                        dom.scrollToElement(self.$refs.tab_item[selectIndex], {offset:-self.offset,animated:true})
                    }else{
                        dom.scrollToElement(self.$refs.tab_item[selectIndex], {offset:-dist_i,animated:true})
                    }
                    animal1Done=true;
                    if(animal1Done&&animal2Done){
                        self.animationIng=false;
                    }
                    // binding.unbindAll();
                });
                animation.transition(indicator, {
                    styles: {
                        transform: `translateX(${dist_i}px)`
                    },
                    duration: duration,
                    timingFunction,
                    delay: 0
                }, () => {
                    animal2Done=true;
                    if(animal1Done&&animal2Done){
                        self.animationIng=false;
                    }
                });
            },
            itemClick(index){
                 let self=this;
                if(self.animationIng){
                    return;
                }
                self.$emit("itemClick",index);
                // self.selectIndex=index;
            }
        },
        created(){
             let self=this;
             self.startScrollIndex=750/self.itemWidth;
             // setTimeout(()=>{
             //     // dom.scrollToElement(this.$refs.tab_item[self.selectIndex], {offset:-self.offset,animated:true})
             //     self.exeAnimationX();
             // },100);
        }
    }
</script>

<style scoped>
    .container{
        overflow: hidden;
        /*position:relative;*/
        /*top:0;*/
        /*left:0;*/
        /*bottom: 0;*/
        /*background-color: yellow;*/
    }
    .tab_content{
        flex-direction: row;
        position:relative;
        top:0;
        left:0;
        bottom: 0;
        overflow: hidden;
    }
    .tab_top{
        flex-direction: row;
    }
    .tab_item{
        height: 90px;
        align-items: center;
        justify-content: center;
    }
    .tab_item_text{
    }
    .tab_item_text_focus{
        font-size: 26px;
        color: #268cf0;
    }
    .indicator{
        height: 5px;
        background-color: white;
        position: absolute;
        bottom: 0;
        left: 20px;
    }
</style>
