/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryOption (option) {
  return axios.get(config.system.server_path + '/config/sysOption/query', {params: option})
}

export function addOption (option) {
  return axios.post(config.system.server_path + '/config/sysOption/save', qs.stringify(option))
}

export function updateOption (option) {
  return axios.put(config.system.server_path + '/config/sysOption/update', qs.stringify(option))
}

export function deleteOption (id) {
  return axios.delete(config.system.server_path + '/config/sysOption/delete/' + id)
}
