<template>
    <div class="formItem" :style="formStyle">
        <text v-if="checkRule[prop]&&checkRule[prop].require" style="color: red;font-size: 30px">*</text>
        <text class="label" :style="{labelStyle}">{{label}}</text>
        <slot/>
        <!--<text>{{checkRule[prop].checkMessage}}</text>-->
    </div>
</template>

<script>
    let modal=weex.requireModule('modal');
    export default {
        name: 'form-item',
        inject:['checkRule'],
        props:{
            formStyle:{
                default:{
                },
                type:Object
            },
            labelStyle:{
                default:{
                },
                type:Object
            },
            check:{
                default:false,
                type:Boolean
            },
            prop:{
                default:'value',
                type:String
            },
            label:{
                default:'',
                type:String
            }
        },
        computed:{
            listeners(){
                return this.$listeners;
            }
        },
        data(){
            return{

            }
        },
        mounted(){
            // modal.alert({message:this.$listeners})
            // modal.alert({message:this.formData})
        }
    };
</script>

<style scoped>
    .label{
        width: 150px;
        font-size: 28px;
        color: #666666;

    }
    .formItem{
        flex-direction: row;align-items: center;padding: 18px;background-color: white;
        border-bottom-width: 1px;
        border-color: #dddddd;
    }
</style>