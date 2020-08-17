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
                :timestamp="activity.timestamp">
                {{activity.content}}
              </el-timeline-item>
            </el-timeline>
          </div>
           <div class="article">
             <el-timeline>
              <el-timeline-item timestamp="2018/4/12" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/12 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/3" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/3 20:46</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2018/4/2" placement="top">
                <el-card shadow="hover">
                  <h4>更新 Github 模板</h4>
                  <p>王小虎 提交于 2018/4/2 20:46</p>
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
  export default {
    components: {
      Recommend,
      FeatureList,
      Friend
    },
    data() {
      return {
        reverse: true,
        activities: [{
          content: '活动按期开始',
          timestamp: '2018-04-15'
        }, {
          content: '通过审核',
          timestamp: '2018-04-13'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }, {
          content: '创建成功',
          timestamp: '2018-04-11'
        }]
      };
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
</style>