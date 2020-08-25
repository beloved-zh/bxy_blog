<template>
  <div class="online">
    <el-table
      :data="tableData"
      stripe
    >
      <el-table-column
        align="center"
        type="index"
      />
      <el-table-column
        align="center"
        prop="username"
        label="用户名"
      />
      <el-table-column
        align="center"
        label="角色"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.authorities[0].roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="source"
        label="来源"
      />
      <el-table-column
        align="center"
        prop="lastLoginIp"
        label="Ip"
      />
      <el-table-column
        align="center"
        prop="lastLoginTime"
        sortable
        :formatter="dateFormat"
        label="登录时间"
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="danger" size="small" @click="Logout(scope.row)">强制退出</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      background
      layout="total, prev, pager, next, jumper"
      :page-size="pageSize"
      :page-count="pageCount"
      :current-page="currentPage"
      :total="total"
      style="margin-top:10px"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { formatDate } from '@/utils/webUtils'
import { getOnline, deleteOnline } from '@/api/online'
export default {
  components: {
  },
  data() {
    return {
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      tableData: []
    }
  },
  computed: {
    userName() {
      return this.$store.getters.name
    }
  },
  created() {
    this.getOnline()
  },
  methods: {
    /**
     * 当前页发生变化 分页
     * val : 当前页
     */
    handleCurrentChange(val) {
      this.currentPage = val
      this.getOnline()
    },
    Logout(row) {
      if (row.username === this.userName) {
        this.$message.error('不能强制退出自己哦~')
        return
      }
      var params = new URLSearchParams()
      params.append('id', row.id)
      params.append('username', row.username)
      params.append('source', row.source)
      deleteOnline(params).then(response => {
        this.$message({
          message: response.message,
          type: 'success'
        })
        this.getOnline()
      })
    },
    getOnline() {
      var params = new URLSearchParams()
      params.append('currentPage', this.currentPage)
      params.append('pageSize', this.pageSize)
      getOnline(params).then(response => {
        const { data } = response
        console.log(data)
        this.currentPage = data.current
        this.pageSize = data.size
        this.total = data.total
        this.pageCount = data.pages
        this.tableData = data.records
      })
    },
    /**
     * 格式化表格日期
     */
    dateFormat: function(row, column) {
      var date = new Date(row[column.property])
      if (date === undefined) {
        return ''
      }
      return formatDate(date, 'YYYY-MM-DD hh:mm:ss')
    }
  }
}
</script>

<style>
  .online{
    margin: 10px 15px 0;
  }
</style>
