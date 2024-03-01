<template>
    <div>


        <div style="padding: 10px">
        </div>
        <el-table :data="tableData" style="margin-bottom: 5px"  :header-cell-class-name="headerby"  >
            <el-table-column  label="图片" width="70">
                <template slot-scope="scope">
                    <img :src="scope.row.img" style="width: 60px;height: 60px"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="名称" width="70">
            </el-table-column>
            <el-table-column label="价格" width="70">
                <template slot-scope="scope">
                   ￥{{scope.row.price }}
                </template>
            </el-table-column>
            <el-table-column prop="num" label="数量" >
            </el-table-column>

            <el-table-column label="操作">
                <!--                            获得一行数据-->
                <template  slot-scope="scope">

                    <el-button type="success"  icon="el-icon-edit" @click="gaibian(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-remove-outline" @click="shanchu(scope.row)">删除</el-button>

                </template>
            </el-table-column>

        </el-table>

        <div style="margin-top: 20px ; ">
          <div  style=" display: inline; ">总价：</div>
           <div style=" display: inline; color: orangered ">￥{{totals}}  </div>
        </div>



       <div style="margin-top: 20px ;text-align: left;  ">
                    <el-button    style=" font-size: 10px; background-color: orangered;color:#cccccc; width: 70px ;"  @click="xiadan">
                        下单
                    </el-button>
       </div>

        <el-dialog
                title="商品修改"
                :visible.sync="dialogVisible"
                width="30%">
            <!--                            :before-close="handleClose">-->


            <span>


                               <el-form  label-width="80px" :model="user">
                                 <el-form-item label="数量">
                               <el-input   v-model.number="user.num"></el-input>
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
                tableData: [],
                headerby: 'heade',
                totals:0,
                data:{
                    name:"",
                    totalPrice: 0,
                    cars:[],
                    supplierId:0
                }



            }},
        methods: {

            xiadan(){

                this.data.name=this.tableData[0].name
                this.data.totalPrice=this.totals
                this.data.cars=this.tableData
                 this.data.supplierId=this.tableData[0].supplierId
                console.log(this.data)
                this.request.post("http://localhost:9090/dingdan/zhengjia",this.data).then(res=>{
                    console.log(res)
                    this.$message.success("下单成功")
                   this.Myserach()
                })

            },
            handleAvatarSuccess(rep){
                this.user.img=rep

            },
            shanchu(row){
                this.$confirm("确定删除吗？","提示",{
                    type: "info"
                }).then(()=>{
                    this.request.get("http://localhost:9090/car/shanchu?id="+row.id).then(response => {
                        if (response.data === true)
                        {
                            this.Myserach(),
                                this.$message.success("删除成功")

                        }
                        else {
                            this.$message.info("删除失败")
                        }
                    })
                }).catch(e=>{
               console.log(e)
                })



            },


            gaibian(row){
                this.dialogVisible=true
                this.user=row

            },
            queding(){
                console.log(this.page)
                console.log(this.size)
                this.request.post("http://localhost:9090/car/zhengjia",this.user
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
                this.request.get("http://localhost:9090/car/getcar").then(response => {

                    this.tableData=response.data
                    var num=0
                     response.data.forEach(data=>{
                         num+=data.price*data.num
                     })

                  this.totals=num.toFixed(2)
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