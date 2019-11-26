<template>
    <page-layout title="表单组件例子">
        <form :formData="formData" :checkRule="checkRule" @submit="submit">
             <form-item label="名称:" prop="name">
                 <input v-model="formData.name" style="width: 500px;height: 60px;font-size: 28px" :placeholder="checkRule.name.message" />
             </form-item>
            <form-item label="手机号:" prop="phone">
                <input v-model="formData.phone" type="tel" style="width: 500px;height: 60px;font-size: 28px" :placeholder="checkRule.phone.message" />
            </form-item>
            <form-item label="邮箱:" prop="email">
                <input v-model="formData.email" type="email" style="width: 500px;height: 60px;font-size: 28px" :placeholder="checkRule.email.message" />
            </form-item>
        </form>
    </page-layout>
</template>

<script>
    import PageLayout from '../components/pageLayout.vue';
    import Form from '../components/form.vue';
    import FormItem from '../components/formItem.vue';

    export default {
        components: {
            FormItem,
            Form,
            PageLayout},
        name: 'form-demo',
        data() {
            return {
                formData: {
                    name: '',
                    phone: '',
                    email:''
                },
                checkRule: {
                    name:{require:true,type:'',message:'请选输入名称',checkMessage:'数据格式错误',
                        checkFun:function (value) {
                            return value.length <= 2;
                        }},
                    phone:{require:true,type:'phone',message:'请选输入手机号',checkMessage:'手机号数据格式错误',checkFun:null},
                    email:{require:true,type:'',message:'请选输入邮箱',checkMessage:'邮箱数据格式错误',checkFun:null}
                }
            }
        },
        methods:{
            submit(){
                let modal=weex.requireModule('modal');
              modal.alert({message:"数据完整通过！"});
            }
        }
    };
</script>

<style scoped>

</style>