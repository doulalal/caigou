<template>
    <div>
      <el-card>
         <div style="display:  flex">
           <div style="width: 300px">
               <el-image :src="good.img" :preview-src-list="[good.img] " style="width: 100%"></el-image>

           </div>
            <div style="flex: 1px; padding-left: 50px">
               <div class="item1"><h3>{{good.name}} </h3> </div>
                <div class="item1">{{good.supplier}}</div>
                <div class="item1" style="color: orangered">￥{{good.price}}</div>
                <div class="item1" > <el-input-number v-model="num" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number> </div>
                <div class="item1" ><el-button  @click="addcar"  type="primary" style="background-color: red;color: white">加入购物车</el-button> </div>

            </div>



         </div>


      </el-card>
    </div>
</template>

<script>
    export default {

        data(){
            return{
                id: this.$route.query.id,
                good:{},
                num: 1
            }
        },
        methods: {
          addcar()
            {


               this.request.post("http://localhost:9090/car/addcar?id="+this.id+"&num= "+this.num+"").then(res=>{
                   if (res.code === 200) {
                       this.$message.success("添加成功")
                   }
               })


            }

        },
       mounted() {
           this.request.get("http://localhost:9090/good/getid?id="+this.id+"").then(res=>{
               this.good=res.data
           })

       }

    }
</script>

<style scoped>
.item1{
    padding: 5px;
    color: #666;
}



</style>