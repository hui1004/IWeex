<template>
    <div>
        <gesture-password height="1200" width="750" size="600" @gestureEnd="gestureEnd"></gesture-password>
    </div>
</template>

<script>
    import GesturePassword from "../components/gesturePassword.vue";
    let modal=weex.requireModule('modal');
    export default {
        components: {GesturePassword},
        name: "gesture-pwd-demo",
        data() {
            return {
                oldPwd:[],
                pwd:[]
            }
        },
        methods:{
            gestureEnd(path){
                let self=this;
                if(this.pwd.length!=0){
                    let can=true;
                    if(path.length!=this.pwd.length){
                        modal.toast({message:"密码错误！"});
                        return;
                    }
                    path.forEach((item,index)=>{
                        if(item.x!=self.pwd[index].x||item.y!=self.pwd[index].y){
                            can=false;
                        }
                    });
                    if(can){
                        modal.toast({message:"密码正确！"});
                    }else{
                        modal.toast({message:"密码错误！"});
                    }
                    return;
                }
                if(this.oldPwd.length==0){
                    this.oldPwd=path;
                    modal.toast({message:"在绘制一次确认密码"});
                }else{
                    let can=true;
                    if(this.oldPwd.length!=path.length){
                        modal.toast({message:"两次绘制不一致"});
                        this.oldPwd=[];
                        return;
                    }
                    this.oldPwd.forEach((item,index)=>{
                           if(item.x!=path[index].x||item.y!=path[index].y){
                               can=false;
                           }
                    });
                    if(can){
                        this.pwd=path;
                        modal.toast({message:"设置手势密码成功"});
                    }else{
                        this.oldPwd=[];
                        modal.toast({message:"两次绘制不一致"});
                    }
                }



            }
        }
    }
</script>

<style scoped>

</style>
