<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">

            <el-upload action="http://localhost:9090/file/upload"
                       style="display: inline-block; padding-left: 10px;padding-right: 10px"
                       :show-file-list="false"
                       :on-success="handleSuccess"
                       name="file"
                       :before-upload="beforeup"

            >
                <el-button type="primary" icon="el-icon-bottom">文件导入</el-button>
            </el-upload>


            <el-button type="danger" icon="el-icon-remove-outline" @click="pshanchu" >批量删除</el-button>
        </div>
        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"   @selection-change="handleselect">
            <el-table-column type="selection" width="55">
            </el-table-column>

            <el-table-column  label="文件图片" width="140">
                <template slot-scope="scope">
                  <img :src="scope.row.url" style="width: 60px;height: 60px"/>
                </template>
            </el-table-column>

            <el-table-column prop="name" label="文件名称" width="140">
            </el-table-column>

            <el-table-column prop="type" label="文件类型" width="120">
            </el-table-column>

            <el-table-column prop="size" label="文件大小" width="120">
            </el-table-column>

            <el-table-column  label="下载" width="120">
                <template slot-scope="scope">
                    <el-button type="primary" @click="xiazai(scope.row.url)">
                        下载
                    </el-button>
                </template>
            </el-table-column>


            <el-table-column prop="enable" label="启用">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.enable"
                               active-color="#13ce66"
                               inactive-color="#ccc"
                               @change="change(scope.row)">
                    </el-switch>

                </template>
            </el-table-column>

            <el-table-column label="操作">
                <!--                            获得一行数据-->
                <template  slot-scope="scope">
                    <el-button type="danger" icon="el-icon-remove-outline" @click="shanchu(scope.row)">删除</el-button>
                </template>
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
                list:[],
                tableData: [],
                totals: 0,
                page: 1,
                size: 10,
                username: "",
                headerby:'heade',
            }
        },
        methods: {
            pshanchu(){
                console.log("dianji")
                let ids=this.list.map(v=>v.id)
                this.$confirm("是否删除", "提示", {
                    type: "warning"
                }).then(() => {
                    console.log(ids)
                    this.request.post("http://localhost:9090/file/pshanchu", ids).then(response => {
                        if (response.data === true) {
                            this.$message.success("删除成功")

                            this.Myserach()
                        } else this.$message.error("删除失败")
                    }).catch(error => {

                        this.$message.error("删除失败")
                    })
                })

                this.list= []},

            change(a){
                this.request.post("http://localhost:9090/file/zhengjia",a
                ).then(response => {

                   if (response.code === 200)
                       this.$message.success("修改成功")
                })

            },

            xiazai(row){
                window.open(row)
            },
            Myserach(){

                this.request.get("http://localhost:9090/file/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

                    this.tableData=response.data.data
                    this.totals=response.data.total
                })
            },

            beforeup(file){


            },
            handleSuccess(){
                this.$message.success("上传成功")
                this.Myserach()
            },


        handleselect (val){
            console.log("修改了")
                this.list=val
            },
            shanchu(row){
                this.$confirm("确定删除吗？","提示",{
                    type: "info"
                }).then(()=>{
                    this.request.get("http://localhost:9090/file/shanchu?id="+row.id).then(response => {
                        if (response.data === true)
                        {
                            this.Myserach(),
                                this.$message.success("删除成功")

                        }
                        else {
                            this.$message.info("删除失败")
                        }
                    })
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
            console.log(this.tableData)
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