/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryPerm (perm) {
  return axios.get(config.system.server_path + '/security/perm/query', {params: perm})
}

export function addPerm (perm) {
  return axios.post(config.system.server_path + '/security/perm/save', qs.stringify(perm))
}

export function updatePerm (perm) {
  return axios.put(config.system.server_path + '/security/perm/update', qs.stringify(perm))
}

export function deletePerm (id) {
  return axios.delete(config.system.server_path + '/security/perm/delete/' + id)
}
