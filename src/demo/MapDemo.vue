<template>
    <div>
        <map  class="map" showLocationButton="true" showsUserLocation="true" model="2" :mapType="mapType">
            <mapMarker  v-for="(item,index) in list" :key="index" :point="item.point" :imageSrc="item.image"
                     :title="item.title"
                     :content="item.content"
                        @onMarkerClick="onclicks"
                        @onInfoWindowClick="onclicks1"
                     showType="222">


            </mapMarker>
        </map>
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
                mapType:1,
               locationInfo:{},
               list:[{point:[39.553869,116.781177],title:"222",content:"222",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                   {point:[39.255869,116.781177],title:"333333",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                   {point:[39.856869,116.781177],title:"111",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                   {point:[39.758869,116.781177],title:"111",content:"111",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}},
                   {point:[39.669869,116.781177],title:"222",content:"222",image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:100,height:100}}],
               title:"111",
               content:"222",
               image:{url:"http://uploads.5068.com/allimg/130520/104Q91501-0.jpg",width:200,height:200}
            }
        },
        methods:{
            onclicks(){
                modal.alert({message:"aaaa"})
            },
            onclicks1(){
                modal.alert({message:"bbbbbb"})
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
