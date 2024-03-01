<template>
    <div>

        <el-menu
                :default-active="activeIndex2"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                router
        >
            <el-menu-item index="/front/purchase">商品列表</el-menu-item>
            <el-menu-item index="/front/goodcar" >购物车</el-menu-item>
            <el-menu-item index="/front/dingdan">订单管理</el-menu-item>
            <el-menu-item index="/front/person">个人信息</el-menu-item>
            <el-menu-item index="/login">退出</el-menu-item>
        </el-menu>


        <el-main>
            <router-view> </router-view>
        </el-main>




    </div>
</template>

<script>
    export default {

    }
</script>

<style scoped>

</style>