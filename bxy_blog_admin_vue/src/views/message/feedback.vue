<template>
  <div class="app">
    <div class="head">
      <el-row type="flex" :gutter="10" justify="start">
        <el-col :span="5" :xs="12">
          <el-input
            v-model="keyword"
            placeholder="请输入标题"
            clearable
            type="text"
          />
        </el-col>
        <el-col :span="4" :xs="12">
          <el-select v-model="state" clearable placeholder="反馈状态">
            <el-option label="未解决" :value="0" />
            <el-option label="已解决" :value="1" />
            <el-option label="已拒绝" :value="2" />
          </el-select>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getFeedback">查找</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table
      :data="tableData"
      stripe
    >
      <el-table-column
        align="center"
        type="index"
        label="序号"
      />
      <el-table-column label="反馈人" align="center">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.user.username }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="title"
        label="标题"
      />
      <el-table-column label="反馈状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state === 0" type="warning">未解决</el-tag>
          <el-tag v-if="scope.row.state === 1" type="success">已解决</el-tag>
          <el-tag v-if="scope.row.state === 2" type="danger">已拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="反馈内容" align="center">
        <template slot-scope="scope">
          <el-popover
            v-if="scope.row.content"
            placement="top-start"
            width="400"
            trigger="hover"
            :content="scope.row.content"
          >
            <el-button slot="reference">{{ scope.row.content.substring(0, 10) }}</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="回复" align="center">
        <template slot-scope="scope">
          <el-popover
            v-if="scope.row.reply"
            placement="top-start"
            width="400"
            trigger="hover"
            :content="scope.row.reply"
          >
            <el-button slot="reference">{{ scope.row.reply.substring(0, 10) }}</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        label="创建时间"
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="updateFeedback(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteFeedback(scope.row)">删除</el-button>
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

    <el-dialog
      title="编辑"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form ref="feedback" :model="feedback" hide-required-asterisk :rules="rules" label-width="80px">
        <el-form-item label="反馈人" prop="userName">
          <el-input v-model="feedback.userName" disabled />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="feedback.title" disabled />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="feedback.content" type="textarea" disabled />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select v-model="feedback.state" clearable placeholder="反馈状态">
            <el-option label="未解决" :value="0" />
            <el-option label="已解决" :value="1" />
            <el-option label="已拒绝" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="回复" prop="reply">
          <el-input v-model="feedback.reply" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('feedback')">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getFeedback, deleteFeedback, updateFeedback } from '@/api/feedback'
import { formatDate } from '@/utils/webUtils'
export default {
  data() {
    return {
      keyword: '',
      state: '',
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      tableData: [],
      feedback: {},
      dialogVisible: false,
      rules: {
        state: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        reply: [
          { required: true, message: '请回复', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getFeedback()
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var params = new URLSearchParams(this.feedback)
          updateFeedback(params).then(response => {
            this.$message({
              type: 'success',
              message: response.message
            })
            this.getFeedback()
            this.dialogVisible = false
          })
        } else {
          return false
        }
      })
    },
    updateFeedback(row) {
      this.feedback = {
        id: row.id,
        title: row.title,
        content: row.content,
        state: row.state,
        reply: row.reply,
        userName: row.user.username
      }
      this.dialogVisible = true
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    deleteFeedback(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteFeedback(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getFeedback()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    getFeedback() {
      var params = new URLSearchParams()
      params.append('keyword', this.keyword)
      params.append('state', this.state)
      params.append('currentPage', this.currentPage)
      params.append('pageSize', this.pageSize)
      getFeedback(params).then(response => {
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
     * 当前页发生变化 分页
     * val : 当前页
     */
    handleCurrentChange(val) {
      this.currentPage = val
      this.getFeedback()
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
  .app{
      margin: 10px 15px 0;
  }

</style>
