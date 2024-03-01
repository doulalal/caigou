<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>

        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"   @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="140">
            </el-table-column>
            <el-table-column prop="header" label="负责人" width="120">
            </el-table-column>
            <el-table-column prop="pnumber" label="电话号">
            </el-table-column>

        </el-table>

        <el-pagination
                @size-change="SizeChange"
                @current-change="PageChange"
                :page-sizes="[3,5,10]"
                :page-size="size"
                :current-page="page"
                layout="total, sizes, prev, pager, next, jumper"
                :total=totals>
        </el-pagination>

    </div>
</template>

<script>
    export default {
        data(){
            return {
                user:{
                    id: 0,
                    name: "",
                    header: "",
                    pnumber: ""
                },
                totals: 0,
                page: 1,
                size: 10,
                username: "",
                headerby:'heade',
            }
        },
        methods: {
            Myserach(){


                this.request.get("http://localhost:9090/purchaser/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

                    this.tableData=response.data.data
                    this.totals=response.data.total
                })
            },

            SizeChange(size){
                this.size=size
                this.Myserach()
            },
            PageChange(page){

                this.page=page
                this.Myserach()
            },
            serachbyname(){
                this.Myserach()
            },
        },
        mounted(){

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