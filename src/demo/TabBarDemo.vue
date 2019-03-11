<template>
    <div>
        <wxc-tab-bar :tab-titles="tabTitles"
                     :tab-styles="tabStyles"
                     title-type="icon"
                     @wxcTabBarCurrentTabSelected="wxcTabBarCurrentTabSelected">
            <!-- 第一个页面内容-->
            <div class="item-container" :style="contentStyle">
                <head  title="首页"></head>
            </div>
            <!-- 第二个页面内容-->
            <div class="item-container" :style="contentStyle">
                <head title="特别推荐"></head>
            </div>

            <!-- 第三个页面内容-->
            <div class="item-container" :style="contentStyle">
                <head title="消息中心"></head>
            </div>

            <!-- 第四个页面内容-->
            <div class="item-container" :style="contentStyle">
                <head title="我的主页"></head>
            </div>
        </wxc-tab-bar>
    </div>

</template>

<style scoped>
    .item-container {
        width: 750px;
        background-color: #f2f3f4;
        align-items: center;
        /*justify-content: center;*/
    }
</style>
<script>
    import { WxcTabBar, Utils } from 'weex-ui';

    // https://github.com/alibaba/weex-ui/blob/master/example/tab-bar/config.js
    import Config from '../components/weex-ui/config'
    import Head from "../components/head";

    export default {
        components: {
            Head,
            WxcTabBar },
        data: () => ({
            tabTitles: Config.tabTitles,
            tabStyles: Config.tabStyles
        }),
        created () {
            const tabPageHeight = Utils.env.getPageHeight();
            // 如果页面没有导航栏，可以用下面这个计算高度的方法
            // const tabPageHeight = env.deviceHeight / env.deviceWidth * 750;
            const { tabStyles } = this;
            this.contentStyle = { height: (tabPageHeight - tabStyles.height) + 'px' };
        },
        methods: {
            wxcTabBarCurrentTabSelected (e) {
                const index = e.page;
                // console.log(index);
            }
        }
    };
</script>
