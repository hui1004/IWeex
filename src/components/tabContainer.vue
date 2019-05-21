<template>
    <div class="container">
        <div class="tab_top">
            <div ref="tab_item" class="tab_item" @click="itemClick(index)"
                 :style="{width:itemWidth,backgroundColor:backgroundColor}" v-for="(item,index) in tabItems">
                <text class="tab_item_text" :style="{fontSize:item.font_size,color:item.textColor}">{{item.text}}</text>
            </div>
            <div ref="indicator" class="indicator" :style="{width:itemWidth-40}"></div>
        </div>
        <div class="tab_content" @touchstart="touchstart" :style="{width:tabItems.length*750}" ref="tab_content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
    import Head from "./head.vue";
    var binding = weex.requireModule('binding');
    import { parse } from 'bindingx-parser';
    let modal=weex.requireModule('modal');
    export default {
        components: {Head},
        name: "tab-container",
        props:{
            tabItems:{
                default:[{
                    text:"测试1",
                    font_size:26,
                    textColor:"#ffffff"
                },{
                    text:"测试2",
                    font_size:26,
                    textColor:"#ffffff"
                },{
                    text:"测试3",
                    font_size:26,
                    textColor:"#ffffff"
                },{
                    text:"测试4",
                    font_size:26,
                    textColor:"#ffffff"
                },],
                type:Array
            },
            backgroundColor:{
                default:"#ff0000",
                type:String
            }
        },
        data(){
            return{
                dx:0,
                dx_i:0,
                animationT:500,
                now_dx:0,
                now_dx_i:0,
                finalX:0,
                finalX_i:0,
                animationIng:false,
                itemWidth:0
            }
        },
        methods:{
            touchstart(e){
                let self=this;
                if(self.animationIng){
                    return;
                }
                let indicator=self.$refs.indicator.ref;
                let tab_content=self.$refs.tab_content.ref;
                let tab_content_x=`x+${self.dx}>0?0:x+${self.dx}`;
                let indicator_x=`x+${self.dx}>0?0:${self.dx_i}-x/${self.tabItems.length}`;
                if(-self.dx/750==self.tabItems.length-1){
                    indicator_x=`x<0?${self.dx_i}:${self.dx_i}-x/${self.tabItems.length}`;
                    tab_content_x=`x<0?${self.dx}:x+${self.dx}`;
                }
                self.token=binding.bind({
                    anchor:tab_content,
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
                },function (event) {
                    if(event.state==='end'){
                        if(self.dx==0&&event.deltaX>0){
                               self.dx=0;
                               self.now_dx=0;
                               self.dx_i=0;
                               self.now_dx_i=0;
                              return;
                        }
                        if(-self.dx/750==self.tabItems.length-1&&event.deltaX<0){
                            return;
                        }
                        self.now_dx=event.deltaX;
                        self.now_dx_i=-event.deltaX/self.tabItems.length;
                        self.exeAnimation();
                    }
                }).token;
            },
            exeAnimation(){
                let self=this;
                self.animationIng=true;
                let tab_content=self.$refs.tab_content.ref;
                let indicator=self.$refs.indicator.ref;
                let translate_x_expression='';
                let translate_x_expression_i='';
                let changeX=0;
                let changeX_i=0;
                if(Math.abs(self.now_dx)>300){
                    if(self.now_dx<0){
                        self.finalX=self.dx-750;
                        self.finalX_i=(self.dx_i)+self.itemWidth;
                    }else{
                        self.finalX=self.dx+750;
                        self.finalX_i=(self.dx_i)-self.itemWidth;
                    }
                    changeX=self.finalX-(self.dx+self.now_dx);
                    changeX_i=self.finalX_i-(self.dx_i+self.now_dx_i);
                }else{
                    self.finalX=self.dx;
                    changeX=-self.now_dx;

                    self.finalX_i=self.dx_i;
                    changeX_i=-self.now_dx_i;
                }
                translate_x_expression=`linear(t,${self.dx+self.now_dx},${changeX},150)`;
                translate_x_expression_i=`linear(t,${self.dx_i+self.now_dx_i},${changeX_i},150)`;
                binding.bind({
                    eventType:'timing',
                    exitExpression: parse(`t>${this.animationT}`),
                    props:[
                        {
                            element:tab_content,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression)
                        },
                        {
                            element:indicator,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression_i)
                        }
                    ]
                },function (event) {
                    if(event.state === 'end'||event.state === 'exit') {
                        self.$emit("itemClick",-self.finalX/750);
                        self.dx=self.finalX;
                        self.dx_i=self.finalX_i;
                        self.animationIng=false;
                        binding.unbindAll();
                    }
                })
            },
            exeAnimationX(index){
                let self=this;
                self.animationIng=true;
                let tab_content=self.$refs.tab_content.ref;
                let indicator=self.$refs.indicator.ref;
                let translate_x_expression='';
                let translate_x_expression_i='';
                let changeX=0;
                let changeX_i=0;
                self.finalX=(-750*index);
                self.finalX_i=(self.itemWidth*index);
                changeX=self.finalX-self.dx;
                changeX_i=self.finalX_i-self.dx_i;
                translate_x_expression=`linear(t,${self.dx},${changeX},200)`;
                translate_x_expression_i=`linear(t,${self.dx_i},${changeX_i},200)`;
                binding.bind({
                    eventType:'timing',
                    exitExpression: parse(`t>${this.animationT}`),
                    props:[
                        {
                            element:tab_content,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression)
                        },
                        {
                            element:indicator,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression_i)
                        }
                    ]
                },function (event) {
                    if(event.state === 'end'||event.state === 'exit') {
                        self.$emit("itemClick",index);
                        self.dx=self.finalX;
                        self.dx_i=self.finalX_i;
                        self.animationIng=false;
                        binding.unbindAll();
                    }
                })
            },
            itemClick(index){
                 let self=this;
                if(self.animationIng){
                    return;
                }
                 self.exeAnimationX(index);
            }
        },
        created(){
             let self=this;
             self.itemWidth=750/self.tabItems.length;
        }
    }
</script>

<style scoped>
    .container{
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
    }
    .tab_top{
        flex-direction: row;
    }
    .tab_item{
        height: 90px;
        align-items: center;
        justify-content: center;
        background-color: red;
    }
    .tab_item_text{
    }
    .tab_item_text_focus{
        font-size: 26px;
        color: white;
    }
    .indicator{
        height: 5px;
        background-color: white;
        position: absolute;
        bottom: 0;
        left: 20px;
    }
</style>