import Vue from 'vue'

import VueRouter from "vue-router";

import Login from "@/views/Login";
import Mange from "@/views/Mange";
import Home from "@/views/Home";



const router= new VueRouter({




})
 export const setrouter=()=>{

     //页面不刷新路由不会重新加载，就是手动加载新路由也是在原来的router上面
    //重新定义matcher
     //因为router匹配原则是先用matcher中match匹配，所以重新new一个matcher就相当与重新定义一个router

          router.matcher=new VueRouter({

              routes:
                  [


                      {
                          path:"/login",
                          component: Login
                      },
                      {
                          path: "*",
                          name:404,
                          component: ()=>import('@/views/404')
                      },
                      {
                          path:"/front",
                          component: () =>import('@/views/yonghu/Home'),
                          children: [
                              {
                                  path:"goodcar",
                                  component: ()=>import("@/views/yonghu/GoodCar")

                              },
                              {
                                  path:"goodlist",
                                  component: ()=>import("@/views/yonghu/GoodList")

                              },
                              {
                                  path:"xiangqing",
                                  component: ()=>import("@/views/yonghu/Xiangqing")
                              },
                              {
                                  path:"dingdan",
                                  component: ()=>import("@/views/yonghu/Dingd")
                              }
                              ,
                              {
                                  path:"comment",
                                  component: ()=>import("@/views/yonghu/Comment")
                              },

                              {
                                  path:"purchase",
                                  component: ()=>import("@/views/yonghu/Purchaser")
                              }
                              ,

                              {
                                  path:"person",
                                  component: ()=>import("@/views/yonghu/Person")
                              }
                          ]

                      }

                  ],


          })

                  const  mange={
                redirect:"/login",
                path: "/",
                name:"",
                component: Mange,
                children:[]
            }
            let parse = JSON.parse(localStorage.getItem("myenums"))

            if(parse){
            parse.forEach(enums  =>{
                if (enums.path) {
                    const mychildren = {
                        path: enums.path.replace("/", ""),
                        name: enums.name,
                        component:()=> import('@/views/' + enums.component )
                    }

            mange.children.push(mychildren)
                }
          else  if(enums.children.length){
              enums.children.forEach(enums =>{
                  const mychildren = {
                      path: enums.path.replace("/", ""),
                      name: enums.name,
                      component:()=> import('@/views/' + enums.component )}
                  mange.children.push(mychildren)

              })
        }

    }
    )
            }
       router.addRoute(mange)


}

setrouter()

router.beforeEach((to,from,next)=>{
    localStorage.setItem("item",to.name)
    next()
})





export default router
