<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="xinzheng">新增</el-button>
            <el-button type="danger" icon="el-icon-remove-outline" @click="pshanchu" >批量删除</el-button>
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="xinzhengzi">新增子菜单</el-button>

        </div>
        <el-table :data="tableData" style="margin-bottom: 5px"
                  :header-cell-class-name="headerby"
                  @selection-change="handleSelectionChange"
                  default-expand-all
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                  row-key="id"

        >
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="name" label="名称">
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
                title="权限修改"
                :visible.sync="dialogVisible"
                width="30%">
            <!--                            :before-close="handleClose">-->
                      <span>
                            <div style="margin: 20px;"></div>
                                  <el-form  label-width="80px" :model="user">
                             <el-form-item label="名称">
                               <el-input v-model="user.name"></el-input>
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

            xinzhengzi(){
                this.user={}
                this.user.pid=2
                this.dialogVisible = true
            },

            pshanchu() {
                let ids=this.list.map(v=>v.id)
                console.log(ids)
                this.$confirm("是否删除", "提示", {
                    type: "warning"
                }).then(() => {
                    console.log(ids)
                    this.request.post("http://localhost:9090/myenum/pshanchu", ids).then(response => {
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
                    this.request.get("http://localhost:9090/myenum/shanchu?id="+row.id).then(response => {
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
                console.log(this.page)
                console.log(this.size)
                this.request.post("http://localhost:9090/myenum/zhengjia",this.user
                ).then(response => {

                    if (response.data === true)
                    {
                        this.Myserach(),
                            this.dialogVisible = false
                        this.$message.success("添加成功")

                    }
                    else {
                        this.$message.error("添加失败")
                    }

                }).catch(error=>{
                    console.log(error.message)
                    this.$message.error("添加失败")
                })

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


                this.request.get("http://localhost:9090/myenum/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {
                      console.log(response)
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