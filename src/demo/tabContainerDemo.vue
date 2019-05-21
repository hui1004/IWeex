<template>
  <div>
     <head></head>
      <tab-container @itemClick="itemClick" :tabItems="tabItems">
          <div class="content"  v-for="(item,index) in contents">
              <text class="tab_item_text" style="font-size: 34px;color:#ffffff">{{item}}</text>
          </div>
      </tab-container>

  </div>
 
</template>
<style>
    .content{
        width: 750px;
        height: 1200px;
        align-items: center;
        justify-content: center;
        background-color: green;
    }
</style>

<script>
  const modal=weex.requireModule('modal');
  import Head from "../components/head.vue";
  import TabContainer from "../components/tabContainer.vue";
  export default {
      components: {
          TabContainer,
          Head},
      data () {
      return {
        text: 'Hello World.',
          tabItems:[{
              text:"测试1",
              font_size:26,
              textColor:"#ffffff"
          },{
              text:"测试2",
              font_size:26,
              textColor:"#ffffff"
          },{
              text:"测试2",
              font_size:26,
              textColor:"#ffffff"
          }],
          contents:[111,222,333],
          param:''
      }
    }
    ,
      methods:{
          itemClick(index){
              modal.toast({message:index})
          }
      }
     ,
      created:function(){

      }
  }
</script>