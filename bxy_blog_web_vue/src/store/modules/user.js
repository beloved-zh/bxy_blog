import { login, getInfo, logout } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  // 全局状态
  state: {
    token: getToken(),
    // 用户信息
    userInfo: '',
    isLogin: false,
  },
  // getters是对数据的包装，例如对数据进行拼接，或者过滤
  getters: {
    userInfo: state => state.userInfo,
    token: state => state.token,
    isLogin: state => state.isLogin,
    //类似于计算属性
    getUserAvatar(state) {
      if(state.isLogin) {
        if(state.userInfo.avatar) {
          return state.userInfo.avatar
        } else {
          return "http://bxyimage.beloved.ink/b54dbbee0d95430ea6fd99a6c894ab9b.png";
        }
      } else {
        return "http://bxyimage.beloved.ink/b54dbbee0d95430ea6fd99a6c894ab9b.png"
      }
    }
  },
  // 如果我们需要更改store中的状态，一定要通过mutations来进行操作
  mutations: {
    // 传入自定义参数
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo
    },
    SET_LOGIN_STATE: (state, isLogin) => {
      state.isLogin = isLogin
    }
  },

  // actions是我们定义的一些操作，正常情况下，我们很少会直接调用mutation方法来改变state
  actions: {
    // 用户登录请求
    login({ commit }, userInfo) {
      const { username, password } = userInfo
      return new Promise((resolve, reject) => {
        // 使用表单传递数据
        var params = new URLSearchParams()
        params.append('username', username.trim())
        params.append('password', password)
        // 发送了网络请求，进行了登录操作
        login(params).then(response => {
          const { data } = response
          // 登录成功保存token
          commit('SET_TOKEN', data);
          setToken(data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    getInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const { data } = response

          if (!data) {
            reject('验证失败，请重新登录')
          }

          commit('SET_LOGIN_STATE', true)
          commit('SET_USER_INFO', data)
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    logout({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          commit('SET_LOGIN_STATE', false)
          commit('SET_USER_INFO', '')

          removeToken()

          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
  }
}
export default user