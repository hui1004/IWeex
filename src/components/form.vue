<template>
    <div class="form" :style="{backgroundColor:'#eee'}">
        <div class="formItems">
            <slot/>
        </div>
        <slot name="actions">
            <div class="actions">
                <div class="cancelBtn">
                    <text class="cancelBtnText">取消</text>
                </div>
                <div class="submitBtn" @click="submit(emit)">
                    <text class="submitBtnText">提交</text>
                </div>
            </div>
        </slot>
    </div>
</template>

<script>
    let modal=weex.requireModule('modal');
    export default {
        name: 'form',
        provide(){
            return {
                checkRule:this.checkRule
            };
        },
        props:{
            formData:{
                default:{

                },
                type:Object
            },
            checkRule:{
                default:{
                   name:{
                       require:true,
                       type:'phone',
                       message:'请选输入名称',
                       trigger:'blur',
                       checkFun:null,
                       checkMessage:"格式错误"
                   }
                },
                type:Object
            },
        },
        methods:{
            submit(submit){
                for(let prop in this.checkRule){
                    /*没有此属性*/
                    if(!this.formData.hasOwnProperty(prop)){
                        return;
                    }
                    /*必填项数据为空*/
                    if(this.checkRule[prop].require&&this.formData[prop]==''){
                         modal.alert({message:this.checkRule[prop].message});
                         return;
                    }
                    /*数据类型校验*/
                    if(this.checkRule[prop].type){
                        switch (this.checkRule[prop].type){
                            case "phone":
                                if(!this.phoneCheck(this.formData[prop])){
                                    modal.alert({message:this.checkRule[prop].checkMessage});
                                    return;
                                }
                                break;
                            case "email":
                                if(!this.emailCheck(this.formData[prop])){
                                    modal.alert({message:this.checkRule[prop].checkMessage});
                                    return;
                                }
                                break;
                            case "number":
                                if(!this.numberCheck(this.formData[prop])){
                                    modal.alert({message:this.checkRule[prop].checkMessage});
                                    return;
                                }
                                break;
                        }
                    }
                    /*自定义校验方法*/
                    if(this.checkRule[prop].checkFun!==undefined&&this.checkRule[prop].checkFun!==null){
                        /*校验未通过*/
                        if(!this.checkRule[prop].checkFun(this.formData[prop])){
                            modal.alert({message:this.checkRule[prop].checkMessage});
                            return;
                        }
                    }
                }
                submit();
            },
            emit(){
                this.$emit('submit',{});
            },
            phoneCheck(value){
                if(!(/^1[3456789]\d{9}$/.test(value))){
                    return false;
                }
                return true;
            },
            telCheck(value){
                if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(value)){
                    return false;
                }
                return true;
            },
            emailCheck(value){
                var regemail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
                if(value==""){
                    return false;
                }else if(!(regemail.test(value))){
                    return false;
                }
                return true;
            },
            numberCheck(value){
                if(typeof value==='number'){
                    return true;
                }else {
                    return false;
                }
            },
        },
        data(){
            return{
                data:{}
            }
        }
    };
</script>

<style scoped>
    .form{
        width: 750px;
    }
    .actions{
        padding: 25px;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
    }
    .cancelBtn{
        height: 80px;
        width: 250px;
        background-color: white;
        border-radius: 10px;
        border-width: 1px;
        border-color: #5b9936;
        align-items: center;
        justify-content: center;
    }
    .submitBtn{
        height: 80px;
        width: 250px;
        background-color: #5b9936;
        border-radius: 10px;
        border-width: 1px;
        border-color: #5b9936;
        align-items: center;
        justify-content: center;
    }
    .cancelBtnText{
        color: #5b9936;
        font-size: 28px;
        font-weight: bold;
    }
    .submitBtnText{
        color: #ffffff;
        font-size: 28px;
        font-weight: bold;
    }

</style>