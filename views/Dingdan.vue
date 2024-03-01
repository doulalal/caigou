<template>
    <div>

        <div style="padding: 10px">
            <el-input placeholder="请输入名称" v-model="username" style="width: 200px ;margin-right: 5px;" suffix-icon="el-icon-search"></el-input>
            <el-button type="primary" @click="serachbyname" >搜索</el-button>
        </div>
        <div style="padding: 10px">
            <el-button type="primary" icon="el-icon-top" @click="exportfile">导出</el-button>
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
                    <el-button v-if="scope.row.statue==='已付款' && id !== 1" type="success" icon="el-icon-remove-outline" @click="xiugai(scope.row)">发货</el-button>
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
                goods:[],
                id: JSON.parse(localStorage.getItem('user')).id




            }},
        methods: {
            xiugai(row){

                this.request.get("http://localhost:9090/dingdan/fahuo?id="+row.id
                ).then(response => {
                    if (response.data === true)
                    {
                        this.Myserach()
                        this.$message.success("发货成功")

                    }
                    else {
                        this.$message.error("发货失败")
                    }

                }).catch(error=>{

                    this.$message.error("发货失败")
                })

            },
            exportfile(){
                window.open("http://localhost:9090/dingdan/export")
            },

            chakan(row){

                this.request.get("http://localhost:9090/dingdan/chakan?id="+row.id).then(res=>{
                    this.goods=res.data

                    this.dialogVisible=true
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
                    console.log(response.data.data)
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
