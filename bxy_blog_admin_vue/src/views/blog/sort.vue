<template>
  <div class="sort">
    <el-row type="flex" :gutter="10" justify="start">
      <el-col :span="5" :xs="12">
        <el-input
          v-model="keyword"
          placeholder="请输入分类名"
          clearable
          type="text"
        />
      </el-col>
      <el-col :span="2" :xs="6">
        <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getSortList()">查找</el-button>
      </el-col>
      <el-col :span="2" :xs="6">
        <el-button type="primary" style="margin-bottom:30px;" @click="addSort()">添加分类</el-button>
      </el-col>
      <el-col :span="2" :xs="6">
        <el-button v-if="isButton" type="danger" style="margin-bottom:30px;" icon="el-icon-delete" @click="deleteSortList()">删除所选数据</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      stripe
      @selection-change="monitorCheckbox"
    >
      <el-table-column
        align="center"
        type="selection"
      />
      <el-table-column label="序号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="sortTitle"
        label="分类名"
      />
      <el-table-column
        align="center"
        prop="content"
        label="分类介绍"
      />
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        label="创建时间"
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
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
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form :ref="form" :model="form" hide-required-asterisk label-width="150px" :rules="rules">
        <el-form-item label="分类名称" prop="sortTitle">
          <el-input
            v-model="form.sortTitle"
            placeholder="请输入分类名称"
            clearable
          />
        </el-form-item>
        <el-form-item label="分类介绍" prop="content">
          <el-input
            v-model="form.content"
            placeholder="请输入分类介绍"
            clearable
          />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-slider v-model="form.sort" :min="0" :max="sort" show-stops show-tooltip />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="submitForm(form)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getSort, addSort, updateSort, deleteSort, deleteSortList, findAllSort } from '@/api/sort'
import { formatDate } from '@/utils/webUtils'
export default {
  data() {
    return {
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      keyword: '',
      tableData: [],
      title: '',
      dialogVisible: false,
      isButton: false,
      ids: [],
      sortList: [],
      form: {
        sortTitle: '',
        content: '',
        sort: 0
      },
      rules: { // 表单校验规则
        sortTitle: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    // 计算属性 根据菜单节点计算排序选择的最大长度
    // eslint-disable-next-line vue/no-dupe-keys
    sort: function() {
      return this.sortList.length
    }
  },
  created() {
    this.getSortList()
  },
  methods: {
    findAll() {
      findAllSort().then(response => {
        this.sortList = response.data
      })
    },
    deleteSortList() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('ids', this.ids)
        deleteSortList(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.ids = []
          this.keyword = ''
          this.getSortList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 监控多选 如果有选择显示批量删除按钮
    monitorCheckbox(val) {
      this.ids = []
      console.log(val)
      if (val.length > 0) {
        this.isButton = true
      } else {
        this.isButton = false
      }
      for (const sort of val) {
        this.ids.push(sort.id)
      }
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteSort(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getSortList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleEdit(row) {
      this.form = {
        id: row.id,
        sortTitle: row.sortTitle,
        content: row.content,
        sort: row.sort
      }
      this.title = '修改' + row.sortTitle
      this.findAll()
      this.dialogVisible = true
    },
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        // 校验规则
        if (valid) {
          var params = new URLSearchParams(this.form)
          if (this.title === '添加分类') {
            addSort(params).then(response => {
              const { data } = response
              this.initForm()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getSortList()
            })
          } else {
            updateSort(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.initForm()
              this.dialogVisible = false
              this.getSortList()
            })
          }
        } else {
          return false
        }
      })
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
    addSort() {
      this.title = '添加分类'
      this.initForm()
      this.findAll()
      this.dialogVisible = true
    },
    initForm() {
      this.form = {
        sortTitle: '',
        content: '',
        sort: 0
      }
    },
    /**
     * 当前页发生变化 分页
     * val : 当前页
     */
    handleCurrentChange(val) {
      this.currentPage = val
      this.getSortList()
    },
    getSortList() {
      const params = {}
      params.keyword = this.keyword
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getSort(params).then(response => {
        const { data } = response
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
  .sort{
    margin: 10px 15px 0;
  }
</style>
