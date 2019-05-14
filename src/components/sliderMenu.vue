<!--侧滑菜单实现-->
<template>
   <div class="container" :style="{backgroundColor:backgroundColor}">
       <div ref="left"   class="left" :style="{height:height,left:0,width:leftWidth*2}">
            <slot name="left"></slot>
       </div>
       <div ref="right" @touchstart="touchstart" class="right" :style="{height:height}">
           <slot name="right"></slot>
       </div>
   </div>
</template>

<script>
    var modal=weex.requireModule('modal');
    var stream = weex.requireModule('stream');
    var utils=weex.requireModule('utils')
    var binding = weex.requireModule('binding');
    import { parse } from 'bindingx-parser'
    export default {
        name: "slider-menu",
        props:{
            timeFunction:{
                default:"linear",
                type:String
            },
            animationT:{
                default:200,
                type:Number
            },
            leftWidth:{
                default:200,
                type:Number
            },
            backgroundColor:{
                default:"#ffa00f",
                type:String
            },
        },
        data(){
            return{
                height:0,
                dx:0,
                endX:0,
                scaleX:1,
                scaleY:1,
                isSlide:false,
                animationIng:false,
            }
        },
        methods:{
            touchstart(e){
                let self=this;
                if(this.animationIng){
                    return;
                }
                let rightRef=self.$refs.right.ref;
                let translate_x_expression="";
                let scaleX_expression='';
                let scaleY_expression='';
                if (!self.isSlide){
                    translate_x_expression=`x>0?x+0:x-x`;
                    scaleX_expression=`x>0?1-(x/1200):1`;
                    scaleY_expression=`x>0?1-(x/1200):1`;
                }else{
                    translate_x_expression=`x+${this.leftWidth}`;
                    scaleX_expression=`x>0?${self.scaleX}-(x/1200):${self.scaleX}-((1-${self.scaleX})/200)*x`;
                    scaleY_expression=`x>0?${self.scaleY}-(x/1200):${self.scaleY}-((1-${self.scaleY})/200)*x`;
                }
                binding.bind({
                    anchor:rightRef,
                    eventType:'pan',
                    props:[
                        {
                            element:rightRef,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression)
                        },
                        {
                            element:rightRef,
                            property:'transform.scaleX',
                            expression:parse(scaleX_expression)
                        },
                        {
                            element: rightRef,
                            property: 'transform.scaleY',
                            expression: parse(scaleY_expression)
                        }
                    ]
                },function (event) {
                    if(event.state==='end'){
                        self.dx=event.deltaX;
                        // modal.alert({message:self.scale })
                        if (!self.isSlide){
                            if(self.dx<0){
                                return;
                            }
                            self.scaleX = self.scaleX-event.deltaX/1200;
                            self.scaleY = self.scaleY-event.deltaX/1200;
                        }else{
                            // modal.alert({message:event })
                            self.scaleX = self.scaleX-event.deltaX/1200;
                            self.scaleY = self.scaleY-event.deltaX/1200;
                            // modal.alert({message:self.scaleX })
                        }
                        self.exeAnimation();
                    }
                })
            },
            //生成表达式
            getLeftExpression(){
                let self=this;
                let final_x=0;
                let final_scaleX=0;
                let final_scaleY=0;
                let changed_x=0;
                let opacity_expression="";
                let scale_expression='';
                //如果不是收起状态
                if (!self.isSlide){
                    final_x =this.leftWidth;
                    changed_x =this.leftWidth-Math.abs(self.dx);
                    final_scaleX = (1-400/1200);
                    final_scaleY = (1-400/1200);
                    let changed_scaleX=final_scaleX-self.scaleX;
                    let changed_scaleY=final_scaleY-self.scaleY;
                    scale_expression = self.timeFunction+"(t,"+1+","+-0.5+","+self.animationT+")";
                    opacity_expression=self.timeFunction+`(t,${1},${0},${self.animationT})`;
                }else{
                    final_x = this.leftWidth;
                    changed_x = -self.dx;
                    final_scaleX = (1-400/1200);
                    final_scaleY = (1-400/1200);
                    let changed_scaleX=final_scaleX-self.scaleX;
                    let changed_scaleY=final_scaleY-self.scaleY;
                    scale_expression = self.timeFunction+"(t,"+0.5+","+0.5+","+self.animationT+")";
                    opacity_expression=self.timeFunction+`(t,${0},${1},${self.animationT})`;
                }
                return {
                    opacity_expression:opacity_expression,
                    scale_expression:scale_expression,
                }
            },
            //生成表达式
            getExpression(type){
                let self=this;
                let final_x=0;
                let final_scaleX=0;
                let final_scaleY=0;
                let changed_x=0;
                let translate_x_expression="";
                let scaleX_expression='';
                let scaleY_expression='';
                if(type){
                    self.dx=0;
                    self.isSlide=false;
                    final_x = 0;
                    final_scaleX = 1;
                    final_scaleY = 1;
                    changed_x = -this.leftWidth-self.dx;
                    let changed_scaleX=1-self.scaleX;
                    let changed_scaleY=1-self.scaleY;
                    translate_x_expression = self.timeFunction+ `(t,${this.leftWidth+self.dx},${changed_x},${self.animationT})`;
                    scaleX_expression=self.timeFunction+`(t,${self.scaleX},${changed_scaleX},${self.animationT})`;
                    scaleY_expression=self.timeFunction+`(t,${self.scaleY},${changed_scaleY},${self.animationT})`;
                    return {
                        translate_x_expression:translate_x_expression,
                        scaleX_expression:scaleX_expression,
                        scaleY_expression:scaleY_expression,
                        final_scaleX:final_scaleX,
                        final_scaleY:final_scaleY
                    }
                }
                //如果不是收起状态
                if (!self.isSlide){
                    if(Math.abs(self.dx)<this.leftWidth) { // 复位
                        self.isSlide=false;
                        final_x = 0;
                        final_scaleX = 1;
                        final_scaleY = 1;
                        changed_x = 0-self.dx;
                        let changed_scaleX=1-self.scaleX;
                        let changed_scaleY=1-self.scaleY;
                        translate_x_expression = self.timeFunction+"(t,"+self.dx+","+changed_x+","+self.animationT+")";
                        scaleX_expression=self.timeFunction+`(t,${self.scaleX},${changed_scaleX},${self.animationT})`;
                        scaleY_expression=self.timeFunction+`(t,${self.scaleY},${changed_scaleY},${self.animationT})`;
                    } else if(Math.abs(self.dx) >this.leftWidth) { // 执行
                        self.isSlide=true;
                        final_x =this.leftWidth;
                        changed_x =this.leftWidth-Math.abs(self.dx);
                        final_scaleX = (1-400/1200);
                        final_scaleY = (1-400/1200);
                        let changed_scaleX=final_scaleX-self.scaleX;
                        let changed_scaleY=final_scaleY-self.scaleY;

                        translate_x_expression = self.timeFunction+"(t,"+self.dx+","+changed_x+","+self.animationT+")";
                        scaleX_expression=self.timeFunction+`(t,${self.scaleX},${changed_scaleX},${self.animationT})`;
                        scaleY_expression=self.timeFunction+`(t,${self.scaleY},${changed_scaleY},${self.animationT})`;
                    }
                }else{
                    if(self.dx<0) { // 复位
                        self.isSlide=false;
                        final_x = 0;
                        final_scaleX = 1;
                        final_scaleY = 1;
                        changed_x = -this.leftWidth-self.dx;
                        let changed_scaleX=1-self.scaleX;
                        let changed_scaleY=1-self.scaleY;
                        translate_x_expression = self.timeFunction+ `(t,${this.leftWidth+self.dx},${changed_x},${self.animationT})`;
                        scaleX_expression=self.timeFunction+`(t,${self.scaleX},${changed_scaleX},${self.animationT})`;
                        scaleY_expression=self.timeFunction+`(t,${self.scaleY},${changed_scaleY},${self.animationT})`;
                    } else if(self.dx>0) { // 执行
                        self.isSlide=true;
                        final_x = this.leftWidth;
                        changed_x = -self.dx;
                        final_scaleX = (1-400/1200);
                        final_scaleY = (1-400/1200);
                        let changed_scaleX=final_scaleX-self.scaleX;
                        let changed_scaleY=final_scaleY-self.scaleY;
                        translate_x_expression = self.timeFunction+`(t,${this.leftWidth+self.dx},${changed_x},${self.animationT})`;
                        scaleX_expression=self.timeFunction+`(t,${self.scaleX},${changed_scaleX},${self.animationT})`;
                        scaleY_expression=self.timeFunction+`(t,${self.scaleY},${changed_scaleY},${self.animationT})`;
                    }
                }

                return {
                    translate_x_expression:translate_x_expression,
                    scaleX_expression:scaleX_expression,
                    scaleY_expression:scaleY_expression,
                    final_scaleX:final_scaleX,
                    final_scaleY:final_scaleY
                }
            },
            //执行动画
            exeAnimation(userDo){
                let self=this;
                this.animationIng=true;
                let rightRef=self.$refs.right.ref;
                let { translate_x_expression,
                    scaleX_expression,
                    scaleY_expression,
                    final_scaleX,
                    final_scaleY} =
                    this.getExpression(userDo);
                binding.bind({
                    eventType:'timing',
                    exitExpression: parse(`t>${this.animationT}`),
                    props:[
                        {
                            element:rightRef,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression)
                        },
                        {
                            element:rightRef,
                            property:'transform.scaleX',
                            expression:parse(scaleX_expression)
                        },
                        {
                            element:rightRef,
                            property:'transform.scaleY',
                            expression:parse(scaleY_expression)
                        }
                    ]
                },function (event) {
                    if(event.state === 'end'||event.state === 'exit') {
                        self.scaleX = final_scaleX;
                        self.scaleY = final_scaleY;
                        self.animationIng=false;
                        // self.exeLeftAnimation()
                        binding.unbindAll()
                    }
                })
            },
            exeLeftAnimation(){
                let self=this;
                this.animationIng=true;
                let leftRef=self.$refs.left.ref;
                let { opacity_expression,
                    scaleX_expression,
                   } =
                    this.getLeftExpression();
                binding.bind({
                    eventType:'timing',
                    exitExpression: parse(`t>${this.animationT}`),
                    props:[
                        {
                            element:leftRef,
                            property:'transform.opacity',
                            expression:parse(opacity_expression)
                        },
                        {
                            element:leftRef,
                            property:'transform.scale',
                            expression:parse(scaleX_expression)
                        },
                    ]
                },function (event) {
                    if(event.state === 'end'||event.state === 'exit') {
                        binding.unbindAll()
                    }
                })
            }
        },
        created(){
            let height=weex.config.env.deviceHeight;
            let width=weex.config.env.deviceWidth;
            this.height= height/width*750+60;``
            // modal.alert({message:this.height})
        }
    }
</script>

<style scoped>
     .container{
         width: 1200px;
         flex-direction: row;
     }
    .left{
        width: 200px;
        position: absolute;
        top:0;
    }
    .right{
        width: 1200px;
        background-color: #ffffff;
    }

</style>
