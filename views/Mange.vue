<template>
  <div style="height: 100% ">
            <el-container style="height: 100%; ">
                <el-aside :width="side+ 'px'"  style="background-color: rgb(238, 241, 246);height: 100%; box-shadow:2px 0 6px rgba(0 21 41 0.35)">
                     <Aside :isshow="isshow"  :iscollapse="iscollapse"></Aside>
               </el-aside>
            <el-container>
                <el-header  >
                      <Header :collapse="collapse" :coll="coll"></Header>
                </el-header>
                <el-main>
                    <router-view> </router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import Aside from "../components/Aside";
import Header from "../components/Header";


    export default {
        data() {
            return {
                side:200,
                iscollapse: false,
                isshow: true,
                coll:"el-icon-s-fold",
            }
        },
        components: {
          Aside,
          Header,
        },
        methods:{
            collapse(){
                this.iscollapse=!this.iscollapse
                if (this.iscollapse)
                {this.side=64
                    this.coll="el-icon-s-unfold"
                    this.isshow=false
                }
                else {
                    this.side = 200
                    this.coll="el-icon-s-fold"
                    this.isshow=true
                }
            },
           },
    }
</script>

<style >
    .el-header {
        background-color: antiquewhite;
        color: #333;
        line-height: 60px;
    }

    .el-aside {
        color: #333;
    }
</style>