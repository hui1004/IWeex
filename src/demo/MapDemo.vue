<template>
    <div>
        <map  class="map" showLocationButton="true" showsUserLocation="true" model="2" :mapType="mapType">
            <mapMarker  v-for="(item,index) in list" :key="index" :point="item.point" :imageSrc="item.image"
                       :title="item.title"
                       :content="item.content"
                        @onMarkerClick="onclicks"
                        @onInfoWindowClick="onclicks1"
                       showType="1">
            </mapMarker>
            <mapCircle v-for="(item,index) in cirCleList" :key="index"
                       :lat="item.lat" :lng="item.lng" :radius="item.radius"
                       :strokeWidth="item.strokeWidth"
                       :strokeColor="item.strokeColor"
                       :fillColor="item.fillColor">

            </mapCircle>
        </map>
        <div style="width: 120px;height: 80px;background-color: red;position: absolute;bottom: 40px;left:20px;
                align-items: center;justify-content: center" @click="add()">
                 <text style="font-size:25px;color: white">添加标记</text>
        </div>
        <div style="width: 120px;height: 80px;background-color: red;position: absolute;bottom: 40px;left:150px;
                align-items: center;justify-content: center" @click="update()">
            <text style="font-size:25px;color: white">更新图标</text>
        </div>
        <div style="width: 120px;height: 80px;background-color: red;position: absolute;bottom: 40px;left:280px;
                align-items: center;justify-content: center" @click="addCircle()">
            <text style="font-size:25px;color: white">添加圆区</text>
        </div>
        <div style="width: 120px;height: 80px;background-color: red;position: absolute;bottom: 40px;left:410px;
                align-items: center;justify-content: center" @click="updateCircle()">
            <text style="font-size:25px;color: white">更新圆形</text>
        </div>
    </div>
</template>

<script>
    var location=weex.requireModule('location');
    var utils=weex.requireModule('util');
    var modal=weex.requireModule('modal');
    export default {
        name: "map-demo",
        data() {
            return {
                lat:39.553869,
                lng:116.781177,
                mapType:1,
               locationInfo:{},
               cirCleList:[{lat:39.553869,lng:116.781177,radius:10000,strokeWidth:20,strokeColor:'rgba(100,100,100,0.75)'
                   ,fillColor:'rgba(250,100,100,0.5)'},
                   {lat:39.856869,lng:116.781177,radius:10000,strokeWidth:20,strokeColor:'rgba(100,100,100,0.75)',fillColor:'rgba(250,100,100,0.5)'}],
               list:[{point:[39.553869,116.781177],title:"222",content:"222",image:{url:"app:image/iclauncher.png",width:100,height:100}},
                   {point:[39.255869,116.781177],title:"333333",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                   {point:[39.856869,116.781177],title:"111",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                   {point:[39.758869,116.781177],title:"111",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                 ],
               title:"测试1",
               content:"222",
               image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:200,height:200}
            }
        },
        methods:{
            onclicks(){
                modal.toast({message:"markerClick"})
            },
            onclicks1(){
                modal.toast({message:"infoClick"})
            },
            add(){
                // this.title="测试2";
                this.list.push({point:[39.458869,116.781177],title:"添加的标记",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}});
            },
            update(){
                this.list[0].image={url:"app:image/iclauncher.png",width:300,height:300};
                this.list[0].title="更新后的标题";
            },
            addCircle(){
                this.cirCleList.push({lat:this.lat+=0.1,lng:this.lng-=0.1,radius:10000,strokeWidth:20,strokeColor:'rgba(100,100,100,0.75)'
                    ,fillColor:'rgba(250,100,100,0.5)'})
            },
            updateCircle(){
                this.cirCleList[0].radius-=500;
            }
        },
        created(){
              // modal.alert({message:"222"})
            var self=this;
             setTimeout(function () {
                 utils.requestPermissions("定位",function (res) {
                     location.getLocation("user",function (res) {
                         self.locationInfo=res;
                         self.title=res.locationInfo.aoiName;
                         self.content=res.locationInfo.aoiName;
                     })
                 })
             },3000)

        }
    }
</script>

<style scoped>
    .map{
        width: 750px;
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
    }

</style>
