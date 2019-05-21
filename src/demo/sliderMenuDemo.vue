<template>
    <div>
        <slider-menu  ref="menue" :left-width="250" timeFunction="easeOutBounce">
            <div slot="left" >
                <div @click="selectItem" style="width: 250px;height: 100px;align-items: center;justify-content: center;margin-top: 400px">
                     <text style="font-size: 34px;font-weight: bold;color: white">菜单一</text>
                </div>
                <div @click="selectItem" style="width: 250px;height: 100px;align-items: center;justify-content: center">
                    <text style="font-size: 34px;font-weight: bold;color: white">菜单二</text>
                </div>
                <div @click="selectItem" style="width: 250px;height: 100px;align-items: center;justify-content: center">
                    <text style="font-size: 34px;font-weight: bold;color: white">菜单三</text>
                </div>
            </div>
            <div slot="right">
                 <head title="测试slider-menu "></head>
            </div>
        </slider-menu>
    </div>
</template>

<script>
    import SliderMenu from "../components/sliderMenu.vue";
    import Head from "../components/head.vue";
    export default {
        components: {
            Head,
            SliderMenu},
        name: "slider-menu-demo",
        data() {
            return {

            }
        },
        methods:{
            selectItem(){
                this.$refs.menue.exeAnimation(1);
            }
        }
    }
</script>

<style scoped>

</style>
