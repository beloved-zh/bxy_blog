<template>
  <div class="sort">
    <div class="content">
      <el-row :gutter="100">
        <el-col :span="17">
          <!--文章分类-->
          <div class="time">
            <el-timeline>
              <el-timeline-item
                v-for="(item, index) in sortList"
                :key="index"
                color="#fff"
              >
                <span
                  @click="changeSortid(item.id)"
                  :class="[item.id == selectSortid ? 'sortBoxSpan sortBoxSpanSelect' : 'sortBoxSpan']"
                >
                  {{item.sortTitle}}
                </span>
              </el-timeline-item>
            </el-timeline>
          </div>
           <div class="article">
             <el-timeline :reverse="true">
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
  import { findAllSort } from '@/api/sort'
  import { getBlogBySortId } from '@/api/blog'
  import { formatDate } from '@/utils/webUtils'
  export default {
    components: {
      Recommend,
      FeatureList,
      Friend
    },
    data() {
      return {
        selectSortid: '',
        blogList: [],
        sortList: []
      };
    },
    watch: {
      'selectSortid'() {
        this.getBlogList()
      }
    },
    created() {
      this.findAllSort()
    },
    methods: {
      getBlogList(){
        var params = new URLSearchParams()
        params.append('sortId', this.selectSortid)
        getBlogBySortId(params).then(response => {
          this.blogList = response.data.map(x => {
            x.createTime = this.dateFormat(x.createTime)
            x.updateTime = this.dateFormat(x.updateTime)
            return x
          })
        })
      },
      changeSortid(id){
        this.selectSortid = id
      },
      findAllSort(){
        findAllSort().then(response => {
          this.sortList = response.data
          this.selectSortid = response.data[0].id
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
  .sort{
    background-color: #e2e2e2;
    .content{
      position: relative;
      width: 1200px;
      margin: 80px auto 0px;
      padding: 20px;
      .time{
        float: left;
        height: 600px;
        width: 20%;
        padding-top: 10px;
        overflow: scroll;
        overflow-x: hidden;
        overflow-y: auto;
      }
      .article{
        float: left;
        height: 600px;
        overflow: scroll;
        overflow-x: hidden;
        overflow-y: auto;
        margin-left: 20px;
        width: 75%;
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