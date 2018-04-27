/**
 * Created by lihui on 2018/3/31.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryModuleTree () {
  return axios.get(config.system.server_path + '/security/module/queryTree')
}

export function addModule (module) {
  return axios.post(config.system.server_path + '/security/module/save', qs.stringify(module))
}

export function updateModule (module) {
  return axios.put(config.system.server_path + '/security/module/update', qs.stringify(module))
}

export function deleteModule (id) {
  return axios.delete(config.system.server_path + '/security/module/delete/' + id)
}

export function queryModuleById (id) {
  return axios.get(config.system.server_path + '/security/module/queryById/' + id)
}

export function queryRoles (id) {
  return axios.get(config.system.server_path + '/security/module/queryRoles/' + id)
}

export function assignRoles (module, type, roles) {
  return axios.post(config.system.server_path + '/security/module/assignRoles', qs.stringify({'module': module, 'type': type, 'roles': roles}))
}

export function queryPerms (module) {
  return axios.get(config.system.server_path + '/security/module/queryPerms', {params: module})
}

export function removePerm (module, perm) {
  return axios.delete(config.system.server_path + '/security/module/removePerm', {params: {'module': module, 'perm': perm}})
}

export function addPerm (module, perm) {
  return axios.post(config.system.server_path + '/security/module/addPerm', qs.stringify({'module': module, 'perm': perm}))
}

export function queryUnSelectPerms (form) {
  return axios.get(config.system.server_path + '/security/module/queryUnSelectPerms', {params: form})
}
