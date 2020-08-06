import { getConfig } from '@/api/web'

const app = {
  // 全局状态
  state : {
    loading: false,
    webConfig: ''
  },
  // 更改store中的状态，一定要通过mutations来进行操作
  mutations : {
    SET_LOADING: (state, loading) => {
        state.loading = loading;
    },
    SET_WEBCONFIG: (state, webConfig) =>{
        state.webConfig = webConfig;
    }
  },
  // actions是义的一些操作，正常情况下，很少会直接调用mutation方法来改变state
  actions : {
    setLoading: ({commit}, v) => {
        commit('SET_LOADING', v);
    },
    getWebConfig: ({commit}) => {
        return new Promise((resolve, reject) => {
            getConfig().then(response => {
              const { data } = response
              commit('SET_WEBCONFIG', data)
              resolve(data)
            }).catch(error => {
              reject(error)
            })
          })
    }
    
  },
  // getters是对数据的包装，类似于计算属性
  getters : {
    loading: state => state.loading,
    webConfig: state => state.webConfig
  }
}

export default app