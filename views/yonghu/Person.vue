<template>
<el-card style="width: 500px;padding: 20px">
    <el-form>

            <el-upload
                    class="avatar-uploader"
                    action="http://localhost:9090/file/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                     stype="center">
                <img v-if="user.headurl" :src="user.headurl" class="avatar" style="margin-left: 120px ;height: 150px;width: 150px">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>




        <el-form-item label="用户账号">
            <el-input type="" v-model="user.name"   disabled autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="负责人">
            <el-input type="text" v-model="user.header"  autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="电话号">
            <el-input type="text" v-model="user.pnumber"  autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="queding" style="width: 100%;">修改</el-button>
        </el-form-item>
    </el-form>
</el-card>
</template>

<script>
    export default {
        data(){
            return{
                dialogVisible: true,
                user: JSON.parse(localStorage.getItem("user"))
            }
        },
        methods:{
            handleAvatarSuccess(res){

                this.user.headurl=res

            },

            queding(){
                this.request.post("http://localhost:9090/zhengjia",this.user
                ).then(response => {

                    if (response.data === true)
                    {

                        this.$message.success("修改成功")

                    }
                    else {
                        this.$message.error("修改失败")
                    }

                }).catch(error=>{
                    console.log(error.message)
                    this.$message.error("修改失败")
                })
               localStorage.setItem("user",JSON.stringify(this.user))
                this.$bus.$emit("gethead",this.user.headurl)
            },
        }
    }
</script>

<style scoped>

</style>