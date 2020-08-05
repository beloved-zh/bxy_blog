<template>
  <div class="app-container">
    <el-row type="flex" :gutter="10" justify="start">
      <el-col :span="4">
        <el-input
          v-model="keyword"
          placeholder="请输入友链名"
          clearable
          type="text"
        />
      </el-col>
      <el-col :span="3">
        <el-select v-model="linkStatus" clearable placeholder="友链状态">
          <el-option label="上架" :value="true" />
          <el-option label="申请" :value="false" />
        </el-select>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getLinks()">查找</el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" style="margin-bottom:30px;" @click="addLink()">添加友链</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      row-key="id"
      style="width: 100%"
    >
      <el-table-column
        align="center"
        label="序号"
        type="index"
      />
      <el-table-column
        align="center"
        prop="linkName"
        label="友链名"
      />
      <el-table-column
        align="center"
        prop="summary"
        label="友链简介"
      />
      <el-table-column
        align="center"
        prop="linkUrl"
        label="友链URL"
      >
        <template slot-scope="scope">
          <a target="_blank" :href="scope.row.linkUrl">
            {{ scope.row.linkUrl }}
          </a>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="状态"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.linkStatus" type="success">上架</el-tag>
          <el-tag v-else type="danger">申请</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        show-overflow-tooltip
        label="创建时间"
        width="150"
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="updateLink(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteLink(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
    <!-- 添加或修改 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form :ref="form" hide-required-asterisk :model="form" :rules="rules">
        <el-form-item label="网站名" label-width="120px" prop="linkName">
          <el-input v-model="form.linkName" />
        </el-form-item>
        <el-form-item label="网站URL" label-width="120px" prop="linkUrl">
          <el-input v-model="form.linkUrl" />
        </el-form-item>
        <el-form-item label="简介" label-width="120px" prop="summary">
          <el-input v-model="form.summary" />
        </el-form-item>
        <el-form-item label="状态" label-width="120px" prop="linkStatus">
          <el-select v-model="form.linkStatus" clearable placeholder="友链状态">
            <el-option label="上架" :value="true" />
            <el-option label="申请" :value="false" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序" label-width="120px" prop="sort">
          <el-slider
            v-model="form.sort"
            :min="0"
            :max="total"
          />
        </el-form-item>
        <el-form-item style="text-align: right">
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="submitForm(form)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getLinks, addLink, deleteLink, updateLink } from '@/api/link'
import { formatDate } from '@/utils/webUtils'
export default {
  data() {
    return {
      keyword: '',
      linkStatus: '',
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      tableData: [],
      dialogVisible: false,
      title: '',
      form: {
        linkName: '',
        linkUrl: '',
        summary: '',
        linkStatus: true,
        sort: 0
      },
      rules: {
        linkName: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        linkUrl: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        summary: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        linkStatus: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        sort: [
          { required: true, message: '请选择', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getLinks()
  },
  methods: {
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        // 校验规则
        if (valid) {
          var params = new URLSearchParams(this.form)
          if (this.title === '添加友链') {
            addLink(params).then(response => {
              const { data } = response
              this.initForm()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getLinks()
            })
          } else {
            updateLink(params).then(response => {
              const { data } = response
              this.initForm()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getLinks()
            })
          }
        } else {
          return false
        }
      })
    },
    updateLink(row) {
      this.title = '修改' + row.linkName
      this.form = {
        id: row.id,
        linkName: row.linkName,
        linkUrl: row.linkUrl,
        summary: row.summary,
        linkStatus: row.linkStatus,
        sort: row.sort
      }
      this.dialogVisible = true
    },
    deleteLink(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteLink(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getLinks()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    addLink() {
      this.initForm()
      this.title = '添加友链'
      this.dialogVisible = true
    },
    initForm() {
      this.form = {
        linkName: '',
        linkUrl: '',
        summary: '',
        linkStatus: true,
        sort: this.total
      }
    },
    // 关闭模态框
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.initForm()
          this.dialogVisible = false
          done()
        })
        .catch(_ => {})
    },
    // 处理分页
    handleCurrentChange(val) {
      this.currentPage = val
      this.getLinks()
    },
    getLinks() {
      const params = {}
      params.keyword = this.keyword
      params.linkStatus = this.linkStatus
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getLinks(params).then(response => {
        const { data } = response
        this.currentPage = data.current
        this.pageSize = data.size
        this.total = data.total
        this.pageCount = data.pages
        this.tableData = data.records
      })
    },
    // 格式化日期
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
  .app-container {
    margin: 10px 15px 0;
  }
</style>
