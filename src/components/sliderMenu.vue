<!--侧滑菜单实现-->
<template>
   <div class="container" :style="{backgroundColor:backgroundColor}">
       <div ref="left"   class="left" :style="{height:height,left:-leftWidth,width:leftWidth*2}">
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
                scaleLeft:0,
                scaleRght:1,
                isSlide:false,
                animationIng:false,
                token:0
            }
        },
        methods:{
            bindRight(){
                let self=this;
                let rightRef=self.$refs.right.ref;
                let leftRef=self.$refs.left.ref;
                let translate_x_expression="";
                let scale_expression='';
                let translate_x_expression_l='';
                let scale_expression_l='';
                if (!self.isSlide){
                    translate_x_expression=`x>0?x+0:x-x`;
                    scale_expression=`x>0?1-(x/1200):1`;
                    translate_x_expression_l=`x>0?x+0:x-x`;
                    scale_expression_l=`x>0?0+(x/${self.leftWidth}):0`;
                }else{
                    translate_x_expression=`x+${this.leftWidth}`;
                    scale_expression=`x>0?${self.scaleRght}-(x/1200):${self.scaleRght}-((1-${self.scaleRght})/200)*x`;
                    translate_x_expression_l=`x+${this.leftWidth}`;
                    scale_expression_l=
                        `x>0?${self.scaleLeft}+(x/${self.leftWidth}):${self.scaleLeft}+((${self.scaleLeft})/${self.leftWidth})*x`;;
                }
               self.token=binding.bind({
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
                            expression:parse(scale_expression)
                        },
                        {
                            element: rightRef,
                            property: 'transform.scaleY',
                            expression: parse(scale_expression)
                        },
                        {
                            element:leftRef,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression_l)
                        },
                        {
                            element:leftRef,
                            property:'transform.scaleX',
                            expression:parse(scale_expression_l)
                        },
                        {
                            element: leftRef,
                            property: 'transform.scaleY',
                            expression: parse(scale_expression_l)
                        },
                    ]
                },function (event) {
                    if(event.state==='end'){
                        self.dx=event.deltaX;
                        // modal.alert({message:self.scale })
                        if (!self.isSlide){
                            if(self.dx<0){
                                return;
                            }
                        }
                        self.scaleRght = self.scaleRght-event.deltaX/1200;
                        self.scaleLeft = self.scaleLeft+event.deltaX/self.leftWidth;
                        self.exeAnimation();
                    }
                }).token;
            },
            touchstart(e){
                let self=this;
                if(this.animationIng){
                    return;
                }
                self.bindRight();
            },
            //生成表达式
            getExpression(type){
                let self=this;
                let final_x=0;
                let final_scale=0;
                let changed_x=0;
                let translate_x_expression="";
                let scale_expression='';
                let final_x_l=0;
                let final_scale_l=0;
                let changed_x_l=0;
                let translate_x_expression_l="";
                let scale_expression_l='';
                if(type){
                    self.dx=0;
                    self.isSlide=false;
                    final_x = 0;
                    final_scale = 1;
                    changed_x = -this.leftWidth-self.dx;
                    let changed_scale=1-self.scaleRght;
                    translate_x_expression = self.timeFunction+ `(t,${this.leftWidth+self.dx},${changed_x},${self.animationT})`;
                    scale_expression=self.timeFunction+`(t,${self.scaleRght},${changed_scale},${self.animationT})`;

                     final_x_l=0;
                     final_scale_l=0;
                     changed_x_l=-this.leftWidth-self.dx;
                     let changed_scale_l=0-self.scaleLeft;
                     translate_x_expression_l= self.timeFunction+ `(t,${this.leftWidth+self.dx},${changed_x_l},${self.animationT})`;
                     scale_expression_l=self.timeFunction+`(t,${self.scaleLeft},${changed_scale_l},${self.animationT})`;

                    return {
                        translate_x_expression:translate_x_expression,
                        scale_expression:scale_expression,
                        final_scale:final_scale,
                        translate_x_expression_l:translate_x_expression_l,
                        scale_expression_l:scale_expression_l,
                        final_scale_l:final_scale_l,
                    }
                }
                //如果不是收起状态
                if (!self.isSlide){
                    if(Math.abs(self.dx)<this.leftWidth) { // 复位
                        self.isSlide=false;
                        final_x = 0;
                        final_scale = 1;
                        changed_x = final_x-self.dx;
                        let changed_scale=final_scale-self.scaleRght;
                        translate_x_expression = self.timeFunction+"(t,"+self.dx+","+changed_x+","+self.animationT+")";
                        scale_expression=self.timeFunction+`(t,${self.scaleRght},${changed_scale},${self.animationT})`;

                        final_x_l=0;
                        final_scale_l=0;
                        changed_x_l=final_x_l-self.dx;
                        let changed_scale_l=final_scale_l-self.scaleLeft;
                        translate_x_expression_l= self.timeFunction+"(t,"+self.dx+","+changed_x_l+","+self.animationT+")";
                        scale_expression_l=self.timeFunction+`(t,${self.scaleLeft},${changed_scale_l},${self.animationT})`;
                    } else if(Math.abs(self.dx) >this.leftWidth) { // 执行
                        self.isSlide=true;
                        final_x =this.leftWidth;
                        changed_x =final_x-Math.abs(self.dx);
                        final_scale = (1-400/1200);
                        let changed_scale=final_scale-self.scaleRght;
                        translate_x_expression = self.timeFunction+"(t,"+self.dx+","+changed_x+","+self.animationT+")";
                        scale_expression=self.timeFunction+`(t,${self.scaleRght},${changed_scale},${self.animationT})`;

                        final_x_l =this.leftWidth;
                        final_scale_l=1;
                        changed_x_l=final_x_l-self.dx;
                        let changed_scale_l=final_scale_l-self.scaleLeft;
                        translate_x_expression_l= self.timeFunction+"(t,"+self.dx+","+changed_x_l+","+self.animationT+")";
                        scale_expression_l=self.timeFunction+`(t,${self.scaleLeft},${changed_scale_l},${self.animationT})`;
                    }
                }else{
                    if(self.dx<0) { // 复位
                        self.isSlide=false;
                        final_x = 0;
                        final_scale= 1;
                        changed_x = -this.leftWidth-self.dx;
                        let changed_scale=1-self.scaleRght;
                        translate_x_expression = self.timeFunction+ `(t,${this.leftWidth+self.dx},${changed_x},${self.animationT})`;
                        scale_expression=self.timeFunction+`(t,${self.scaleRght},${changed_scale},${self.animationT})`;

                        final_x_l =0;
                        final_scale_l=0;
                        changed_x_l = -this.leftWidth-self.dx;
                        let changed_scale_l=final_scale_l-self.scaleLeft;
                        translate_x_expression_l=  self.timeFunction+ `(t,${this.leftWidth+self.dx},${changed_x_l},${self.animationT})`;
                        scale_expression_l=self.timeFunction+`(t,${self.scaleLeft},${changed_scale_l},${self.animationT})`;
                    } else if(self.dx>0) { // 执行
                        self.isSlide=true;
                        final_x = this.leftWidth;
                        changed_x = -self.dx;
                        final_scale = (1-400/1200);
                        let changed_scale=final_scale-self.scaleRght;
                        translate_x_expression = self.timeFunction+`(t,${this.leftWidth+self.dx},${changed_x},${self.animationT})`;
                        scale_expression=self.timeFunction+`(t,${self.scaleRght},${changed_scale},${self.animationT})`;

                        final_x_l =this.leftWidth;
                        final_scale_l=1;
                        changed_x_l = -self.dx;
                        let changed_scale_l=final_scale_l-self.scaleLeft;
                        translate_x_expression_l = self.timeFunction+`(t,${this.leftWidth+self.dx},${changed_x_l},${self.animationT})`;
                        scale_expression_l=self.timeFunction+`(t,${self.scaleLeft},${changed_scale_l},${self.animationT})`;
                    }
                }
                return {
                    translate_x_expression:translate_x_expression,
                    scale_expression:scale_expression,
                    final_scale:final_scale,
                    translate_x_expression_l:translate_x_expression_l,
                    scale_expression_l:scale_expression_l,
                    final_scale_l:final_scale_l,
                }
            },
            //执行动画
            exeAnimation(userDo){
                let self=this;
                this.animationIng=true;
                let rightRef=self.$refs.right.ref;
                let leftRef=self.$refs.left.ref;
                let { translate_x_expression,
                    scale_expression,
                    final_scale,
                    translate_x_expression_l,
                    scale_expression_l,
                    final_scale_l,
                     } =
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
                            expression:parse(scale_expression)
                        },
                        {
                            element:rightRef,
                            property:'transform.scaleY',
                            expression:parse(scale_expression)
                        },
                        {
                            element:leftRef,
                            property:'transform.translateX',
                            expression:parse(translate_x_expression_l)
                        },
                        {
                            element:leftRef,
                            property:'transform.scaleX',
                            expression:parse(scale_expression_l)
                        },
                        {
                            element:leftRef,
                            property:'transform.scaleY',
                            expression:parse(scale_expression_l)
                        }
                    ]
                },function (event) {
                    if(event.state === 'end'||event.state === 'exit') {
                        self.scaleRght = final_scale;
                        self.scaleLeft = final_scale_l;
                        self.animationIng=false;
                        binding.unbindAll();
                    }
                })
            },
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
