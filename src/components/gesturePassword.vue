<template>
    <div class="container" :style="{width:width,height:height}">
        <div class="pan" :style="{width:size,height:size,backgroundColor:backgroundColor}">
            <gcanvas class="canvas" @touchstart="touchStart" @touchmove="touchMove" @touchend="touchEnd" ref="areas"
                     :style="{width:size,height:size,left:0,top:0}">
            </gcanvas>
            <div ref="dot" :class="[item.select?'dot_select':'dot']" :index="index" v-for="(item,index) in dots"
                 :style="{left:(item.select?45:70)+size/3*item.x,top:(item.select?45:70)+size/3*item.y}"></div>
        </div>

    </div>
</template>

<script>
    var libGCanvas=require("weex-gcanvas");
    let modal=weex.requireModule('modal');
    let dom=weex.requireModule('dom');
    export default {
        name: "gesture-password",
        props:{
            //手势面板大小
            size:{
                default:600,
                type:Number
            },
            //手势面板背景色
            backgroundColor:{
                default:"orange",
                type:String
            },
            //整个组件的宽度
            width:{
                default:750,
                type:Number
            },
            //整个组件的高度
            height:{
                default:800,
                type:Number
            },
            touchRadius:{
                default:50,
                type:Number
            }
        },
        data(){
            return{
                dots:[ {x:0,y:0,select:false},{x:1,y:0,select:false},{x:2,y:0,select:false},
                       {x:0,y:1,select:false},{x:1,y:1,select:false},{x:2,y:1,select:false},
                       {x:0,y:2,select:false},{x:1,y:2,select:false},{x:2,y:2,select:false}
                    ],
                real_x_y_done:false,
                path:[],
                context:{}
            }
        },
        methods:{
            clear(){
                this.dots.forEach(item=>{
                    item.select=false;
                });
                this.path=[];
                this.context.fillStyle=this.backgroundColor;
                this.context.beginPath();
                this.context.fillRect(0,0,this.size,this.size);
                this.context.closePath();
            },
            touchStart(event){
                let self=this;
                if(!this.real_x_y_done){
                    this.setReal_X_Y()
                }
                // modal.alert({message:event.changedTouches[0]})
                let touchX=event.changedTouches[0].screenX;
                let touchY=event.changedTouches[0].screenY;
                this.dots.forEach(item=>{
                    if(touchX>item.realX-this.touchRadius&&touchX<item.realX+this.touchRadius
                        &&touchY>item.realY-this.touchRadius&&touchY<item.realY+this.touchRadius){
                        item.select=true;
                        self.path.push({x:item.realX-(self.width-self.size)/2+25,y:item.realY
                            -(self.height-self.size)/2+25,dataX:item.x,dataY:item.y});
                    }
                })
            },
            touchMove(event){
                let self=this;
                let touchX=event.changedTouches[0].screenX;
                let touchY=event.changedTouches[0].screenY;
                // if(self.path.length!=0){
                //     let lastIndex=self.path.length-1;
                //     self.context.restore()
                //     self.context.moveTo(self.path[lastIndex].x,self.path[lastIndex].y);
                //     self.context.lineTo(touchX-(750-self.size)/2+25,touchY-(1000-self.size)/2+25);
                //     self.context.stroke();
                //     self.context.save();
                // }
                this.dots.forEach(item=>{
                    if(touchX>item.realX-this.touchRadius&&touchX<item.realX+this.touchRadius&&
                        touchY>item.realY-this.touchRadius&&touchY<item.realY+this.touchRadius){
                        if(!item.select){
                            item.select=true;
                            if(self.path.length==0){
                                self.path.push({x:item.realX-(self.width-self.size)/2+25,y:item.realY
                                    -(self.height-self.size)/2+25,dataX:item.x,dataY:item.y});
                            }else{
                                self.path.push({x:item.realX-(self.width-self.size)/2+25,y:item.realY
                                    -(self.height-self.size)/2+25,dataX:item.x,dataY:item.y});
                                self.drawPath()
                            }

                        }
                    }
                })
            },
            drawPath(){
                let self=this;
                let lastIndex=self.path.length-2;
                let nowIndex=self.path.length-1;
                self.context.moveTo(self.path[lastIndex].x,self.path[lastIndex].y);
                self.context.lineTo(self.path[nowIndex].x,self.path[nowIndex].y);
                self.context.stroke();
            },
            touchEnd(event){
                 let self=this;
                 //返回绘制的路径数据，去每个点的x,y属性对比
                 this.$emit("gestureEnd",self.path);
                 self.clear();
            },
            /*得到每个点在屏幕中的真实坐标*/
            setReal_X_Y(){
                let self=this;
                for (let i=0;i<9;i++){
                    let dot=this.$refs['dot'][i].ref;
                    dom.getComponentRect(dot,function (res) {
                        self.$set(self.dots[i],"realX",res.size.left);
                        self.$set(self.dots[i],"realY",res.size.top);
                    });
                }
                this.real_x_y_done=true;
            }
        },
        created(){
            let self=this;
            /*获取元素引用*/
            setTimeout(function () {
                let ref=self.$refs.areas;
                // modal.alert({message:ref})
                /*通过元素引用获取canvas对象*/
                self.canvasObj =libGCanvas.start(ref);
                /*获取绘图所需的上下文，目前不支持3d*/
                self.context =  self.canvasObj.getContext('2d');
                self.context.lineWidth=10;
                self.context.strokeStyle = "#268cf0"
            },200);

        }
    }
</script>

<style scoped>
    .canvas{
        position: absolute;
        background-color: rgba(1,1,1,0);
    }
    .container{
        justify-content: center;
        align-items: center;
    }
    .pan{

    }
    .dot{
        width: 50px;
        height: 50px;
        border-radius: 50px;
        background-color: white;
        border-width: 10px;
        border-color: #eeeeee;
        position: absolute;
        transition-property: width,height,top,left;
        transition-duration:.25s;
        transition-delay: .1s;
        transition-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1.0);
    }
    .dot_select{
        width: 100px;
        height: 100px;
        border-radius: 50px;
        background-color: white;
        border-width:10px;
        border-color: #268cf0;
        position: absolute;
        transition-property: width,height,top,left;
        transition-duration:.25s;
        transition-delay: .1s;
        transition-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1.0);
    }
</style>
