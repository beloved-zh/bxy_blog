<template>
  <div class="aboutMe">
    <div class="content">
      <el-row :gutter="100">
        <el-col :span="17">
          <div class="VMdPreview entry-content">
            <VMdPreview :value="contentMd"/>
          </div>
        </el-col>
        <el-col :span="7">
          <el-row>
            <el-col :span="24">
              <FeatureList />
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <!-- 联系方式 -->
              <Recommend />
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <!-- 友情链接 -->
              <Friend />
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import FeatureList from '@/components/FeatureList'
  import Recommend from '@/components/Recommend'
  import Friend from '@/components/Friend'
  import VMdPreview from '@/components/MyEditor/VMdPreview'
  import { getAboutMe } from '@/api/aboutMe'
  export default {
    components: {
      Recommend,
      FeatureList,
      Friend,
      VMdPreview
    },
    data() {
      return {
        contentMd: '',
        contentHtml: ''
      };
    },
    watch: {
      
    },
    created() {
      this.getAboutMe()
    },
    methods: {
      getAboutMe() {
        getAboutMe().then(response => {
          const { contentMd, contentHtml } = response.data
          this.contentMd = contentMd
          this.contentHtml = contentHtml
        })
      }
    }
  }
</script>

<style scoped lang="less">
  .aboutMe{
    background-color: #f6f6f6;
    .content{
      position: relative;
      width: 1200px;
      margin: 80px auto 0px;
      padding: 20px;
    }
  }
  .VMdPreview{
    // background: #fff;
  }


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