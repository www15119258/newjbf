/**
 * Created by lihui on 2018/3/31.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function login (user) {
  return axios.post(config.system.server_path + '/login.do', qs.stringify(user))
}

export function logout () {
  return axios.post(config.system.server_path + '/logout.do')
}
