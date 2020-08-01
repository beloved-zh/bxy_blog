<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />

    <panel-group :panel-data="panelData" @btnClick="btnClick" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <CalendarChart v-if="showCalendar" :range="contributeDate" :data="blogContributeCount" />
      <el-row>
        <el-col :span="24" style="text-align: center;margin-bottom: 10px;">
          少
          <div class="CalendarChart" style="background-color: #ebedf0;" />
          <div class="CalendarChart" style="background-color: #9be9a8;" />
          <div class="CalendarChart" style="background-color: #40c463;" />
          <div class="CalendarChart" style="background-color: #30a14e;" />
          <div class="CalendarChart" style="background-color: #216e39;" />
          多
        </el-col>
      </el-row>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart
            v-if="showPieBlogSortChart"
            name="文章分类所占条目数"
            :title="blogSortNameList"
            :value="blogCountByBlogSort"
            @clickPie="clickBlogSortPie"
          />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart
            v-if="showPieBlogTagChart"
            name="文章标签所占条目数"
            :title="blogTagNameList"
            :value="blogCountByTag"
            @clickPie="clickBlogTagPie"
          />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart
            v-if="showPieBlogLevelChart"
            name="推荐等级所占条目数"
            :title="blogLevelNameList"
            :value="blogCountByLevel"
            @clickPie="clickBlogLevelPie"
          />
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

  </div>
</template>

<script>
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import PieChart from './components/PieChart'
import CalendarChart from './components/CalendarChart'

import { init, getBlogCountByBlogSort, getBlogCountByTag, getBlogCountByLevel, getBlogContributeCount } from '@/api/index'

export default {
  name: 'DashboardAdmin',
  components: {
    GithubCorner,
    PanelGroup,
    LineChart,
    PieChart,
    CalendarChart
  },
  data() {
    return {
      lineChartData: {
        date: ['07-24', '07-25', '07-26', '07-27', '07-28', '07-29', '07-30'],
        uv: [100, 120, 161, 134, 105, 160, 165],
        pv: [120, 82, 91, 154, 162, 140, 145]
      },
      panelData: {},
      blogCountByBlogSort: [],
      blogSortNameList: [],
      showPieBlogSortChart: false, // 确保每次请求数据 数据发生改变能够重新渲染，避免空白
      blogCountByTag: [],
      blogTagNameList: [],
      showPieBlogTagChart: false,
      blogCountByLevel: [],
      blogLevelNameList: [],
      showPieBlogLevelChart: false,
      contributeDate: [],
      blogContributeCount: [],
      showCalendar: false
    }
  },
  created() {
    this.init()
    this.getBlogCountByBlogSort()
    this.getBlogCountByTag()
    this.getBlogCountByLevel()
    this.getBlogContributeCount()
  },
  methods: {
    getBlogContributeCount() {
      getBlogContributeCount().then(response => {
        const { contributeDate, blogContributeCount } = response.data

        this.contributeDate = contributeDate

        this.blogContributeCount = blogContributeCount

        this.showCalendar = true
      })
    },
    clickBlogLevelPie(index) {
      var blogLevel = this.blogCountByLevel[index]
      this.$router.push({
        path: '/blog',
        query: { blogLevel: blogLevel }
      })
    },
    getBlogCountByLevel() {
      getBlogCountByLevel().then(response => {
        this.blogCountByLevel = response.data

        this.blogLevelNameList = response.data.map(x => { return x.name })

        this.showPieBlogLevelChart = true
      })
    },
    clickBlogTagPie(index) {
      var blogTag = this.blogCountByTag[index]
      this.$router.push({
        path: '/blog',
        query: { blogTag: blogTag }
      })
    },
    getBlogCountByTag() {
      getBlogCountByTag().then(response => {
        this.blogCountByTag = response.data

        this.blogTagNameList = response.data.map(x => { return x.name })

        this.showPieBlogTagChart = true
      })
    },
    clickBlogSortPie(index) {
      var blogSort = this.blogCountByBlogSort[index]
      this.$router.push({
        path: '/blog',
        query: { blogSort: blogSort }
      })
    },
    getBlogCountByBlogSort() {
      getBlogCountByBlogSort().then(response => {
        this.blogCountByBlogSort = response.data

        this.blogSortNameList = response.data.map(x => { return x.name })

        this.showPieBlogSortChart = true
      })
    },
    init() {
      init().then(response => {
        this.panelData = response.data
      })
    },
    btnClick(url) {
      this.$router.push({ path: url })
    }
  }
}
</script>

<style lang="scss" scoped>

.CalendarChart {
  width: 15px;
  height: 15px;
  background-color: #ebedf0;
  margin: 0 1px;
  display: inline-block
}

.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
