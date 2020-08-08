<template>
  <div class="articles">
    <!-- 文章目录 -->
    <template v-if="menus.length !== 0">
        <menu-tree :menus="menus" @handleAnchorClick="handleAnchorClick"></menu-tree>
    </template>
    <div class="site-content animate">
        <!-- 正文 -->
        <main class="site-main">
            <article class="hentry">
                <!-- 文章头部 -->
                <header class="entry-header">
                    <!-- 标题输出 -->
                    <h1 class="entry-title">{{ blog.title }}</h1>
                    <hr>
                    <!-- <div class="breadcrumbs">
                        <div id="crumbs">最后更新时间：{{ blog.updateTime }}</div>
                    </div> -->
                    <div class="breadcrumbs">
                        <div>
                          <i class="iconfont icon-xiaobiao-"></i>
                          {{ blog.user.userName }}
                        </div>
                        <div>
                          <i class="iconfont icon-leimupinleifenleileibie"></i>
                          {{ blog.sort.sortTitle }}
                        </div>
                        <div>
                          <i class="iconfont icon-shijian00"></i>
                          {{ blog.updateTime }}
                        </div>
                        <div>
                          <i class="iconfont icon-yanjing"></i>
                          {{ blog.clickCount }}
                        </div>
                        <div>
                          <i class="iconfont icon-xin"></i>
                          {{ blog.clickCount }}
                        </div>
                    </div>
                    <div class="breadcrumbs">
                        <div>
                          <i class="iconfont icon-biaoqian"></i>
                          <router-link style="margin-right: 10px" to="/category/web" v-for="tag in blog.tags" :key="tag.id">
                            {{ tag.tagTitle }}
                          </router-link>
                        </div>
                    </div>
                </header>
                <!-- 正文输出 -->
                <div class="entry-content">
                    <VMdPreview :value="blog.contentMd" :lineIndex="lineIndex" @getMenus="getMenus" />
                </div>
                <!-- 文章底部 -->
                <footer class="post-footer">
                    <!-- 点赞 -->
                    <div class="post-like">
                        <i class="iconfont icon-xin" id="zan" ref="zan" @click="clickZan"></i>
                    </div>
                    <!-- 赞助按钮 -->
                    <div class="donate" @click="showDonate=!showDonate">
                        <span>赏</span>
                        <ul class="donate_inner" :class="{'show':showDonate}">
                            <li class="wedonate"><img :src="$store.state.app.webConfig.weixinPay"><p>微信</p></li>
                            <li class="alidonate"><img :src="$store.state.app.webConfig.aliPay"><p>支付宝</p></li>
                        </ul>
                    </div>
                </footer>
                <!--声明-->
                <div class="open-message">
                    <p>声明：巴学园博客|版权所有，如未注明|均为原创</p>
                    <p>转载：转载请注明原文链接 - <a :href="blogUrl">{{ blog.title }}</a></p>
                </div>
                <!--评论-->
                <!-- <div class="comments">
                    <comment v-for="item in comments" :key="item.comment.id" :comment="item.comment">
                        <template v-if="item.reply.length">
                            <comment v-for="reply in item.reply" :key="reply.id" :comment="reply"></comment>
                        </template>
                    </comment>
                </div> -->
            </article>
        </main>
    </div>
  </div>
</template>

<script>
  import { getBlogById } from '@/api/blog'
  import VMdPreview from '@/components/MyEditor/VMdPreview'
  import MenuTree from '@/components/MenuTree'
  import { formatDate } from '@/utils/webUtils'
  export default {
      name: 'articles',
      components: {
        VMdPreview,
        MenuTree
      },
      data(){
        return{
            blogId: '',
            showDonate: false,
            blog: {
                // user:{},
                // sort:{}
            },
            menus: [],
            lineIndex: '0'
        }
      },
      computed: {
        blogUrl: () => {
          return window.location.href
        }
      },
      mounted(){
      },
      created() {
        this.blogId = this.$route.params.id

        this.getBlog()
      },
      methods: {
        handleAnchorClick(val){
            const { lineIndex } = val;

            this.lineIndex = lineIndex
        },
        getMenus(val){
            this.menus = val
        },
        clickZan(){
            this.$refs.zan.style.cssText = 'color: #d82e16;';
        },
        getBlog(){
          var params = new URLSearchParams()
          params.append('id', this.blogId)
          getBlogById(params).then(response => {
            this.blog = response.data
            this.blog.createTime = this.dateFormat(this.blog.createTime)
            this.blog.updateTime = this.dateFormat(this.blog.updateTime)
          })
        },
        // 格式化日期
        dateFormat: function(value) {
            var date = new Date(value)
            if (date === undefined) {
                return ''
            }
            return formatDate(date, 'YYYY-MM-DD')
        } 
      }
  }
</script>
<style scoped lang="less">
    .site-content {
        position: relative;
        .site-main {
            padding: 80px 0 0 0;
        }
    }
    // #article-menus{
        
    // }
    article.hentry {
        .entry-header {
            margin-bottom: 30px;
            .entry-title {
                font-size: 23px;
                font-weight: 600;
                color: #737373;
                margin: 0.67em 0;
                &:before {
                    content: "#";
                    margin-right: 6px;
                    color: #d82e16;
                    font-size: 20px;
                    font-weight: 600;
                }
            }

            hr {
                height: 1px;
                border: 0;
                background: #EFEFEF;
                margin: 15px 0;
            }

            .breadcrumbs {
                font-size: 14px;
                color: #D2D2D2;
                text-decoration: none;
                margin-bottom: 10px;
                div{
                  display: inline-block;
                  margin-right: 50px;
                }
            }
        }

        // .entry-content {}

        footer.post-footer {
            width: 100%;
            padding: 20px 10px;
            margin-top: 30px;
            height: 65px;
            position: relative;
            i{
                font-size: 18px;
                margin-right: 5px;
            }
            .post-like {
                float: right;
                margin: 7px 0 0 20px;
            }

            .post-share {
                float: right;
                list-style: none;
                margin-right: 20px;
                ul{
                    list-style: none;
                    margin: 0;
                    padding: 0;
                    opacity: 0;
                    display: inline-block;
                    top: -3px;
                    position: relative;
                    -moz-transition: opacity .4s ease-out;
                    -o-transition: opacity .4s ease-out;
                    -webkit-transition: opacity .4s ease-out;
                    transition: opacity .4s ease-out;
                    li{
                        float: left;
                        margin-right: 20px;
                        display: list-item;
                        text-align: -webkit-match-parent;
                    }
                }
                .icon-forward-null{
                    margin-left: 10px;
                    cursor: pointer;
                    opacity: .6;
                    float: right;
                    -moz-transition: opacity .1s ease-out;
                    -o-transition: opacity .1s ease-out;
                    -webkit-transition: opacity .1s ease-out;
                    transition: opacity .1s ease-out;
                }
            }

            .donate {
                float: left;
                line-height: 36px;
                border-radius: 100%;
                -webkit-border-radius: 100%;
                -moz-border-radius: 100%;
                border: 1px solid #2B2B2B;
                &:hover{
                    border: 1px solid goldenrod;
                    span{
                        color: goldenrod;
                    }
                }
                span {
                    color: #2B2B2B;
                    padding: 10px;
                    position: relative;
                    cursor: pointer;
                }

                .donate_inner {
                    display: none;
                    margin: 0;
                    list-style: none;
                    position: absolute;
                    left: 80px;
                    top: -40px;
                    background: #FFF;
                    padding: 10px;
                    border: 1px solid #ddd;
                    box-shadow: 0 2px 6px rgba(0, 0, 0, .08);
                    border-radius: 3px;
                    &.show{
                        display: block;
                    }
                    li {
                        float: left;
                    }

                    img {
                        width: 100px;
                    }
                    p {
                        text-align: center;
                        font-size: 15px;
                        color: #D2D2D2;
                        line-height: 1rem;
                    }
                }

                .donate_inner:after, .donate_inner:before {
                    content: "";
                    position: absolute;
                    left: 0;
                    bottom: 45%;
                    margin-left: -8px;
                    border-top: 8px solid transparent;
                    border-bottom: 8px solid transparent;
                    border-right: 8px solid #fff;
                }

                .donate_inner:before {
                    left: -1px;
                    border-right: 8px solid #ddd;
                }

            }
        }
        .open-message {
            margin: 50px 0;
            position: relative;
            background: #2B2B2B;
            padding: 10px 30px;
            border-radius: 3px;
            font-size: 14px;
            color: #fff;
            &:after {
                content: "";
                border-left: 10px solid transparent;
                border-right: 10px solid transparent;
                border-bottom: 10px solid #2B2B2B;
                position: absolute;
                top: -8px;
                left: 48%;
            }
            p {
                margin: 10px 0;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            a {
                color: #A0DAD0;
                padding: 0 5px;
            }
        }
    }
</style>
