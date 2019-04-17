<template>
    <div>
        <!--<head title="TabPage"></head>-->
        <wxc-tab-page ref="wxc-tab-page"
                      :tab-titles="tabTitles"
                      :tab-styles="tabStyles"
                      title-type="icon"
                      :tab-page-height="tabPageHeight"
                      @wxcTabPageCurrentTabSelected="wxcTabPageCurrentTabSelected">
            <list v-for="(v,index) in tabList"
                  :key="index"
                  class="item-container"
                  :style="{ height: (tabPageHeight - tabStyles.height) + 'px' }">
                <cell class="border-cell"></cell>
                <cell v-for="(demo,key) in v"
                      class="cell"
                      :key="key">
                    <wxc-pan-item :ext-id="'1-' + (v) + '-' + (key)"
                                  url="https://h5.m.taobao.com/trip/ticket/detail/index.html?scenicId=2675"
                                  @wxcPanItemPan="wxcPanItemPan">
                        <div class="content">
                            <text>{{key}}</text>
                        </div>
                    </wxc-pan-item>
                </cell>
            </list>
        </wxc-tab-page>
    </div>
</template>

<style scoped>
    .item-container {
        width: 750px;
        background-color: #f2f3f4;
    }

    .border-cell {
        background-color: #f2f3f4;
        width: 750px;
        height: 24px;
        align-items: center;
        justify-content: center;
        border-bottom-width: 1px;
        border-style: solid;
        border-color: #e0e0e0;
    }

    .cell {
        background-color: #ffffff;
    }

    .content{
        width:750px;
        height:300px;
        border-bottom-width:1px;
        align-items: center;
        justify-content: center;
    }
</style>
<script>
    import Head from "../components/head";
    const dom = weex.requireModule('dom');
    /*不要使用这种方式引入，会导致js文件体积很大，要引入项目中的vue组件，参考 weex-ui.vue demo*/
    import { WxcTabPage, WxcPanItem, Utils, BindEnv } from 'weex-ui';
    // import Config from '../components/weex-ui/config'
    // import  WxcTabPage  from '../components/weex-ui/packages/wxc-tab-page';
    // import  WxcPanItem  from '../components/weex-ui/packages/wxc-pan-item';
    // import  BindEnv  from '../components/weex-ui/packages/bind-env';
    // import  Utils  from '../components/weex-ui/packages/utils';

    export default {
        components: {
            Head,
            WxcTabPage, WxcPanItem },
        data: () => ({
            tabTitles: Config.tabTitles,
            tabStyles: Config.tabStyles,
            tabList: [],
            demoList: [1, 2, 3, 4, 5, 6, 7, 8, 9],
            tabPageHeight: 1334
        }),
        created () {
            this.tabPageHeight = Utils.env.getPageHeight();
            this.tabList = [...Array(this.tabTitles.length).keys()].map(i => []);
            Vue.set(this.tabList, 0, this.demoList);
        },
        methods: {
            wxcTabPageCurrentTabSelected (e) {
                const self = this;
                const index = e.page;
                /* Unloaded tab analog data request */
                if (!Utils.isNonEmptyArray(self.tabList[index])) {
                    setTimeout(() => {
                        Vue.set(self.tabList, index, self.demoList);
                    }, 100);
                }
            },
            wxcPanItemPan (e) {
                if (BindEnv.supportsEBForAndroid()) {
                    this.$refs['wxc-tab-page'].bindExp(e.element);
                }
            }
        }
    };
</script>
