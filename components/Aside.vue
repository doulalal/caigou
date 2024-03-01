<template>
        <el-menu :default-openeds="['1', '3']" style="height: 100%;overflow-x: hidden"
                 background-color="rgb(48, 65, 86)"
                 text-color="#fff"
                 :collapse-transition="false"
                 :collapse="iscollapse"
                 router

        >
            <div style="height: 50px;line-height: 60px; text-align: center">
                <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 10px ;left:5px">
                <b style="color: white" v-show="isshow">后台管理系统</b>
            </div>

            <div v-for="item in enums" :key="item.id">
                  <div v-if="item.path">
                      <el-menu-item :index="item.path">
                          <template slot="title"><i :class="item.icon"></i>
                              <span slot="title">{{item.name}}</span>
                          </template>
                      </el-menu-item>
                  </div>
                 <div v-else>
                     <el-submenu index="">
                         <template slot="title"><i :class="item.icon"></i><span slot="title">{{item.name}}</span></template>
                         <div v-for="children in item.children" :key="children.id">
                             <div v-if="children.name != '个人信息'">
                             <el-menu-item :index="children.path">
                                 <template slot="title"><i :class="children.icon"></i>
                                     <span slot="title">{{children.name}}</span>
                                 </template>
                             </el-menu-item>
                             </div>
                         </div>
                     </el-submenu>
                 </div>
            </div>



<!--            <el-menu-item index="/home">-->
<!--                <template slot="title"><i class="el-icon-house"></i>-->
<!--                    <span slot="title">主页</span>-->
<!--                </template>-->
<!--            </el-menu-item>-->



<!--            el-submenu 有下一级-->
<!--            el-menu-item-group 里面全是同一级-->
<!--            el-menu-item 最低级-->
<!--            <el-submenu index="">-->
<!--                <template slot="title"><i class="el-icon-menu"></i><span slot="title">系统管理</span></template>-->

<!--                <el-menu-item index="/user">-->
<!--                    <template slot="title"><i class="el-icon-user"></i>-->
<!--                        <span slot="title">用户管理</span>-->
<!--                    </template>-->
<!--                </el-menu-item>-->

<!--                <el-menu-item index="/file">-->
<!--                    <template slot="title"><i class="el-icon-document"></i>-->
<!--                        <span slot="title">文件管理</span>-->
<!--                    </template>-->
<!--                </el-menu-item>-->

<!--                <el-menu-item index="/role">-->
<!--                    <template slot="title"><i class="el-icon-document"></i>-->
<!--                        <span slot="title">角色管理</span>-->
<!--                    </template>-->
<!--                </el-menu-item>-->

<!--                <el-menu-item index="/enum">-->
<!--                    <template slot="title"><i class="el-icon-menu"></i>-->
<!--                        <span slot="title">菜单管理</span>-->
<!--                    </template>-->
<!--                </el-menu-item>-->




<!--                <el-menu-item-group>-->
<!--                    <template slot="title">分组一</template>-->
<!--                    <el-menu-item index="/user">选项1</el-menu-item>-->
<!--                    <el-menu-item index="2-2">选项2</el-menu-item>-->
<!--                </el-menu-item-group>-->
<!--                <el-menu-item-group title="分组2">-->
<!--                    <el-menu-item index="2-3">选项3</el-menu-item>-->
<!--                </el-menu-item-group>-->

<!--            </el-submenu>-->
        </el-menu>
</template>

<script>
    export default {
        data(){
            return{
          enums: JSON.parse(localStorage.getItem("myenums"))
            }
        },
        props:["iscollapse","isshow"],
        methods:{



        },

        mounted() {


        }

    }
</script>

<style scoped>

</style>