<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-row :gutter="10">
                <el-col :span="12" v-for="item in tableData" :key="item.id" style="margin-bottom: 10px "  >

                    <div  style="border: 1px solid #ccc;padding-bottom: 10px;border-radius: 10px;overflow: hidden ">
                        <img :src="item.img" style="width: 25%;margin: 0 220px">
                        <div  style="padding: 5px; color: #666 ;font-size: 18px">{{item.name}}  </div>
                        <div  style="padding: 5px;color: orangered;font-size: 14px">￥{{item.price}}  </div>
                        <div  style="padding: 10px;"><el-button type="primary" @click="tiaozhuan(item.id)">购买 </el-button> </div>
                    </div>
                </el-col>

            </el-row>

        </div>



    </div>
</template>

<script>
    export default {
        data(){
            return {
                user: {

                },
                list: [],
                tableData: [],
                totals: 0,
                page: 1,
                size: 10,
                username: "",

                headerby: 'heade',

                data: [],
                id: this.$route.query.id,



            }},
        methods: {
            tiaozhuan(id){
                this.$router.push({
                    path:"/front/xiangqing",
                    query:{
                        id:id
                    }
                })
            },
            Myserach(){
                this.request.get("http://localhost:9090/good/test?page="+this.page+"&size="+this.size+"&username="+this.username+"&id="+this.id).then(response => {
                    console.log(response)
                    this.tableData=response.data.data
                    this.totals=response.data.total
                })


            },
            Myload(){
                let self=this
                this.request.get("http://localhost:9090/good/getall?id="+this.id).then( response => {
                    self.tableData=response.data
                })}
            ,

            serachbyname(){
                this. Myserach()
            },
        },
        mounted() {
            this.Myload()


        }


    }
</script>

<style scoped>
    .el-header {
        background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
    }

    .el-aside {
        color: #333;
    }
    .heade{
        background-color: oldlace!important;

    }
</style>
