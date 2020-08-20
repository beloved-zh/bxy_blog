<template>
  <div class="authority-user">
    <div class="head">
      <el-row type="flex" justify="start" :gutter="10">
        <el-col :span="5">
          <el-input
            v-model="keyword"
            placeholder="请输入用户名"
            clearable
            type="text"
          />
        </el-col>
        <el-col :span="3">
          <el-select v-model="role_id" placeholder="请选择角色" clearable>
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
        </el-col>
        <el-col :span="3">
          <el-select v-model="source" placeholder="账号类型" clearable>
            <el-option label="BXY" value="BXY" />
            <el-option label="QQ" value="QQ" />
            <el-option label="GITHUB" value="GITHUB" />
            <el-option label="GITEE" value="GITEE" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="isLock" placeholder="账号状态" clearable>
            <el-option label="正常" :value="true" />
            <el-option label="锁定" :value="false" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="commentStatus" placeholder="评论状态" clearable>
            <el-option label="正常" :value="true" />
            <el-option label="禁言" :value="false" />
          </el-select>
        </el-col>
        <el-col :span="3" align="center">
          <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getUser()">查找</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="addUser()">添加用户</el-button>
        </el-col>
      </el-row>
    </div>

    <el-table
      :data="tableData"
      row-key="id"
    >
      <el-table-column
        align="center"
        label="序号"
        type="index"
        width="50"
      />
      <el-table-column label="头像" align="center" width="80">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar" />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="username"
        label="用户名"
        width="80"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="source"
        label="账号来源"
        width="80"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="authorities[0].roleName"
        label="用户角色"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="email"
        label="邮箱"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="mobile"
        label="电话"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column label="账号状态" align="center" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isLock" type="danger">锁定</el-tag>
          <el-tag v-else>正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="评论状态" align="center" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.commentStatus" type="danger">禁言</el-tag>
          <el-tag v-else>正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="lastLoginIp"
        label="登录IP"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        label="注册时间"
        width="135"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="lastLoginTime"
        sortable
        :formatter="dateFormat"
        label="登录时间"
        width="135"
        show-overflow-tooltip
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="updateUser(scope.row)">编辑</el-button>
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

    <!-- 添加或修改模态框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form
        :ref="user"
        label-width="150px"
        :model="user"
        hide-required-asterisk
        :rules="rules"
      >
        <el-form-item
          label="头像"
          prop="avatar"
        >
          <template>
            <img v-if="user.avatar" :src="user.avatar" class="avatar" @click="show = !show">
            <div v-else class="avatar-uploader">
              <i class="el-icon-plus avatar-uploader-icon" @click="show = !show" />
            </div>
            <my-upload
              v-model="show"
              :width="300"
              :height="300"
              img-format="png"
              @crop-success="cropSuccess"
            />
          </template>
        </el-form-item>
        <el-form-item
          label="用户名"
          prop="userName"
        >
          <el-input
            v-model="user.userName"
            clearable
          />
        </el-form-item>
        <el-form-item
          v-if="title === '添加用户'"
          label="密码"
          prop="password"
        >
          <el-input
            v-model="user.password"
            show-password
            clearable
          />
        </el-form-item>
        <el-form-item
          label="角色"
          prop="role"
          :rules="rules"
        >
          <el-select v-model="user.role" placeholder="请选择角色" clearable>
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
        <el-form-item
          label="邮箱"
          prop="email"
        >
          <el-input
            v-model="user.email"
            clearable
          />
        </el-form-item>
        <el-form-item
          label="联系电话"
          prop="mobile"
        >
          <el-input
            v-model="user.mobile"
            clearable
          />
        </el-form-item>
        <el-form-item
          v-if="title !== '添加用户'"
          label="账号状态"
          prop="isLock"
        >
          <el-select v-model="user.isLock" placeholder="账号状态" clearable>
            <el-option label="正常" :value="true" />
            <el-option label="锁定" :value="false" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="title !== '添加用户'"
          label="评论状态"
          prop="commentStatus"
        >
          <el-select v-model="user.commentStatus" placeholder="评论状态" clearable>
            <el-option label="正常" :value="true" />
            <el-option label="锁定" :value="false" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="submitForm(user)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { findAll } from '@/api/role'
import { getUser, addUser, updateUser, deleteUser } from '@/api/user'
import { formatDate } from '@/utils/webUtils'
// 图片裁剪组件
// https://github.com/dai-siki/vue-image-crop-upload
import myUpload from 'vue-image-crop-upload'
export default {
  components: {
    'my-upload': myUpload
  },
  data() {
    return {
      show: false,
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      keyword: '',
      role_id: '',
      source: '',
      isLock: '',
      commentStatus: '',
      roles: [],
      tableData: [],
      dialogVisible: false,
      title: '',
      user: {
        userName: '',
        password: '',
        role: '',
        email: '',
        mobile: '',
        avatar: ''
      },
      rules: {
        avatar: [
          { required: true, message: '头像是必选项', trigger: 'blur' },
          { pattern: /^[\s\S]*.*[^\s][\s\S]*$/, message: '头像是必选项' }
        ],
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 0, max: 10, message: '最大10个字符' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, message: '最少6个字符' }
        ],
        role: [
          { required: true, message: '角色不能为空', trigger: 'blur' },
          { pattern: /^[\s\S]*.*[^\s][\s\S]*$/, message: '角色不能为空' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '输入正确的邮箱格式' }
        ],
        mobile: [
          { required: true, message: '电话不能为空', trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: '输入正确的联系方式' }
        ],
        isLock: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { pattern: /^[\s\S]*.*[^\s][\s\S]*$/, message: '不能为空' }
        ],
        commentStatus: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { pattern: /^[\s\S]*.*[^\s][\s\S]*$/, message: '不能为空' }
        ]
      }
    }
  },
  created() {
    this.findAllRoles()
    this.getUser()
  },
  methods: {
    handleDelete(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteUser(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getUser()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    submitForm(user) {
      this.$refs[user].validate((valid) => {
        if (valid) {
          var params = new URLSearchParams(this.user)
          if (this.title === '添加用户') {
            addUser(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.initUser()
              this.dialogVisible = false
              this.getUser()
            })
          } else {
            updateUser(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.initUser()
              this.dialogVisible = false
              this.getUser()
            })
          }
        } else {
          return false
        }
      })
    },
    updateUser(row) {
      this.title = '修改' + row.username
      this.user = {
        id: row.id,
        userName: row.username,
        role: row.authorities[0].id,
        email: row.email,
        source: row.source,
        mobile: row.mobile,
        avatar: row.avatar,
        isLock: !row.isLock,
        commentStatus: !row.commentStatus
      }
      this.dialogVisible = true
    },
    addUser() {
      this.title = '添加用户'
      this.dialogVisible = true
      this.initUser()
    },
    /**
     * 模态框关闭方法
     */
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.initUser()
          this.dialogVisible = false
          done()
        })
        .catch(_ => {})
    },
    // 裁剪图片回调
    cropSuccess(imgDataUrl, field) {
      console.log('-------- crop success --------')
      this.user.avatar = imgDataUrl
    },
    /**
     * 初始化user数据
     */
    initUser() {
      this.user = {
        userName: '',
        password: '',
        role: '',
        email: '',
        mobile: '',
        avatar: ''
      }
    },
    // 处理分页
    handleCurrentChange(val) {
      this.currentPage = val
      this.getUser()
    },
    // 获取角色
    findAllRoles() {
      findAll().then(response => {
        const { data } = response
        this.roles = data
      })
    },
    getUser() {
      const params = {}
      params.keyword = this.keyword
      params.role_id = this.role_id
      params.source = this.source
      params.isLock = this.isLock
      params.commentStatus = this.commentStatus
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getUser(params).then(response => {
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
  .authority-user{
      margin: 10px 15px 0;
  }
  .avatar-uploader{
    background-color: #fbfdff;
    border: 1px dashed #c0ccda;
    border-radius: 6px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    width: 100px;
    height: 100px;
    cursor: pointer;
    line-height: 100px;
    vertical-align: top;
  }
  .avatar-uploader:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
</style>
