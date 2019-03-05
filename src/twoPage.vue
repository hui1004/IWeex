<template>
    <div style="width: 750px;height: 700px;align-items: center;justify-content: center;" @click="popPage">
        <text style="font-size: 35px;font-weight: bold">Two Page</text>
    </div>
</template>

<script>
    var nav=weex.requireModule('navigator')
    export default {
        name: "two-page",
        methods:{
          popPage(){
              navigator.pop({
                  url: '',
                  animated: "true"
              }, event => {
              })
          }
        },
        created(){
            console.log("aaa");
        }
    }
</script>

<style scoped>

</style>
