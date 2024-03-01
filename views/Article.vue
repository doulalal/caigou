<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">


        </div>
        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"   @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="dingdanname" label="名称" width="140">
            </el-table-column>
            <el-table-column label="内容" >
                <template slot-scope="scope" >
                    <el-button type="primary"  @click="xiangqing(scope.row)">
                        查看详情
                    </el-button>
                </template>
            </el-table-column>

            <el-table-column prop="username" label="发布人" >
            </el-table-column>
            <el-table-column prop="time" label="发布时间" >
            </el-table-column>
            <el-table-column label="订单" >
                <template slot-scope="scope" >
                    <el-button type="primary"  @click="chakan(scope.row)">
                        订单详情
                    </el-button>
                </template>
            </el-table-column>
            <el-table-column label="服务星级" >
                <template slot-scope="scope">
                    <el-rate
                            v-model="scope.row.fuwuRate"
                            disabled
                            show-score
                            text-color="#ff9900"
                    >
                    </el-rate>
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


        <el-dialog
                title="订单详情"
                :visible.sync="dialogVisible"
                width="60%">
            <!--                            :before-close="handleClose">-->
            <el-table :data="goods" style="margin-bottom: 5px"  :header-cell-class-name="headerby"  >
                <el-table-column  label="图片" width="70">
                    <template slot-scope="scope">
                        <img :src="scope.row.img" style="width: 60px;height: 60px"/>
                    </template>
                </el-table-column>

                <el-table-column prop="name" label="名称" width="70">
                </el-table-column>
                <el-table-column  label="价格" width="70">
                    <template  slot-scope="scope">
                        ￥{{scope.row.price}}
                    </template>
                </el-table-column>

                <el-table-column prop="supplier" label="卖家" width="70">
                </el-table-column>

            </el-table>


        </el-dialog>

        <el-dialog

                title="详情"
                :visible.sync="xiangqingdialogv"
                width="60%">
             <div style="margin: 20px;"></div>

                    <mavon-editor
                            :value="content"
                            ref="md"
                            :ishljs="true"
                            :subfield="false"
                            :defaultOpen="'preview'"
                            :toolbarsFlag="false"
                            :editable="false"
                            :scrollStyle="true"
                    />



        </el-dialog>


    </div>
</template>

<script>
    import axios from "axios";

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
                username: "",
                xiangqingdialogv: false,
                headerby: 'heade',
                content: "",
                goods:[]

            }},
        methods: {
            chakan(row){

                this.request.get("http://localhost:9090/dingdan/chakan?id="+row.dingdanId).then(res=>{
                    this.goods=res.data
                    this.dialogVisible=true
                }).catch(e=>{

                })

            },
            xiangqing(row){

                this.content=row.content
              this.xiangqingdialogv=true

            },
            imgAdd(pos,$file){
               let  $vm=this.$refs.md

                // 第一步.将图片上传到服务器.
                var formdata = new FormData();
                formdata.append('file', $file);
               console.log(formdata)
                axios({
                    url: 'http://localhost:9090/file/upload',
                    method: 'post',
                    data: formdata,
                    headers: { 'Content-Type': 'multipart/form-data' },
                }).then((url) => {
                    // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                    /**
                     * $vm 指为mavonEditor实例，可以通过如下两种方式获取
                     * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
                     * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
                     */
                    $vm.$img2Url(pos, url.data);
                })


            }


            ,
            handleSelectionChange(val){
                this.list=val
            },
            shanchu(row){
                this.$confirm("确定删除吗？","提示",{
                    type: "info"
                }).then(()=>{
                    this.request.get("http://localhost:9090/comment/shanchu?id="+row.id).then(response => {
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
                if (!this.user.hasOwnProperty('user')){
                    this.user.user=JSON.parse(localStorage.getItem("user")).name
                }
                this.request.post("http://localhost:9090/comment/zhengjia",this.user
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


                this.request.get("http://localhost:9090/comment/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

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