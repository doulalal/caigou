<template >




    <div >

        <div>
            <el-card>
                实时概况
            </el-card>

        </div>

        <el-row  :gutter="6" style="margin-bottom: 100px">
            <el-col :span="6"   >
                <el-card >
                    <div v-if="data.id===1" >
                        <div  style="font-size: 23px">
                            供应商数
                        </div>
                        <div style="text-align: center">
                            <el-tag style="font-size: 20px">
                                {{data.supplier}}
                            </el-tag>
                        </div>
                    </div>
                    <div v-if="data.id!==1" >
                        <div  style="font-size: 23px">
                            已发货订单
                        </div>
                        <div style="text-align: center">
                            <el-tag style="font-size: 20px">
                                {{data.fahuo}}
                            </el-tag>
                        </div>
                    </div>
                </el-card>

            </el-col>
            <el-col :span="6">
                <el-card >
                    <div v-if="data.id===1" >
                        <div  style="font-size: 23px">
                            档口总数
                        </div>
                        <div style="text-align: center">
                            <el-tag style="font-size: 20px">
                                {{data.supplier}}
                            </el-tag>
                        </div>
                    </div>
                    <div v-if="data.id!==1" >
                        <div  style="font-size: 23px">
                            已评价
                        </div>
                        <div style="text-align: center">
                            <el-tag style="font-size: 20px">
                                {{data.pingjia}}
                            </el-tag>
                        </div>
                    </div>
                </el-card>

            </el-col>

            <el-col :span="6">
                <el-card >
                    <div style="font-size: 23px">
                        已付款订单
                    </div>
                    <div style="text-align: center">
                        <el-tag style="font-size: 20px">
                            {{data.fukuan}}
                        </el-tag>
                    </div>
                </el-card>

            </el-col>
            <el-col :span="6">

                <el-card >
                    <div style="font-size: 23px">
                        待付款订单
                    </div>
                    <div style="text-align: center">
                        <el-tag style="font-size: 20px">
                            {{data.daifu}}
                        </el-tag>
                    </div>
                </el-card>

            </el-col>


        </el-row>



        <div class="block" style="text-align: right">
            <span class="demonstration"></span>
            <el-date-picker
                    @change="change"
                    v-model="value"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
            </el-date-picker>
        </div>

        <el-row >
            <el-col :span="12">
                <div id="main" style="width: 1100px; height: 400px" >
                </div>
            </el-col>
        </el-row>

        <div class="block" style="text-align: right">
            <span class="demonstration"></span>
            <el-date-picker
                    @change="change1"
                    v-model="value1"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
            </el-date-picker>
        </div>

        <el-row >
            <el-col :span="12">
                <div id="yingyee" style="width: 1100px; height: 400px" >
                </div>
            </el-col>
        </el-row>




        <el-col :span="12">
            <div class="block" style="text-align: right">
                <span class="demonstration"></span>
                <el-date-picker
                        v-model="value2"
                        type="month"
                        @change="change2"
                        placeholder="选择月"

                >
                </el-date-picker>
            </div>

            <div id="yuan" style="width: 500px; height: 400px" >
            </div>
        </el-col>
    </div>
</template>
<!--new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(),-->
<script>
    import * as echarts from 'echarts';
    export default {
        data(){
            return{
                data:[],
                value:"",
                value1:"",
                value2:"",
                start: new Date(new Date()-6*24*60*60*1000),
                end: new  Date(),
                start1: new Date(new Date()-6*24*60*60*1000),
                end1: new  Date(),
                start2: new  Date(),

                feilei:[],
                dingdan:[],
                yingyee:[],

            }

        },
        computed: {
            riqi(){
                var arr=new Array()
                var l=(this.end.getTime()-this.start.getTime())/6
                for (var i=0;i<7;i++)
                {
                    let y = new Date(this.start.getTime()+l*i).getFullYear()
                    let m = new Date(this.start.getTime()+l*i).getMonth() + 1
                    m = m < 10 ? ('0' + m) : m
                    let d = new Date(this.start.getTime()+l*i).getDate()
                    d = d < 10 ? ('0' + d) : d
                    var a= y + '-' + m + '-' + d
                    arr.push(a)
                }


                return arr

            },
            riqi1(){
                var arr=new Array()
                var l=(this.end1.getTime()-this.start1.getTime())/6
                for (var i=0;i<7;i++)
                {
                    let y = new Date(this.start1.getTime()+l*i).getFullYear()
                    let m = new Date(this.start1.getTime()+l*i).getMonth() + 1
                    m = m < 10 ? ('0' + m) : m
                    let d = new Date(this.start1.getTime()+l*i).getDate()
                    d = d < 10 ? ('0' + d) : d
                    var a= y + '-' + m + '-' + d
                    arr.push(a)
                }


                return arr

            },
            riqi2(){
                let y = this.start2.getFullYear()
                let m = this.start2.getMonth() + 1
                m = m < 10 ? ('0' + m) : m
                let d = this.start2.getDate()
                d = d < 10 ? ('0' + d) : d
                return  y + '-' + m + '-' + d

            }
        },
        methods:{
            xiugai(){
                var chartDom = document.getElementById('main');
                var myChart = echarts.init(chartDom);
                var option;
                option = {
                    title: {
                        text: "订单数",
                        subtext: "趋势图",
                        left: "center"

                    },
                    xAxis: {
                        type: 'category',
                        data: this.riqi
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            data: this.dingdan,
                            type: 'line'
                        },
                        {
                            data: this.dingdan,
                            type: "bar"

                        }
                    ]
                };



                var chartDom2 = document.getElementById('yuan');
                var myChart2 = echarts.init(chartDom2);
                var option2;
                option2 = {
                    title: {
                        text: '商品分类销售占比',
                        subtext: '比例图',
                        left: 'center'
                    },

                    //鼠标放到饼上的显示
                    tooltip: {
                        trigger: 'item',
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                    },
                    series: [
                        {
                            name: '类目',
                            type: 'pie',
                            radius: '50%',
                            data: this.feilei,
                            //默认饼上显示
                            label: {
                                normal: {
                                    show: true,
                                    position: "inner",
                                    formatter: "{d}%",
                                    rich: {
                                        text: {
                                            align: "center",
                                            verticalAlign: "middle",
                                            padding: 8,
                                            fontSize: 12,
                                        }}}},
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };


                var chartDom3 = document.getElementById('yingyee');
                var myChart3 = echarts.init(chartDom3);
                var option3;
                option3 = {
                    title: {
                        text: "营业额",
                        subtext: "趋势图",
                        left: "center"

                    },
                    xAxis: {
                        type: 'category',
                        data: this.riqi1
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            data: this.yingyee,
                            type: 'line'
                        },
                        {
                            data: this.yingyee,
                            type: "bar"

                        }
                    ]
                };


                myChart.setOption(option,true)
                myChart2.setOption(option2,true)
                myChart3.setOption(option3,true)

            },
            change(value){
                this.start=value[0]
                this.end=value[1]
                this.request.post("http://localhost:9090/home/dingdan",this.riqi).then(res=>{
                    this.dingdan=res.data
                    this.xiugai()
                }).catch(error=>{

                })

            },
            change1(value){
                this.start1 =value[0]
                this.end1 =value[1]
                this.request.post("http://localhost:9090/home/yingyee",this.riqi1).then(res=>{
                    this.yingyee=res.data
                    this.xiugai()
                }).catch(error=>{

                })
            },
            change2(value){
                this.start2=value
                this.request.get("http://localhost:9090/home/leimu?leimu="+this.riqi2).then(res=>{
                    this.feilei=res.data
                    this.xiugai()
                }).catch(error=>{

                })


            }
        },

        mounted() {

            this.request.get("http://localhost:9090/home/getall").then(res=>{
                this.data=res.data
            }).catch(error=>{

            })
            this.request.post("http://localhost:9090/home/dingdan",this.riqi).then(res=>{
                this.dingdan=res.data

            }).catch(error=>{

            })
            this.request.post("http://localhost:9090/home/yingyee",this.riqi1).then(res=>{
                this.yingyee=res.data

            }).catch(error=>{

            })
            this.request.get("http://localhost:9090/home/leimu?leimu="+this.riqi2).then(res=>{
                this.feilei=res.data
                this.xiugai()
            }).catch(error=>{

            })

        }
    }
</script>

<style scoped>

</style>
