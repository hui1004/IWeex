<template>
    <div style="width: 750px;height: 700px;align-items: center;justify-content: center;">
        <text style="font-size: 35px;font-weight: bold">Two11111a Page</text>
    </div>
</template>

<script>
    export default {
        name: "two-page",
        created(){
            var modal=weex.requireModule('modal');
            modal.alert({message: weex.config.env});

            console.log("aaa");
        }
    }
</script>

<style scoped>

</style>