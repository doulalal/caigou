<template>
    <div>

            <el-rate
                v-model="data.fuwuRate"
                show-text
                :texts="['极差', '失望', '一般', '满意', '惊喜']"
                style="text-align: center"
             >
            </el-rate>

         <div style="padding: 50px">
             <el-input   v-model="data.content"  type="textarea" placeholder="请输入评论"></el-input>
            <div style="padding: 10px;text-align: right">
                <el-button type="primary" @click="tijiao" >
                       评论
                </el-button>

            </div>

         </div>

    </div>
</template>

<script>
    export default {
      data(){
          return{
              data:{
              fuwuRate: 4,
               content: '',
              dingdanId:this.$route.query.id
          }


          }
      },
        methods:{
            tijiao(){
                console.log(this.data)
                this.request.post("http://localhost:9090/comment/zhengjia",this.data).then(rsp=>{
                    this.$message.success("评论成功")
                    this.$router.push("/front")

                })

            }
        },
        mounted() {
          console.log(this.$route.query.id)
        }


    }
</script>

<style scoped>

</style>