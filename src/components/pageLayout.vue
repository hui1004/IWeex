<template>
    <div>
        <slot name="head" v-if="showHead">
            <head :title="title" :bgColor="headBgColor" :back="back">
            </head>
        </slot>
        <div v-if="containerType==='div'" class="pageContent" :style="{backgroundColor:bgColor,height:pageHeight}">
            <slot>
            </slot>
        </div>
        <scroller v-if="containerType==='scroller'" class="pageContent" :style="{backgroundColor:bgColor,height:pageHeight}">
            <slot>
            </slot>
        </scroller>
        <list v-if="containerType==='list'" class="pageContent" :style="{backgroundColor:bgColor,height:pageHeight}">
            <slot>
            </slot>
        </list>
    </div>
</template>

<script>
    import head from './newHead.vue'
    export default {
        name: 'page-layout',
        components:{
            head
        },
        props:{
            containerType:{
              default:'div',
              type:String
            },
            showHead:{
                default:true,
                type:Boolean
            },
            bgColor:{
                default:'#eeeeee',
                type:String
            },
            headBgColor:{
                default:'#17acf6',
                type:String
            },
            back:{
                default:true,
                type:Boolean
            },
            title:{
                default:"标题",
                type:String
            }
        },
        data(){
          return {
              pageHeight:0
          }
        },
        created(){
            let self=this;
            let headHeight=weex.requireModule('utils').getDevice().statusBarHeight+80;
            if(self.showHead){
                self.pageHeight=weex.requireModule('utils').getDevice().screenHeight-headHeight;
            }
            else{
                self.pageHeight=weex.requireModule('utils').getDevice().screenHeight;
            }

            // let globalEvent = weex.requireModule('globalEvent');
            // globalEvent.addEventListener("onPageInit", function (e) {
            //
            // })
        }
    };
</script>

<style scoped>
  .pageContent{
      width: 750px;
  }
</style>