<template>
  <div class="authority-api">
    <div class="head">
      <el-row type="flex" :gutter="10" justify="start">
        <el-col :span="5" :xs="12">
          <el-input
            v-model="keyword"
            placeholder="请输入接口地址"
            clearable
            type="text"
          />
        </el-col>
        <el-col :span="3" :xs="6" class="switch">
          <el-select v-model="type" clearable placeholder="请选择类型">
            <el-option label="后台接口" :value="false" />
            <el-option label="前台接口" :value="true" />
          </el-select>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getApiList()">查找</el-button>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" @click="addApi()">添加接口</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table
      :data="tableData"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        align="center"
        label="序号"
        type="index"
      />
      <el-table-column
        align="center"
        prop="url"
        label="请求地址"
      />
      <el-table-column
        align="center"
        prop="method"
        label="请求方式"
      />
      <el-table-column
        align="center"
        prop="method"
        label="角色"
        show-overflow-tooltip
        width="200"
      >
        <template slot-scope="scope">
          <span v-for="(role,index) in scope.row.roles" :key="index">
            {{ role.roleName }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="summary"
        label="描述"
      />
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        show-overflow-tooltip
        label="创建时间"
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="updateApi(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteApi(scope.row)">删除</el-button>
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
    <!-- 添加或修改模态框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form :ref="api" :model="api" label-width="150px" :rules="rules">
        <el-form-item label="url地址" prop="url">
          <el-input v-model="api.url" clearable />
        </el-form-item>
        <el-form-item label="请求方式" prop="method">
          <el-select v-model="api.method" clearable placeholder="请选择请求方式">
            <el-option label="ALL" value="ALL" />
            <el-option label="GET" value="GET" />
            <el-option label="POST" value="POST" />
            <el-option label="PUT" value="PUT" />
            <el-option label="DELETE" value="DELETE" />
          </el-select>
        </el-form-item>
        <el-form-item label="父接口" prop="requestId">
          <el-select v-model="api.requestId" clearable filterable placeholder="请选择父接口">
            <el-tooltip
              v-for="(item,index) in fatherApi"
              :key="index"
              effect="dark"
              :content="item.summary"
              placement="right"
            >
              <el-option :label="item.url" :value="item.id" />
            </el-tooltip>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="roles">
          <el-select v-model="api.roles" clearable multiple placeholder="请选择角色">
            <el-tooltip
              v-for="(item,index) in roles"
              :key="index"
              effect="dark"
              :content="item.summary"
              placement="right"
            >
              <el-option
                :label="item.roleName"
                :value="item.id"
              />
            </el-tooltip>
          </el-select>
        </el-form-item>
        <el-form-item label="接口描述" prop="summary">
          <el-input v-model="api.summary" clearable />
        </el-form-item>
        <el-form-item label="菜单分类" prop="apiType">
          <el-tooltip
            effect="dark"
            :content="api.apiType ? '后台' : '前台'"
            placement="right"
          >
            <el-switch v-model="api.apiType" />
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="submitForm(api)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getApi, addApi, updateApi, deleteApi, getFatherApi } from '@/api/api'
import { findAll } from '@/api/role'
import { formatDate } from '@/utils/webUtils'
export default {
  data() {
    return {
      keyword: '',
      type: false,
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      tableData: [],
      roles: [],
      title: '',
      dialogVisible: false,
      fatherApi: [],
      api: {
        url: '',
        method: '',
        apiType: true,
        summary: '',
        requestId: '',
        roles: []
      },
      rules: { // 表单校验规则
        url: [
          { required: true, message: 'url地址不能为空', trigger: 'blur' },
          { pattern: /^\/.*/, message: `地址必须以\/开头` }
        ],
        method: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        roles: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        summary: [
          { required: true, message: '接口描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getApiList()
    this.findAllRoles()
  },
  methods: {
    getFatherApi() {
      getFatherApi().then(response => {
        this.fatherApi = response.data
      })
    },
    deleteApi(row) {
      if (row.children !== undefined) {
        if (row.children.length > 0) {
          this.$message.error('父组件不可以直接删除')
          return false
        }
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteApi(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getApiList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    submitForm(api) {
      this.$refs[api].validate((valid) => {
        // 校验规则
        if (valid) {
          // eslint-disable-next-line no-unused-vars
          var params = new URLSearchParams(this.api)
          if (this.title === '添加接口') {
            addApi(params).then(response => {
              const { data } = response
              this.initApi()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getApiList()
            })
          } else {
            updateApi(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.initApi()
              this.dialogVisible = false
              this.getApiList()
            })
          }
        } else {
          return false
        }
      })
    },
    updateApi(row) {
      this.title = '修改' + row.url
      this.api = {
        id: row.id,
        url: row.url,
        method: row.method,
        apiType: !row.apiType,
        summary: row.summary,
        requestId: row.requestId === undefined ? '' : row.requestId,
        roles: []
      }
      for (const role of row.roles) {
        this.api.roles.push(role.id)
      }
      this.getFatherApi()
      this.dialogVisible = true
    },
    addApi() {
      this.title = '添加接口'
      this.dialogVisible = true
      this.initApi()
      this.getFatherApi()
    },
    // 关闭模态框
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.initApi()
          this.dialogVisible = false
          done()
        })
        .catch(_ => {})
    },
    initApi() {
      this.api = {
        url: '',
        method: '',
        apiType: true,
        summary: '',
        requestId: '',
        roles: []
      }
    },
    // 获取角色
    findAllRoles() {
      findAll().then(response => {
        const { data } = response
        this.roles = data
      })
    },
    // 处理分页
    handleCurrentChange(val) {
      this.currentPage = val
      this.getApiList()
    },
    // 获取菜单数据
    getApiList() {
      if (this.type === '' || null) {
        this.$message({
          message: '类型必须选择，默认后台类型',
          type: 'warning'
        })
        this.type = false
      }
      const params = {}
      params.keyword = this.keyword
      params.type = this.type
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getApi(params).then(response => {
        const { data } = response
        console.log(data)
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
  .authority-api{
      margin: 10px 15px 0;
  }
</style>
