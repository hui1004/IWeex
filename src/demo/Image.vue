<template>
    <div >
        <head title="ImageDemo" ></head>
        <!-- ../ 跳出当前目录-->
        <image style="height: 300px;width: 300px" src="../image/iclauncher.png"></image>
        <!-- ./ 当前目录下-->
        <image style="height: 300px;width: 300px" src="./image/iclauncher.png"></image>
        <!-- 相对于根目录下-->
        <image style="height: 300px;width: 300px" src="app:image/iclauncher.png"></image>
    </div>
</template>

<script>
    import head from '../components/head'
    export default {
        name: "image",
        components:{
            head
        }
    }
</script>

<style scoped>

</style>
