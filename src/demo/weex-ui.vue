<template>
    <div style="background-color: #eeeeee">
        <head title="weex-ui"></head>
        <scroller>
            <div class="cell">
                <div class="head">
                    <text style="font-weight: bold;font-size: 30px;color: #00B4FF">选择框</text>
                </div>
                <wxc-grid-select
                        :single="true"
                        :cols="5"
                        :customStyles="customStyles"
                        :list="testData1"
                        @select="params => onSelect('res3', params)">
                </wxc-grid-select>

                <wxc-grid-select
                        :limit="5"
                        :list="testData2"
                        @overLimit="onOverLimit"
                        @select="params => onSelect('res3', params)">
                </wxc-grid-select>
            </div>
            <div class="cell">
                <div class="head">
                    <text style="font-weight: bold;font-size: 30px;color: #00B4FF">动画滑块</text>
                </div>
                <div class="wrapper">
                    <wxc-ep-slider :slider-id="sliderId"
                                   :card-length='cardLength'
                                   :card-s="cardSize"
                                   :select-index="2"
                                   @wxcEpSliderCurrentIndexSelected="wxcEpSliderCurrentIndexSelected">
                        <!--自动生成demo-->
                        <div v-for="(v,index) in [1,2,3,4,5]"
                             :key="index"
                             :slot="`card${index}_${sliderId}`"
                             :class="['slider',`slider${index}`]">
                            <text>这里是第{{index + 1}}个滑块</text>
                        </div>
                    </wxc-ep-slider>
                </div>
            </div>
            <div class="cell">
                <div class="head">
                    <text style="font-weight: bold;font-size: 30px;color: #00B4FF">进度</text>
                </div>
                <wxc-simple-flow :list="testData" :themeColor="themeColor"></wxc-simple-flow>
            </div>
        </scroller>
    </div>

</template>
<style>
    .cell{
        width: 750px;
        background-color: white;
        padding: 20px;
        margin-top: 20px;
    }
    .head{
        height: 70px;
        width: 710px;
        /*align-items: center;*/
        justify-content: center;
        border-bottom-width: 2px;
        border-color: #dddddd;
        margin-bottom:20px;
    }
    .wrapper {
        padding-top: 100px;
    }

    .slider {
        width: 400px;
        height: 300px;
        background-color: #C3413D;
        align-items: center;
        justify-content: center;
    }

    .slider1 {
        background-color: #635147;
    }

    .slider2 {
        background-color: #FFC302;
    }

    .slider3 {
        background-color: #FF9090;
    }
    .slider4 {
        background-color: #546E7A;
    }
</style>
<script>
    /*使用此方式引入项目中的vue组件，而不是 import { WxcTabPage, WxcPanItem, Utils, BindEnv } from 'weex-ui';这样引入，
    * 后者会导致js文件体积过大*/
    import  WxcGridSelect  from '../components/weex-ui/packages/wxc-grid-select';
    import  WxcEpSlider  from '../components/weex-ui/packages/wxc-ep-slider';
    import  WxcSimpleFlow  from '../components/weex-ui/packages/wxc-simple-flow';
    import Head from "../components/head";
    export default {
        components: {
            Head,
            WxcEpSlider,
            WxcSimpleFlow,
            WxcGridSelect },
        data: () => ({
            sliderId: 1,
            cardLength: 5,
            cardSize: {
                width: 400,
                height: 300,
                spacing: 0,
                scale: 0.8
            },
            customStyles: {
                lineSpacing: '14px',
                width: '120px',
                height: '50px',
                icon: '',
                color: '#333333',
                checkedColor: '#ffffff',
                disabledColor: '#eeeeee',
                borderColor: '#666666',
                checkedBorderColor: '#ffb200',
                backgroundColor: '#ffffff',
                checkedBackgroundColor: '#ffb200'
            },
            testData1: [
                {
                    'title': '上海'
                },
                {
                    'title': '杭州',
                    'checked': true
                },
                {
                    'title': '北京'
                },
                {
                    'title': '广州'
                },
                {
                    'title': '深圳'
                },
                {
                    'title': '南京'
                }
            ],
            testData2: [
                {
                    'title': '上海'
                },
                {
                    'title': '杭州',
                    'checked': true
                },
                {
                    'title': '北京',
                    'checked': true
                },
                {
                    'title': '广州'
                },
                {
                    'title': '深圳'
                },
                {
                    'title': '南京'
                }
            ],
            themeColor: {
                lineColor: '#bf280b',
                pointInnerColor: '#b95048',
                pointBorderColor: '#bf280b',
                highlightTitleColor: '#bf280b',
                highlightPointInnerColor: '#bf280b',
                highlightPointBorderColor: '#d46262'
            },
            testData: [
                {
                    'date': '2017-05-24 21:10:29',
                    'desc': '',
                    'highlight': true,
                    'title': '方案已确认'
                },
                {
                    'date': '2017-05-24 19:54:28',
                    'desc': '',
                    'title': '方案已更新'
                },
                {
                    'date': '2017-05-24 19:50:21',
                    'desc': '您以确定了方案',
                    'title': '方案已上传'
                },
                {
                    'date': '2017-05-24 19:49:03',
                    'desc': '商家会在2个工作小时内电话或旺旺联系您',
                    'title': '商家已接单'
                }
            ]
        }),
        methods: {
            onSelect (res, {selectIndex, checked, checkedList}) {
                Vue.set(this, res, `本次选择的index：${selectIndex}\n是否选中：${checked ? '是' : '否'}\n选中列表：${checkedList.map(item => item.title).join(',')}`);
            },
            onOverLimit (limit) {
                modal.toast({
                    message: `最多选择${limit}个`,
                    duration: 0.8
                });
            },
            wxcEpSliderCurrentIndexSelected (e) {
                const index = e.currentIndex;
                console.log(index);
            }
        }
    }
</script>
