/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import config from '@/config'

export function queryVisitLog (visitLog) {
  return axios.get(config.system.server_path + '/statistics/visitLog/query', {params: visitLog})
}

export function deleteVisitLog (id) {
  return axios.delete(config.system.server_path + '/statistics/visitLog/delete/' + id)
}
