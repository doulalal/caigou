<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>

        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"  >


            <el-table-column prop="name" label="名称" width="70">
            </el-table-column>
            <el-table-column prop="no" label="订单号" width="140">
            </el-table-column>
            <el-table-column prop="time" label="下单时间" width="140">
            </el-table-column>
            <el-table-column prop="userName" label="买家" width="70">
            </el-table-column>


            <el-table-column  label="价格" width="70">
                <template  slot-scope="scope">
                    ￥{{scope.row.totalPrice}}

                </template>
            </el-table-column>
            <el-table-column prop="statue" label="状态" >
            </el-table-column>

            <el-table-column label="商品详情" >
                <template  slot-scope="scope">
                    <el-button type="primary" @click="chakan(scope.row)">
                        查看详情
                    </el-button>
                </template>
            </el-table-column>

            <el-table-column label="操作">
                <!--                            获得一行数据-->

                <template  slot-scope="scope">
                    <el-button type="success"  v-if="scope.row.statue === '已发货'" icon="el-icon-goods" @click="pinglun(scope.row)">收货评价</el-button>
                    <el-button type="success"  v-if="scope.row.statue === '待付款'" icon="el-icon-goods" @click="fukuan(scope.row)">付款</el-button>
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

                <el-table-column prop="supplier" label="买家" width="70">
                </el-table-column>


                <el-table-column prop="statue" label="状态" >
                </el-table-column>
            </el-table>


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
                tableData: [],
                totals: 0,
                page: 1,
                size: 10,
                username: "",
                headerby: 'heade',
                goods:[]




            }},
        methods: {
            pinglun(row){
                this.request.get("http://localhost:9090/dingdan/pingjia?id="+row.id).then(res=>{
                    if (res.data === true)
                    {
                        this.Myserach()
                    }

                    else {
                        this.$message.info("评论失败")
                    }
                }).catch(e=>{
                    this.$message.info("评论失败")

                })
                this.$router.push("/front/comment?id="+row.id)

            },
            fukuan(row){
                this.$confirm("确定付款吗？","提示",{
                    type: "success"
                }).then(()=>{
                    this.request.get("http://localhost:9090/dingdan/fukuan?id="+row.id).then(response => {
                        if (response.data === true)
                        {
                            this.Myserach(),
                                this.$message.success("付款成功")


                        }
                        else {
                            this.$message.info("付款失败")
                        }
                    })
                }).catch(e=>{
                    this.$message.info("付款失败")

                })


            },
            chakan(row){

                this.request.get("http://localhost:9090/dingdan/chakan?id="+row.id).then(res=>{
                    this.goods=res.data

                    this.dialogVisible=true
                }).catch(e=>{

                })

            },

            shanchu(row){
                this.$confirm("确定删除吗？","提示",{
                    type: "info"
                }).then(()=>{
                    this.request.get("http://localhost:9090/dingdan/shanchu?id="+row.id).then(response => {
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


            queding(){

                this.request.post("http://localhost:9090/dingdan/zhengjia",this.user
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
                this.request.get("http://localhost:9090/dingdan/test?page="+this.page+"&size="+this.size+"&username="+this.username).then(response => {

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