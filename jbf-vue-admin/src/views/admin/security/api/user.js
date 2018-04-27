/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryUser (user) {
  return axios.get(config.system.server_path + '/security/user/query', {params: user})
}

export function queryRoles (username) {
  return axios.get(config.system.server_path + '/security/user/queryRoles/' + username)
}

export function assignRoles (user, type, roles) {
  return axios.post(config.system.server_path + '/security/user/assignRoles', qs.stringify({'user': user, 'type': type, 'roles': roles}))
}

export function addUser (user) {
  return axios.post(config.system.server_path + '/security/user/save', qs.stringify(user))
}

export function updateUser (user) {
  return axios.put(config.system.server_path + '/security/user/update', qs.stringify(user))
}

export function deleteUser (id) {
  return axios.delete(config.system.server_path + '/security/user/delete/' + id)
}
