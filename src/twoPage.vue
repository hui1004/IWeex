<template>
    <div style="width: 750px;height: 700px;align-items: center;justify-content: center;" @click="popPage">
        <text style="font-size: 35px;font-weight: bold">Two11111a Page</text>
    </div>
</template>

<script>
    var nav=weex.requireModule('navigator');
    export default {
        name: "two-page",
        methods:{
          popPage(){
              nav.pop({
                  url: '',
                  animated: "true"
              }, event => {
              })
          }
        },
        created(){
            var modal=weex.requireModule('modal');
            console.log("aaa");
        }
    }
</script>

<style scoped>

</style>
