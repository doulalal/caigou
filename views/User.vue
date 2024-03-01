<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="xinzheng">新增</el-button>
            <el-button type="danger" icon="el-icon-remove-outline" @click="pshanchu" >批量删除</el-button>

            <el-upload action="http://localhost:9090/import"
                       style="display: inline-block; padding-left: 10px;padding-right: 10px"
                       :accept="xlsx"
                       :show-file-list="false"
                       :on-success="handleSuccess"
                       name="file"
                       :before-upload="beforeup"

            >
                <el-button type="primary" icon="el-icon-bottom">导入</el-button>
            </el-upload>

            <el-button type="primary" icon="el-icon-top" @click="exportfile">导出</el-button>
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
            <el-table-column label="操作">
                <!--                            获得一行数据-->
                <template  slot-scope="scope">
                    <el-button type="success"  icon="el-icon-edit" @click="gaibian(scope.row)">编辑</el-button>
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

        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%">
            <!--                            :before-close="handleClose">-->
            <span>
                            <div style="margin: 20px;"></div>
                                  <el-form  label-width="80px" :model="user">
                             <el-form-item label="姓名">
                               <el-input v-model="user.name"></el-input>
                             </el-form-item>
                             <el-form-item label="负责人">
                               <el-input v-model="user.header"></el-input>
                             </el-form-item>
                             <el-form-item label="电话号">
                               <el-input v-model="user.pnumber"></el-input>
                             </el-form-item>
                             <el-form-item label="权限">
                               <el-input v-model="user.role"></el-input>
                             </el-form-item>
                            </el-form>
                        </span>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="quxiao">取 消</el-button>
                   <el-button type="primary" @click="queding">确 定</el-button>
                     </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                dialogVisible: false,
                user:{
                    id: 0,
                    name: "",
                    header: "",
                    pnumber: ""
                },
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
            beforeup(file){
                console.log("啊啊啊")
                console.log(file)

            },
            handleSuccess(){
                this.$message.success("导入成功")
                this.Myserach()
            },

            exportfile(){
                window.open("http://localhost:9090/export")
            },
            pshanchu() {
                let ids=this.list.map(v=>v.id)
                console.log(ids)
                this.$confirm("是否删除", "提示", {
                    type: "warning"
                }).then(() => {
                    console.log(ids)
                    this.request.post("http://localhost:9090/pshanchu", ids).then(response => {
                        if (response.data === true) {
                            this.$message.success("修改成功")

                            this.Myserach()
                        } else this.$message.error("修改失败")
                    }).catch(error => {

                        this.$message.error("修改失败")
                    })
                })

                this.list= []
            }
            ,
            handleSelectionChange(val){
                this.list=val
            },
            shanchu(row){
                this.$confirm("确定删除吗？","提示",{
                    type: "info"
                }).then(()=>{
                    this.request.get("http://localhost:9090/shanchu?id="+row.id).then(response => {
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
            xinzheng(){
                this.user={}
                this.dialogVisible = true


            },

            gaibian(row){

                this.dialogVisible=true
                this.user=row


            },
            queding(){
            console.log(this.user.role)
                if (this.user.role === '1')
                {
                    this.$message.info("role不能为1")

                }
                else {
                    this.request.post("http://localhost:9090/zhengjia", this.user
                    ).then(response => {

                        if (response.data === true) {
                            this.Myserach(),
                                this.dialogVisible = false
                            this.$message.success("添加成功")

                        } else {
                            this.$message.error("添加失败")
                        }

                    }).catch(error => {
                        console.log(error.message)
                        this.$message.error("添加失败")
                    })


                }

            },
            quxiao(){
                this.$confirm("是否退出","提示",{
                    type: 'info'
                }).then(()=>{
                    this.dialogVisible = false
                    this.Myserach()
                    this.$message.success("退出成功")
                }).catch(()=>{
                    this.$message.info("返回")
                })
            },


            Myserach(){


                this.request.get("http://localhost:9090/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

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