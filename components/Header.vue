<template>
     <div style="font-size: 12px ;display:flex">
        <div style="flex: 1;font-size: 18px">
            <span :class="coll" style="cursor: pointer" @click="collapse">
            </span>
            <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px" >

                <el-breadcrumb-item :to="{ path: '/' }" @click.native="itemo">首页</el-breadcrumb-item>
                <el-breadcrumb-item v-if="itemname !=''">{{itemname}}</el-breadcrumb-item>
            </el-breadcrumb>

        </div>



        <el-dropdown style="width: 100px;cursor: pointer ">
            <img :src="user.headurl" alt="" style="width: 30px;border-radius: 50%;position: relative ;top: 10px; right: 1px "/>
            <span style="margin: 5px; ">{{user.name}}</span>
            <i class="el-icon-arrow-down" ></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                   <router-link to="/person">个人信息</router-link>
                </el-dropdown-item>

                <el-dropdown-item  @click.native="tuichu">退出</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        props:["collapse","coll"],
        data(){
            return{

                itemname: localStorage.getItem("item"),

                user: JSON.parse(localStorage.getItem("user"))
            }
        },

        watch:{
            "$route"(){
                  this.itemname=localStorage.getItem("item")
            }
        },
        methods:{
            tuichu(){
              localStorage.removeItem("myenums")
              localStorage.removeItem("user")
              this.$router.push("/login")

            },

            itemo(){
                this.itemname=""
            },
            getheadurl(url){
                this.user.headurl=url
            }

        },
        created() {
              this.$bus.$on("gethead",this.getheadurl)
        }


    }
</script>

<style scoped>


</style>