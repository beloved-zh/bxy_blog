<template>
  <div class="authority-role">
    <div class="head">
      <el-row type="flex" :gutter="10" justify="start">
        <el-col :span="5" :xs="12">
          <el-input
            v-model="keyword"
            placeholder="请输入角色名称"
            clearable
            type="text"
          />
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getRoleList()">查找</el-button>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" @click="openDialog()">添加角色</el-button>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button v-if="isButton" type="danger" style="margin-bottom:30px;" icon="el-icon-delete" @click="deleteRoleList()">删除所选数据</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table
      :data="tableData"
      stripe
      @selection-change="monitorCheckbox"
    >
      <el-table-column
        align="center"
        type="selection"
      />
      <el-table-column label="序号" width="60" align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="roleName"
        label="角色名"
      />
      <el-table-column
        align="center"
        prop="summary"
        label="介绍"
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
      width="30%"
      :before-close="handleClose"
    >
      <el-form :ref="role" :model="role" hide-required-asterisk :rules="rules">
        <el-form-item label="角色名称" prop="roleName">
          <el-input
            v-model="role.roleName"
            placeholder="角色名称必须大写以ROLE_开头"
            clearable
            type="text"
          />
        </el-form-item>
        <el-form-item label="角色描述" prop="summary">
          <el-input
            v-model="role.summary"
            placeholder="请输入描述内容"
            clearable
            type="text"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="submitForm(role)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getRoles, addRole, updateRole, deleteRole, deleteRoleList } from '@/api/role'
import { formatDate } from '@/utils/webUtils'

export default {
  data() {
    return {
      title: '', // 模态框标题 添加/修改
      isButton: false, // 批量删除按钮显示条件
      dialogVisible: false, // 模态框显示条件
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      keyword: '', // 搜索值
      roleIds: [], // 多选id
      role: { // from表单数据
        roleName: 'ROLE_',
        summary: ''
      },
      tableData: [], // table数据
      rules: { // 表单校验规则
        roleName: [
          { required: true, message: '角色名不能位空', trigger: 'blur' },
          { pattern: /^ROLE_.*/, message: '角色名称必须以ROLE_开头' }
        ],
        summary: [
          { required: true, message: '角色描述不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ]
      }
    }
  },
  created() {
    this.getRoleList()
  },
  methods: {
    /**
     * 批量删除
     */
    deleteRoleList() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('ids', this.roleIds)
        deleteRoleList(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.roleIds = []
          this.keyword = ''
          this.getRoleList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /**
     * 模态框添加修改表单提交
     */
    submitForm(role) {
      this.$refs[role].validate((valid) => {
        if (valid) {
          var params = new URLSearchParams(this.role)
          if (this.title === '添加角色') {
            addRole(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.role.roleName = 'ROLE_'
              this.role.summary = ''
              this.dialogVisible = false
              this.getRoleList()
            })
          } else {
            updateRole(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.role.roleName = 'ROLE_'
              this.role.summary = ''
              delete this.role['id']
              this.dialogVisible = false
              this.getRoleList()
            })
          }
        } else {
          return false
        }
      })
    },
    /**
     * 当前页发生变化 分页
     * val : 当前页
     */
    handleCurrentChange(val) {
      this.currentPage = val
      this.getRoleList()
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
    },
    /**
     * 获取角色数据
     */
    getRoleList() {
      const keyword = this.keyword
      const currentPage = this.currentPage
      const pageSize = this.pageSize
      getRoles(keyword, currentPage, pageSize).then(response => {
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
     * 模态框关闭方法
     */
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.role.roleName = 'ROLE_'
          this.role.summary = ''
          this.dialogVisible = false
          delete this.role['id']
          done()
        })
        .catch(_ => {})
    },
    /**
     * 删除单个数据
     */
    handleDelete(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteRole(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getRoleList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /**
     * 打开修改模态框
     */
    handleEdit(row) {
      this.title = '编辑角色' + row.roleName
      this.role.roleName = row.roleName
      this.role.summary = row.summary
      this.dialogVisible = true
      this.role['id'] = row.id
    },
    // 监控多选 如果有选择显示批量删除按钮
    monitorCheckbox(val) {
      this.roleIds = []
      console.log(val)
      if (val.length > 0) {
        this.isButton = true
      } else {
        this.isButton = false
      }
      for (const role of val) {
        this.roleIds.push(role.id)
      }
      console.log(this.roleIds)
    },
    // 添加打开提示框
    openDialog() {
      this.title = '添加角色'
      this.dialogVisible = true
    }
  }
}
</script>

<style>
  .authority-role{
      margin: 10px 15px 0;
  }

</style>
