<template>
  <div class="placeOnFile">
    <div class="content">
      <el-row :gutter="100">
        <el-col :span="17">
          <!--文章归档-->
          <div class="time">
            <el-switch
              class="radio"
              v-model="reverse"
              active-text="正序"
              inactive-text="倒序"
              active-color="#000000"
              inactive-color="#13ce66"
            >
            </el-switch>
            <el-timeline :reverse="reverse">
              <el-timeline-item
                v-for="(activity, index) in activities"
                :key="index"
                color="#fff"
                hide-timestamp
                >
                <span
                  @click="changeTimestamp(activity.timestamp)"
                  :class="[activity.timestamp === selectTimestamp ? 'sortBoxSpan sortBoxSpanSelect' : 'sortBoxSpan']"
                >
                  {{activity.content}}
                </span>
              </el-timeline-item>
            </el-timeline>
          </div>
           <div class="article">
             <el-timeline :reverse="reverse">
                <el-timeline-item
                  v-for="(item, index) in blogList"
                  :key="index"
                  color="#fff" 
                  :timestamp="item.createTime" 
                  placement="top"
                >
                  <el-card shadow="always">
                    <router-link slot="header" :to="`/article/${item.id}`">
                      <h4 class="itemTitle">{{item.title}}</h4>
                    </router-link>
                    <el-tag class="elTag" v-if="item.isOriginal" type="danger">原创</el-tag>
                    <el-tag class="elTag" v-else type="info">转载</el-tag>

                    <el-tag class="elTag">{{item.user.username}}</el-tag>

                    <el-tag class="elTag" type="success">{{item.sort.sortTitle}}</el-tag>

                  </el-card>
                </el-timeline-item>
              </el-timeline>
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
  import { getFirstBlogGoNowMonth, getBlogByMonth } from '@/api/blog'
  import { formatDate } from '@/utils/webUtils'
  export default {
    components: {
      Recommend,
      FeatureList,
      Friend
    },
    data() {
      return {
        reverse: true,
        activities: [],
        blogList: [],
        selectTimestamp: ''
      }
    },
    watch: {
      'selectTimestamp'() {
        this.getBlogList()
      }
    },
    created(){
      this.getFirstBlogGoNowMonth()
    },
    methods: {
      getBlogList(){
        var params = new URLSearchParams()
        params.append('month', this.selectTimestamp)
        getBlogByMonth(params).then(response => {
          this.blogList = response.data.map(x => {
            x.createTime = this.dateFormat(x.createTime)
            x.updateTime = this.dateFormat(x.updateTime)
            return x
          })
        })
      },
      changeTimestamp(val){
        this.selectTimestamp = val
      },
      getFirstBlogGoNowMonth(){
        getFirstBlogGoNowMonth().then(response => {
          this.activities = response.data.map(x => {
            var content = x.substring(0,7)
            content = content.replace('-','年')
            content = content.concat('月')
            const item = {
              content,
              timestamp: x
            }
            return item
          })
          this.selectTimestamp = response.data[response.data.length-1]
        })
      },
      // 格式化日期
      dateFormat: function(value) {
        var date = new Date(value)
        if (date === undefined) {
            return ''
        }
        return formatDate(date, 'YYYY-MM-DD hh:mm:ss')
      }
    }
  }
</script>

<style scoped lang="less">
  .placeOnFile{
    background-color: #e2e2e2;
    .content{
      position: relative;
      width: 1200px;
      margin: 80px auto 0px;
      padding: 20px;
      .time{
        float: left;
        height: 600px;
        width: 25%;
        overflow: scroll;
        overflow-x: hidden;
        overflow-y: auto;
        padding-top: 20px;
        .radio{
          margin-bottom:20px;
        }
      }
      .article{
        float: left;
        height: 600px;
        overflow: scroll;
        overflow-x: hidden;
        overflow-y: auto;
        padding-top: 20px;
        margin-left: 20px;
        width: 70%;
      }
    }
  }
  .sortBoxSpan {
    cursor: pointer;
  }
  .sortBoxSpan:hover {
    color: #ff6d6d;
  }
  .sortBoxSpanSelect {
    color: #ff6d6d;
  }
  .itemTitle {
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }
  .itemTitle:hover {
    color: #ff6d6d;
  }
  .elTag {
    margin-right: 10px;
    cursor: pointer;
  }
</style>