<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="xinzheng">新增</el-button>
            <el-button type="danger" icon="el-icon-remove-outline" @click="pshanchu" >批量删除</el-button>
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="jiatype" >类目管理</el-button>

        </div>
        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"   @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="img" label="图片" width="140">
                <template slot-scope="scope">
                    <img :src="scope.row.img" style="width: 60px;height: 60px"/>
                </template>
            </el-table-column>


            <el-table-column prop="name" label="名称" width="140">
            </el-table-column>

            <el-table-column prop="type" label="类型" >
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
                title="增加模板"
                :visible.sync="dialogVisible"
                width="30%">
            <!--                            :before-close="handleClose">-->
            <span>


                            <div style="margin: 20px;"></div>
         <el-form  label-width="80px" :model="user" size="small">

           <el-form-item label="图片">
                <el-upload
                        class="avatar-uploader"
                        action="http://localhost:9090/file/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        >
                   <img v-if="user.img" :src="user.img" class="avatar" style="height: 100px;width: 100px">

                   <i v-else class="el-icon-plus avatar-uploader-icon" style="height: 110px;width: 110px"></i>

             </el-upload>

 </el-form-item>
           <el-form-item label="名称">
                               <el-input v-model="user.name"></el-input>
            </el-form-item>
             <el-form-item label="类型">
                   <template>
                        <el-select v-model="user.type" filterable placeholder="请选择" style="width: 100%">
                                <el-option
                                 v-for="item in options"
                                 :key="item.id"
                                 :label="item.name"
                                 :value="item.name">
                               </el-option>
                       </el-select>
                  </template>
              </el-form-item>

           </el-form>
                        </span>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="quxiao">取 消</el-button>
                   <el-button type="primary" @click="queding">确 定</el-button>
                     </span>
        </el-dialog>


        <el-dialog
                title="类目修改"
                :visible.sync="typedialogv"
                width="30%">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="tianjiazi">
                增加子类目
            </el-button>
            <el-button type="danger" icon="el-icon-remove-outline" @click="shanchuzi">
                删除子项
            </el-button>
            <el-tree :data="mydata" show-checkbox="true" ref="tree" @check="check"  :props="defaultProps" node-key="id" >
            </el-tree>


        </el-dialog>


        <el-dialog
                title="添加类目"
                :visible.sync="typedialogVisible"
                width="30%">
            <!--                            :before-close="handleClose">-->
            <span>

                            <div style="margin: 20px;"></div>
         <el-form  label-width="80px" :model="type" size="small">

           <el-form-item label="名称">
                               <el-input v-model="type.name"></el-input>
            </el-form-item>

           </el-form>
                        </span>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="typequxiao">取 消</el-button>
                   <el-button type="primary" @click="typequeding">确 定</el-button>
            </span>
        </el-dialog>
<!--        <el-dialog-->
<!--                title="菜单分配"-->
<!--                :visible.sync="enumdialog"-->
<!--                width="30%">-->
<!--            &lt;!&ndash;                            :before-close="handleClose">&ndash;&gt;-->


<!--            &lt;!&ndash;                    :default-expanded-keys="[1, 6]"&ndash;&gt;-->
<!--            &lt;!&ndash;                    :default-checked-keys="[5]"&ndash;&gt;-->

<!--            &lt;!&ndash;            :props="props"指定节点属性与接受数据对应关系&ndash;&gt;-->
<!--            <el-tree-->

<!--                    :props="props"-->
<!--                    :data="data"-->
<!--                    show-checkbox-->
<!--                    node-key="id"-->
<!--                    :default-expand-all="true"-->
<!--            >-->
<!--            </el-tree>-->


<!--        </el-dialog>-->



    </div>
</template>

<script>
    export default {
        data(){
            return {
                options:[],
                mydata: [{
                    id:0,
                    name: '类目',
                    children: []
                    }
                    ],
                     defaultProps: {
                     children: 'children',
                     label: 'name'
                       },

                dialogVisible: false,
                typedialogVisible: false,
                user: {
                    id: 0,
                    name: "",
                    type: "",
                    img: ""
                },
                typelist: [],
                type:{},

                tableData: [],
                totals: 0,
                page: 1,
                size: 10,
                username: "",
                typedialogv: false,
                headerby: 'heade',
                list:[],

                data: [],

                props: {
                    label:"name"
                }


            }},
        methods: {
            shanchuzi(){
                console.log(this.typelist)
                console.log(1111)
                let ids=this.typelist
                this.$confirm("是否删除", "提示", {
                    type: "warning"
                }).then(() => {
                    console.log(ids)
                    this.request.post("http://localhost:9090/type/pshanchu", ids).then(response => {
                        if (response.data === true) {
                            this.$message.success("修改成功")

                            this.ziMyserach()
                        } else this.$message.error("修改失败")
                    }).catch(error => {

                        this.$message.error("修改失败")
                    })
                })

                this.typelist= []

            },
            typequeding(){
                this.request.post("http://localhost:9090/type/zhengjia",this.type
                ).then(response => {

                    if (response.data === true)
                    {
                        this.ziMyserach(),
                            this.typedialogVisible = false
                        this.$message.success("添加成功")

                    }
                    else {
                        this.$message.error("添加失败")
                    }
                     this.type={}
                }).catch(error=>{
                    console.log(error.message)
                    this.$message.error("添加失败")
                })

            },
            typequxiao(){
                this.$confirm("是否退出","提示",{
                    type: 'info'
                }).then(()=>{
                    this.typedialogVisible = false

                    this.$message.success("退出成功")
                }).catch(()=>{
                    this.$message.info("返回")
                })
            },
            tianjiazi(){
         this.typedialogVisible=true
            },
            check(data){
                if (this.typelist.some(x => {
                    return x===data.id
                }
                )) {
                    this.typelist.pop()
                }

                else {
                    this.typelist.push(data.id)
                }
                console.log(this.typelist)

            },
            jiatype(){
                this.typedialogv=true
            },


            handleAvatarSuccess(row){


                this.user.img=row
            },
            pshanchu() {
                let ids=this.list.map(v=>v.id)
                this.$confirm("是否删除", "提示", {
                    type: "warning"
                }).then(() => {
                    console.log(ids)
                    this.request.post("http://localhost:9090/gmode/pshanchu", ids).then(response => {
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
                    this.request.get("http://localhost:9090/gmode/shanchu?id="+row.id).then(response => {
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
                this.request.post("http://localhost:9090/gmode/zhengjia",this.user
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


            ziMyserach(){
                this.request.get("http://localhost:9090/type/test").then(res=>{
                     this.options=res.data
                    this.mydata[0].children=res.data

                })
            },

            Myserach(){


                this.request.get("http://localhost:9090/gmode/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

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


           this.ziMyserach()
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