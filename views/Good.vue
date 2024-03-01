<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="xinzheng">新增</el-button>

        </div>
        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"   @selection-change="handleSelectionChange">

            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column  label="图片" width="70">
                <template slot-scope="scope">
                    <img :src="scope.row.img" style="width: 60px;height: 60px"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="名称" width="70">
            </el-table-column>
            <el-table-column prop="type" label="类型" width="70">
            </el-table-column>
            <el-table-column prop="supplier" label="供应商" width="70">
            </el-table-column>
            <el-table-column prop="content" label="净含量" width="70">
            </el-table-column>
            <el-table-column prop="unit" label="单位" width="70">
            </el-table-column>
            <el-table-column prop="price" label="价格" width="70">
            </el-table-column>
            <el-table-column  label="状态" >
               <template scope="scope">
                <el-switch
                        v-model="scope.row.statue"
                        active-text="销售"
                        inactive-text="停售"
                        @change="change(scope.row)"
                >
                </el-switch>
               </template>
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
                title="商品增加"
                :visible.sync="dialogVisible"
                width="30%">
            <!--                            :before-close="handleClose">-->


                         <span>


                             <el-form  label-width="80px" :model="user">
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
                                 <template >
                                    <el-select   v-model="name" filterable placeholder="请选择" style="width: 100%">
                                      <el-option
                                        v-for="item in options"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.name"
                                      >
                                     </el-option>
                                    </el-select>
                                 </template>

                             </el-form-item>

                                 <el-form-item label="类型">
                               <el-input  readonly="readonly" v-model="user.type"></el-input>
                             </el-form-item>
                             <el-form-item label="净含量">
                               <el-input v-model="user.content"></el-input>
                             </el-form-item>
                                 <el-form-item label="单位">
                               <el-input v-model="user.unit"></el-input>
                             </el-form-item>
                                 <el-form-item label="价格">
                               <el-input v-model="user.price"></el-input>
                             </el-form-item>


                      <el-form-item label="状态">
                        <template >
                           <el-select v-model="user.statue" placeholder="请选择" style="width: 100%">
                                     <el-option
                                           v-for="item in statue"
                                           :key="item.value"
                                           :label="item.label"
                                           :value="item.value">
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
                title="商品修改"
                :visible.sync="enumdialog"
                width="30%">
            <!--                            :before-close="handleClose">-->


            <span>


                             <el-form  label-width="80px" :model="user">
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
                                   <el-input  readonly="readonly" v-model="user.name"></el-input>
                             </el-form-item>

                                 <el-form-item label="类型">
                               <el-input  readonly="readonly" v-model="user.type"></el-input>
                             </el-form-item>
                             <el-form-item label="净含量">
                               <el-input v-model="user.content"></el-input>
                             </el-form-item>
                                 <el-form-item label="单位">
                               <el-input v-model="user.unit"></el-input>
                             </el-form-item>
                                 <el-form-item label="价格">
                               <el-input v-model="user.price"></el-input>
                             </el-form-item>


                      <el-form-item label="状态">
                        <template >
                           <el-select v-model="user.statue" placeholder="请选择" style="width: 100%">
                                     <el-option
                                             v-for="item in statue"
                                             :key="item.value"
                                             :label="item.label"
                                             :value="item.value">
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

    </div>
</template>

<script>
    export default {
        data(){
            return {
                dialogVisible: false,
                user: {

                },

                list: [],
                tableData: [],
                totals: 0,
                page: 1,
                size: 10,
                name:"",
                username: "",
                enumdialog: false,
                headerby: 'heade',
                flag:false,

                data: [],
                statue:[{value:true,label: '售卖'},{value:false,label: '停售'}],
                options:[],

                props: {
                    label:"name"
                }


            }},
        watch:{
          name(name){
              this.user=this.options.find(list=>list.name === name)

          }

        },
        methods: {


            change(r){

                this.request.post("http://localhost:9090/good/zhengjia",r
                ).then(res=>{

                })
            },

            handleAvatarSuccess(rep){
                this.user.img=rep

            },

            pshanchu() {
                let ids=this.list.map(v=>v.id)
                console.log(ids)
                this.$confirm("是否删除", "提示", {
                    type: "warning"
                }).then(() => {
                    console.log(ids)
                    this.request.post("http://localhost:9090/good/pshanchu", ids).then(response => {
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
                    this.request.get("http://localhost:9090/good/shanchu?id="+row.id).then(response => {
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
                this.enumdialog=true
                this.user=row
                this.flag=true


            },
            queding(){


                if (this.tableData.some(good => good.name === this.user.name)&&this.flag === false)
                {
                    this.$message.info("已有商品，重新添加")
                    return
                }
                else {
                this.request.post("http://localhost:9090/good/zhengjia",this.user
                ).then(response => {

                    if (response.data === true)
                    {
                        this.Myserach(),
                            this.dialogVisible = false
                            this.enumdialog=false
                        this.$message.success("添加成功")

                    }
                    else {
                        this.$message.error("添加失败")
                    }

                }).catch(error=>{
                    console.log(error.message)
                    this.$message.error("添加失败")
                })
               this.flag=false
            }},
            quxiao(){
                this.$confirm("是否退出","提示",{
                    type: 'info'
                }).then(()=>{
                    this.dialogVisible = false
                    this.enumdialog=false
                    this.Myserach()
                    this.$message.success("退出成功")
                }).catch(()=>{
                    this.$message.info("返回")
                })
            },


            Myserach(){
                this.request.get("http://localhost:9090/good/user/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

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


            this.request.get("http://localhost:9090/gmode/getall").then(res=>{
                this.options= res.data.map(item=>{
                     delete item.id
                     return item
                 })

            })


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