<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-row :gutter="10">
                <el-col :span="12" v-for="item in tableData" :key="item.id" style="margin: 25px 25px 25px 25px "  >

                    <div  style="border: 1px solid #ccc;padding-bottom: 10px;border-radius: 10px;overflow: hidden; ">
                        <img :src="item.headurl" style="width: 25%;margin: 0 220px">
                        <div  style="padding: 5px; color: #666 ;font-size: 18px">{{item.name}}  </div>
                        <div  style="padding: 10px;"><el-button type="primary" @click="tiaozhuan(item.id)">采购 </el-button> </div>
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
                size: 100,
                username: "",

                headerby: 'heade',

                data: [],

            }},
        methods: {
            tiaozhuan(id){
                this.$router.push({
                    path:"/front/goodlist",
                    query:{
                        id:id
                    }
                })
            },
            Myserach(){
                this.request.get("http://localhost:9090/supplier/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {
                    console.log(response)
                    this.tableData=response.data.data
                    this.totals=response.data.total
                })

            },

            serachbyname(){
                this. Myserach()
            },
        },
        mounted() {
            this.Myserach()
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

