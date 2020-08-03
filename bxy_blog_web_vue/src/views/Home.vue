<template>
  <div class="home">
    <div class="content">
      <Carousel />
      <el-row :gutter="200">
        <el-col :span="16">
          <!--文章列表-->
          <main class="site-main">
            <section-title>
              <div style="display: flex;align-items: flex-end;">文章列表<small-ico></small-ico></div>
            </section-title>
            <template v-for="item in postList">
              <post :post="item" :key="item.id"></post>
            </template>
          </main>
          <div class="more">
        <div class="more-btn">More</div>
      </div>
        </el-col>
        <el-col :span="8">
          <el-row>
            <el-col :span="24">
              <div class="top-feature">
                <section-title>
                    <div style="display: flex;align-items: flex-end;">
                      特别推荐<small-ico></small-ico>
                      <span>
                        更多》
                      </span>
                    </div>
                </section-title>
                <div class="feature-content">
                    <div class="feature-item" v-for="item in features" :key="item.title">
                        <Feature :data="item"></Feature>
                    </div>
                </div>
            </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="top-feature">
                <section-title>
                    <div style="display: flex;align-items: flex-end;">
                      联系方式<small-ico></small-ico>
                    </div>
                </section-title>
                <div class="top-social">
                  <div v-for="item in socials" :key="item.id" :title="item.title"><a :href="item.href" target="_blank" :style="{'color':item.color}"><i class="iconfont" :class="item.icon"></i></a></div>
                </div>
            </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="top-feature">
                <section-title>
                    <div style="display: flex;align-items: flex-end;">
                      友情链接<small-ico></small-ico>
                    </div>
                </section-title>
                <div class="footer-item">
                    <div><a target="_blank" class="out-link" href="https://segmentfault.com/weekly?utm_source=sf-footer">每周精选</a></div>
                    <div><a target="_blank" class="out-link" href="https://www.jmjc.tech/">简明教程</a></div>
                    <div><a target="_blank" class="out-link" href="https://www.liaoxuefeng.com/">廖雪峰的官方网站</a></div>
                </div>
            </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      
    </div>
  </div>
</template>

<script>
  import Carousel from '@/components/Carousel'
  import SectionTitle from '@/components/SectionTitle'
  import SmallIco from '@/components/SmallIco'
  import Post from '@/components/Post'
  import Feature from '@/components/Feature'
  export default {
    name: 'Home',
    data() {
      return {
        socials: [
          {
            color:"#1AB6FF ",
            href:"http://wpa.qq.com/msgrd?v=3&uin=1224971566&site=qq&menu=yes",
            icon:"iconqq",
            id:1,
            title:"QQ"
          },
          {
            color:"#d81e06",
            href:"https://gitee.com/fengziy",
            icon:"icongitee",
            id:2,
            title:"Gitee"
          },
          {
            color:"",
            href:"https://github.com/fengziye",
            icon:"icongithub",
            id:3,
            title:"GitHub"
          },
          {
            color:"red",
            href:"https://blog.csdn.net/feng_zi_ye",
            icon:"iconcsdn",
            id:4,
            title:"CSDN"
          }
        ],
        features: [
          {
            id:1,
            img:"https://s1.ax1x.com/2020/05/14/YDfRnU.jpg",
            title:"Akina"
          },
          {
            id:2,
            img:"https://s1.ax1x.com/2020/05/14/YDf4AJ.jpg",
            title:"使用说明"
          },
          {
            id:3,
            img:"https://s1.ax1x.com/2020/05/14/YDfT91.jpg",
            title:"文章归档",
          }
        ],
        postList: [
          {
            banner:"https://s1.ax1x.com/2020/05/14/YDhagx.jpg",
            commentsCount:99,
            content:"",
            id:0,
            isHot:false,
            isTop:false,
            pubTime:505057707561,
            summary:"Lua 是一种轻量小巧的脚本语言，能为应用程序提供灵活的扩展和定制功能。",
            title:"测试文章",
            viewsCount:4045
          },
          {
            banner:"https://s1.ax1x.com/2020/05/14/YDhagx.jpg",
            commentsCount:99,
            content:"",
            id:0,
            isHot:true,
            isTop:true,
            pubTime:505057707561,
            summary:"Lua 是一种轻量小巧的脚本语言，能为应用程序提供灵活的扩展和定制功能。",
            title:"测试文章",
            viewsCount:4045
          },
          {
            banner:"https://s1.ax1x.com/2020/05/14/YDhagx.jpg",
            commentsCount:99,
            content:"",
            id:0,
            isHot:true,
            isTop:true,
            pubTime:505057707561,
            summary:"Lua 是一种轻量小巧的脚本语言，能为应用程序提供灵活的扩展和定制功能。",
            title:"测试文章",
            viewsCount:4045
          }
        ]
      }
    },
    components: {
      Carousel,
      SectionTitle,
      SmallIco,
      Post,
      Feature
    },
    computed: {
    },
    methods: {
    },
    mounted() {
    }
  }
</script>
<style scoped lang="less">
  .home{
    background-color: #e2e2e2;
    .content{
      position: relative;
      width: 1200px;
      margin: 80px auto 0px;
    }
  }
  .more{
    margin: 50px 0;
    .more-btn{
      width: 100px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      color: #ADADAD;
      border: 1px solid #ADADAD;
      border-radius: 20px;
      margin: 0 auto;
      cursor: pointer;
      &:hover{
        color: #8fd0cc;
        border: 1px dashed #8fd0cc;
      }
    }
  }
  .top-social {
            height: 32px;
            margin-top: 30px;
            margin-left: 10px;
            list-style: none;
            display: inline-block;
            font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
            div {
                float: left;
                margin-right: 10px;
                height: 32px;
                line-height: 32px;
                text-align: center;
                width: 32px;
                background: white;
                border-radius: 100%;
            }
        }
    .footer-item{
            flex: 1;
            & > div:not(:last-child){
                margin-bottom: 10px;
            }
            i{
                margin-right: 10px;
            }
        }
</style>
